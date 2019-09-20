package de.tu.bs.guido.key.pooling.distributed;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.net.ServerSocketFactory;

import de.tu.bs.guido.key.pooling.WorkingInfo;
import de.tu.bs.guido.key.pooling.WorkingPool;

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
public class DistributedWorkingPool extends Observable implements WorkingPool, Observer {

	private final List<Runnable> jobs = new ArrayList<>();

	private final ServerSocket server;
	private final Thread serverThread;
	private final ServerSocketRunnable ssr;

	private final PoolStatus status;

	public DistributedWorkingPool(int port, NewResultNotifier nrn) throws IOException {
		server = ServerSocketFactory.getDefault().createServerSocket(port);
		status = new PoolStatus(true);
		status.addObserver(this);
		ssr = new ServerSocketRunnable(server, status, jobs, nrn);

		serverThread = new Thread(ssr, "ServerSocket");
		serverThread.start();
	}

	@Override
	public void addJob(Runnable r) {
		synchronized (jobs) {
			jobs.add(r);
			jobs.notifyAll();
		}
	}

	@Override
	public void stopWorking() {
		status.setRunning(false);
		synchronized (jobs) {
			jobs.notifyAll();
		}
		serverThread.interrupt();
		ssr.stop();
	}

	@Override
	public int getWaitingProcesses() {
		int value;
		synchronized (jobs) {
			value = jobs.size();
		}
		return value;
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}

	@Override
	public boolean isRunning() {
		return status.isRunning();
	}

	@Override
	public Collection<WorkingInfo> getWorkingInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
