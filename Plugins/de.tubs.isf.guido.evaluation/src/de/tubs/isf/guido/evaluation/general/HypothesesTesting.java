package de.tubs.isf.guido.evaluation.general;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

import de.tubs.isf.guido.core.databasis.DataBasis;
import de.tubs.isf.guido.core.databasis.DefaultDataBasisElement;
import de.tubs.isf.guido.core.experiments.AExperiment;
import de.tubs.isf.guido.core.experiments.BatchExperimentResult;
import de.tubs.isf.guido.core.experiments.ExperimentGenerator;
import de.tubs.isf.guido.core.statistics.EvaluatedHypothesis;
import de.tubs.isf.guido.core.statistics.Hypotheses;
import de.tubs.isf.guido.core.statistics.Hypothesis;
import de.tubs.isf.guido.core.statistics.Hypothesis.Dependency;
import de.tubs.isf.guido.core.statistics.correction.IAlphaCorrection;
import de.tubs.isf.guido.core.statistics.correction.NoCorrection;
import de.tubs.isf.guido.core.statistics.tests.McNemar;
import de.tubs.isf.guido.core.statistics.tests.PairedWilcoxon;
import de.tubs.isf.guido.core.statistics.tests.SingleWilcoxon;
import de.tubs.isf.guido.verification.systems.cpachecker.CPACheckerDataBasisElement;
import de.tubs.isf.guido.verification.systems.key.KeyDataBasisElement;

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
		Path ctables = Paths.get(dir.getAbsolutePath() + File.separator + "/contingencyTables.r");

		try {
			String base = "base <- \"" + expDir.getAbsolutePath().replace("\\", "/") + "/\"" + System.lineSeparator();
			base += "pvector = c()" + System.lineSeparator();
			Files.write(rfile, base.getBytes());
			Files.write(ctables, base.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Hypothesis h : theList) {
			try {
				String content = System.lineSeparator() + "#" + h.getParameter().toUpperCase() + " (Hypothesis: "
						+ h.getIdentifier() + ")" + System.lineSeparator(); // header
				content += "filename <- paste(sep=\"\",base,\"" + result.getExperiment(h).getName() + ".txt\")"
						+ System.lineSeparator();
				content += "results <- read.table(filename, header = TRUE, sep = '|')" + System.lineSeparator();

				try (Stream<String> lines = Files.lines(Paths
						.get(expDir.getAbsolutePath() + File.separator + result.getExperiment(h).getName() + ".txt"))) {
					if (lines.count() <= 2) {
						content += "### EMPTY experiment (or too few elements)!!!! So, this hypothesis will be skipped"
								+ System.lineSeparator();

						if (h.equals(theList.get(0))) {
							content += "pvector = c(1.0)" + System.lineSeparator();
							content += "efvector = c(0.5)" + System.lineSeparator();
						} else {
							content += "pvector = c(pvector, 1.0)" + System.lineSeparator();
							content += "efvector = c(efvecotr, 0.5)" + System.lineSeparator();
						}

						Files.write(rfile, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

						continue;
					}
				}

				String tablecontent = content;

				if (h.isAboutProvability()) {
					content += "results$Closed...A <- ordered(results$Closed...A, levels = c(\"\", \"X\"))"
							+ System.lineSeparator();
					content += "results$Closed...B <- ordered(results$Closed...B, levels = c(\"\", \"X\"))"
							+ System.lineSeparator();
					content += "dataAC <- as.numeric(results$Closed...A)" + System.lineSeparator();
					content += "dataBC <- as.numeric(results$Closed...B)" + System.lineSeparator();
					content += "dataAC[is.na(dataAC)] <- 1" + System.lineSeparator();
					content += "dataBC[is.na(dataAC)] <- 1" + System.lineSeparator();
					content += "dataAC <- c(dataAC, 1)" + System.lineSeparator();
					content += "dataBC <- c(dataBC, 1)" + System.lineSeparator();
					content += "dataAC <- c(dataAC, 2)" + System.lineSeparator();
					content += "dataBC <- c(dataBC, 2)" + System.lineSeparator();
					content += "if(identical(dataAC,dataBC)) {" + System.lineSeparator();
					if (h.equals(theList.get(0))) {
						content += "pvector = c(1.0)" + System.lineSeparator();
						content += "efvector = c(0.5)" + System.lineSeparator();
					} else {
						content += "pvector = c(pvector, 1.0)" + System.lineSeparator();
						content += "efvector = c(efvecotr, 0.5)" + System.lineSeparator();
					}
					content += "} else {" + System.lineSeparator();
					content += "	testresult <- mcnemar.test(dataAC,dataBC)" + System.lineSeparator();
					content += "	testresult" + System.lineSeparator();
					if (h.equals(theList.get(0))) {
						content += "	pvector = c(testresult$p.value)" + System.lineSeparator();
						content += "    efvector = c(effsize::VD.A(dataAN, dataBN)$estimate)" + System.lineSeparator();
					} else {
						content += "	pvector = c(pvector, testresult$p.value)" + System.lineSeparator();
						content += "    efvector = c(efvector, effsize::VD.A(dataAN, dataBN)$estimate)" + System.lineSeparator();
					}
					content += "}" + System.lineSeparator();

				} else if (h.getOptionB() == null || h.getOptionB().isEmpty()) { // Single
					content += "dataFilter <- subset(results, Closed...A == Closed...B & Closed...A ==\"X\")"
							+ System.lineSeparator();
					content += "dataAC <- as.numeric(results$Closed...A)" + System.lineSeparator();
					content += "testresult <- wilcox.test(dataAC, mu = 0, alternative = \"two.sided\")"
							+ System.lineSeparator();
				} else { // Paired
					content += "dataFilter <- subset(results, Closed...A == Closed...B & Closed...A ==\"X\")"
							+ System.lineSeparator();
					content += "if(nrow(dataFilter) > 0)  {" + System.lineSeparator();
					content += "	dataAN <- as.numeric(dataFilter$Effort...A)" + System.lineSeparator();
					content += "	dataBN <- as.numeric(dataFilter$Effort...B)" + System.lineSeparator();

					String alternative = "less";

					if (h.dependency() == Dependency.GREATER) {
						alternative = "greater";
					} else if (h.dependency() == Dependency.UNEQUAL) {
						alternative = "two.sided";
					}

					content += "	testresult <- wilcox.test(dataAN, dataBN, paired=TRUE, alternative = \""
							+ alternative + "\")" + System.lineSeparator();
					content += "	testresult" + System.lineSeparator();
					if (h.equals(theList.get(0))) {
						content += "	pvector = c(testresult$p.value)" + System.lineSeparator();
						content += "    efvector = c(effsize::VD.A(dataAN, dataBN)$estimate)" + System.lineSeparator();
					} else {
						content += "	pvector = c(pvector, testresult$p.value)" + System.lineSeparator();
						content += "    efvector = c(efvector, effsize::VD.A(dataAN, dataBN)$estimate)" + System.lineSeparator();
					}

					content += "} else {" + System.lineSeparator();
					if (h.equals(theList.get(0))) {
						content += "pvector = c(1.0)" + System.lineSeparator();
						content += "efvector = c(0.5)" + System.lineSeparator();
					} else {
						content += "pvector = c(pvector, 1.0)" + System.lineSeparator();
						content += "efvector = c(efvecotr, 0.5)" + System.lineSeparator();
					}
					content += "}" + System.lineSeparator();
				}

				if (h.isAboutProvability()) {
					tablecontent += "tabl <- with(results , table(results$Closed...A, results$Closed...B))"
							+ System.lineSeparator();
					tablecontent += "addmargins(tabl)" + System.lineSeparator();
					Files.write(ctables, tablecontent.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				}

				Files.write(rfile, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			String pvalueFile = dir.getAbsolutePath() + File.separator + "pValues.txt";
			pvalueFile = pvalueFile.replace("\\", "/");

			Files.write(rfile,
					new String(
							System.lineSeparator() + "pvaluef <- file(\"" + pvalueFile + "\")" + System.lineSeparator())
									.getBytes(),
					StandardOpenOption.APPEND);
			Files.write(rfile,
					new String("write(pvector, file = pvaluef, sep = \"\\n\")" + System.lineSeparator()
							+ "unlink(pvaluef) # tidy up" + System.lineSeparator()).getBytes(),
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String effectSizeFile = dir.getAbsolutePath() + File.separator + "effectSizes.txt";
			effectSizeFile = effectSizeFile.replace("\\", "/");

			Files.write(rfile,
					new String(
							System.lineSeparator() + "effectsizesf <- file(\"" + effectSizeFile + "\")" + System.lineSeparator())
									.getBytes(),
					StandardOpenOption.APPEND);
			Files.write(rfile,
					new String("write(effvector, file = effectsizesf, sep = \"\\n\")" + System.lineSeparator()
							+ "unlink(effectsizesf) # tidy up" + System.lineSeparator()).getBytes(),
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
//		String RBaseDir = "./testData/cpachecker/R";
//		String pathToHypotheses = "./testData/cpachecker/allCPAcheckerHypotheses.txt";
//		String pathToDataBasis = "./testData/cpachecker/CPAcheckerDatabasis.txt";
		String RBaseDir = "./testData/keyproject/R";
		String pathToHypotheses = "./testData/keyproject/hypotheses_all.txt";
		String pathToDataBasis = "./testData/keyproject/keyresults.txt";		
//		String RBaseDir = "./testData/keyproject2/R";
//		String pathToHypotheses = "./testData/keyproject2/allKeyHypotheses.txt";
//		String pathToDataBasis = "./testData/keyproject2/keyresults.txt";

		File dir = new File(RBaseDir);

//		if(!dir.exists())
//			dir.mkdirs();

		// #### 1. Create experiments and R scripts...
		Hypotheses hypotheses = new Hypotheses(new File(pathToHypotheses));
		hypotheses.getHypotheses().stream().forEach(System.out::println);

		DataBasis<KeyDataBasisElement> db = DataBasis.readFromFile(new File(pathToDataBasis),
				KeyDataBasisElement.class);

		new HypothesesTesting().createRTestSuite(hypotheses, db, dir);

		// #### 2. Create and execute shell script...
		File shell = new File(dir.getAbsolutePath() + File.separator + "/execR.sh");

		try {
			String command = "#!/bin/bash" + System.lineSeparator() + "RScript " + dir.getAbsolutePath()
					+ File.separator + "experiments.r > " + dir.getAbsolutePath() + File.separator + "expOutput.txt";

//			String command = "#!/bin/bash" + System.lineSeparator() + "RScript " + dir.getAbsolutePath()
//			+ File.separator + "experiments.r";

			command = command.replace("\\", "/");

			Files.write(Paths.get(shell.getAbsolutePath()), command.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

		try {

			Process process;
			if (isWindows) {
				process = Runtime.getRuntime().exec("cmd.exe /c " + shell.getAbsolutePath());
			} else {
				process = Runtime.getRuntime().exec("sh -c " + shell.getAbsolutePath());
			}

			StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
			Executors.newSingleThreadExecutor().submit(streamGobbler);
			int exitCode = process.waitFor();
//
			process.destroyForcibly();
			process.destroy();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// #### 3. Evaluate hypotheses
		File pvalues = new File(RBaseDir + "/pValues.txt");
		Hypotheses evaluated = new Hypotheses();
		Hypotheses accepted = new Hypotheses();

		try (Stream<String> lines = Files.lines(Paths.get(pvalues.getAbsolutePath()), StandardCharsets.UTF_8)) {
			int i = 0;
			for (String line : (Iterable<String>) lines::iterator) {
				if (line.equals("NaN"))
					line = "1";
				evaluated.addHypothesis(new EvaluatedHypothesis(hypotheses.getHypotheses().get(i++), line));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IAlphaCorrection corr = new NoCorrection();

		double uncompensatedAlpha = 0.05;
		double alpha = corr.apply(evaluated, uncompensatedAlpha);

		for (Hypothesis h : evaluated.getHypotheses()) {
			EvaluatedHypothesis evalH = (EvaluatedHypothesis) h;
			if (evalH.getPValue() <= alpha) {
				accepted.addHypothesis(evalH);
			}
		}

		evaluated.writeToFile(new File(RBaseDir + "/evaluatedHypotheses.txt"));

		accepted.sort();
		accepted.writeToFile(new File(RBaseDir + "/acceptedHypotheses.txt"));
//
//		for (Hypothesis hs : accepted.getHypotheses()) {
//			if (hs.getProperties().size() > 0) {
//				if (accepted.getHypotheses().stream()
//						.filter(h -> h.getParameter().equals(hs.getParameter())
//								&& h.getOptionA().equals(hs.getOptionA()) && h.getOptionB().equals(hs.getOptionB())
//								&& h.getRequirement().equals(hs.getRequirement())
//								&& h.getDependency().equals(hs.getDependency()) && h.getProperties().isEmpty())
//						.count() == 0) {
//					System.out.println("Special: " + hs);
//				}
//			}
//		}
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

	private static class StreamGobbler implements Runnable {
		private InputStream inputStream;
		private Consumer<String> consumer;

		public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
			this.inputStream = inputStream;
			this.consumer = consumer;
		}

		@Override
		public void run() {
			new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
		}
	}
}
