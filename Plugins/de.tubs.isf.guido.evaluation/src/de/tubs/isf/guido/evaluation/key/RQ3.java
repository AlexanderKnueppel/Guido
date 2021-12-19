package de.tubs.isf.guido.evaluation.key;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.tubs.isf.guido.core.analysis.AnalysisCombinator;
import de.tubs.isf.guido.core.analysis.IAnalyzer.LanguageConstruct;
import de.tubs.isf.guido.core.analysis.JMLContractAnalyzer;
import de.tubs.isf.guido.core.analysis.JavaSourceCodeAnalyzer;
import de.tubs.isf.guido.core.costs.CostNetwork;
import de.tubs.isf.guido.core.costs.Parameter;
import de.tubs.isf.guido.core.generator.DefaultConfigurationGenerator;
import de.tubs.isf.guido.core.generator.GuidoConfigurationGenerator;
import de.tubs.isf.guido.core.generator.GuidoConfigurationGenerator.Mechanism;
import de.tubs.isf.guido.core.generator.RandomConfigurationGenerator;
import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.statistics.Hypotheses;
import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;
import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.tubs.isf.guido.evaluation.util.Helper;
import de.tubs.isf.guido.verification.systems.key.KeyCodeContainer;
import de.tubs.isf.guido.verification.systems.key.KeyDataBasisElement;
import de.tubs.isf.guido.verification.systems.key.KeyFactory;

public class RQ3 {

	private static Map<String, Double> timeGuido = new HashMap<String, Double>();
	private static Map<String, Double> timeRandom = new HashMap<String, Double>();
	private static Map<String, Double> timeDefault = new HashMap<String, Double>();
	
	private static Map<String, SettingsObject> settingsGuido = new HashMap<String, SettingsObject>();
	private static Map<String, SettingsObject> settingsRandom = new HashMap<String, SettingsObject>();
	private static Map<String, SettingsObject> settingsDefault = new HashMap<String, SettingsObject>();

	private static List<String> notClosedGuido = new ArrayList<String>();
	private static List<String> notClosedDefault = new ArrayList<String>();
	private static List<String> notClosedRandom = new ArrayList<String>();
	
	private static String projectPath = "testData/keyproject";
	//private static String projectPath = "testData/keyproject";

	private static void guido(IJob job, Hypotheses accepted) {
		IProofControl verifier = AVerificationSystemFactory.getFactory().createProofControl();
		KeyCodeContainer cc = (KeyCodeContainer) job.getCodeContainer();

		File dir = new File(cc.getSource());
		if (dir.isFile())
			dir = new File(cc.getSource()).getParentFile();

		JavaSourceCodeAnalyzer jsca = new JavaSourceCodeAnalyzer(dir, cc.getClazz(), cc.getMethod(), cc.getParameter());
		jsca.setContractAnalyzer(new JMLContractAnalyzer());
		JMLContractAnalyzer ca = new JMLContractAnalyzer(jsca.getCommentString());
		List<LanguageConstruct> lcs = AnalysisCombinator.and(jsca, ca);

		List<Parameter> parameters = Helper.convertTo(AVerificationSystemFactory.getFactory().createSettingsObject());

		GuidoConfigurationGenerator generator = new GuidoConfigurationGenerator(
				new CostNetwork(accepted, lcs, parameters), Mechanism.NONE, 0.5);

		SettingsObject so = generator.computeNext();
		so.setMaxEffort(100000);
		job.setSo(so);

		KeyDataBasisElement result = (KeyDataBasisElement) verifier.performProof(job);
		
		settingsGuido.put(result.getName(), so);

		if (result.isProvable()) {
			timeGuido.put(result.getName(), (double) result.getEffort());
			System.out.println("Guido: " + result.getName() + " -> " + result.getEffort());
		} else {
			notClosedGuido.add(result.getName());
		}
	}

	private static void random(IJob job) {
		IProofControl verifier = AVerificationSystemFactory.getFactory().createProofControl();

		RandomConfigurationGenerator generator = new RandomConfigurationGenerator();

		SettingsObject so = generator.computeNext();
		so.setMaxEffort(100000);
		job.setSo(so);

		KeyDataBasisElement result = (KeyDataBasisElement) verifier.performProof(job);
		
		settingsRandom.put(result.getName(), so);

		if (result.isProvable()) {
			timeRandom.put(result.getName(), result.getEffort());
			System.out.println("Random: " + result.getName() + " -> " + result.getEffort());
		} else {
			notClosedRandom.add(result.getName());
		}
	}

	private static void def(IJob job) {
		IProofControl verifier = AVerificationSystemFactory.getFactory().createProofControl();

		DefaultConfigurationGenerator generator = new DefaultConfigurationGenerator();

		SettingsObject so = generator.computeNext();
		so.setMaxEffort(100000);
		job.setSo(so);

		KeyDataBasisElement result = (KeyDataBasisElement) verifier.performProof(job);
		
		settingsDefault.put(result.getName(), so);

		if (result.isProvable()) {
			timeDefault.put(result.getName(), (double) result.getEffort());
			System.out.println("Default: " + result.getName() + " -> " + result.getEffort());
		} else {
			notClosedDefault.add(result.getName());
		}
	}

