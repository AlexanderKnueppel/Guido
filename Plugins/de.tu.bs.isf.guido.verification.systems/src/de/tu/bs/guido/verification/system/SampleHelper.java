package de.tu.bs.guido.verification.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tu.bs.guido.verification.systems.key.generators.FeatureIdeTranslator;

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
