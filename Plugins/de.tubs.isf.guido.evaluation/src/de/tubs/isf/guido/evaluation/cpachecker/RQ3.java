package de.tubs.isf.guido.evaluation.cpachecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.tubs.isf.guido.core.analysis.CSourceCodeAnalyzer;
import de.tubs.isf.guido.core.analysis.IAnalyzer.LanguageConstruct;
import de.tubs.isf.guido.core.costs.CostNetwork;
import de.tubs.isf.guido.core.costs.Parameter;
import de.tubs.isf.guido.core.databasis.DefaultDataBasisElement;
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
import de.tubs.isf.guido.verification.systems.cpachecker.CPACheckerCodeContainer;
import de.tubs.isf.guido.verification.systems.cpachecker.CPACheckerFactory;
import de.tubs.isf.guido.verification.systems.cpachecker.CPACheckerDataBasisElement;

public class RQ3 {
	
	private static Map<String, Double> timeGuido = new HashMap<String, Double>();
	private static Map<String, Double> timeRandom = new HashMap<String, Double>();
	private static Map<String, Double> timeDefault = new HashMap<String, Double>();
	
	private static void guido(IJob job, Hypotheses accepted) {
		IProofControl verifier = AVerificationSystemFactory.getFactory().createProofControl();
		CPACheckerCodeContainer cc = (CPACheckerCodeContainer) job.getCodeContainer();

		System.out.println(cc.getSource());
		System.out.println(cc.getBinary());
		File source = new File(cc.getSource());

		CSourceCodeAnalyzer csca = new CSourceCodeAnalyzer(source);
		List<LanguageConstruct> lcs = csca.analyze();
		
		List<Parameter> parameters = Helper.convertTo(AVerificationSystemFactory.getFactory().createSettingsObject());

		GuidoConfigurationGenerator generator = new GuidoConfigurationGenerator(
				new CostNetwork(accepted, lcs, parameters),  Mechanism.NONE, 0.5);
		
		SettingsObject so = generator.computeNext();
		so.setMaxEffort(10000);
		job.setSo(so);
		
		CPACheckerDataBasisElement result = (CPACheckerDataBasisElement)verifier.performProof(job);
		
		if(result.isProvable()) {
			timeGuido.put(result.getName(), (double)result.getEffort());
			System.out.println("Guido: " + result.getName() + " -> " + result.getEffort());
		}
	}
	
	private static void random(IJob job) {
		IProofControl verifier = AVerificationSystemFactory.getFactory().createProofControl();
		
		RandomConfigurationGenerator generator = new RandomConfigurationGenerator();
		
		SettingsObject so = generator.computeNext();
		so.setMaxEffort(10000);
		job.setSo(so);

		DefaultDataBasisElement result = (DefaultDataBasisElement)verifier.performProof(job);
		
		if(result.isProvable()) {
			timeRandom.put(result.getName(), result.getEffort());
			System.out.println("Random: " + result.getName() + " -> " + result.getEffort());
		}
	}
	
	private static void def(IJob job) {
		IProofControl verifier = AVerificationSystemFactory.getFactory().createProofControl();
		
		DefaultConfigurationGenerator generator = new DefaultConfigurationGenerator();
		
		SettingsObject so = generator.computeNext();
		so.setMaxEffort(10000);
		job.setSo(so);

		CPACheckerDataBasisElement result = (CPACheckerDataBasisElement)verifier.performProof(job);
		
		if(result.isProvable()) {
			timeDefault.put(result.getName(), (double)result.getEffort());
			System.out.println("Default: " + result.getName() + " -> " + result.getEffort());
		}
	}
	
	public static void main(String[] args) {
		String testArgsInput = "testData/cpachecker/cpa_job.xml";// args[0];
		
		List<IJob> jobs = null;

		AVerificationSystemFactory.setFactory(new CPACheckerFactory());

		Hypotheses accepted = new Hypotheses(new File("./testData/cpachecker/R/acceptedHypotheses.txt"), true);

		try {
			jobs = AVerificationSystemFactory.getFactory().createBatchXMLHelper()
					.generateJobFromXML(new File(testArgsInput));
			
			String content = "name approach time\n";
			int counter = 1;
			for(int run = 0; run < 100; run++) {
				for(IJob job : jobs) {
					def(job);
					guido(job, accepted);
					//random(job);
				}
				

				for(Entry<String, Double> elem : timeGuido.entrySet()) {
					content += (counter++) + " ";
					content += elem.getKey() + " Guido ";
					content += elem.getValue() + " ";
					content += "\n";
				}
				
				for(Entry<String, Double> elem : timeDefault.entrySet()) {
					content += (counter++) + " ";
					content += elem.getKey() + " Default ";
					content += elem.getValue() + " ";
					content += "\n";
				}
			}
			
			try {
				Files.write(Paths.get("./testData/cpachecker/RQ3boxplot-data.txt"), content.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String ggplot = "library(ggplot2)\r\n" + 
					"library(latex2exp)\n";
			
			ggplot += "mydata = read.table(\""+ new File("testData/cpachecker/RQ3boxplot-data.txt").getAbsolutePath().replace("\\", "/")+"\")\n";
			ggplot += "ggplot(data = mydata,aes(x = reorder(name, time, mean, na.rm=T), y = time, fill = approach)) + \r\n" + 
					"    stat_boxplot(geom = \"errorbar\") +\r\n" + 
					"    geom_boxplot(outlier.colour = NA) +\r\n" + 
					"    xlab(\"Benchmark\") + \r\n" + 
					"    ylab(\"time [ms]\") + \r\n" + 
					"    labs(title = \"CPAChecker\") + \r\n" + 
					"    theme(plot.title = element_text(hjust = 0.5)) + \r\n" + 
					"    scale_y_continuous(trans = 'log10')\n";
			ggplot += "ggsave(\""+ new File("testData/cpachecker/RQ3boxplot-data.pdf").getAbsolutePath().replace("\\", "/") +"\")\n";
			
			try {
				Files.write(Paths.get("./testData/cpachecker/RQ3boxplot.r"), ggplot.getBytes());
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
