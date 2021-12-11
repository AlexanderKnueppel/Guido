package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;

import de.tubs.isf.guido.core.verifier.OptionableContainer;

 public enum CpaSmgUnknownOnUndefinedOptions implements KonfigurationOptionable{
	FALSE("false"),TRUE("true");
	private final String value;

	CpaSmgUnknownOnUndefinedOptions(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	@Override
	public OptionableContainer getOptionableContainer() {
		return CPACheckerKonfigurationOptions.CPA_SMG_UNKNOWNONUNDEFINED;
	}
}
