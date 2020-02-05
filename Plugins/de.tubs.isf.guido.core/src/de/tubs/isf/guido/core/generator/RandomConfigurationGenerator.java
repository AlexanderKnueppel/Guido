package de.tubs.isf.guido.core.generator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;
import de.tubs.isf.guido.core.verifier.Optionable;
import de.tubs.isf.guido.core.verifier.OptionableContainer;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LPWizard;
import scpsolver.problems.LPWizardConstraint;

public class RandomConfigurationGenerator implements IConfigurationGenerator {

	private List<SettingsObject> oldSettings = null; // history

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

		for (OptionableContainer o : so.getAllPossibleSettings()) {

			LPWizardConstraint c1 = lpw.addConstraint("c" + lpw.getLP().getConstraints().size(), 1, "<=");
			LPWizardConstraint c2 = lpw.addConstraint("c" + lpw.getLP().getConstraints().size(), 1, ">=");

			for (Optionable option : o.getOptions()) {

				String id = encode(o.getValue(), option.getValue());

				Random r = new Random();
				double score1 = r.nextDouble() * 2.0 - 1.0;
				double score2 = r.nextDouble() * 2.0 - 1.0;

				lpw.plus(id, score1 + score2);
				lpw.setBoolean(id);

				c1.plus(id);
				c2.plus(id);

				numberElements++;
			}

			c1.setAllVariablesBoolean();
			c2.setAllVariablesBoolean();
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
		return true;
	}
}
