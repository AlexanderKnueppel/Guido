package de.tu.bs.guido.key.simulator.options;

public interface OptionableContainer {

	Optionable[] getOptions();
	String getValue();
	Optionable getDefault();
}
