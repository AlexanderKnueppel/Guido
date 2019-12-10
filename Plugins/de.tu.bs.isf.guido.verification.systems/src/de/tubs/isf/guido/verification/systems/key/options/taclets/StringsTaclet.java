package de.tubs.isf.guido.verification.systems.key.options.taclets;

import de.tubs.isf.core.verifier.OptionableContainer;


public enum StringsTaclet implements TacletOptionable{

	ON, OFF;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.STRINGS;
	}

}
