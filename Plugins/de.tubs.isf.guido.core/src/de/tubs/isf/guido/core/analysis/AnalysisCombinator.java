package de.tubs.isf.guido.core.analysis;

import java.util.ArrayList;
import java.util.List;

public class AnalysisCombinator {
	public static List<IAnalyzer.LanguageConstruct> and(IAnalyzer... analyzers) {
		List<IAnalyzer.LanguageConstruct> result = new ArrayList<IAnalyzer.LanguageConstruct>();

		for (IAnalyzer analyzer : analyzers) {
			result.addAll(analyzer.analyze());
		}

		return result;
	}
}
