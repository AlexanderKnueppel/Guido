package de.tu.bs.guido.key.simulator.options.taclets;

import de.tu.bs.guido.key.simulator.options.OptionableContainer;


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
