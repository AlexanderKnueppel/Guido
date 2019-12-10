package de.tubs.isf.guido.core.verifier;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class Result implements Serializable {

	private static final long serialVersionUID = 4015855254309825043L;
	
	private final String proof;
	private final String name;
	private final boolean closed;
	private final int steps;
	private final long timeInMillis;
	private final Map<String, String> options;
	private final Map<String, String> taclets;
	private String code;
	private Map<String, Integer> experiments;
 
	
	public Result(String proof, String name, boolean closed, int steps, long timeInMillis2,
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
		return Collections.unmodifiableMap(options);
	}
	public Map<String, String> getTaclets() {
		return Collections.unmodifiableMap(taclets);
	}
	public void setCode(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	
}