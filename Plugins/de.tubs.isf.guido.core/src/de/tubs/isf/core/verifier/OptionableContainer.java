package de.tubs.isf.core.verifier;

public interface OptionableContainer {

	Optionable[] getOptions();
	String getValue();
	Optionable getDefault();
}
