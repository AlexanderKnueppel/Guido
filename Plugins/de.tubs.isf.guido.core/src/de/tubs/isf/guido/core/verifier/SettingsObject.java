package de.tubs.isf.guido.core.verifier;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class SettingsObject implements Cloneable, Serializable {

	private static final long serialVersionUID = -5974976146446740045L;

	protected int debugNumber;
	protected int maxSteps;
	protected Map<String, String> settingsMap = new HashMap<>();

	public SettingsObject() {
		
	}
	
	public abstract void reinitialize();
	public int getMaxSteps() {
		return maxSteps;
	}

	public void setMaxSteps(int maxSteps) {
		this.maxSteps = maxSteps;
	}


	public abstract Map<String, String> getSettingsMap();

	public abstract Map<String, String> getTacletMap();
	
	public abstract Optionable getOption(OptionableContainer o);

	public abstract void setParameter(Optionable o);

	public abstract void setParameter(String option, String value);




	public abstract int getDebugNumber();

	public abstract void setDebugNumber(int debugNumber);


	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract String toString();
	

	public abstract SettingsObject clone() throws CloneNotSupportedException;
}