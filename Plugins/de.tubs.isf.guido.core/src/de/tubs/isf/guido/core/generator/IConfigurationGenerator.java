package de.tubs.isf.guido.core.generator;

import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.SettingsObject;

public interface IConfigurationGenerator {
	public SettingsObject computeNext(IJob job); 
}
