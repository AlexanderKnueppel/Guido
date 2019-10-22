package de.tu.bs.guido.key.simulator.options.taclets;

import de.tu.bs.guido.key.simulator.options.OptionableContainer;


public enum ModelFieldsTaclet implements TacletOptionable{

	TREAT_AS_AXIOM("treatAsAxiom"), SHOW_SATISFIABILITY("showSatisfiability");
	
	private final String value;
	
	private ModelFieldsTaclet(String value) {
		this. value = value;
	}
	
	@Override
	public String getValue() {
		return getType()+":"+value;
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.MODEL_FIELDS;
	}

}
