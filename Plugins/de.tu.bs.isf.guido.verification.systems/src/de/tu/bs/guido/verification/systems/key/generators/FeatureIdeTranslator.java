package de.tu.bs.guido.verification.systems.key.generators;

import java.util.ArrayList;
import java.util.List;

public class FeatureIdeTranslator {
	
	private static final List<Tuple> REPLACEMENTS = new ArrayList<>();
	
	static{
		REPLACEMENTS.add(new Tuple("_", "_0_"));
		REPLACEMENTS.add(new Tuple(":", "_1_"));
		REPLACEMENTS.add(new Tuple(" ", "_2_"));
	}

	public static String encode(String name){
		String intermediate = name;
		for (Tuple t : REPLACEMENTS) {
			intermediate = intermediate.replaceAll(t.getFrom(), t.getTo());
		}
		return intermediate;
	}
	public static String decode(String name){
		String intermediate = name;
		for (int i = REPLACEMENTS.size()-1; i >= 0 ; i--) {
			Tuple t = REPLACEMENTS.get(i);
			intermediate = intermediate.replaceAll(t.getTo(), t.getFrom());
		}
		return intermediate;
		
	}
	
	private static class Tuple{
		private final String from;
		private final String to;
		
		public Tuple(String from, String to) {
			super();
			this.from = from;
			this.to = to;
		}
		public String getFrom() {
			return from;
		}
		public String getTo() {
			return to;
		}
		
		
	}
}
