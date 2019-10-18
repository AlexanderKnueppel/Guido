package de.tu.bs.masterthesis.key.simulator.options;

public interface OptionableContainer {

	Optionable[] getOptions();
	String getValue();
	Optionable getDefault();
}
