package de.tubs.isf.guido.core.verifier;

import de.tubs.isf.guido.core.proof.controller.IProofControl;

public abstract class AVerificationSystemFactory {
	public static AVerificationSystemFactory abst;

	public static AVerificationSystemFactory getAbst() {
		return abst;
	}

	public static void setAbst(AVerificationSystemFactory abst) {
		AVerificationSystemFactory.abst = abst;
	}

	public abstract IJob getJobwithGson(String line);

	public abstract IProofControl createControl();

	public abstract SampleHelper createSampleHelper();

	public abstract BatchXMLHelper createBatchXMLHelper();

	public abstract SettingsObject createSettingsObject();

	public abstract OptionableContainer[] createContainer();

	public abstract OptionableContainer createContainer(String name);
}