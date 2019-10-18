package de.tu.bs.masterthesis.key.simulator.options.taclets;

import de.tu.bs.masterthesis.key.simulator.options.OptionableContainer;


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
