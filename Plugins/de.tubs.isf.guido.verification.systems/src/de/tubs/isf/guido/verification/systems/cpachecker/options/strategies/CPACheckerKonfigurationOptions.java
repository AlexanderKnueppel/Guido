package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;

import java.util.HashMap;
import java.util.Map;

import de.tubs.isf.guido.core.verifier.OptionableContainer;
import de.tubs.isf.guido.verification.systems.key.options.strategies.MergePointStatementsOptions;
import de.tubs.isf.guido.verification.systems.key.options.strategies.StrategyOptionable;

public enum CPACheckerKonfigurationOptions implements OptionableContainer {
	MERGE_POINT_STATEMENTS("Merge point statements", MergePointStatementsOptions.values(),
			MergePointStatementsOptions.MERGE);
	private static final Map<String, CPACheckerKonfigurationOptions> optionMap = new HashMap<>();
	private static final Map<String, StrategyOptionable> outputName = new HashMap<>();
	private static final Map<CPACheckerKonfigurationOptions, Map<String, StrategyOptionable>> valueMap = new HashMap<>();
	
	static {
		for (CPACheckerKonfigurationOptions kto : CPACheckerKonfigurationOptions.values()) {
			optionMap.put(kto.getValue(), kto);
			Map<String, StrategyOptionable> values = new HashMap<>();
			valueMap.put(kto, values);
			for(StrategyOptionable so: kto.getOptions()){
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
	
	public static StrategyOptionable getOption(String key, String value){
		return valueMap.get(optionMap.get(key)).get(value);
	}
	
	public static StrategyOptionable getOptionByName(String v){
		return outputName.get(v);
	}

	private final String value;
	private final StrategyOptionable[] options;
	private final StrategyOptionable defaultOption;

	CPACheckerKonfigurationOptions(String value, StrategyOptionable[] options,
			StrategyOptionable defaultOptionable) {
		this.value = value;
		this.options = options;
		this.defaultOption = defaultOptionable;
	}

	public StrategyOptionable[] getOptions() {
		return options;
	}

	public String getValue() {
		return value;
	}

	@Override
	public StrategyOptionable getDefault() {
		return defaultOption;
	}

}
