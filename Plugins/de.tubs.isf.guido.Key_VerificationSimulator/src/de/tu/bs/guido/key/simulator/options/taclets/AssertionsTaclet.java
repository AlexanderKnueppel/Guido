package de.tu.bs.guido.key.simulator.options.taclets;

import de.tu.bs.guido.key.simulator.options.OptionableContainer;


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
