package de.tu.bs.guido.key.simulator.options.strategy;

import de.tu.bs.guido.key.simulator.options.OptionableContainer;

public enum MergePointStatementsOptions  implements StrategyOptionable{
	MERGE("Merge"), SKIP("Skip"), NONE("None");

	private final String value;

	MergePointStatementsOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.MERGE_POINT_STATEMENTS;
	}

}
