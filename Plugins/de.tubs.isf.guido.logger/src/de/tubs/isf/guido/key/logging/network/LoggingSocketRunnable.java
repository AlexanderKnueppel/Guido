package de.tubs.isf.guido.key.logging.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import de.tubs.isf.guido.key.logging.Logger;
import de.tubs.isf.guido.key.logging.network.order.LogOrder;
import de.tubs.isf.guido.key.logging.network.order.LogOrderLoggerMapper;

public class LoggingSocketRunnable implements Runnable{

	private final int number;
	private final Logger logTo;
	private final Socket client;

	private ObjectInputStream inFromClient;
	public LoggingSocketRunnable(Socket client, int number, Logger logTo) throws IOException {
		super();
		this.number = number;
		this.logTo = logTo;
		this.client = client;
		
		inFromClient =  new ObjectInputStream(client.getInputStream());
	}
	@Override
	public void run() {
		while (client.isConnected() && !client.isClosed()) {
			try {
				Object text;
				text = inFromClient.readObject();
				if(!(text instanceof LogOrder)){
					logTo.logError(new RuntimeException("Client has send a strange object... "+text.getClass().getName()));
				} else {
					LogOrder loggingOrder = (LogOrder) text;
					synchronized (logTo) {
						LogOrderLoggerMapper.map("Got a message from client "+number+" with IP "+client.getInetAddress(), loggingOrder, logTo);
					}
				}
			} catch (SocketException e) {
				try {
					client.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (ClassNotFoundException | IOException e) {
				logTo.logError(e);
			}
		}
	}
	
	
	
}
