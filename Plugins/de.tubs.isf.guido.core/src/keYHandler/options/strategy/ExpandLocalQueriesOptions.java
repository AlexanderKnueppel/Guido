package keYHandler.options.strategy;

import keYHandler.options.OptionableContainer;

public enum ExpandLocalQueriesOptions implements StrategyOptionable{
	ON("On"), OFF("Off");

	private final String value;

	ExpandLocalQueriesOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.EXPAND_LOCAL_QUERIES;
	}
}
