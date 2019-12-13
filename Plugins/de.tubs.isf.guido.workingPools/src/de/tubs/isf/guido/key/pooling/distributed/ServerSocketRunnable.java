package de.tubs.isf.guido.key.pooling.distributed;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class ServerSocketRunnable implements Runnable {

	private ServerSocket socket;
	private int clientNumber = 0;
	private final PoolStatus poolStatus;
	private final List<Runnable> jobs;
	private final NewResultNotifier nrn;
	private final Set<ThreadRunnableBundle> clients = new HashSet<>();

	public ServerSocketRunnable(ServerSocket socket, final PoolStatus poolStatus, final List<Runnable> jobs,
			NewResultNotifier nrn) {
		this.socket = socket;
		this.poolStatus = poolStatus;
		this.jobs = jobs;
		this.nrn = nrn;
	}

	public void stop() {
		try {
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		synchronized (clients) {
			for (ThreadRunnableBundle trb : clients) {
				try {
					trb.csr.stop();
					trb.t.interrupt();
				} catch (IOException e) {
				}
			}
		}
	}

	@Override
	public void run() {
		try {
			while (poolStatus.isRunning()) {
				Socket clientSocket = socket.accept();
				clientNumber++;
				ClientSocketRunnable csr = new ClientSocketRunnable(clientSocket, poolStatus, jobs, nrn);
				Thread t = new Thread(csr, "Client" + clientNumber);
				ThreadRunnableBundle trb = new ThreadRunnableBundle(csr, t);
				synchronized (clients) {
					clients.add(trb);
				}
				t.start();
			}
		} catch (SocketException e) {
			if (!socket.isClosed()) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ThreadRunnableBundle {

		public ThreadRunnableBundle(ClientSocketRunnable csr, Thread t) {
			super();
			this.csr = csr;
			this.t = t;
		}

		ClientSocketRunnable csr;
		Thread t;
	}
}
