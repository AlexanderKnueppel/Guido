package de.tubs.isf.guido.verification.systems.key;

import de.tubs.isf.core.verifier.ASystemFactory;
import de.tubs.isf.core.verifier.BatchXMLHelper;
import de.tubs.isf.core.verifier.Control;
import de.tubs.isf.core.verifier.GetJobs;
import de.tubs.isf.core.verifier.OptionableContainer;
import de.tubs.isf.core.verifier.Result;
import de.tubs.isf.core.verifier.SampleHelper;
import de.tubs.isf.core.verifier.SettingsObject;


public class KeyFactory extends ASystemFactory {

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
	public BatchXMLHelper createBatchXMLHelper() {
		// TODO Auto-generated method stub
		return new KeyBatchXmlHelper();
	}

	public SettingsObject createSettingsObject() {
		
		return new KeySettingsObject();
	}

	@Override
	public OptionableContainer createContainer() {
		// TODO Auto-generated method stub
		return null;
	}

}
