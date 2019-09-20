package de.tu.bs.guido.key.logging.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import de.tu.bs.guido.key.logging.Logger;

public class LoggingServerSocketRunnable implements Runnable{

	private final ServerSocket socket;
	private final Logger logTo;
	private final IntegerWrapper iw = new IntegerWrapper();
	
	public LoggingServerSocketRunnable(Logger logTo, int port) throws IOException{
		this.logTo = logTo;
		this.socket = ServerSocketFactory.getDefault().createServerSocket(port);
	}
	
	public void close() throws IOException{
		socket.close();
	}
	
	@Override
	public void run() {
		while(!socket.isClosed()){
			try {
				Socket client = socket.accept();
				LoggingSocketRunnable clientRunnable = new LoggingSocketRunnable(client, iw.getNext(), logTo);
				new Thread(clientRunnable).start();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	private static class IntegerWrapper{
		
		private int next = 0;
		
		public synchronized int getNext(){
			return next++;
		}
	}

}
