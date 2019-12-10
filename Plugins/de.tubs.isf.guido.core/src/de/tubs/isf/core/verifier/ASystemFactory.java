package de.tubs.isf.core.verifier;

public abstract class ASystemFactory {
	public static ASystemFactory abst;

	public static ASystemFactory getAbst() {
		return abst;
	}

	public static void setAbst(ASystemFactory abst) {
		ASystemFactory.abst = abst;
	}

	public abstract Control createControl();

	public abstract GetJobs createGetJobs();

	public abstract Result createResult();


	public abstract SampleHelper createSampleHelper();


	public abstract BatchXMLHelper createBatchXMLHelper();

	public abstract SettingsObject createSettingsObject();


	public abstract OptionableContainer createContainer();
}