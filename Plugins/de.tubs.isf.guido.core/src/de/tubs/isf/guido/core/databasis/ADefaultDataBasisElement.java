package de.tubs.isf.guido.core.databasis;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class ADefaultDataBasisElement implements IDataBasisElement, Serializable {

	private static final long serialVersionUID = 4015855254309825043L;

	private final String name;
	private final boolean closed;
	private final long timeInMillis;
	private final List<String> languageConstructs;
	private final Map<String, String> options;

	public ADefaultDataBasisElement(String name, boolean closed, long timeInMillis, List<String> languageConstructs,
			Map<String, String> options) {
		super();
		this.name = name;
		this.closed = closed;
		this.timeInMillis = timeInMillis;
		this.languageConstructs = languageConstructs;
		this.options = options;
	}

	public String getName() {
		return name;
	}

	public boolean isClosed() {
		return closed;
	}

	public long getTimeInMillis() {
		return timeInMillis;
	}

	public List<String> getLanguageConstructs() {
		return languageConstructs;
	}

	public Map<String, String> getOptions() {
		return Collections.unmodifiableMap(options);
	}

}
