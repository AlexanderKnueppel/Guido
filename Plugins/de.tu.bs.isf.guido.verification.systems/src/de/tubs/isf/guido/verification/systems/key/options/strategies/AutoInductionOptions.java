package de.tubs.isf.guido.verification.systems.key.options.strategies;

import de.tubs.isf.guido.core.verifier.Optionable;
import de.tubs.isf.guido.core.verifier.OptionableContainer;

public enum AutoInductionOptions implements StrategyOptionable{
	ON("On"), RESTRICTED("Restricted"), OFF("Off");

	private final String value;

	AutoInductionOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.AUTO_INDUCTION;
	}
}
