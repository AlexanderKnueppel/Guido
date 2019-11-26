package de.tu.bs.guido.verification.system;

public abstract class AbstractFactory {
	public static AbstractFactory abst;

	public static AbstractFactory getAbst() {
		return abst;
	}

	public static void setAbst(AbstractFactory abst) {
		AbstractFactory.abst = abst;
	}

	public abstract Control createControl();

	public abstract GetJobs createGetJobs();

	public abstract Result createResult();


	public abstract SampleHelper createSampleHelper();


	public abstract BatchXMLHelper createBatchXMLHelper();

	public abstract Job createJob(String code, int expNumb, String source, String classpath, String clazz,
			String method, String[] parameter, SettingsObject so);
	

}