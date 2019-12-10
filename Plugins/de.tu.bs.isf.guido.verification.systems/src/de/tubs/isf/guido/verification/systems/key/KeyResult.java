package de.tubs.isf.guido.verification.systems.key;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import de.tubs.isf.core.verifier.Result;

public class KeyResult extends Result implements Serializable  {

	private static final long serialVersionUID = 4015855254309825043L;
	

	
	public KeyResult(String proof, String name, boolean closed, int steps, long timeInMillis2,
			Map<String, String> options, Map<String, String> taclets) {
		super(proof, name, closed, steps, timeInMillis2, taclets, taclets);

	}
	
}