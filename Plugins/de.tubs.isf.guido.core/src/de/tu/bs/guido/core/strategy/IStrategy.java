package de.tu.bs.guido.core.strategy;

import de.tu.bs.guido.verification.system.SettingsObject;

public interface IStrategy {
	SettingsObject computeNextConfiguration();
}
