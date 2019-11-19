package de.tu.bs.guido.verification.system;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.tu.bs.guido.verification.systems.key.options.SettingsObject;

public abstract  class Example{

	public abstract List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, SettingsObject so);
		

}
