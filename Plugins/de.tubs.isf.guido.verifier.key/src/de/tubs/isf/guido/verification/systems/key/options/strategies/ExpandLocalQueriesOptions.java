package de.tubs.isf.guido.verification.systems.key.options.strategies;

import de.tubs.isf.guido.core.verifier.OptionableContainer;

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
