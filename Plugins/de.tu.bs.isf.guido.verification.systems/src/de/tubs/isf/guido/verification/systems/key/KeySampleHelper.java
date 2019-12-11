package de.tubs.isf.guido.verification.systems.key;

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

import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.verifier.ASystemFactory;
import de.tubs.isf.guido.core.verifier.SampleHelper;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.tubs.isf.guido.verification.systems.key.generators.FeatureIdeTranslator;

public class KeySampleHelper extends SampleHelper {
	
	private static final int DEFAULT_MAX_STEPS = 1000;

	public void main(String[] args) throws FileNotFoundException,
			IOException{
		if (args.length != 4) {
			throw new IllegalArgumentException(
					"Pass four parameters: classpath, class, method and samples");
		}
		List<List<String>> results = new ArrayList<>();
		GuiBasedKeyControl kc = new GuiBasedKeyControl();
		File source = new File(args[0]);
		String clazz = args[1];
		String method = args[2];
		File samples = new File(args[3]);
		try (BufferedReader br = new BufferedReader(new FileReader(samples))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				line = line.substring("prefix".length(), line.length()
						- "postfix".length());
				line = line.replaceFirst("\".*\"", "");
				String[] options = line.split(";");
				KeySettingsObject so = new KeySettingsObject();
				so.setMaxSteps(100000);
				System.out.print("[");
				boolean first = true;
				for (String option : options) {
					option = option.trim();
					String[] vals = option.split("::");
					if (vals.length != 2)
						continue;
					if (first) {
						first = false;
					} else {
						System.out.print(", ");
					}
					System.out.print(option);
					so.setParameter(vals[0], vals[1]);
				}
				System.out.println("]");
				List<IDataBasisElement> res = outPutProofResults(kc.getResultForProof(
						source, null, clazz, method, so));
				int nums = 0;
				for (IDataBasisElement result : res) {
					if (!(results.size() > nums)) {
						results.add(new ArrayList<String>());
					}
					List<String> resultList = results.get(nums++);
					resultList.add(""
							+ (result.isProvable() ? result.getEffort() : -1));
				}
			}
		}

		int num = 0;
		for (List<String> list : results) {
			File f = new File(num + ".txt");
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
				for (String string : list) {
					bw.append(string).append("\n");
				}
			}
		}

	}

	public List<SettingsObject> readSPLSamples(File samples)
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
				KeySettingsObject so = new KeySettingsObject();
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

	public List<SettingsObject> readFeatureIDESamples(File sampleFolder)
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
				KeySettingsObject so = new KeySettingsObject();
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


	public List<IDataBasisElement> outPutProofResults(List<IDataBasisElement> res) {
		
		res.forEach(result -> {
			KeyDataBasis kres = (KeyDataBasis)result;
			System.out.println(kres.getName());
			System.out.println(kres.getProof());
			System.out.println(kres.isClosed() ? kres.getSteps()
					: "notClosed!");
			System.out.println();
			System.out.println("Options:");
			kres.getOptions().forEach(
					(key, value) -> System.out.println(key + ": " + value));
			System.out.println();
			System.out.println("Taclets:");
			kres.getTaclets().forEach(
					(key, value) -> System.out.println(key + ": " + value));
			System.out.println("____________________________________________");
		});
		return res;
	}

	


}
