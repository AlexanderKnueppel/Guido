package de.tubs.isf.guido.verification.systems.key.options.taclets;

import java.util.HashMap;
import java.util.Map;

import de.tubs.isf.guido.core.verifier.OptionableContainer;

public enum KeyTacletOptions implements OptionableContainer {
	ASSERTIONS("assertions", AssertionsTaclet.values(), AssertionsTaclet.SAFE),
	INITIALISATION("initialisation", InitialisationTaclet.values(), InitialisationTaclet.DISABLE_STATIC_INITIALISATION),
	INT_RULES("intRules", IntRulesTaclet.values(), IntRulesTaclet.JAVA_SEMANTICS),
	PROGRAM_RULES("programRules", ProgramRulesTaclet.values(), ProgramRulesTaclet.JAVA),
	RUNTIME_EXCEPTIONS("runtimeExceptions", RuntimeExceptionsTaclet.values(), RuntimeExceptionsTaclet.BAN),
	JAVA_CARD("JavaCard", JavaCardTaclet.values(), JavaCardTaclet.OFF),
	STRINGS("Strings", StringsTaclet.values(), StringsTaclet.ON),
	MODEL_FIELDS("modelFields", ModelFieldsTaclet.values(), ModelFieldsTaclet.TREAT_AS_AXIOM),
	BIGINT("bigint", BigIntTaclet.values(), BigIntTaclet.ON),
	SEQUENCES("sequences", SequencesTaclet.values(), SequencesTaclet.ON),
	MORE_SEQ_RULES("moreSeqRules", MoreSeqRulesTaclet.values(), MoreSeqRulesTaclet.OFF),
	REACH("reach", ReachTaclet.values(), ReachTaclet.ON),
	INTEGER_SIMPLIFICATION_RULES("integerSimplificationRules", IntegerSimplificationRulesTaclet.values(),
			IntegerSimplificationRulesTaclet.FULL),
	PERMISSIONS("permissions", PermissionsTaclet.values(), PermissionsTaclet.OFF),
	WD_OPERATOR("wdOperator", WdOperatorTaclet.values(), WdOperatorTaclet.L),
	WD_CHECKS("wdChecks", WdChecksTaclet.values(), WdChecksTaclet.OFF),
	MERGE_GENERATE_IS_WEAKENING_GOAL("mergeGenerateIsWeakeningGoal", MergeGenerateIsWeakeningGoalTaclet.values(),
			MergeGenerateIsWeakeningGoalTaclet.OFF);

	private static final Map<String, KeyTacletOptions> optionMap = new HashMap<>();
	private static final Map<String, TacletOptionable> outputName = new HashMap<>();
	private static final Map<KeyTacletOptions, Map<String, TacletOptionable>> valueMap = new HashMap<>();

	static {
		for (KeyTacletOptions kto : KeyTacletOptions.values()) {
			optionMap.put(kto.getValue(), kto);
			Map<String, TacletOptionable> values = new HashMap<>();
			valueMap.put(kto, values);
			for (TacletOptionable so : kto.getOptions()) {
				values.put(so.getValue(), so);
				outputName.put(so.getOutputString().replaceAll(";", ""), so);
			}
		}
	}

	public static boolean isTaclet(String value) {
		return optionMap.containsKey(value);
	}

	public static KeyTacletOptions getOption(String value) {
		return optionMap.get(value);
	}

	public static TacletOptionable getOption(String key, String value) {
		return valueMap.get(optionMap.get(key)).get(value);
	}

	public static TacletOptionable getOptionByName(String v) {
		return outputName.get(v);
	}

	private final String value;
	private final TacletOptionable[] options;
	private final TacletOptionable defaultOption;

	KeyTacletOptions(String value, TacletOptionable[] options, TacletOptionable defaultOption) {
		this.value = value;
		this.options = options;
		this.defaultOption = defaultOption;
	}

	public String getValue() {
		return value;
	}

	public TacletOptionable[] getOptions() {
		return options;
	}

	@Override
	public TacletOptionable getDefault() {
		return defaultOption;
	}

}
