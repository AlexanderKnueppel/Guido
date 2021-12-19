package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;

import de.tubs.isf.guido.core.verifier.OptionableContainer;

 public enum AnalysisTraversalOrderOptions implements KonfigurationOptionable{
	RAND("rand"),DFS("dfs"),BFS("bfs"),RANDOM_PATH("random_path");
	private final String value;

	AnalysisTraversalOrderOptions(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	@Override
	public OptionableContainer getOptionableContainer() {
		return CPACheckerKonfigurationOptions.ANALYSIS_TRAVERSAL_ORDER;
	}
}
