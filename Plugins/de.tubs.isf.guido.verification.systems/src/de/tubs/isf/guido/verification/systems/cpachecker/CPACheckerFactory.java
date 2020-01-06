package de.tubs.isf.guido.verification.systems.cpachecker;

import com.google.gson.Gson;

import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;
import de.tubs.isf.guido.core.verifier.BatchXMLHelper;
import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.OptionableContainer;
import de.tubs.isf.guido.core.verifier.SampleHelper;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.tubs.isf.guido.verification.systems.cpachecker.options.strategies.CPACheckerStrategyOptions;

public class CPACheckerFactory extends AVerificationSystemFactory{
	public IProofControl createProofControl() {

		return new ExampleBasedCPACheckerControl();
	}

	@Override
	public SampleHelper createSampleHelper() {
		// TODO Auto-generated method stub
		return new CPACheckerSampleHelper();
	}

	@Override
	public BatchXMLHelper createBatchXMLHelper() {
		// TODO Auto-generated method stub
		return new CPACheckerBatchXmlHelper();
	}

	public SettingsObject createSettingsObject() {

		return new CPASettingsObject();
	}

	@Override
	public OptionableContainer[] createOptionableContainer() {
		// TODO Auto-generated method stub
		return CPACheckerStrategyOptions.values();
	}

	@Override
	public OptionableContainer createOptionableContainer(String name) {
		// TODO Auto-generated method stub
		return CPACheckerStrategyOptions.getOption(name);
	}

	@Override
	public IJob parseJobWithGson(String line) {
		Gson g = new Gson();
		return g.fromJson(line, CPACJob.class);
	}
}
