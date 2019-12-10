package de.tubs.isf.guido.verification.systems.key.options.strategies;

import de.tubs.isf.guido.core.verifier.Optionable;
import de.tubs.isf.guido.core.verifier.OptionableContainer;

public enum ArithmeticTreatmentOptions implements StrategyOptionable{
	BASIC("Basic"), DEFOPS("DefOps"), MODEL_SEARCH("Model Search");

	private final String value;

	ArithmeticTreatmentOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.ARITHMETIC_TREATMENT;
	}
}
