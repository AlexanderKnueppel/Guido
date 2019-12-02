package de.tu.bs.guido.verification.systems.key.options.strategies;

import de.tu.bs.guido.verification.systems.key.options.Optionable;
import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;

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
