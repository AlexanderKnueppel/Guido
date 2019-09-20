package de.tu.bs.guido.key.logging;

public interface Logger {

	void logVerbose(String log);
	void logVerbose(String log, boolean stacktrace);
	void logVerbose(String log, StackTraceElement[] stes);
	
	void logWarning(String log);
	void logWarning(String log, boolean stacktrace);
	void logWarning(String log, StackTraceElement[] stes);
	
	void logError(String log);
	void logError(Throwable log);
	
	void logStatus(String log);
	void logStatus(String log, boolean stacktrace);
	void logStatus(String log, StackTraceElement[] stes);
}
