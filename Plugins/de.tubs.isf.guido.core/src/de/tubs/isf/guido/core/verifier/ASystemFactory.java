package de.tubs.isf.guido.core.verifier;

import de.tubs.isf.guido.core.proof.controller.IProofControl;

public abstract class ASystemFactory {
	public static ASystemFactory abst;

	public static ASystemFactory getAbst() {
		return abst;
	}

	public static void setAbst(ASystemFactory abst) {
		ASystemFactory.abst = abst;
	}

	public abstract IProofControl createControl();



	public abstract SampleHelper createSampleHelper();


	public abstract BatchXMLHelper createBatchXMLHelper();

	public abstract SettingsObject createSettingsObject();


	public abstract OptionableContainer[] createContainer();

	public abstract OptionableContainer createContainer(String name) ;
}