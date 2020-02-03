package de.tubs.isf.guido.core.verifier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class SettingsObject implements Cloneable, Serializable {

	private static final long serialVersionUID = -5974976146446740045L;

	protected double maxEffort;

	public abstract double getMaxEffort();

	public abstract void setMaxEffort(double maxEffort);

	protected Map<String, String> settingsMap = new HashMap<>();



	public abstract void reinitialize();

	public abstract Map<String, String> getSettingsMap();

	public abstract Optionable getParameterOption(OptionableContainer o);

	public abstract void setParameter(Optionable o);

	public abstract void setParameter(String option, String value);

	public abstract OptionableContainer[] getAllPossibleSettings();

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract String toString();

	public abstract SettingsObject clone() throws CloneNotSupportedException;
}