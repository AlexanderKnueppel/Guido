package de.tubs.isf.guido.core.verifier;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



public interface IJob  {
	static final long serialVersionUID = 778517411407136861L;

	String source =null;
	SettingsObject so=null;
	String classpath=null;
	String code=null; 
	Map<String, Integer> experiments = new HashMap<>();



	public void reinitialize();
	public SettingsObject getSo();

	public void setSo(SettingsObject so);

	
	public String getSource();

	public void setSource(String source);

	public String getClazz();

	public void setClazz(String clazz);

	public String getMethod();

	public void setMethod(String method);

	public String getClasspath();

	public void setClasspath(String classpath);

	public int getContractNumber();

	public void setContractNumber(int contractNumber);

	public String[] getParameter();

	public void setParameter(String[] parameter);
	
	public String getCode();

	public void setCode(String code);

	public Map<String, Integer> getExperiments();


	@Override
	public boolean equals(Object obj);
	
	@Override
	public String toString();


	public IJob clone() throws CloneNotSupportedException;

}
