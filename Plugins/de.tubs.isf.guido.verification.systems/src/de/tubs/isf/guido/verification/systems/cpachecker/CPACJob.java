package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.Serializable;

import de.tubs.isf.guido.core.verifier.ACodeContainer;
import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.SettingsObject;

public class CPACJob implements IJob, Serializable {

	public CPACJob(String configFilePath, String binary, String source, int expNumb, String[] parameter,
			SettingsObject so, int num) {
		this.setSo(so);
		setCodeContainer(new CPACheckerCodeContainer(configFilePath, binary, source, expNumb, parameter, num));
	}
	public CPACJob(String configFilePath, String binary, String source, int i,String[] parameter,
			SettingsObject so) {
		this(configFilePath, binary, source, -1,  parameter, so, -1);
	}
	
	public CPACJob(SettingsObject so) {
		this.setSo(so);
	}
	static final long serialVersionUID = 778517411407136861L;
	CPASettingsObject so;
	CPACheckerCodeContainer codeContainer;

	public void reinitialize() {
		so.reinitialize();
	}

	public SettingsObject getSo() {
		return so;
	}

	public void setSo(SettingsObject so) {
		this.so = (CPASettingsObject) so;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CPACJob other = (CPACJob) obj;

		if (so == null) {
			if (other.getSo() != null)
				return false;
		} else if (!so.equals(other.getSo()))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return so.toString();
	}

	public IJob clone() throws CloneNotSupportedException {
		IJob newJob = (IJob) super.clone();
		newJob.setSo(getSo().clone());
		return newJob;
	}
	
	@Override
	public void setCodeContainer(ACodeContainer cc) {
		this.codeContainer = (CPACheckerCodeContainer) cc;
		
	}

	@Override
	public ACodeContainer getCodeContainer() {
		// TODO Auto-generated method stub
		return codeContainer;
	}
}
