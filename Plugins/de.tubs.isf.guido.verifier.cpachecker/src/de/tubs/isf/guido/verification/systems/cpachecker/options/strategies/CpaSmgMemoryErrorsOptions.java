package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;

import de.tubs.isf.guido.core.verifier.OptionableContainer;

 public enum CpaSmgMemoryErrorsOptions implements KonfigurationOptionable{
	TRUE("true"),FALSE("false");
	private final String value;

	CpaSmgMemoryErrorsOptions(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	@Override
	public OptionableContainer getOptionableContainer() {
		return CPACheckerKonfigurationOptions.CPA_SMG_MEMORYERRORS;
	}
}
