package de.tubs.isf.guido.key.pooling.distributed;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

/**
*
* @author  Carsten Padylla
* @author  Maren Süwer
* @author  Alexander Knüppel
* 
* @version 1.0
* 
* @since   2018-01-01 
*/
public class ClientSocketRunnable implements Runnable {

	private Socket client;
	private final PoolStatus poolStatus;
	private final List<Runnable> jobs;
	private final NewResultNotifier nrn;
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;

	public ClientSocketRunnable(Socket client, final PoolStatus poolStatus, final List<Runnable> jobs,
			NewResultNotifier nrn) throws IOException {
		this.client = client;
		this.poolStatus = poolStatus;
		this.jobs = jobs;
		this.nrn = nrn;
		try {
			inFromClient = new ObjectInputStream(client.getInputStream());
			outToClient = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			client.close();
		}
	}

	public void stop() throws IOException {
		client.close();
	}

	@Override
	public void run() {
		System.out.println(new Date().toString() + " | Connection established: " + Thread.currentThread().getName());
		while (poolStatus.isRunning() && client.isConnected() && !client.isClosed()) {
			Runnable r = null;
			synchronized (jobs) {
				if (!jobs.isEmpty()) {
					r = jobs.remove(0);
				} else {
					try {
						poolStatus.setAndNotify(false);
						jobs.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if (r != null) {
				try {
					outToClient.writeObject(r);
					Object result = inFromClient.readObject();
					if (result == null) {
						throw new NullPointerException();
					} else if (result instanceof Throwable) {
						System.err.println(Thread.currentThread().getName() + " had the following error: ");
						((Throwable) result).printStackTrace();
					} else {
						nrn.notifyAboutResult(result);
					}
				} catch (SocketException | EOFException e) {
					System.out.println(Thread.currentThread().getName() + " seems to be dead...");
					try {
						client.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					synchronized (jobs) {
						jobs.add(0, r);
					}
				} catch (Exception e) {
					System.out.println("Exception in " + Thread.currentThread().getName());
					try {
						client.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("Socket closed in " + Thread.currentThread().getName());
					synchronized (jobs) {
						jobs.add(r);
					}
					System.out.println("Job readded by " + Thread.currentThread().getName());
					e.printStackTrace();
				}
			}
		}
	}
}
