package de.tubs.isf.guido.verification.systems.key;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.tubs.isf.guido.core.verifier.ACodeContainer;

public class KeyCodeContainer extends ACodeContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7057222153950841911L;
	private String source;
	private String clazz;
	private String method;
	private String[] parameter;
	private String classpath;
	private int contractNumber = -1;
	private String code;
	private Map<String, Integer> experiments = new HashMap<>();

	public KeyCodeContainer(String code, int expNumb, String source, String classpath, String clazz, String method,
			String[] parameter, int contractNumber) {
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

	public String[] getParameter() {
		return parameter;
	}

	public void setParameter(String[] parameter) {
		this.parameter = parameter;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Integer> getExperiments() {
		return experiments;
	}

	public void setExperiments(Map<String, Integer> experiments) {
		this.experiments = experiments;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {

			return true;
		}
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyCodeContainer other = (KeyCodeContainer) obj;
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
	public String getFilePath() {
		// TODO Auto-generated method stub
		return getClasspath();
	}
}
