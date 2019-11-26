package de.tu.bs.guido.verification.system;

import java.io.File;
import java.util.List;

public interface Control {

	List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, int contractNumber, SettingsObject so);
	
	List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, SettingsObject so) ;

	List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, String[] parameters, int contractNumber, SettingsObject so);
	
	List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, String[] parameters, SettingsObject so);

	int getNumberOfContracts(File source, File classPath,
			String className, String methodName);
	
	int getNumberOfContracts(File source, File classPath,
			String className, String methodName, String[] parameters);
	
}
