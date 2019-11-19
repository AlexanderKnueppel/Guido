package de.tu.bs.guido.verification.system;

public interface GetJobs {
	public int getNumbofJobs(String source, String classpath, String className, String methodName,
			String[] parameters);
}
