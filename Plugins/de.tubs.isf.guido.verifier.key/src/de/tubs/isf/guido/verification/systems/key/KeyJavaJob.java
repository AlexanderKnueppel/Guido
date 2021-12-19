package de.tubs.isf.guido.verification.systems.key;

import java.io.Serializable;

import de.tubs.isf.guido.core.verifier.ACodeContainer;
import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.SettingsObject;

public class KeyJavaJob implements IJob, Serializable {

	public KeyJavaJob(String code, int expNumb, String source, String classpath, String clazz, String method,
			String[] parameter, SettingsObject so, int num) {

		this.setSo(so);
		setCodeContainer(new KeyCodeContainer(code, expNumb, source, classpath, clazz, method, parameter, num));

	}

	public KeyJavaJob(String code, int i, String source, String classpath, String clazz, String method,
			String[] parameter, SettingsObject so) {
		this(code, -1, source, classpath, clazz, method, parameter, so, -1);
	}

	public KeyJavaJob(SettingsObject so) {

		this.setSo(so);

	}

	static final long serialVersionUID = 778517411407136861L;
	KeySettingsObject so;
	KeyCodeContainer kcc;

	public void reinitialize() {
		so.reinitialize();
	}

	public SettingsObject getSo() {
		return so;
	}

	public void setSo(SettingsObject so) {
		this.so = (KeySettingsObject) so;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyJavaJob other = (KeyJavaJob) obj;

		if (so == null) {
			if (other.getSo() != null)
				return false;
		} else if (!so.equals(other.getSo()))
			return false;
		if (kcc == null) {
			if (other.getCodeContainer() != null)
				return false;
		} else if (!kcc.equals(other.getCodeContainer()))
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
		this.kcc = (KeyCodeContainer) cc;
		
	}

	@Override
	public ACodeContainer getCodeContainer() {
		// TODO Auto-generated method stub
		return kcc;
	}

}
