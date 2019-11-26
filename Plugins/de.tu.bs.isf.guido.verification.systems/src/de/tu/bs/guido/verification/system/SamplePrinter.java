package de.tu.bs.guido.verification.system;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;
import de.tu.bs.guido.verification.systems.key.options.strategies.KeyStrategyOptions;
import de.tu.bs.guido.verification.systems.key.options.taclets.KeyTacletOptions;

public class SamplePrinter {

	private static final File CONFIG_TABLE = new File("configs.tex");
	private static final File SAMPLING = new File(
			"C:\\Users\\Carsten\\Microsoft OneDrive\\OneDrive\\Dokumente\\TU\\Masterarbeit\\SVN\\Samplings\\Strat\\StopAtDefault\\javaSemantics");

	private static final String VALUE = "Stop at";

	public static void main(String[] args) throws IOException {
		List<SettingsObject> res = SampleHelper.readFeatureIDESamples(SAMPLING);

		StringBuilder sb = new StringBuilder();
		sb.append("\\begin{longtabu}{ l | l | l }").append("\n");
		sb.append("\\parbox[t]{2mm}{\\rotatebox[origin=c]{90}{Konfiguration}}  & ")
			.append("Parameter & ")
			.append("Option \\\\")
			.append("\n");
		for (SettingsObject settingsObject : res) {
			sb.append("\\hline").append("\n");
			sb.append(getSettingText(settingsObject));
		}
		sb.append("\\end{longtabu}");
		
		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter(CONFIG_TABLE))) {
			bw.append(sb);
		}
	}

	private static String getSettingText(SettingsObject so) {
		StringBuilder sb = new StringBuilder();
//		sb.append("\\multirow{30}{*}{").append(so.getDebugNumber() + "}");
		sb.append(so.getDebugNumber());

		for (KeyStrategyOptions kso : KeyStrategyOptions.values()) {
			appendToSB(sb, kso, so);
		}
		for (KeyTacletOptions kso : KeyTacletOptions.values()) {
			appendToSB(sb, kso, so);
		}

		return sb.toString();
	}

	private static void appendToSB(StringBuilder sb, OptionableContainer oc,
			SettingsObject so) {
		if (!oc.getValue().equals(VALUE)) {
			sb.append(" & ");
			sb.append(oc.getValue()).append(" & ");
			sb.append(so.getOption(oc).getValue()).append("\\\\");
			sb.append("\n");
		}
	}
}
