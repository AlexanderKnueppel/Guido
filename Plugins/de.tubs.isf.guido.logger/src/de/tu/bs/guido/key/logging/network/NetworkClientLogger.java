package de.tu.bs.guido.key.logging.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import de.tu.bs.guido.key.logging.Logger;
import de.tu.bs.guido.key.logging.network.order.LogLevel;
import de.tu.bs.guido.key.logging.network.order.LogOrder;

public class NetworkClientLogger implements Logger{

	private final ObjectOutputStream outToClient;
	private final Socket socket;
	
	public NetworkClientLogger(String uri, int port) throws UnknownHostException, IOException{
		socket = new Socket(uri, port);
		outToClient = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public void stop() throws IOException{
		socket.close();
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
	public void logVerbose(String log, StackTraceElement[] stes) {
		sendLog(LogLevel.VERBOSE, log, stes);
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
	public void logWarning(String log, StackTraceElement[] stes) {
		sendLog(LogLevel.WARN, log, stes);
	}

	@Override
	public void logError(String log) {
		sendLogOrder(new LogOrder(log, null));
	}

	@Override
	public void logError(Throwable log) {
		sendLogOrder(new LogOrder(null, log));
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
	public void logStatus(String log, StackTraceElement[] stes) {
		sendLog(LogLevel.STATUS, log, stes);
	}
	
	private StackTraceElement[] getStackTrace(){
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		return Arrays.copyOfRange(ste, 3, ste.length);
	}
	
	private void sendLog(LogLevel level, String log, StackTraceElement[] stes){
		LogOrder lo = stes == null ? new LogOrder(level, log) : new LogOrder(level, log, stes);
		sendLogOrder(lo);
	}
	
	private void sendLogOrder(LogOrder order){
		synchronized (outToClient) {
			try {
				outToClient.writeObject(order);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
