package de.tu.bs.guido.verification.system;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;
import de.tu.bs.guido.verification.systems.key.options.strategies.KeyStrategyOptions;
import de.tu.bs.guido.verification.systems.key.options.taclets.KeyTacletOptions;

public abstract class SamplePrinter {

	
	abstract String getSettingText(SettingsObject so);

	abstract void appendToSB(StringBuilder sb, OptionableContainer oc,
			SettingsObject so);
}
