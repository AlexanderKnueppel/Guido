package de.tubs.isf.core.verifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public abstract class SampleHelper {
	
	protected static final int DEFAULT_MAX_STEPS = 1000;

	public abstract void main(String[] args) throws FileNotFoundException,
			IOException;
	

	public abstract List<SettingsObject> readSPLSamples(File samples)
			throws IOException;

	public abstract List<SettingsObject> readFeatureIDESamples(File sampleFolder)
			throws IOException;

	public abstract List<Result> outPutProofResults(List<Result> res);

}
