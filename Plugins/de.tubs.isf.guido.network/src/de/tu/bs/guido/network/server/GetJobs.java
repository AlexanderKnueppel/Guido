package de.tu.bs.guido.network.server;

public interface GetJobs {
	public int getNumbofJobs(String source, String classpath, String className, String methodName,
			String[] parameters);
}
