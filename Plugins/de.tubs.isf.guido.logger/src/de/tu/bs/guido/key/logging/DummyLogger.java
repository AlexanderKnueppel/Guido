package de.tu.bs.guido.key.logging;

public class DummyLogger implements Logger{

	@Override
	public void logVerbose(String log) {
	}

	@Override
	public void logVerbose(String log, boolean stacktrace) {
	}

	@Override
	public void logWarning(String log) {
	}

	@Override
	public void logWarning(String log, boolean stacktrace) {
	}

	@Override
	public void logError(String log) {
	}

	@Override
	public void logError(Throwable log) {
	}

	@Override
	public void logStatus(String log) {
	}

	@Override
	public void logStatus(String log, boolean stacktrace) {
	}

	@Override
	public void logVerbose(String log, StackTraceElement[] stes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logWarning(String log, StackTraceElement[] stes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logStatus(String log, StackTraceElement[] stes) {
		// TODO Auto-generated method stub
		
	}

}
