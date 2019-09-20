package de.tu.bs.guido.key.network.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import automaticProof.GuidanceSystemResult;
import automaticProof.Prover;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import keYHandler.options.SettingsObject;

public class SampleHelper {
	
	public static void main(String[] args) throws FileNotFoundException,
			IOException, ProofInputException, ProblemLoaderException {
		if (args.length != 4) {
			throw new IllegalArgumentException(
					"Pass four parameters: classpath, class, method and samples");
		}
		List<List<String>> results = new ArrayList<>();
		Prover kc = new Prover();
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
				SettingsObject so = new SettingsObject();
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
				GuidanceSystemResult result = outPutProofResults(kc.getResultForProof(
						source, null, clazz, method));
				int nums = 0;
					if (!(results.size() > nums)) {
						results.add(new ArrayList<String>());
					}
					List<String> resultList = results.get(nums++);
					resultList.add(""
							+ (result.isClosed() ? result.getSteps() : -1));
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

	public static GuidanceSystemResult outPutProofResults(GuidanceSystemResult result) {
		
			System.out.println(result.getName());
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
		
		return result;
	}

}
