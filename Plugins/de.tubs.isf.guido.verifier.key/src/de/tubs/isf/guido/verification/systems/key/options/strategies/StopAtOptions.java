package de.tubs.isf.guido.verification.systems.key.options.strategies;

import de.tubs.isf.guido.core.verifier.OptionableContainer;

public enum StopAtOptions implements StrategyOptionable{
	DEFAULT("Default"), UNCLOSABLE("Unclosable");

	private final String value;

	StopAtOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.STOP_AT;
	}
}
