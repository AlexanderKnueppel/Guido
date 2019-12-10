package de.tubs.isf.guido.core.verifier;

public interface OptionableContainer {

	Optionable[] getOptions();
	String getValue();
	Optionable getDefault();
}
