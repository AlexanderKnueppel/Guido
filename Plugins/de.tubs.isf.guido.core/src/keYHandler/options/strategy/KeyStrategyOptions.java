package keYHandler.options.strategy;

import java.util.HashMap;
import java.util.Map;

import keYHandler.options.OptionableContainer;

public enum KeyStrategyOptions implements OptionableContainer {
	STOP_AT("Stop at", StopAtOptions.values(), StopAtOptions.DEFAULT), ONE_STEP_SIMPLIFICATION(
			"One Step Simplification", OneStepSimplificationOptions.values(),
			OneStepSimplificationOptions.ENABLED), PROOF_SPLITTING(
			"Proof splitting", ProofSplittingOptions.values(),
			ProofSplittingOptions.FREE), LOOP_TREATMENT("Loop treatment",
			LoopTreatmentOptions.values(), LoopTreatmentOptions.INVARIANT), BLOCK_TREATMENT(
			"Block treatment", BlockTreatmentOptions.values(),
			BlockTreatmentOptions.INTERNALCONTRACT), METHOD_TREATMENT(
			"Method treatment", MethodTreatmentOptions.values(),
			MethodTreatmentOptions.CONTRACT), MERGE_POINT_STATEMENTS(
			"Merge point statements", MergePointStatementsOptions.values(),
			MergePointStatementsOptions.MERGE), DEPENDENCY_CONTRACTS(
			"Dependency contracts", DependencyContractsOptions.values(),
			DependencyContractsOptions.ON), QUERY_TREATMENT("Query treatment",
			QueryTreatmentOptions.values(), QueryTreatmentOptions.ON), EXPAND_LOCAL_QUERIES(
			"Expand local queries", ExpandLocalQueriesOptions.values(),
			ExpandLocalQueriesOptions.ON), ARITHMETIC_TREATMENT(
			"Arithmetic treatment", ArithmeticTreatmentOptions.values(),
			ArithmeticTreatmentOptions.DEFOPS), QUANTIFIER_TREATMENT(
			"Quantifier treatment", QuantifierTreatmentOptions.values(),
			QuantifierTreatmentOptions.NO_SPLITS_WITH_PROGS), CLASS_AXIOM_RULE(
			"Class axiom rule", ClassAxiomRulesOptions.values(),
			ClassAxiomRulesOptions.FREE), AUTO_INDUCTION("Auto Induction",
			AutoInductionOptions.values(), AutoInductionOptions.OFF);

	private static final Map<String, KeyStrategyOptions> optionMap = new HashMap<>();
	private static final Map<String, StrategyOptionable> outputName = new HashMap<>();
	private static final Map<KeyStrategyOptions, Map<String, StrategyOptionable>> valueMap = new HashMap<>();

	static {
		for (KeyStrategyOptions kto : KeyStrategyOptions.values()) {
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
	
	public static KeyStrategyOptions getOption(String value){
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

	KeyStrategyOptions(String value, StrategyOptionable[] options,
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
