package de.tubs.isf.guido.core.verifier;

public abstract class SamplePrinter {

	
	abstract String getSettingText(SettingsObject so);

	abstract void appendToSB(StringBuilder sb, OptionableContainer oc,
			SettingsObject so);
}
