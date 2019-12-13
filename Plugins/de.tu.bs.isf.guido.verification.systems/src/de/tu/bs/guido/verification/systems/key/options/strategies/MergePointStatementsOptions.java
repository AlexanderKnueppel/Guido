package de.tu.bs.guido.verification.systems.key.options.strategies;

import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;

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