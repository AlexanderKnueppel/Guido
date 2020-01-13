package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;

import java.util.HashMap;
import java.util.Map;
import de.tubs.isf.guido.core.verifier.OptionableContainer;

public enum CPACheckerKonfigurationOptions implements OptionableContainer {
ANALYSIS_RESTARTAFTERUNKNOWN("analysis.restartAfterUnknown ",AnalysisRestartAfterUnknownOptions.values(), AnalysisRestartAfterUnknownOptions.TRUE),
CFA_USECFACLONINGFORMULTITHREADEDPROGRAMS("cfa.useCFACloningForMultiThreadedPrograms",CfaUseCFACloningForMultiThreadedProgramsOptions.values(), CfaUseCFACloningForMultiThreadedProgramsOptions.TRUE);

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


