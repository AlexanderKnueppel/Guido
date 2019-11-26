package de.tu.bs.guido.verification.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tu.bs.guido.verification.systems.key.generators.FeatureIdeTranslator;

public abstract class SampleHelper {
	
	private static final int DEFAULT_MAX_STEPS = 1000;

	public abstract void main(String[] args) throws FileNotFoundException,
			IOException;
	

	public static List<SettingsObject> readSPLSamples(File samples)
			throws IOException {
		List<SettingsObject> result = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(samples))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				line = line.substring("prefix".length(), line.length()
						- "postfix".length());
				line = line.replaceFirst("\".*\"", "");
				String[] options = line.split(";");
				SettingsObject so = new SettingsObject();
				for (String option : options) {
					option = option.trim();
					String[] vals = option.split("::");
					if (vals.length != 2)
						continue;
					so.setParameter(vals[0], vals[1]);
				}
				so.setMaxSteps(100000);
				result.add(so);
			}
		}
		return result;
	}

	public static List<SettingsObject> readFeatureIDESamples(File sampleFolder)
			throws IOException {
		List<SettingsObject> result = new ArrayList<>();
		List<File> configFiles = new ArrayList<>();
		if (!sampleFolder.isDirectory()) {
			throw new IOException("Folder is not a folder: "+sampleFolder.getAbsolutePath());
		}
		File[] files = sampleFolder.listFiles(file -> file.isDirectory());
		for (File configFolder : files) {
			File[] configFileArray = configFolder.listFiles(file -> file
					.getName().endsWith(".config"));
			configFiles.addAll(Arrays.asList(configFileArray));
		}
		for (File config : configFiles) {
			try (BufferedReader br = new BufferedReader(new FileReader(config))) {
				String line;
				SettingsObject so = new SettingsObject();
				while ((line = br.readLine()) != null) {
					line = FeatureIdeTranslator.decode(line);
					line = line.trim();
					String[] vals = line.split("::");
					if (vals.length != 2)
						continue;
					so.setParameter(vals[0], vals[1]);
				}

				int number = Integer.parseInt(config.getName().split("\\.")[0]);
				so.setMaxSteps(DEFAULT_MAX_STEPS);
				so.setDebugNumber(number);
				result.add(so);
			}
		}
		return result;
	}

	public static List<Result> outPutProofResults(List<Result> res) {
		res.forEach(result -> {
			System.out.println(result.getName());
			System.out.println(result.getProof());
			System.out.println(result.isClosed() ? result.getSteps()
					: "notClosed!");
			System.out.println();
			System.out.println("Options:");
			result.getOptions().forEach(
					(key, value) -> System.out.println(key + ": " + value));
			System.out.println();
			System.out.println("Taclets:");
			result.getTaclets().forEach(
					(key, value) -> System.out.println(key + ": " + value));
			System.out.println("____________________________________________");
		});
		return res;
	}

}
