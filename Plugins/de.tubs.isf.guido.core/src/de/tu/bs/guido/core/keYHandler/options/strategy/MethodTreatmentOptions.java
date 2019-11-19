package keYHandler.options.strategy;

import keYHandler.options.OptionableContainer;

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
