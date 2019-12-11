package de.tubs.isf.guido.verification.systems.key;

import java.io.Serializable;
import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.SettingsObject;

public class KeyJavaJob implements IJob, Serializable {

	public KeyJavaJob(String code, int expNumb, String source, String classpath, String clazz, String method,
			String[] parameter, SettingsObject so, int num) {

		this.setSo(so);
		((KeyCodeContainer) this.getSo().getCc()).setSource(source);
		((KeyCodeContainer) this.getSo().getCc()).setClasspath(classpath);
		;
		((KeyCodeContainer) this.getSo().getCc()).setClazz(clazz);
		((KeyCodeContainer) this.getSo().getCc()).setMethod(method);
		((KeyCodeContainer) this.getSo().getCc()).setParameter(parameter);
		((KeyCodeContainer) this.getSo().getCc()).setMethod(method);
		((KeyCodeContainer) this.getSo().getCc()).setContractNumber(num);
		((KeyCodeContainer) this.getSo().getCc()).getExperiments().put(code, expNumb);

	}

	public KeyJavaJob(String code, int i, String source, String classpath, String clazz, String method,
			String[] parameter, SettingsObject so) {
		this(code, -1, source, classpath, clazz, method, parameter, so, -1);
	}

	public KeyJavaJob(SettingsObject so) {

		this.setSo(so);

	}

	static final long serialVersionUID = 778517411407136861L;
	SettingsObject so;

	public void reinitialize() {
		so.reinitialize();
	}

	public SettingsObject getSo() {
		return so;
	}

	public void setSo(SettingsObject so) {
		this.so = so;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IJob other = (IJob) obj;

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
