package de.tubs.isf.guido.verification.systems.key.options.taclets;

import de.tubs.isf.guido.core.verifier.OptionableContainer;


public enum IntegerSimplificationRulesTaclet implements TacletOptionable{

	FULL, MINIMAL;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.INTEGER_SIMPLIFICATION_RULES;
	}

}
