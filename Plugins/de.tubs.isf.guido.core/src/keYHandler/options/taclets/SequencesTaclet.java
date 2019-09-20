package keYHandler.options.taclets;

import keYHandler.options.OptionableContainer;


public enum SequencesTaclet implements TacletOptionable{

	ON, OFF;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.SEQUENCES;
	}

}
