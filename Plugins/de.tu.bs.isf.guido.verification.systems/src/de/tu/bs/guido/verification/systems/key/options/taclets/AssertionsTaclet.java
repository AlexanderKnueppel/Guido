package de.tu.bs.guido.verification.systems.key.options.taclets;

import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;


public enum AssertionsTaclet implements TacletOptionable{

	ON, OFF, SAFE;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.ASSERTIONS;
	}

}
