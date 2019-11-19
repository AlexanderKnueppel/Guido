package de.tu.bs.guido.core.strategy;

import de.tu.bs.guido.verification.systems.key.options.SettingsObject;

public interface IStrategy {
	SettingsObject computeNextConfiguration();
}
