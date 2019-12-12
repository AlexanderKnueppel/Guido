package de.tubs.isf.guido.key.pooling.distributed;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Carsten Padylla
 * @author Maren Süwer
 * @author Alexander Knüppel
 * 
 * @version 1.0
 * 
 * @since 2018-01-01
 */
public class Client {
	int jobs = 0;
	private static final int MAX_JOBS = 1;
	private static final int MAX_RUNTIME = 60 * 1000;

	private boolean restart = false;

	public Client(final String ip, int port) throws UnknownHostException, IOException, ClassNotFoundException {
		Socket socket = new Socket(ip, port);
		ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inFromClient = new ObjectInputStream(socket.getInputStream());

		long startTime = System.currentTimeMillis();
		long currentTime = System.currentTimeMillis();
		while (socket.isConnected() && jobs < MAX_JOBS && (currentTime - startTime) < MAX_RUNTIME) {
			jobs++;
			ResultRunnable job = (ResultRunnable) inFromClient.readObject();
			job.setIPAdress(ip);
			job.run();
			Object result = job.getResult();
			try {
				outToClient.writeObject(result);
			} catch (NotSerializableException e) {
				outToClient.writeObject(e);
			}
			currentTime = System.currentTimeMillis();
		}

		if (!socket.isClosed()) {
			restart = true;
		}

		socket.close();
	}

	public boolean isRestart() {
		return restart;
	}
}
