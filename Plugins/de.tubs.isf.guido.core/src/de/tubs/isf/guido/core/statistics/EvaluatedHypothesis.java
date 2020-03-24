package de.tubs.isf.guido.core.statistics;

import java.math.BigDecimal;
import java.util.List;

public class EvaluatedHypothesis extends Hypothesis {

	private static final long serialVersionUID = 1L;
	private final BigDecimal pValue;

	public EvaluatedHypothesis(String identifier, List<Parameter> parametersA, List<Parameter> parametersB,
			String requirement, String dependency, List<String> properties, String pValue) {
		super(identifier, parametersA, parametersB, requirement, dependency, properties);
		this.pValue = new BigDecimal(pValue);
	}

	public EvaluatedHypothesis(Hypothesis hyp, String pValue) {
		this(hyp.identifier, hyp.parametersA, hyp.parametersA, hyp.requirement, hyp.dependency, hyp.properties, pValue);
	}

	public double getValueForOptionA() {
		if (dependency.equals("<=")) {
			return isAboutProvability() ? pValue.doubleValue() : 1 - pValue.doubleValue();
		} else if (dependency.equals(">=")) {
			return isAboutProvability() ? 1 - (pValue.doubleValue()) : pValue.doubleValue();
		} else if (dependency.equals("<>")) {
			return 1 - pValue.doubleValue();
		}
		return 0.0;
	}

	public double getValueForOptionB() {
		if (dependency.equals("<=")) {
			return isAboutProvability() ? 1 - (pValue.doubleValue()) : pValue.doubleValue();
		} else if (dependency.equals(">=")) {
			return isAboutProvability() ? (pValue.doubleValue()) : 1 - pValue.doubleValue();
		} else if (dependency.equals("<>")) {
			return 1 - pValue.doubleValue();
		}
		return 0.0;
	}

	public double getPValue() {
		return pValue.doubleValue();
	}

	@Override
	public String toString() {
		String properties = hasProperty() ? "; properties: " + this.properties : "";
		String ret = "parameterA:{";
		for (Parameter parameterA : parametersA) {
			ret += "parameter: " + parameterA.getParameter() + "; optionA: " + parameterA.getOption() + ";";
		}
		ret += "}parameterB:{";
		for (Parameter parameterB : parametersB) {
			ret += "parameter: " + parameterB.getParameter() + "; optionB: " + parameterB.getOption() + ";";
		}
		ret += "}";
		return ret + "; requirement: " + requirement + "; dependency: " + dependency + properties + "; p-value: "
				+ getPValue();

	}
}
