package de.tu.bs.guido.key.simulator.options.taclets;

import de.tu.bs.guido.key.simulator.options.OptionableContainer;


public enum RuntimeExceptionsTaclet implements TacletOptionable{

	BAN, ALLOW, IGNORE;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.RUNTIME_EXCEPTIONS;
	}

}
