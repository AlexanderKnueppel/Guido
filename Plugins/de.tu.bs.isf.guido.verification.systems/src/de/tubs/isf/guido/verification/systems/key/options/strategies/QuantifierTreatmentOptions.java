package de.tubs.isf.guido.verification.systems.key.options.strategies;

import de.tubs.isf.core.verifier.OptionableContainer;

public enum QuantifierTreatmentOptions implements StrategyOptionable{
	NONE("None"), NO_SPLITS("No Splits"), NO_SPLITS_WITH_PROGS("No Splits with Progs"), FREE("Free");

	private final String value;

	QuantifierTreatmentOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.QUANTIFIER_TREATMENT;
	}
}
