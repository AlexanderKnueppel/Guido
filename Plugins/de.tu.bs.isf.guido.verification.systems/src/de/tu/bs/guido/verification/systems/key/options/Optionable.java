package de.tu.bs.guido.verification.systems.key.options;

public interface Optionable {
	
	String getValue();
	OptionableContainer getOptionableContainer();
	
	default String getOutputString(){
		return getType() + "::" + getValue()+";";
	}
	
	default String getType(){
		return getOptionableContainer().getValue();
	}
}
