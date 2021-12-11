package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;

import java.util.HashMap;
import java.util.Map;
import de.tubs.isf.guido.core.verifier.OptionableContainer;










public enum CPACheckerKonfigurationOptions implements OptionableContainer {
ANALYSIS_RESTARTAFTERUNKNOWN("analysis.restartAfterUnknown",AnalysisRestartAfterUnknownOptions.values(), AnalysisRestartAfterUnknownOptions.TRUE),
CFA_USECFACLONINGFORMULTITHREADEDPROGRAMS("cfa.useCFACloningForMultiThreadedPrograms",CfaUseCFACloningForMultiThreadedProgramsOptions.values(), CfaUseCFACloningForMultiThreadedProgramsOptions.TRUE),
CPA_SMG_EXPORTSMGWHEN("cpa.smg.exportSMGwhen",CpaSmgExportSMGwhenOptions.values(), CpaSmgExportSMGwhenOptions.INERESTING),
CPA_SMG_MEMORYERRORS("cpa.smg.memoryErrors",CpaSmgMemoryErrorsOptions.values(), CpaSmgMemoryErrorsOptions.TRUE),
CPA_SMG_ENABLEMALLOCFAIL("cpa.smg.enableMallocFail",CpaSmgEnableMallocFailOptions.values(), CpaSmgEnableMallocFailOptions.FALSE),
CPA_SMG_UNKNOWNONUNDEFINED("cpa.smg.unknownOnUndefined",CpaSmgUnknownOnUndefinedOptions.values(), CpaSmgUnknownOnUndefinedOptions.FALSE),
CPA_SMG_RUNTIMECHECK("cpa.smg.runtimeCheck",CpaSmgRuntimeCheckOptions.values(), CpaSmgRuntimeCheckOptions.FULL),
ANALYSIS_TRAVERSAL_ORDER("analysis.traversal.order",AnalysisTraversalOrderOptions.values(), AnalysisTraversalOrderOptions.RAND),
ANALYSIS_TRAVERSAL_USEPOSTORDER("analysis.traversal.usePostorder",AnalysisTraversalUsePostorderOptions.values(), AnalysisTraversalUsePostorderOptions.TRUE),
ANALYSIS_SUMMARYEDGES("analysis.summaryEdges",AnalysisSummaryEdgesOptions.values(), AnalysisSummaryEdgesOptions.TRUE),
CPA_CALLSTACK_SKIPRECURSION("cpa.callstack.skipRecursion",CpaCallstackSkipRecursionOptions.values(), CpaCallstackSkipRecursionOptions.TRUE),
CFA_SIMPLIFYCFA("cfa.simplifyCfa",CfaSimplifyCfaOptions.values(), CfaSimplifyCfaOptions.FALSE),
CFA_SIMPLIFYCONSTEXPRESSIONS("cfa.simplifyConstExpressions",CfaSimplifyConstExpressionsOptions.values(), CfaSimplifyConstExpressionsOptions.FALSE),
CFA_FINDLIVEVARIABLES("cfa.findLiveVariables",CfaFindLiveVariablesOptions.values(), CfaFindLiveVariablesOptions.FALSE),
CPA_PREDICATE_HANDLESTRINGLITERALINITIALIZERS("cpa.predicate.handleStringLiteralInitializers",CpaPredicateHandleStringLiteralInitializersOptions.values(), CpaPredicateHandleStringLiteralInitializersOptions.TRUE),
ANALYSIS_USEPARALLELANALYSES("analysis.useParallelAnalyses",AnalysisUseParallelAnalysesOptions.values(), AnalysisUseParallelAnalysesOptions.TRUE),
CPA_INVARIANTS_ABSTRACTIONSTATEFACTORY("cpa.invariants.abstractionStateFactory",CpaInvariantsAbstractionStateFactoryOptions.values(), CpaInvariantsAbstractionStateFactoryOptions.ENTERING_EDGES);

	private static final Map<String, CPACheckerKonfigurationOptions> optionMap = new HashMap<>();
	private static final Map<String, KonfigurationOptionable> outputName = new HashMap<>();
	private static final Map<CPACheckerKonfigurationOptions, Map<String, KonfigurationOptionable>> valueMap = new HashMap<>();
	
	static {
		for (CPACheckerKonfigurationOptions kto : CPACheckerKonfigurationOptions.values()) {
			optionMap.put(kto.getValue(), kto);
			Map<String, KonfigurationOptionable> values = new HashMap<>();
			valueMap.put(kto, values);
			for(KonfigurationOptionable so: kto.getOptions()){
				values.put(so.getValue(), so);
				outputName.put(so.getOutputString().replaceAll(";", ""), so);
				}
			}
		}
	public static boolean isOption(String value) {
		return optionMap.containsKey(value);
		}
	public static CPACheckerKonfigurationOptions getOption(String value){
		return optionMap.get(value);
		}
	public static KonfigurationOptionable getOption(String key, String value){
		return valueMap.get(optionMap.get(key)).get(value);
		}
	public static KonfigurationOptionable getOptionByName(String v){
		return outputName.get(v);
		}
	private final String value;
	private final KonfigurationOptionable[] options;
	private final KonfigurationOptionable defaultOption;	
	
	CPACheckerKonfigurationOptions(String value, KonfigurationOptionable[] options,
			KonfigurationOptionable defaultOptionable) {
		this.value = value;		this.options = options;
		this.defaultOption = defaultOptionable;
		}
	public KonfigurationOptionable[] getOptions() {
		return options;
		}
	public String getValue() {
		return value;
		}
	@Override
	public KonfigurationOptionable getDefault() {
		return defaultOption;
	}
}	


