package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;

import de.tubs.isf.guido.core.verifier.OptionableContainer;

 public enum CpaSmgExportSMGwhenOptions implements KonfigurationOptionable{
	INERESTING("ineresting"),NEVER("never"),LEAVE("leave"),EVERY("every");
	private final String value;

	CpaSmgExportSMGwhenOptions(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	@Override
	public OptionableContainer getOptionableContainer() {
		return CPACheckerKonfigurationOptions.CPA_SMG_EXPORTSMGWHEN;
	}
}
