package de.tubs.isf.guido.verification.systems.key;

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

}
