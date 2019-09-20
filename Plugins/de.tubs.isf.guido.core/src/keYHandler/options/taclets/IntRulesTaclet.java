package keYHandler.options.taclets;

import keYHandler.options.OptionableContainer;


public enum IntRulesTaclet implements TacletOptionable{

	ARITHMETICS_SEMANTICS_IGNORING_OF("arithmeticSemanticsIgnoringOF"), ARITHMETICS_SEMANTICS_CHECKING_OF("arithmeticSemanticsCheckingOF"),
	JAVA_SEMANTICS("javaSemantics");
	
	private final String value;
	
	private IntRulesTaclet(String value) {
		this. value = value;
	}
	
	@Override
	public String getValue() {
		return getType()+":"+value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.INT_RULES;
	}

}
