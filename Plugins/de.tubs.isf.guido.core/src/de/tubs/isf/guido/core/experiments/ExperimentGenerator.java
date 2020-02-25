package de.tubs.isf.guido.core.experiments;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import de.tubs.isf.guido.core.databasis.DataBasis;
import de.tubs.isf.guido.core.databasis.DefaultDataBasisElement;
import de.tubs.isf.guido.core.statistics.Hypotheses;
import de.tubs.isf.guido.core.statistics.Hypothesis;

public class ExperimentGenerator {

	private static class MatchingComperator implements Comparator<DefaultDataBasisElement> {
		private String excludeParameter = "";

		MatchingComperator(String excludeParameter) {
			this.excludeParameter = excludeParameter;
		}

		public static String representation(String excludeParameter, DefaultDataBasisElement ddbe) {
			String result = ddbe.getName();

			for (Entry<String, String> entry : ddbe.getOptionsSorted().entrySet()) {
				if (!excludeParameter.equals(entry.getKey()))
					result += (entry.getKey() + ":" + entry.getValue());
			}
			return result;
		}

		@Override
		public int compare(DefaultDataBasisElement ddbe1, DefaultDataBasisElement ddbe2) {
			return representation(excludeParameter, ddbe1).compareTo(representation(excludeParameter, ddbe2));
		}
	}

	public static BatchExperimentResult generateBatch(DataBasis<? extends DefaultDataBasisElement> databasis,
			List<Hypothesis> hypotheses) {
		Map<String, List<Hypothesis>> grouped = new HashMap<String, List<Hypothesis>>();

		for (Hypothesis hyp : hypotheses) {
			List<String> options = new ArrayList<String>();
			options.add(hyp.getOptionA());

			if (hyp.getOptionB() != null) {
				options.add(hyp.getOptionB());
			}

			options.addAll(hyp.getProperties() == null ? new ArrayList<String>() : hyp.getProperties());

			Collections.sort(options);
			String key = hyp.getParameter() + "." + options.stream().collect(Collectors.joining("."));

			if (!grouped.containsKey(key)) {
				grouped.put(key, new ArrayList<Hypothesis>());
			}
			grouped.get(key).add(hyp);
		}

		DataBasis<? extends DefaultDataBasisElement> db = databasis.clone();

		List<AExperiment> aelist = new ArrayList<AExperiment>();
		Map<AExperiment, List<Hypothesis>> mapping = new HashMap<AExperiment, List<Hypothesis>>();

		for (Entry<String, List<Hypothesis>> entry : grouped.entrySet()) {
			String name = entry.getValue().stream().map(h -> h.getIdentifier()).collect(Collectors.joining("__"));

			AExperiment ae = generate(name, db, entry.getValue().get(0), false);

			aelist.add(ae);
			mapping.put(ae, entry.getValue());
		}

		return new BatchExperimentResult(aelist, mapping);
	}

	public static AExperiment generate(String name, DataBasis<? extends DefaultDataBasisElement> databasis,
			Hypothesis hyp) {
		return generate(name, databasis, hyp, true);
	}

