package de.tu.bs.guido.key.simulator.options.strategy;

import de.tu.bs.guido.key.simulator.options.OptionableContainer;

public enum LoopTreatmentOptions implements StrategyOptionable{
	INVARIANT("Invariant"), EXPAND("Expand"), NONE("None"), LOOP_SCOPE_INVARIANT("Loop Scope Invariant");

	private final String value;

	LoopTreatmentOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.LOOP_TREATMENT;
	}
}
