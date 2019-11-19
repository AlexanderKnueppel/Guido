package strategy;

import keYHandler.options.SettingsObject;

public interface IStrategy {
	SettingsObject computeNextConfiguration();
}
