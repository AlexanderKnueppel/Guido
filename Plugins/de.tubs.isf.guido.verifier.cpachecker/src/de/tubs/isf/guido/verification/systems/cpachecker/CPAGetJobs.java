package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.File;

import de.tubs.isf.guido.core.verifier.GetJobs;
import de.tubs.isf.guido.verification.systems.cpachecker.ExampleBasedCPACheckerControl;

public class CPAGetJobs implements GetJobs {

	@Override
	public int getNumbofJobs(String source, String classpath, String className, String methodName,
			String[] parameters) {
		
		return new ExampleBasedCPACheckerControl().getNumberOfJobs(methodName);

	}



}
