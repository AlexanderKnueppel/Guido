package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;

import de.tubs.isf.guido.core.verifier.OptionableContainer;

 public enum CpaInvariantsAbstractionStateFactoryOptions implements KonfigurationOptionable{
	ENTERING_EDGES("entering_edges"),ALWAYS("always"),NEVER("never");
	private final String value;

	CpaInvariantsAbstractionStateFactoryOptions(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	@Override
	public OptionableContainer getOptionableContainer() {
		return CPACheckerKonfigurationOptions.CPA_INVARIANTS_ABSTRACTIONSTATEFACTORY;
	}
}
