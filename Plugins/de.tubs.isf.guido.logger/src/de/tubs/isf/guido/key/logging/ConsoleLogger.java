package de.tubs.isf.guido.key.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ConsoleLogger implements Logger {
	private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Override
	public void logVerbose(String log) {
		logVerbose(log, false);
	}

	@Override
	public void logVerbose(String log, boolean stacktrace) {
		logVerbose(log, stacktrace ? getStackTrace() : null);
	}

	@Override
	public void logVerbose(String log, StackTraceElement[] stacktrace) {
		System.out.println("@Verbose "+dateFormat.format(new Date())+": "+log);
		if(stacktrace != null){
			System.out.println("@StackTrace:");
			printStackTrace(stacktrace);
		}
	}

	@Override
	public void logWarning(String log) {
		logWarning(log, false);
	}

	@Override
	public void logWarning(String log, boolean stacktrace) {
		logWarning(log, stacktrace ? getStackTrace() : null);
	}

	@Override
	public void logWarning(String log, StackTraceElement[] stacktrace) {
		System.out.println("@Warning "+dateFormat.format(new Date())+": "+log);
		if(stacktrace != null){
			System.out.println("@StackTrace:");
			printStackTrace(stacktrace);
		}
	}

	@Override
	public void logError(String log) {
		System.err.println("@Error: " +log);
	}

	@Override
	public void logError(Throwable log) {
		log.printStackTrace();
	}

	@Override
	public void logStatus(String log) {
		logStatus(log, false);
	}

	@Override
	public void logStatus(String log, boolean stacktrace) {
		logStatus(log, stacktrace ? getStackTrace() : null);
	}

	@Override
	public void logStatus(String log, StackTraceElement[] stacktrace) {
		System.out.println("@Status "+dateFormat.format(new Date())+": "+log);
		if(stacktrace != null){
			System.out.println("@StackTrace:");
			printStackTrace(stacktrace);
		}
	}
	
	private StackTraceElement[] getStackTrace(){
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		return Arrays.copyOfRange(ste, 3, ste.length);
	}

	private void printStackTrace(StackTraceElement[] ste) {
		for (int i = 0; i < ste.length; i++) {
			StackTraceElement stackTraceElement = ste[i];
			System.out.println(stackTraceElement.toString());
		}
	}

}
