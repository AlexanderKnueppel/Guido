package de.tu.bs.guido.key.simulator.options.strategy;

import de.tu.bs.guido.key.simulator.options.OptionableContainer;

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
