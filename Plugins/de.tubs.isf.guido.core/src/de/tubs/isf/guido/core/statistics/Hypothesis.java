package de.tubs.isf.guido.core.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hypothesis implements Serializable {
	private static final long serialVersionUID = 4015855254309825043L;

	public enum Dependency {
		LESS, GREATER, UNEQUAL, UNKNOWN;
	}

	protected final String identifier;
	protected final List<Parameter> parametersA;

	public List<Parameter> getParametersA() {
		return parametersA;
	}
	public List<Parameter> getParameters() {
		List<Parameter> par = new ArrayList<Parameter>();
		par.addAll(parametersA);
		par.addAll(parametersB);
		return par ;
	}

	public List<Parameter> getParametersB() {
		return parametersB;
	}

	protected final List<Parameter> parametersB;

	protected final String requirement;
	protected final String dependency;
	protected final List<String> properties;

	public Hypothesis(String identifier, List<Parameter> parametersA, List<Parameter> parametersB, String requirement,
			String dependency, List<String> properties) {
		super();
		this.identifier = identifier;
		this.parametersA = parametersA;
		this.parametersB = parametersB;
		this.requirement = requirement;
		this.dependency = dependency;
		this.properties = properties;
	}

	public boolean isAboutProvability() {
		return requirement.equals("P") ? true : false;
	}

	public boolean isAboutVerificationEffort() {
		return requirement.equals("VE") ? true : false;
	}

	public String getRequirement() {
		return requirement;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getDependency() {
		return dependency;
	}

	public Dependency dependency() {
		if (dependency.equals("<="))
			return Dependency.LESS;
		else if (dependency.equals(">="))
			return Dependency.GREATER;
		else if (dependency.equals("<>"))
			return Dependency.UNEQUAL;
		else
			return Dependency.UNKNOWN;
	}

	public boolean hasProperty() {
		return properties != null && !properties.isEmpty();
	}

	public List<String> getProperties() {
		return properties;
	}

	public boolean isAboutProperty(String property) {
		return properties.contains(property);
	}

	public List<Parameter> getBetterOption() {
		if (dependency.equals("<=")) {
			return isAboutProvability() ? getParametersB() : getParametersB();
		} else if (dependency.equals(">=")) {
			return isAboutProvability() ? getParametersA() : getParametersA();
		} else if (dependency.equals("<>")) {
			return getParametersA();
		}
		return getParametersA();
	}

	public List<Parameter> getWorseOption() {
		if (dependency.equals("<=")) {
			return isAboutProvability() ? getParametersA() : getParametersA();
		} else if (dependency.equals(">=")) {
			return isAboutProvability() ? getParametersB() : getParametersB();
		} else if (dependency.equals("<>")) {
			return getParametersB();
		}
		return getParametersB();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Hypothesis)) {
			return false;
		}

		Hypothesis h = (Hypothesis) o;
		for (int i = 0; i < parametersA.size(); i++) {
			if (!parametersA.get(i).equals(h.getParametersA().get(i))) {
				return false;
			}
			
		}
		for (int i = 0; i < parametersB.size(); i++) {
			if (!parametersB.get(i).equals(h.getParametersB().get(i))) {
				return false;
			}
		}
		return  dependency.equals(h.getDependency())
				&& identifier.equals(h.getIdentifier()) && requirement.equals(h.getRequirement())
				&& (!hasProperty() || (h.hasProperty() && properties.equals(h.getProperties())));
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
		return ret + "; requirement: " + requirement + "; dependency: " + dependency + properties;
	}
}
