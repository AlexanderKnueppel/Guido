package de.tubs.isf.guido.verification.systems.key.options.taclets;

import de.tubs.isf.core.verifier.OptionableContainer;


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
