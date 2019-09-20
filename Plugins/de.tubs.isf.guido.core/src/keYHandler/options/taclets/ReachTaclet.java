package keYHandler.options.taclets;

import keYHandler.options.OptionableContainer;


public enum ReachTaclet implements TacletOptionable{

	ON, OFF;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.REACH;
	}

}
