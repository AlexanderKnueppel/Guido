package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;

import de.tubs.isf.guido.core.verifier.OptionableContainer;

 public enum CpaSmgRuntimeCheckOptions implements KonfigurationOptionable{
	FULL("full"),FORCED("forced"),NONE("none"),HALF("half");
	private final String value;

	CpaSmgRuntimeCheckOptions(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	@Override
	public OptionableContainer getOptionableContainer() {
		return CPACheckerKonfigurationOptions.CPA_SMG_RUNTIMECHECK;
	}
}
