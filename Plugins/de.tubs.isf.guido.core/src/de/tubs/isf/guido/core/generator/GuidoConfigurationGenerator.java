package de.tubs.isf.guido.core.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tubs.isf.guido.core.costs.CostNetwork;
import de.tubs.isf.guido.core.costs.Option;
import de.tubs.isf.guido.core.costs.Parameter;
import de.tubs.isf.guido.core.costs.ScoredOption;
import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LPWizard;
import scpsolver.problems.LPWizardConstraint;

public class GuidoConfigurationGenerator implements IConfigurationGenerator {

	static public class Pair {
		public String attribute;
		public double value;

		public Pair(String attribute, double value) {
			this.attribute = attribute;
			this.value = value;
		}
	}

	public enum Mechanism {
		PENALTY(new Pair("k", 0.1)), ADJUST(new Pair("i", 0.1)), NEXT, NONE;

		private Map<String, Double> attributes = new HashMap<String, Double>();

		Mechanism(Pair... pairs) {
			for (Pair p : pairs) {
				attributes.put(p.attribute, p.value);
			}
		}

		public void set(String attribute, double value) {
			if (!attributes.containsKey(attribute))
				throw new IllegalArgumentException("Attribute " + attribute + " does not exist!");

			attributes.put(attribute, value);
		}

		public double get(String attribute) {
			if (!attributes.containsKey(attribute))
				throw new IllegalArgumentException("Attribute " + attribute + " does not exist!");

			return attributes.get(attribute);
		}
	}

	private List<SettingsObject> oldSettings = null; // history
	private CostNetwork network = null;
	private Mechanism mechanism = Mechanism.ADJUST;
	private double gamma = 0.5;
	private boolean hasNext = true;

	public GuidoConfigurationGenerator(CostNetwork network, Mechanism mechanism) {
		this.network = network;
		this.mechanism = mechanism;
		this.oldSettings = new ArrayList<SettingsObject>();
	}

	public GuidoConfigurationGenerator(CostNetwork network, Mechanism mechanism, double initialGamma) {
		this(network, mechanism);
		setGamma(initialGamma);
	}

	public void setGamma(double gamma) {
		if (gamma < 0.0 || gamma > 1.0)
			throw new IllegalArgumentException("Gamma has to be between 0 and 1!");

		this.gamma = gamma;
	}

	public int getIteration() {
		return oldSettings.size();
	}

	public List<SettingsObject> getHistory() {
		return Collections.unmodifiableList(oldSettings);
	}

	@Override
	public SettingsObject computeNext() {
		SettingsObject so = AVerificationSystemFactory.getFactory().createSettingsObject();

		int numberElements = 0;

		LPWizard lpw = new LPWizard();

		for (Parameter scoredParameter : network.computeScores()) {
			LPWizardConstraint c1 = lpw.addConstraint("c" + lpw.getLP().getConstraints().size(), 1, "<=");
			LPWizardConstraint c2 = lpw.addConstraint("c" + lpw.getLP().getConstraints().size(), 1, ">=");

			for (Entry<String, Option> option : scoredParameter.getOptions().entrySet()) {
				double provability = ((ScoredOption) option.getValue()).getScoreForProvability();
				double effort = ((ScoredOption) option.getValue()).getScoreForVerificationEffort();

				String id = encode(scoredParameter.getParameter(), option.getKey());

				double penalty = 0.0;
				if (mechanism == Mechanism.PENALTY) {
					penalty = oldSettings.stream().mapToInt(
							m -> m.getSettingsMap().get(scoredParameter.getParameter()).equals(option.getKey()) ? 1 : 0)
							.sum();
					penalty *= mechanism.get("k");
				}

				lpw.plus(id, provability * gamma + effort * (1.0 - gamma) + penalty);
				lpw.setBoolean(id);

				c1.plus(id);
				c2.plus(id);

				numberElements++;
			}

			c1.setAllVariablesBoolean();
			c2.setAllVariablesBoolean();
		}

		if (mechanism == Mechanism.NEXT) {
			int i = 0;
			double epsilon = 0.00001;

			for (SettingsObject old : oldSettings) {
				LPWizardConstraint nextConstraint = lpw.addConstraint("next" + i, old.getSettingsMap().size() - epsilon,
						"<=");

				for (Entry<String, String> e : old.getSettingsMap().entrySet()) {
					nextConstraint.plus(encode(e.getKey(), e.getValue()));
				}

				nextConstraint.setAllVariablesBoolean();
			}
		}

		lpw.setMinProblem(false);
		LinearProgramSolver solver = SolverFactory.newDefault();
		double[] sol = solver.solve(lpw.getLP());

		Map<String, Integer> indexMap = lpw.getLP().getIndexmap();
		List<String> elements = Arrays.asList(new String[numberElements]);

		indexMap.forEach((v, k) -> {
			elements.set(k, v);
		});

		for (int i = 0; i < sol.length; ++i) {
			if (sol[i] > 0) {
				so.setParameter(decode(elements.get(i))[0], decode(elements.get(i))[1]);
			}
		}

		oldSettings.add(so);

		if (mechanism == Mechanism.ADJUST) {
			if (gamma + mechanism.get("i") > 1.0) {
				setGamma(1.0);
				hasNext = false;
			} else {
				setGamma(gamma + mechanism.get("i"));
			}
		}

		return so;
	}

	private String encode(String parameter, String option) {
		return parameter + "___" + option;
	}

	private String[] decode(String str) {
		return new String[] { str.split("___")[0], str.split("___")[1] };
	}

	@Override
	public boolean hasNext() {
		if(mechanism == Mechanism.NONE)
			return false;
		return hasNext;
	}
}
