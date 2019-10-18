package de.tu.bs.masterthesis.key.simulator.options.taclets;

import de.tu.bs.masterthesis.key.simulator.options.OptionableContainer;


public enum PermissionsTaclet implements TacletOptionable{

	ON, OFF;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.PERMISSIONS;
	}

}
