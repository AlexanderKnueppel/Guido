package de.tubs.isf.guido.verification.systems.key;

import java.io.Serializable;
import java.util.Map;

import de.tubs.isf.guido.core.databasis.IDataBasisElement;

public class KeyDataBasisElement implements IDataBasisElement, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1935265156680654042L;
	private final String proof;
	private final String name;
	private final boolean closed;
	private final int steps;
	private final long timeInMillis;
	private final Map<String, String> options;
	private final Map<String, String> taclets;
	private String code;
	private Map<String, Integer> experiments;

	public KeyDataBasisElement(String proof, String name, boolean closed, int steps, long timeInMillis2,
			Map<String, String> options, Map<String, String> taclets) {
		super();
		this.proof = proof;
		this.name = name;
		this.closed = closed;
		this.steps = steps;
		this.timeInMillis = timeInMillis2;
		this.options = options;
		this.taclets = taclets;
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

	public String getProof() {
		return proof;
	}

	public String getName() {
		return name;
	}

	public boolean isClosed() {
		return closed;
	}

	public int getSteps() {
		return steps;
	}

	public long getTimeInMillis() {
		return timeInMillis;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public Map<String, String> getTaclets() {
		return taclets;
	}

	@Override
	public boolean isProvable() {
		// TODO Auto-generated method stub
		return isClosed();
	}

	@Override
	public double getEffort() {
		// TODO Auto-generated method stub
		return timeInMillis;
	}

}
