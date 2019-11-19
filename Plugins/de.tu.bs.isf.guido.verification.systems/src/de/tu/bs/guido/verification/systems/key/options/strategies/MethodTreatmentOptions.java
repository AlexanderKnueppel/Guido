package de.tu.bs.guido.verification.systems.key.options.strategies;

import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;

public enum MethodTreatmentOptions implements StrategyOptionable{
	CONTRACT("Contract"), EXPAND("Expand"), NONE("None");

	private final String value;

	MethodTreatmentOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.METHOD_TREATMENT;
	}
}
