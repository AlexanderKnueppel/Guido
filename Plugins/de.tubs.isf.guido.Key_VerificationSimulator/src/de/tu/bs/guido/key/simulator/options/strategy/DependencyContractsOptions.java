package de.tu.bs.guido.key.simulator.options.strategy;

import de.tu.bs.guido.key.simulator.options.OptionableContainer;

public enum DependencyContractsOptions implements StrategyOptionable{
	ON("On"), OFF("Off");

	private final String value;

	DependencyContractsOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.DEPENDENCY_CONTRACTS;
	}
}
