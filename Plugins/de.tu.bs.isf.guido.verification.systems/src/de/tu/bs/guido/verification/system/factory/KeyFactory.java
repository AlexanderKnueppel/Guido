package de.tu.bs.guido.verification.system.factory;

import de.tu.bs.guido.verification.system.BatchXMLHelper;
import de.tu.bs.guido.verification.system.Control;
import de.tu.bs.guido.verification.system.GetJobs;
import de.tu.bs.guido.verification.system.Job;
import de.tu.bs.guido.verification.system.Result;
import de.tu.bs.guido.verification.system.SampleHelper;
import de.tu.bs.guido.verification.systems.key.ExampleBasedKeyControl;
import de.tu.bs.guido.verification.systems.key.KeyBatchXmlHelper;
import de.tu.bs.guido.verification.systems.key.KeyGetJobs;
import de.tu.bs.guido.verification.systems.key.KeyJob;
import de.tu.bs.guido.verification.systems.key.KeyResult;
import de.tu.bs.guido.verification.systems.key.KeySampleHelper;
import de.tu.bs.guido.verification.systems.key.options.SettingsObject;

public class KeyFactory extends AbstractFactory {

	public Control createControl() {

		return new ExampleBasedKeyControl();

	}

	public GetJobs createGetJobs() {

		return new KeyGetJobs();

	}

	@Override
	public Result createResult() {
		// TODO Auto-generated method stub
		return new KeyResult(null, null, false, 0, 0, null, null);
	}



	@Override
	public SampleHelper createSampleHelper() {
		// TODO Auto-generated method stub
		return new KeySampleHelper();
	}

	@Override
	public Job createJob(String code, int expNumb, String source, String classpath, String clazz, String method,
			String[] parameter, SettingsObject so) {
		// TODO Auto-generated method stub
		return new KeyJob(code, expNumb, source, classpath, clazz, method, parameter, so);
	}

	@Override
	public BatchXMLHelper createBatchXMLHelper() {
		// TODO Auto-generated method stub
		return new KeyBatchXmlHelper();
	}



}
