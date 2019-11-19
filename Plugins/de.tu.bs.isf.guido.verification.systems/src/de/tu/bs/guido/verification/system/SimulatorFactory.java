package de.tu.bs.guido.verification.system;

import java.util.Map;

public class SimulatorFactory {
	
	private static SimulatorFactory theInstance;

	
	
	public enum Mode  {Key, Other, Default};
	 public Result createResult(String proof, String name, boolean closed, int steps, long timeInMillis2,
				Map<String, String> options, Map<String, String> taclets) {
		 return new Result(proof, name, closed, steps, timeInMillis2, options, taclets);
		 
	 }
	public static SimulatorFactory getTheInstance() {
		return theInstance;
	}
	public static void setTheInstance(SimulatorFactory theInstance) {
		SimulatorFactory.theInstance = theInstance;
	}
	 
}
