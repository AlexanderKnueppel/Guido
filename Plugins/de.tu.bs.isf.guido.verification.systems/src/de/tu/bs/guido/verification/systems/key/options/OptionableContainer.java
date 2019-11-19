package de.tu.bs.guido.verification.systems.key.options;

public interface OptionableContainer {

	Optionable[] getOptions();
	String getValue();
	Optionable getDefault();
}
