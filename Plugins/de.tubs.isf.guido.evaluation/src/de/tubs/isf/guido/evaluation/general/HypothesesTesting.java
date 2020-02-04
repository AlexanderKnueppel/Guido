package de.tubs.isf.guido.evaluation.general;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.tubs.isf.guido.core.databasis.DataBasis;
import de.tubs.isf.guido.core.databasis.DefaultDataBasisElement;
import de.tubs.isf.guido.core.experiments.AExperiment;
import de.tubs.isf.guido.core.experiments.BatchExperimentResult;
import de.tubs.isf.guido.core.experiments.ExperimentGenerator;
import de.tubs.isf.guido.core.statistics.EvaluatedHypothesis;
import de.tubs.isf.guido.core.statistics.Hypotheses;
import de.tubs.isf.guido.core.statistics.Hypothesis;
import de.tubs.isf.guido.core.statistics.tests.McNemar;
import de.tubs.isf.guido.core.statistics.tests.PairedWilcoxon;
import de.tubs.isf.guido.core.statistics.tests.SingleWilcoxon;

public class HypothesesTesting {

	public <T extends DefaultDataBasisElement> void createRTestSuite(Hypotheses hypotheses, DataBasis<T> db, File dir) {
		if (!dir.exists()) {
			dir.mkdirs();
		}

		if (!dir.isDirectory())
			throw new IllegalArgumentException("Path is not a directory! [" + dir.getPath() + "]");

		File expDir = new File(dir.getAbsolutePath() + File.separator + "experiments");

		if (expDir.exists())
			deleteDirectory(expDir);

		expDir.mkdir();

		List<Hypothesis> theList = new ArrayList<Hypothesis>();

		int i = 0;
		for (Hypothesis h : hypotheses.getHypotheses()) {
			theList.add(new Hypothesis("H" + (i++), h.getParameter(), h.getOptionA(), h.getOptionB(),
					h.getRequirement(), h.getDependency(), h.getProperties()));
		}

		BatchExperimentResult result = ExperimentGenerator.generateBatch(db, theList);
		result.getExperiments().stream().forEach(a -> {
			try {
				a.writeToFile(new File(expDir.getAbsolutePath() + File.separator + a.getName() + ".txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		theList.stream()
				.forEach(h -> System.out.println(h.getIdentifier() + " -> " + result.getExperiment(h).getName()));

		Path rfile = Paths.get(dir.getAbsolutePath() + File.separator + "/experiments.r");
		try {
			Files.write(rfile,
					new String("base <- \"" + expDir.getAbsolutePath().replace("\\", "/") + "/\"" + System.lineSeparator()).getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Hypothesis h : theList) {
			try {
				String content = System.lineSeparator()+"#"+h.getParameter().toUpperCase()+System.lineSeparator(); //header
				content += "filename <- paste(sep=\"\",base,\""+result.getExperiment(h).getName()+".txt\")"+System.lineSeparator();
				content += "results <- read.table(filename, header = TRUE, sep = '|')"+System.lineSeparator();
				
				if(h.isAboutProvability()) {
					content += "dataAC <- as.numeric(results$Closed...A)"+System.lineSeparator();
					content += "dataBC <- as.numeric(results$Closed...B)"+System.lineSeparator();
					content += "mcnemar.test(dataAC,dataBC)"+System.lineSeparator();
				} else if(h.getOptionB() == null || h.getOptionB().isEmpty()) { // Single
					content += "dataFilter <- subset(results, Closed...A == Closed...B & Closed...A ==\"X\")"+System.lineSeparator();
					content += "dataAC <- as.numeric(results$Closed...A)"+System.lineSeparator();
					content += "wilcox.test(dataAC, mu = 0, alternative = \"two.sided\")"+System.lineSeparator();
				} else { //Paired
					content += "dataFilter <- subset(results, Closed...A == Closed...B & Closed...A ==\"X\")"+System.lineSeparator();
					content += "dataAN <- as.numeric(dataFilter$Effort...A)"+System.lineSeparator();
					content += "dataBN <- as.numeric(dataFilter$Effort...B)"+System.lineSeparator();
					content += "wilcox.test(dataAN, dataBN, paired=TRUE)"+System.lineSeparator();
				}

				Files.write(rfile, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public <T extends DefaultDataBasisElement> Hypotheses doTest(Hypotheses hypotheses, DataBasis<T> db) {

		Hypotheses result = new Hypotheses();

		if (hypotheses.evaluated()) {
			// TODO: Information? Warning?
		}

		int counter = 1;
		for (Hypothesis hyp : hypotheses.getHypotheses()) {
			System.out.print("Experiment " + counter + "/" + hypotheses.getHypotheses().size() + "...");

			AExperiment ae = ExperimentGenerator.generate("Exp" + (counter++), db, hyp);

			System.out.print("experiement created!");

			Optional<Double> p = null;

			if (hyp.isAboutVerificationEffort()) {
				if (!hyp.getWorseOption().isEmpty()) {
					p = (new PairedWilcoxon().computeP(ae));
				} else {
					p = (new SingleWilcoxon().computeP(ae));
				}
			} else {
				p = (new McNemar().computeP(ae));
			}

			result.addHypothesis(new EvaluatedHypothesis(hyp, p.isPresent() ? p.get().toString() : "1.0"));
		}

		return hypotheses;
	}

	public static void main(String[] args) {
		Hypotheses hypotheses = new Hypotheses(new File("./testData/hypotheses.txt"));
		hypotheses.getHypotheses().stream().forEach(System.out::println);

		DataBasis<DefaultDataBasisElement> db = DataBasis.readFromFile(new File("./testData/zwischenergebnisse.txt"));

		new HypothesesTesting().createRTestSuite(hypotheses, db, new File("./testData/R"));
	}

//	public static void main(String[] args) {
//		Hypotheses hypotheses = new Hypotheses(new File("./testData/hypotheses.txt"));
//		hypotheses.getHypotheses().stream().forEach(System.out::println);
//		
//		DataBasis<DefaultDataBasisElement> db = DataBasis.readFromFile(new File("./testData/zwischenergebnisse.txt"));
//		
//		Hypotheses evaluated = (new HypothesesTesting().doTest(hypotheses, db));
//		
//		double uncompensatedAlpha = 0.05;
//		IAlphaCorrection corr = new NoCorrection();
//		System.out.println("No correction: " + corr.apply(evaluated, uncompensatedAlpha));
//		
//		corr = new BonferroniCorrection();
//		System.out.println("Bonferroni: " + corr.apply(evaluated, uncompensatedAlpha));
//		
//		double alpha = corr.apply(evaluated, uncompensatedAlpha);
//		
//		System.out.println("All evaluated: ");
//		evaluated.getHypotheses().stream().forEach(System.out::println);
//		
//		System.out.println("Accepted: ");
//		evaluated.getHypotheses().stream().filter(h -> ((EvaluatedHypothesis)h).getPValue() <= alpha).forEach(System.out::println);
//	}

	private static boolean deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (directory.delete());
	}
}
