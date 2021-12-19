package de.tubs.isf.guido.core.verifier.loader;

public class VerifierDescription<T> {
	private T verifier;
	private String name;
	private String handle;
	private String description;

	public VerifierDescription(T verifier, String name, String handle, String description) {
		this.verifier = verifier;
		this.name = name;
		this.handle = handle;
		this.description = description;
	}

	public T getVerifier() {
		return verifier;
	}

	public String getName() {
		return name;
	}
	
	public String getHandle() {
		return handle;
	}

	public String getDescription() {
		return description;
	}

}
