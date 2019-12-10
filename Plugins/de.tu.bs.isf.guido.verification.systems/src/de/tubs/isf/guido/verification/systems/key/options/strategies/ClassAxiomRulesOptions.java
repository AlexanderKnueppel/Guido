package de.tubs.isf.guido.verification.systems.key.options.strategies;

import de.tubs.isf.core.verifier.OptionableContainer;

public enum ClassAxiomRulesOptions implements StrategyOptionable{
	FREE("Free"), DELAYED("Delayed"), OFF("Off");

	private final String value;

	ClassAxiomRulesOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.CLASS_AXIOM_RULE;
	}
}
