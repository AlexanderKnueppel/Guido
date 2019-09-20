package keYHandler.options.strategy;

import keYHandler.options.OptionableContainer;

public enum QueryTreatmentOptions implements StrategyOptionable{
	ON("On"), RESTRICTED("Restricted"), OFF("Off");

	private final String value;

	QueryTreatmentOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyStrategyOptions.QUERY_TREATMENT;
	}
}
