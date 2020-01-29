package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.Serializable;

import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.SettingsObject;

public class CPACJob implements IJob, Serializable {

	public CPACJob(String binary, int expNumb, String source, String configFilePath, String clazz, String method,
			String[] parameter, SettingsObject so, int num) {
		this.setSo(so);
		so.setCc(new CPACheckerCodeContainer(configFilePath, binary, source, expNumb, clazz, method, parameter, num));
	}
	public CPACJob(String code, int i, String source, String configFilePath, String clazz, String method,
			String[] parameter, SettingsObject so) {
		this(code, -1, source, configFilePath, clazz, method, parameter, so, -1);
	}
	
	public CPACJob(SettingsObject so) {
		this.setSo(so);
	}
	static final long serialVersionUID = 778517411407136861L;
	CPASettingsObject so;

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
}
