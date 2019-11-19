package de.tu.bs.guido.verification.systems.key;

import java.io.File;
import de.tu.bs.guido.verification.system.GetJobs;

public class KeyGetJobs implements GetJobs {

	public int getNumbofJobs(String source, String classpath, String className, String methodName,
			String[] parameters) {

		return new ExampleBasedKeyControl().getNumberOfContracts(source == null ? null : new File(source),
				classpath == null ? null : new File(classpath), className, methodName, parameters);

	}
}
