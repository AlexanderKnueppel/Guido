package de.tu.bs.guido.verification.systems.key.options.taclets;

import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;


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
