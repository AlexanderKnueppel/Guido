package de.tubs.isf.guido.core.verifier;

public interface GetJobs {
	public int getNumbofJobs(String source, String classpath, String className, String methodName,
			String[] parameters);
}
