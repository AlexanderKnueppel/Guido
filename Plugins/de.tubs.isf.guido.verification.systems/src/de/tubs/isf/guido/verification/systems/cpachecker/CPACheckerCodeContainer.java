package de.tubs.isf.guido.verification.systems.cpachecker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.tubs.isf.guido.core.verifier.ACodeContainer;

public class CPACheckerCodeContainer extends ACodeContainer {
	
	private static final long serialVersionUID = 7057222153950841911L;
	private String source;
	private String clazz;
	private String method;
	private String[] parameter;
	private String specificationPath;
	private int contractNumber = -1;
	private String binary;
	private Map<String, Integer> experiments = new HashMap<>();
	
	public CPACheckerCodeContainer(String binary,int expNumb, String source, String specificationPath, String clazz, String method,
		String[] parameter, int contractNumber) {
		super();
		this.source = source;
		this.clazz = clazz;
		this.method = method;
		this.binary = binary;
		this.specificationPath = specificationPath;
		this.contractNumber = contractNumber;
		this.parameter = parameter;
		experiments.put(binary, expNumb);
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
	public String getBinary() {
		return binary;
	}

	public void setBinary(String code) {
		this.binary = code;
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

	public String getSpecificationPath() {
		return specificationPath;
	}

	public void setSpecificationPath(String classpath) {
		this.specificationPath = classpath;
	}

	public int getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(int contractNumber) {
		this.contractNumber = contractNumber;
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
		CPACheckerCodeContainer other = (CPACheckerCodeContainer) obj;
		if (specificationPath == null) {
			if (other.specificationPath != null)
				return false;
		} else if (!specificationPath.equals(other.specificationPath))
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
}
