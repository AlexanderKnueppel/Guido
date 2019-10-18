package de.tu.bs.masterthesis.key.simulator.options.strategy;

import de.tu.bs.masterthesis.key.simulator.options.Optionable;
import de.tu.bs.masterthesis.key.simulator.options.OptionableContainer;

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
