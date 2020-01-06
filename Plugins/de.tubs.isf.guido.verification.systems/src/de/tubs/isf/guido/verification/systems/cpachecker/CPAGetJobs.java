package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.File;

import de.tubs.isf.guido.core.verifier.GetJobs;

public class CPAGetJobs implements GetJobs {

	@Override
	public int getNumbofJobs(String source, String classpath, String className, String methodName,
			String[] parameters) {
		// TODO Auto-generated method stub
		return new ExampleBasedCPACheckerControl().getNumberOfContracts(source == null ? null : new File(source),
				classpath == null ? null : new File(classpath), className, methodName, parameters);
	}

}
