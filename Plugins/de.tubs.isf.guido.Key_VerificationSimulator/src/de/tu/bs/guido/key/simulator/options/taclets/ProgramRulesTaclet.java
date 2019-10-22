package de.tu.bs.guido.key.simulator.options.taclets;

import de.tu.bs.guido.key.simulator.options.OptionableContainer;


public enum ProgramRulesTaclet implements TacletOptionable{

	JAVA("Java"), NONE("None");
	
	private final String value;
	
	private ProgramRulesTaclet(String value) {
		this. value = value;
	}
	
	@Override
	public String getValue() {
		return getType()+":"+value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.PROGRAM_RULES;
	}

}
