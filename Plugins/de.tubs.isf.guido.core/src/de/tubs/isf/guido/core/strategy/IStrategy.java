package de.tubs.isf.guido.core.strategy;

import de.tubs.isf.guido.core.verifier.SettingsObject;

public interface IStrategy {
	SettingsObject computeNextConfiguration();
}
