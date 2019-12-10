package de.tubs.isf.guido.core.verifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class SamplePrinter {

	
	abstract String getSettingText(SettingsObject so);

	abstract void appendToSB(StringBuilder sb, OptionableContainer oc,
			SettingsObject so);
}
