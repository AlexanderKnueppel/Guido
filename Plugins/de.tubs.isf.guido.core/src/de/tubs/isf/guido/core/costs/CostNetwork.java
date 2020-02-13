package de.tubs.isf.guido.core.costs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import de.tubs.isf.guido.core.analysis.AnalysisCombinator;
import de.tubs.isf.guido.core.analysis.JMLContractAnalyzer;
import de.tubs.isf.guido.core.analysis.JavaSourceCodeAnalyzer;
import de.tubs.isf.guido.core.analysis.IAnalyzer.LanguageConstruct;
import de.tubs.isf.guido.core.costs.CostNetwork.WeightedDirectedEdge.Criterion;
import de.tubs.isf.guido.core.costs.metrics.DefaultMetric;
import de.tubs.isf.guido.core.costs.metrics.IMetric;
import de.tubs.isf.guido.core.generator.GuidoConfigurationGenerator;
import de.tubs.isf.guido.core.generator.GuidoConfigurationGenerator.Mechanism;
import de.tubs.isf.guido.core.statistics.EvaluatedHypothesis;
import de.tubs.isf.guido.core.statistics.Hypotheses;
import de.tubs.isf.guido.core.statistics.Hypothesis;

public class CostNetwork {
	private Hypotheses accepted;
	private List<LanguageConstruct> lcs;
	private List<Parameter> parameters;
	private IMetric metric;
	private Set<WeightedDirectedEdge> edges;

	public Hypotheses getAccepted() {
		return accepted;
	}

	public List<LanguageConstruct> getLcs() {
		return lcs;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public IMetric getMetric() {
		return metric;
	}

	public Set<WeightedDirectedEdge> getEdges() {
		return edges;
	}

	static class WeightedDirectedEdge {
		enum Criterion {
			PROVABILITY, EFFORT
		};

		private String parameter;
		private String from;
		private String to;
		private double weight = 0.0;
		private Criterion criterion;

		public String getFrom() {
			return from;
		}

		public String getTo() {
			return to;
		}

		public double getWeight() {
			return weight;
		}

		public Criterion getCriterion() {
			return criterion;
		}

		public WeightedDirectedEdge(String parameter, String from, String to, double weight, Criterion criterion) {
			super();
			this.parameter = parameter;
			this.from = from;
			this.to = to;
			this.weight = weight;
			this.criterion = criterion;
		}
	}

	public CostNetwork(Hypotheses accepted, List<LanguageConstruct> lcs, List<Parameter> parameters) {
		if (accepted == null || !accepted.evaluated()) {
			throw new IllegalArgumentException(
					"Hypotheses have to be valid and have to be tested (i.e., contain a p-value)!");
		}
		this.accepted = accepted;
		this.lcs = lcs;
		this.parameters = parameters;
		this.edges = new HashSet<WeightedDirectedEdge>();
		this.metric = new DefaultMetric();
	}

	public void setMetric(IMetric metric) {
		this.metric = metric;
	}

	public List<Parameter> computeScores() {
		createEdges();

		List<Parameter> result = new ArrayList<Parameter>();

		for (Parameter param : this.parameters) {

			Parameter scoredParameter = new Parameter(param.getParameter());

			for (Entry<String, Option> entry : param.getOptions().entrySet()) {
				double theta_p = edges.stream()
						.filter(edge -> edge.criterion == Criterion.PROVABILITY && edge.from.equals(entry.getKey())
								&& param.getParameter().equals(edge.parameter))
						.mapToDouble(edge -> edge.getWeight()).reduce(0.0, Double::sum);

				theta_p -= edges.stream()
						.filter(edge -> edge.criterion == Criterion.PROVABILITY && edge.to.equals(entry.getKey())
								&& param.getParameter().equals(edge.parameter))
						.mapToDouble(edge -> edge.getWeight()).reduce(0.0, Double::sum);

				double theta_ve = edges.stream()
						.filter(edge -> edge.criterion == Criterion.EFFORT && edge.from.equals(entry.getKey())
								&& param.getParameter().equals(edge.parameter))
						.mapToDouble(edge -> edge.getWeight()).reduce(0.0, Double::sum);
				theta_ve -= edges.stream()
						.filter(edge -> edge.criterion == Criterion.EFFORT && edge.to.equals(entry.getKey())
								&& param.getParameter().equals(edge.parameter))
						.mapToDouble(edge -> edge.getWeight()).reduce(0.0, Double::sum);

				//System.out.println(theta_p);
				//System.out.println(theta_ve);

				scoredParameter.setOption(new ScoredOption(entry.getKey(), theta_p, theta_ve));
			}

			result.add(scoredParameter);
		}

		return result;
	}

	private void createEdges() {
		for (Hypothesis hypothesis : accepted.getHypotheses()) {
			if (!hypothesis.hasProperty() || lcs.stream().map(l -> l.toString()).collect(Collectors.toList())
					.containsAll(hypothesis.getProperties())) {
				
				double p = ((EvaluatedHypothesis) hypothesis).getPValue();
				edges.add(new WeightedDirectedEdge(hypothesis.getParameter(), hypothesis.getBetterOption(),
						hypothesis.getWorseOption(), metric.compute(p),
						hypothesis.isAboutProvability() ? Criterion.PROVABILITY : Criterion.EFFORT));
			}
		}
	}

	public static void main(String[] args) {
		JavaSourceCodeAnalyzer jsca = new JavaSourceCodeAnalyzer(new File("./testData"),
				"Examples_from_Chapter_16.Sort", "max", new String[] { "int" });
		JMLContractAnalyzer ca = new JMLContractAnalyzer(jsca.getComment().toString());
		List<LanguageConstruct> lcs = AnalysisCombinator.and(jsca, ca);
		System.out.println(lcs); // Language Constructs

		Hypotheses accepted = new Hypotheses(new File("./testData/accepted.txt"), true);

		List<Parameter> result = new ArrayList<Parameter>();
		result.add(new Parameter("One Step Simplification", new Option("Enabled"), new Option("Disabled")));
		result.add(new Parameter("Proof splitting", new Option("Delayed"), new Option("Free"), new Option("Off")));
		result.add(new Parameter("Expand local queries", new Option("On"), new Option("Off")));
		result.add(new Parameter("Arithmetic treatment", new Option("Basic"), new Option("ModelSearch"),
				new Option("DefOps")));
		result.add(new Parameter("Quantifier treatment", new Option("Free"), new Option("No Splits with Progs"),
				new Option("Just another option")));
		result.add(new Parameter("ntegerSimplificationRules", new Option("integerSimplificationRules:full"),
				new Option("integerSimplificationRules:minimal")));
		result.add(new Parameter("Class axiom rule", new Option("Off"), new Option("Delayed"), new Option("Free")));
		result.add(new Parameter("Dummy", new Option("Yes"), new Option("No"), new Option("Whatever")));

		CostNetwork network = new CostNetwork(accepted, lcs, result);
		
		for (Parameter scoredParameter : network.computeScores()) {
			System.out.println("Parameter " + scoredParameter.getParameter() + ": ");
			for (Entry<String, Option> option : scoredParameter.getOptions().entrySet()) {
				double provability = ((ScoredOption) option.getValue()).getScoreForProvability();
				double effort = ((ScoredOption) option.getValue()).getScoreForVerificationEffort();
				System.out.println("    |-> " + option.getKey() + "[P: " + provability + ", VE: " + effort + "]");
			}
		}
		
		//new GuidoConfigurationGenerator(network, Mechanism.ADJUST).computeNext();
	}
}
