package de.tu.bs.masterthesis.key.simulator.options.taclets;

import de.tu.bs.masterthesis.key.simulator.options.OptionableContainer;


public enum WdOperatorTaclet implements TacletOptionable{

	L, Y, D;
	
	@Override
	public String getValue() {
		return getType()+":"+name();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.WD_OPERATOR;
	}

}
