package de.tubs.isf.guido.core.verifier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SettingsObject implements Cloneable, Serializable {

	private static final long serialVersionUID = -5974976146446740045L;

	
	protected double maxEffort;
	public double getMaxEffort() {
		return maxEffort;
	}
	

	public void setMaxEffort(double maxEffort) {
		this.maxEffort = maxEffort;
	}

	protected Map<String, String> settingsMap = new HashMap<>();
	protected ACodeContainer cc;

	public ACodeContainer getCc() {
		return null;
	}

	public void setCc(ACodeContainer cc) {
	}

	

	public void reinitialize() {
	}

	

	public Map<String, String> getSettingsMap() {
		return null;
	}

	public Optionable getParameterOption(OptionableContainer o) {
		return null;
	}

	public void setParameter(Optionable o) {
	}

	public void setParameter(String option, String value) {
	}

	public OptionableContainer[] getAllPossibleSettings() {
		return null;
	}



	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public String toString() {
		return null;
	}

	public SettingsObject clone() throws CloneNotSupportedException {
		return null;
	}
}