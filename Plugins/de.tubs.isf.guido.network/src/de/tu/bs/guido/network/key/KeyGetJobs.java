package de.tu.bs.guido.network.key;

import java.io.File;
import de.tu.bs.guido.key.simulator.control.ExampleBasedKeyControl;
import de.tu.bs.guido.network.server.GetJobs;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;

public class KeyGetJobs implements GetJobs {

	public int getNumbofJobs(String source, String classpath, String className, String methodName,
			String[] parameters) {

		try {
			return new ExampleBasedKeyControl().getNumberOfContracts(source == null ? null : new File(source),
					classpath == null ? null : new File(classpath), className, methodName, parameters);
		} catch (ProofInputException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
		} catch (ProblemLoaderException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
		}
		return 0;

	}
}
