package de.tubs.isf.guido.core.generator;

import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;
import de.tubs.isf.guido.core.verifier.SettingsObject;

public class DefaultConfigurationGenerator implements IConfigurationGenerator {

	@Override
	public SettingsObject computeNext() {
		return AVerificationSystemFactory.getFactory().createSettingsObject();
	}

	@Override
	public boolean hasNext() {
		return false;
	}

}
