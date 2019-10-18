package de.tu.bs.masterthesis.key.simulator.options.taclets;

import de.tu.bs.masterthesis.key.simulator.options.OptionableContainer;


public enum MergeGenerateIsWeakeningGoalTaclet implements TacletOptionable{

	ON, OFF;
	
	@Override
	public String getValue() {
		return getType()+":"+name().toLowerCase();
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return KeyTacletOptions.MERGE_GENERATE_IS_WEAKENING_GOAL;
	}

}
