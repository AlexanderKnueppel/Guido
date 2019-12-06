package de.tubs.isf.guido.core.databasis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class DefaultDataBasisElement implements IDataBasisElement, Serializable {

	private static final long serialVersionUID = 4015855254309825043L;

	private final String name;
	private final boolean closed;
	private final long timeInMillis;
	private final List<String> languageConstructs;
	private final Map<String, String> options;

	public DefaultDataBasisElement(String name, boolean closed, long timeInMillis, List<String> languageConstructs,
			Map<String, String> options) {
		super();
		this.name = name;
		this.closed = closed;
		this.timeInMillis = timeInMillis;
		this.languageConstructs = languageConstructs == null ? new ArrayList<String>() : languageConstructs;
		this.options = new TreeMap<String,String>(options);
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
	
	public Map<String, String> getOptionsSorted() {
		return Collections.unmodifiableSortedMap((TreeMap<String,String>)options);
	}
	
	
	public Map<String, String> getOptionsCopy() {
		return new HashMap<String,String>(options);
	}

	public DefaultDataBasisElement clone() {
		return new DefaultDataBasisElement(name, closed, timeInMillis, languageConstructs, options);
	}

	@Override
	public boolean isProvable() {
		return closed;
	}

	@Override
	public double getEffort() {
		return timeInMillis;
	}

	@Override
	public boolean equals(Object o) {
		DefaultDataBasisElement db = (DefaultDataBasisElement) o;
		
		return this.getName().equals(db.getName())
				&& this.getLanguageConstructs().equals(db.getLanguageConstructs())
				&& this.getOptions().equals(db.getOptions());
	}
}
