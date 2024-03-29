package de.tubs.isf.guido.core.statistics;

import java.math.BigDecimal;
import java.util.List;

public class EvaluatedHypothesis extends Hypothesis {

	private static final long serialVersionUID = 1L;
	private final BigDecimal pValue;

	public EvaluatedHypothesis(String identifier, String parameter, String optionA, String optionB, String requirement, String dependency,
			List<String> properties, String pValue) {
		super(identifier, parameter, optionA, optionB, requirement, dependency, properties);
		this.pValue = new BigDecimal(pValue);
	}

	public EvaluatedHypothesis(Hypothesis hyp, String pValue) {
		this(hyp.identifier, hyp.parameter, hyp.optionA, hyp.optionB, hyp.requirement, hyp.dependency, hyp.properties, pValue);
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
		return "parameter: " + parameter + "; optionA: " + optionA + "; optionB: " + optionB + "; requirement: "
				+ requirement + "; dependency: " + dependency + properties + "; p-value: " + getPValue();
	}
}
