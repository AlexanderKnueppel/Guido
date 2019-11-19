package de.tu.bs.guido.verification.systems.key.options.taclets;

import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;


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
