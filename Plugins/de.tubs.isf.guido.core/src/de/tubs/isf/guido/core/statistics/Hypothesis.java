package de.tubs.isf.guido.core.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hypothesis implements Serializable {
	private static final long serialVersionUID = 4015855254309825043L;
	
	public enum Dependency {
		LESS,GREATER,UNEQUAL,UNKNOWN;
	}

	protected final String identifier;
	protected final String parameter;
	protected final String optionA;
	protected final String optionB;
	protected final String requirement;
	protected final String dependency;
	protected final List<String> properties;

	public Hypothesis(String identifier, String parameter, String optionA, String optionB, String requirement,
			String dependency, List<String> properties) {
		super();
		this.identifier = identifier;
		this.parameter = parameter;
		this.optionA = optionA;
		this.optionB = optionB;
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

	public String getParameter() {
		return parameter;
	}

	public String getOptionA() {
		return optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getDependency() {
		return dependency;
	}
	
	public Dependency dependency() {
		if(dependency.equals("<="))
			return Dependency.LESS;
		else if(dependency.equals(">="))
			return Dependency.GREATER;
		else if(dependency.equals("<>"))
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

	public String getBetterOption() {
		if (dependency.equals("<=")) {
			return isAboutProvability() ? getOptionB() : getOptionA();
		} else if (dependency.equals(">=")) {
			return isAboutProvability() ? getOptionA() : getOptionB();
		} else if (dependency.equals("<>")) {
			return getOptionA();
		}
		return getOptionA();
	}

	public String getWorseOption() {
		if (dependency.equals("<=")) {
			return isAboutProvability() ? getOptionA() : getOptionB();
		} else if (dependency.equals(">=")) {
			return isAboutProvability() ? getOptionB() : getOptionA();
		} else if (dependency.equals("<>")) {
			return getOptionB();
		}
		return getOptionB();
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

		return optionA.equals(h.getOptionA()) && optionB.equals(h.getOptionB()) && dependency.equals(h.getDependency())
				&& identifier.equals(h.getIdentifier()) && requirement.equals(h.getRequirement())
				&& (!hasProperty() || (h.hasProperty() && properties.equals(h.getProperties())));
	}

	@Override
	public String toString() {
		String properties = hasProperty() ? "; properties: " + this.properties : "";
		return "parameter: " + parameter + "; optionA: " + optionA + "; optionB: " + optionB + "; requirement: "
				+ requirement + "; dependency: " + dependency + properties;
	}
}
