package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.verifier.SampleHelper;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.tubs.isf.guido.verification.systems.cpachecker.generators.FeatureIdeTranslator;

public class CPACheckerSampleHelper extends SampleHelper{
	private static final int DEFAULT_MAX_STEPS = 1000;
	
	
	
	public List<SettingsObject> readSPLSamples(File samples) throws IOException {
		List<SettingsObject> result = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(samples))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				line = line.substring("prefix".length(), line.length() - "postfix".length());
				line = line.replaceFirst("\".*\"", "");
				String[] options = line.split(";");
				CPASettingsObject so = new CPASettingsObject();
				for (String option : options) {
					option = option.trim();
					String[] vals = option.split("::");
					if (vals.length != 2)
						continue;
					so.setParameter(vals[0], vals[1]);
				}
				so.setMaxEffort(100000);
				result.add(so);
			}
		}
		return result;
	}

	public List<SettingsObject> readFeatureIDESamples(File sampleFolder) throws IOException {
		List<SettingsObject> result = new ArrayList<>();
		List<File> configFiles = new ArrayList<>();
		if (!sampleFolder.isDirectory()) {
			throw new IOException("Folder is not a folder: " + sampleFolder.getAbsolutePath());
		}
		File[] files = sampleFolder.listFiles(file -> file.isDirectory());
		for (File configFolder : files) {
			File[] configFileArray = configFolder.listFiles(file -> file.getName().endsWith(".config"));
			configFiles.addAll(Arrays.asList(configFileArray));
		}
		for (File config : configFiles) {
			try (BufferedReader br = new BufferedReader(new FileReader(config))) {
				String line;
				CPASettingsObject so = new CPASettingsObject();
				while ((line = br.readLine()) != null) {
					String [] lineArray = FeatureIdeTranslator.decode(line);
					if (lineArray.length != 2)
						continue;
					so.setParameter(lineArray[0], lineArray[1]);
				}

				int number = Integer.parseInt(config.getName().split("\\.")[0]);
				so.setMaxEffort(DEFAULT_MAX_STEPS);
				so.setDebugNumber(number);
				result.add(so);
			}
		}
		return result;
	}

	public List<IDataBasisElement> outPutProofResults(List<IDataBasisElement> res) {

		res.forEach(result -> {
			CPACheckerDataBasisElement kres = (CPACheckerDataBasisElement) result;
			System.out.println(kres.getName());
			System.out.println(kres.getProof());
			System.out.println(kres.isClosed() ? kres.getSteps() : "notClosed!");
			System.out.println();
			System.out.println("Options:");
			kres.getOptions().forEach((key, value) -> System.out.println(key + ": " + value));
			System.out.println();
			System.out.println("Specification:");
			kres.getSpezification().forEach((key, value) -> System.out.println(key + ": " + value));
			System.out.println("____________________________________________");
		});
		return res;
	}

	public void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
	}

}


