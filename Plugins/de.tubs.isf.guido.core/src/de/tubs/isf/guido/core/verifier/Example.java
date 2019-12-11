package de.tubs.isf.guido.core.verifier;

import java.io.File;
import java.util.List;

public abstract  class Example{

	public abstract List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, SettingsObject so);
		

}
