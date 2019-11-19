package de.tu.bs.guido.core.automaticProof.proofFile;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.tu.bs.guido.verification.systems.key.options.SettingsObject;


public class Job implements Serializable, Cloneable {
	private static final long serialVersionUID = 778517411407136861L;

	private String source;
	private String clazz;
	private String method;
	private String[] parameter;
	private String classpath;
	private int contractNumber = -1;
	private String code;
	private Map<String, Integer> experiments = new HashMap<>();

	public Job(String code, int expNumb, String source, String classpath, String clazz,
			String method, String[] parameter, SettingsObject so) {
		this(code, -1, source, classpath, clazz, method, parameter, -1);
	}

	public Job(String code, int expNumb, String source, String classpath, String clazz,
			String method, String[] parameter, int contractNumber) {
		super();
		this.source = source;
		this.clazz = clazz;
		this.method = method;
		this.classpath = classpath;
		this.contractNumber = contractNumber;
		this.parameter = parameter;
		this.code = code;
		experiments.put(code, expNumb);
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getClasspath() {
		return classpath;
	}

	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}

	public int getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(int contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String[] getParameter() {
		return parameter;
	}

	public void setParameter(String[] parameter) {
		this.parameter = parameter;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Integer> getExperiments() {
		return experiments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((classpath == null) ? 0 : classpath.hashCode());
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + contractNumber;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + Arrays.hashCode(parameter);
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (classpath == null) {
			if (other.classpath != null)
				return false;
		} else if (!classpath.equals(other.classpath))
			return false;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (contractNumber != other.contractNumber)
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (!Arrays.equals(parameter, other.parameter))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Job [code=" + code + ", source=" + source + ", clazz=" + clazz
				+ ", method=" + method + ", parameter= " + parameter
				+ ", contractNumber=" + contractNumber + ", classpath=" + classpath + "]";
	}


	public Job clone() throws CloneNotSupportedException {
		Job newJob = (Job) super.clone();
		return newJob;
	}

}
