package de.tu.bs.guido.verification.systems.key.options.strategies;

import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;

public enum ProofSplittingOptions implements StrategyOptionable{
	FREE("Free"), DELAYED("Delayed"), OFF("Off");

	private final String value;

	ProofSplittingOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.PROOF_SPLITTING;
	}
}
