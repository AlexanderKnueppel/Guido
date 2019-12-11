package de.tubs.isf.guido.core.verifier;

import java.io.Serializable;
import java.util.Map;

public abstract class AJavaJob implements IJob, Serializable {
	

	@Override
	public abstract void reinitialize();

	@Override
	public abstract SettingsObject getSo();

	@Override
	public abstract void setSo(SettingsObject so);

	@Override
	public abstract String getSource();

	@Override
	public abstract void setSource(String source);


	public abstract String getClazz();

	
	public abstract void setClazz(String clazz);

	
	public abstract String getMethod();

	
	public abstract void setMethod(String method);

	@Override
	public abstract String getClasspath();

	@Override
	public abstract void setClasspath(String classpath);


	public abstract int getContractNumber();

	
	public abstract void setContractNumber(int contractNumber);

	
	public abstract String[] getParameter();

	
	public abstract void setParameter(String[] parameter);

	
	public abstract String getCode();

	
	public abstract void setCode(String code);

	
	public abstract Map<String, Integer> getExperiments() ;

	
	public abstract IJob clone() throws CloneNotSupportedException ;
	
}
