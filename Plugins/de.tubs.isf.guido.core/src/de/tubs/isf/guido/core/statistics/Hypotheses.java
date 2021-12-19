package de.tubs.isf.guido.core.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import de.tubs.isf.guido.core.statistics.correction.BonferroniCorrection;
import de.tubs.isf.guido.core.statistics.correction.HolmCorrection;
import de.tubs.isf.guido.core.statistics.correction.IAlphaCorrection;
import de.tubs.isf.guido.core.statistics.correction.NoCorrection;
import de.tubs.isf.guido.core.statistics.correction.SidakCorrection;

public class Hypotheses {
	private List<Hypothesis> hypotheses;
	private boolean evaluated;

	public Hypotheses() {
		this.hypotheses = new ArrayList<>();
		this.evaluated = false;
	}

	public Hypotheses(File f) {
		this.evaluated = false;
		readFromFile(f, false);
	}

	public Hypotheses(File f, boolean accepted) {
		this.evaluated = accepted;
		readFromFile(f, accepted);
	}

	public boolean evaluated() {
		return evaluated;
	}

	public void addHypothesis(Hypothesis hyp) {
		hypotheses.add(hyp);

		if (hyp instanceof EvaluatedHypothesis) {
			evaluated = true;
		}
	}

	// reads hypotheses.txt and write it to class variables
	public void readFromFile(File f, boolean accepted) {
		hypotheses = new ArrayList<Hypothesis>();

		f.getParentFile().mkdirs();
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			Gson gson = new GsonBuilder().create();
			while ((line = br.readLine()) != null) {
				if (!evaluated())
					hypotheses.add(gson.fromJson(line, Hypothesis.class));
				else
					hypotheses.add(gson.fromJson(line, EvaluatedHypothesis.class));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToFile(File f) {
		try {
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			boolean first = true;
			for (Hypothesis h : getHypotheses()) {
				Writer writer = new FileWriter(f, !first);
				gson.toJson(h, writer);
				writer.append("\n");
				writer.flush();
				writer.close();
				first = false;
			}
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Hypothesis> getHypotheses() {
		return hypotheses;
	}

	public List<Hypothesis> getHypothesesSorted() {
		List<Hypothesis> result = getHypotheses(); // deep copy?
		if (evaluated) {
			result.stream().map(h -> (EvaluatedHypothesis) h).collect(Collectors.toList())
					.sort(Comparator.comparing(EvaluatedHypothesis::getPValue));
		}
		return result;
	}
	
	public void sort() {
		if (evaluated) {
			hypotheses = getHypothesesSorted();
		}
	}

	public static void main(String[] args) {
		// Hypotheses normal = new Hypotheses(new File("./testData/hypotheses.txt"));
		// normal.getHypotheses().stream().forEach(System.out::println);
		// normal.writeToFile(new File("./testData/normal.txt"));

		Hypotheses accepted = new Hypotheses(new File("./testData/accepted.txt"), true);
		accepted.getHypotheses().stream().forEach(System.out::println);

		double uncompensatedAlpha = 0.05;
		IAlphaCorrection corr = new NoCorrection();
		System.out.println("No correction: " + corr.apply(accepted, uncompensatedAlpha));

		corr = new BonferroniCorrection();
		System.out.println("Bonferroni: " + corr.apply(accepted, uncompensatedAlpha));

		corr = new HolmCorrection();
		System.out.println("Holm-Bonferroni: " + corr.apply(accepted, uncompensatedAlpha));

		corr = new SidakCorrection();
		System.out.println("Sidak: " + corr.apply(accepted, uncompensatedAlpha));
		// accepted.writeToFile(new File("./testData/accepted.txt"));
	}

//	public Map<String, Parameter> analyze(Map<String, Parameter> parameter){
//		for(Hypothesis hyp: hypotheses){
//		
//			if(!hyp.hasProperty() && hyp.isAboutProvability() && parameter.containsKey(hyp.getParameter())){
//				parameter.get(hyp.getParameter()).setProvabilityHyp(hyp.getOptionA(), hyp.getValueForOptionA());
//				parameter.get(hyp.getParameter()).setProvabilityHyp(hyp.getOptionB(), hyp.getValueForOptionB());
//			}
//			else if(!hyp.hasProperty() && hyp.isAboutVerificationEffort() && parameter.containsKey(hyp.getParameter())){
//				parameter.get(hyp.getParameter()).setVerificationEffortHyp(hyp.getOptionA(), hyp.getValueForOptionA());
//				parameter.get(hyp.getParameter()).setVerificationEffortHyp(hyp.getOptionB(), hyp.getValueForOptionB());
//			}
//			else { //hypothesis is about a certain property
//				parameter.get(hyp.getParameter()).setProperty(hyp.getOptionA(), hyp.getProperty(), hyp.getRequirement(), hyp.getValueForOptionA());
//				parameter.get(hyp.getParameter()).setProperty(hyp.getOptionB(), hyp.getProperty(), hyp.getRequirement(), hyp.getValueForOptionB());
//			}
//		}
//		return parameter;
//	}
}
