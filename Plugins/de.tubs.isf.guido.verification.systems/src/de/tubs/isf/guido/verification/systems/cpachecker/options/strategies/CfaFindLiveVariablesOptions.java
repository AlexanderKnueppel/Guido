package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;

import de.tubs.isf.guido.core.verifier.OptionableContainer;

 public enum CfaFindLiveVariablesOptions implements KonfigurationOptionable{
	FALSE("false"),TRUE("true");
	private final String value;

	CfaFindLiveVariablesOptions(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	@Override
	public OptionableContainer getOptionableContainer() {
		return CPACheckerKonfigurationOptions.CFA_FINDLIVEVARIABLES;
	}
}