	public static void main(String[] args) {
		String testArgsInput = projectPath + "/job2.xml";// args[0];

		List<IJob> jobs = null;

		AVerificationSystemFactory.setFactory(new KeyFactory());

		Hypotheses accepted = new Hypotheses(new File(projectPath + "/R/acceptedHypotheses.txt"), true);

		try {
			jobs = AVerificationSystemFactory.getFactory().createBatchXMLHelper()
					.generateJobFromXML(new File(testArgsInput));

			System.out.println("Jobs read... " + jobs.size() + " jobs to process!");

			String content = "name approach closed effort\n";
			int counter = 1;
			// for(int run = 0; run < 100; run++) {
			for (IJob job : jobs) {
				System.out.println("Start with " + ((KeyCodeContainer) job.getCodeContainer()).getMethod() + "["
						+ ((KeyCodeContainer) job.getCodeContainer()).getContractNumber() + "] of class "
						+ ((KeyCodeContainer) job.getCodeContainer()).getClazz() + "!");
				def(job);
				guido(job, accepted);
				random(job);
			}

			for (Entry<String, Double> elem : timeGuido.entrySet()) {
				content += (counter++) + " ";
				content += "\"" + elem.getKey() + "\" Guido true ";
				content += elem.getValue() + " ";
				content += "\n";
			}

			for (String name : notClosedGuido) {
				content += (counter++) + " " + name + " Guido false 0";
			}

			for (Entry<String, Double> elem : timeDefault.entrySet()) {
				content += (counter++) + " ";
				content += "\"" + elem.getKey() + "\" Default true ";
				content += elem.getValue() + " ";
				content += "\n";
			}

			for (String name : notClosedDefault) {
				content += (counter++) + " " + name + " Default false 0";
			}

			for (Entry<String, Double> elem : timeRandom.entrySet()) {
				content += (counter++) + " ";
				content += "\"" + elem.getKey() + "\" Random true ";
				content += elem.getValue() + " ";
				content += "\n";
			}

			for (String name : notClosedRandom) {
				content += (counter++) + " " + name + " Random false 0";
			}

			try {
				Files.write(Paths.get(projectPath + "/RQ2-data.txt"), content.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				String full = "name approach provable time settings";
				
				for(Entry<String, SettingsObject> entry : settingsGuido.entrySet()) {
					full += entry.getKey() + " Guido " + (notClosedGuido.contains(entry.getKey()) ? "false" : "true") + " ";
					full += (!notClosedGuido.contains(entry.getKey()) ? timeGuido.get(entry.getKey()) : "-") + " ";
					full += entry.getValue() + "\n";
				}
				
				Files.write(Paths.get(projectPath + "/RQ2-full-data.txt"), full.getBytes());
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

			String ggplot = "library(ggplot2)\r\n" + "library(latex2exp)\n";

			ggplot += "mydata = read.table(\""
					+ new File("testData/keyproject/RQ2-data.txt").getAbsolutePath().replace("\\", "/") + "\")\n";
			ggplot += "ggplot(data = mydata,aes(x = reorder(name, time, mean, na.rm=T), y = effort, fill = criterium)) + \r\n"
					+ "    stat_boxplot(geom = \"errorbar\") +\r\n" + "    geom_boxplot(outlier.colour = NA) +\r\n"
					+ "    xlab(\"Configuration\") + \r\n" + "    ylab(\"Verification Effort [Proof steps]\") + \r\n"
					+ "    labs(title = \"KeY\") + \r\n" + "    theme(plot.title = element_text(hjust = 0.5)) + \r\n"
					+ "    scale_y_continuous(trans = 'log10')\n";
			ggplot += "ggsave(\""
					+ new File("testData/keyproject/RQ3boxplot-data.pdf").getAbsolutePath().replace("\\", "/")
					+ "\")\n";

			try {
				Files.write(Paths.get("./testData/keyproject/RQ3boxplot.r"), ggplot.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		String content = "\t category\t version\t n\t\n";
//		content += "1" + "\t steps" + "\t Guido \t" + timeGuido.entrySet().stream().mapToDouble(e -> e.getValue()).sum() + "\n";
//		content += "2" + "\t steps" + "\t Default \t" + timeDefault.entrySet().stream().mapToDouble(e -> e.getValue()).sum() + "\n";
//		content += "3" + "\t steps" + "\t Random \t" + timeRandom.entrySet().stream().mapToDouble(e -> e.getValue()).sum() + "\n";
//		
//		try {
//			Files.write(Paths.get("./testData/cpachecker/RQ3.r"), content.getBytes());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		//timeGuido.entrySet().addAll(time)
//		Set<String> success = new HashSet<String>();
//		success.addAll(timeGuido.keySet());
//		success.addAll(timeDefault.keySet());
//		success.addAll(timeRandom.keySet());
//		
//		content = "name \t Guido \t Default \t Random\n";
//		for(String name : success) {
//			content += name + " \t ";
//			content += timeGuido.containsKey(name) ? timeGuido.get(name) : "-";
//			content += " \t ";
//			content += timeDefault.containsKey(name) ? timeDefault.get(name) : "-";
//			content += " \t ";
//			content += timeRandom.containsKey(name) ? timeRandom.get(name) : "-";
//			content += " \t ";
//			content += "\n";
//		}
//		
//		try {
//			Files.write(Paths.get("./testData/cpachecker/RQ3full-results.txt"), content.getBytes());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}
}
