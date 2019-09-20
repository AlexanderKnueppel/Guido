package de.tu.bs.guido.key.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class FileLogger implements Logger {
	private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final File logFile;
	private final BufferedWriter writer;

	public FileLogger(File log) throws IOException {
		this.logFile = log;
		writer = new BufferedWriter(new FileWriter(logFile,true));
	}

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
		synchronized (logFile) {
			printLine("@Verbose " + dateFormat.format(new Date()) + ": " + log);
			if (stacktrace != null) {
				printLine("@StackTrace:");
				printStackTrace(stacktrace);
			}
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
		synchronized (logFile) {
			printLine("@Warning " + dateFormat.format(new Date()) + ": " + log);
			if (stacktrace != null) {
				printLine("@StackTrace:");
				printStackTrace(stacktrace);
			}
		}
	}

	@Override
	public void logError(String log) {
		printLine("@Error " + dateFormat.format(new Date()) + ": " + log);
	}

	public void logError(Throwable log) {
		synchronized (logFile) {
			printLine("@Error " + dateFormat.format(new Date()) + ": " + log.getMessage());
			printStackTrace(log.getStackTrace());
			if (log.getCause() != null) {
				printCause(log.getCause());
			}
		}
	}
	
	private void printCause(Throwable cause){
		printLine("@Caused by:"+ cause.getMessage());
		printStackTrace(cause.getStackTrace());
		if (cause.getCause() != null) {
			printCause(cause.getCause());
		}
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
		synchronized (logFile) {
			printLine("@Status " + dateFormat.format(new Date()) + ": " + log);
			if (stacktrace != null) {
				printLine("@StackTrace:");
				printStackTrace(stacktrace);
			}
		}
	}

	private StackTraceElement[] getStackTrace() {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		return Arrays.copyOfRange(ste, 3, ste.length);
	}

	private void printStackTrace(StackTraceElement[] ste) {
		String[] lines = new String[ste.length];
		for (int i = 0; i < ste.length; i++) {
			StackTraceElement stackTraceElement = ste[i];
			lines[i] = stackTraceElement.toString();
		}
		printLine(lines);
	}

	private void printLine(String... file) {
		try {
			for (String string : file) {
				writer.write(string);
				writer.newLine();
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
