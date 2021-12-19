package de.tubs.isf.guido.verification.systems.key;

import com.google.gson.Gson;

import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;
import de.tubs.isf.guido.core.verifier.BatchXMLHelper;
import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.OptionableContainer;
import de.tubs.isf.guido.core.verifier.SampleHelper;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.tubs.isf.guido.verification.systems.key.options.strategies.KeyStrategyOptions;

public class KeyFactory extends AVerificationSystemFactory {

	public IProofControl createProofControl() {

		return new ExampleBasedKeyControl();

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
	public OptionableContainer[] createOptionableContainer() {
		// TODO Auto-generated method stub
		return KeyStrategyOptions.values();
	}

	@Override
	public OptionableContainer createOptionableContainer(String name) {
		// TODO Auto-generated method stub
		return KeyStrategyOptions.getOption(name);
	}

	@Override
	public IJob parseJobWithGson(String line) {
		Gson g = new Gson();
		return g.fromJson(line, KeyJavaJob.class);
	}
}
