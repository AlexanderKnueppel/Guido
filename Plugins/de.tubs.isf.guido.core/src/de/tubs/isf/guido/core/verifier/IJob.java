package de.tubs.isf.guido.core.verifier;

import java.io.Serializable;



public interface IJob extends Cloneable, Serializable  {
	static final long serialVersionUID = 778517411407136861L;

	



	public void reinitialize();
	public SettingsObject getSo();

	public void setSo(SettingsObject so);

	
	public String getSource();

	public void setSource(String source);


	public String getCode();

	public void setCode(String clazz);




	@Override
	public boolean equals(Object obj);
	
	@Override
	public String toString();


	public IJob clone() throws CloneNotSupportedException;
	String getClasspath();
	void setClasspath(String classpath);

}
