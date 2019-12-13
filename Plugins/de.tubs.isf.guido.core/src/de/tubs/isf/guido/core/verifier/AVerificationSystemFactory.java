package de.tubs.isf.guido.core.verifier;

import de.tubs.isf.guido.core.proof.controller.IProofControl;

public abstract class AVerificationSystemFactory {
	public static AVerificationSystemFactory factory;

	public static AVerificationSystemFactory getFactory() {
		return factory;
	}

	public static void setFactory(AVerificationSystemFactory abst) {
		AVerificationSystemFactory.factory = abst;
	}

	public abstract IJob parseJobWithGson(String line);

	public abstract IProofControl createProofControl();

	public abstract SampleHelper createSampleHelper();

	public abstract BatchXMLHelper createBatchXMLHelper();

	public abstract SettingsObject createSettingsObject();

	public abstract OptionableContainer[] createOptionableContainer();

	public abstract OptionableContainer createOptionableContainer(String name);
}