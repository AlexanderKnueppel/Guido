package de.tu.bs.guido.key.logging.network.order;

import java.io.Serializable;

public class LogOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8260914428646360619L;
	private LogLevel level;
	private String text;
	private boolean stacktrace;
	private StackTraceElement[] stes; 
	private Throwable e;

	public LogOrder(LogLevel level, String text) {
		super();
		this.level = level;
		this.text = text;
		this.stacktrace = false;
	}
	

	public LogOrder(LogLevel level, String text, StackTraceElement[] stes) {
		super();
		this.level = level;
		this.text = text;
		this.stacktrace = true;
		this.stes = stes;
	}

	public LogOrder(String text, Throwable e) {
		super();
		this.level = LogLevel.ERROR;
		this.text = text;
		this.e = e;
	}

	public LogLevel getLevel() {
		return level;
	}

	public String getText() {
		return text;
	}

	public boolean isStacktrace() {
		return stacktrace;
	}

	public Throwable getE() {
		return e;
	}


	public StackTraceElement[] getStes() {
		return stes;
	}

	public void setStes(StackTraceElement[] stes) {
		this.stes = stes;
	}

}
