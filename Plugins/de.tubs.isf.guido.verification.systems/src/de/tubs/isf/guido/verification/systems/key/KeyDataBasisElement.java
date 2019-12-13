package de.tubs.isf.guido.verification.systems.key;

import java.util.List;
import java.util.Map;
import de.tubs.isf.guido.core.databasis.DefaultDataBasisElement;

public class KeyDataBasisElement extends DefaultDataBasisElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1935265156680654042L;
	private final String proof;
	private final int steps;
	private final Map<String, String> taclets;
	private String code;
	private Map<String, Integer> experiments;

	public KeyDataBasisElement(String proof, String name, boolean closed, int steps, long timeInMillis,
			List<String> languageConstructs, Map<String, String> options, Map<String, String> taclets) {
		super(name, closed, timeInMillis, languageConstructs, options);
		this.proof = proof;
		this.steps = steps;
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

	public int getSteps() {
		return steps;
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
		return steps;
	}

}
