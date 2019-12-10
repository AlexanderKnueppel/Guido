package de.tubs.isf.guido.verification.systems.key.options.taclets;

import de.tubs.isf.core.verifier.OptionableContainer;


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
