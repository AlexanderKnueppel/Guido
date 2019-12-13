package de.tubs.isf.guido.key.logging.network;

import java.io.File;
import java.io.IOException;

import de.tubs.isf.guido.key.logging.FileLogger;
import de.tubs.isf.guido.key.logging.Logger;

public class LoggingServer {
	
	public static final int PORT = 19877;

	public static void main(String[] args) throws IOException {
		final Logger logTo = new FileLogger(new File("Log.txt"));
		
		LoggingServerSocketRunnable lssr = new LoggingServerSocketRunnable(logTo, PORT);
		new Thread(lssr).start();
	}
}