	public static AExperiment generate(String name, DataBasis<? extends DefaultDataBasisElement> databasis,
			Hypothesis hyp, boolean clone) {
		// Analyze hyp and only use the respective data that has to be considered (i.e.,
		// language constructs...)

		final AExperiment ae;

		if (hyp.getOptionA() == null || hyp.getOptionB() == null) {
			ae = new SingleExperiment(name);
		} else {
			ae = new PairExperiment(name);
		}

		DataBasis<? extends DefaultDataBasisElement> db = (clone) ? databasis.clone() : databasis;

//		Map<String, Integer> dups = new HashMap<String, Integer>();
//		for(DefaultDataBasisElement entry : db.getEntries()) {
//			String s = MatchingComperator.representation("", entry);
//			if(dups.containsKey(s)) {
//				dups.put(s, dups.get(s).intValue() + 1);
//			} else {
//				dups.put(s, 0);
//			}
//		}
//		
//		System.out.println("real duplicates: " + dups.entrySet().stream().mapToInt(e -> e.getValue()).sum());

		/*
		 * 1. single experiment
		 */
		int counter = 1;

		if (ae instanceof SingleExperiment) {
			for (DefaultDataBasisElement entryA : db.getEntries()) {
				if (!entryA.getLanguageConstructs().containsAll(hyp.getProperties()))
					continue;
				ae.addRow(counter++, entryA.getName(), entryA.isProvable(), entryA.getEffort());
			}

			return ae;
		} else {
			List<DefaultDataBasisElement> entriesA = new ArrayList<DefaultDataBasisElement>();
			List<DefaultDataBasisElement> entriesB = new ArrayList<DefaultDataBasisElement>();

			for (DefaultDataBasisElement entry : db.getEntries()) {
				if(entry.getLanguageConstructs() == null && hyp.getProperties() != null && !hyp.getProperties().isEmpty()) {
					continue;
				}
				
				if (entry.getLanguageConstructs() != null && hyp.getProperties() != null && !entry.getLanguageConstructs().containsAll(hyp.getProperties()))
					continue;
				
				try {
				if (entry.getOptions().get(hyp.getParameter()).equals(hyp.getOptionA()))
					entriesA.add(entry);
				else if (entry.getOptions().get(hyp.getParameter()).equals(hyp.getOptionB()))
					entriesB.add(entry);
				} catch(NullPointerException n) {
					System.out.println(n);
				}
				
			}

			entriesA.sort(new MatchingComperator(hyp.getParameter()));
			entriesB.sort(new MatchingComperator(hyp.getParameter()));

			int idx = 0;
			List<String> duplicates = new ArrayList<String>();

			for (DefaultDataBasisElement entryA : entriesA) {
				for (int i = idx; i < entriesB.size(); ++i) {

					int compare = new MatchingComperator(hyp.getParameter()).compare(entryA, entriesB.get(i));
					String match = MatchingComperator.representation(hyp.getParameter(), entryA);

					if (compare == 0) {
						if (duplicates.contains(match))
							break;

						ae.addRow(counter++, entryA.getName(), entryA.isProvable(), entriesB.get(i).isProvable(),
								entryA.getEffort(), entriesB.get(i).getEffort());
						idx++;

//						System.out.println("Found Match #" + counter);
//						System.out.println("	" + MatchingComperator.representation(hyp.getParameter(), entryA) + "_"
//								+ hyp.getParameter() + ":" + hyp.getOptionA());
//						System.out
//								.println("	" + MatchingComperator.representation(hyp.getParameter(), entriesB.get(i))
//										+ "_" + hyp.getParameter() + ":" + hyp.getOptionB());

						duplicates.add(match);
						break;
					} else if (compare < 0) {
						break;
					} else {
						// compare > 0
						idx = i;
					}
				}

			}

			return ae;
		}
	}

	public static void main(String[] args) {
		DataBasis<DefaultDataBasisElement> db = DataBasis.readFromFile(new File("./testData/zwischenergebnisse.txt"));
		// "Proof
		// splitting","optionA":"Delayed","optionB":"Free","requirement":"VE","dependency":"<="
		Hypothesis hyp = new Hypothesis("H1", "Proof splitting", "Delayed", "Free", "VE", "<=",
				new ArrayList<String>());
		Hypothesis hyp2 = new Hypothesis("H2", "Proof splitting", "Delayed", "Free", "VE", "<=",
				new ArrayList<String>());
		Hypothesis hyp3 = new Hypothesis("H3", "Proof splitting", "Off", "Free", "VE", "<=",
				new ArrayList<String>());
		
		Hypotheses container = new Hypotheses();
		container.addHypothesis(hyp);
		container.addHypothesis(hyp2);
		container.addHypothesis(hyp3);
		
		//container.writeToFile(new File("path/to/file.json"));
		
		List<Hypothesis> hypotheses = new ArrayList<Hypothesis>();
		hypotheses.add(hyp);
		hypotheses.add(hyp2);
		hypotheses.add(hyp3);
		
		System.out.println(db.size());

		AExperiment ae = ExperimentGenerator.generate("MyFirstExp", db, hyp);
		try {
			ae.writeToFile(new File("./testData/experiments/" + ae.getName() + ".txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		BatchExperimentResult result = ExperimentGenerator.generateBatch(db, hypotheses);
		result.getExperiments().stream().forEach(a -> {
			try {
				a.writeToFile(new File("./testData/experiments/" + a.getName() + ".txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

//		System.out.println(ae.getHeader().stream().collect(Collectors.joining("|")));
//		ae.getRows().stream().forEach(row -> {
//			System.out.println(row.stream().map(r -> r.toString()).collect(Collectors.joining("|")));
//		});
	}
}
