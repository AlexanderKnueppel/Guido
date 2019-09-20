package keYHandler.options.strategy;

import keYHandler.options.OptionableContainer;

public enum OneStepSimplificationOptions implements StrategyOptionable{
	ENABLED("Enabled"), DISABLED("Disabled");

	private final String value;

	OneStepSimplificationOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.ONE_STEP_SIMPLIFICATION;
	}
}

