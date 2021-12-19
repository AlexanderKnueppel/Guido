package de.tubs.isf.guido.core.analysis;

import java.util.ArrayList;
import java.util.List;

import de.tubs.isf.guido.core.analysis.IAnalyzer.LanguageConstruct;

public class AnalysisCombinator {
	public static List<IAnalyzer.LanguageConstruct> and(IAnalyzer... analyzers) {
		List<IAnalyzer.LanguageConstruct> result = new ArrayList<IAnalyzer.LanguageConstruct>();

		for (IAnalyzer analyzer : analyzers) {
			List<LanguageConstruct> c = analyzer.analyze();
			if(c != null)
				result.addAll(c);
		}

		return result;
	}
}
