package de.tu.bs.guido.key.pooling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
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
public class ThreadPool extends Observable implements WorkingPool {

	private final List<Runnable> jobs;
	private final Set<GetRunnable> runnables;

	public ThreadPool(int size) {
		jobs = new ArrayList<>();
		runnables = new HashSet<>();

		for (int i = 0; i < size; i++) {
			GetRunnable r = new GetRunnable();
			runnables.add(r);
			new Thread(r).start();
		}
	}

	public void addJob(Runnable r) {
		synchronized (jobs) {
			jobs.add(r);
			jobs.notifyAll();
		}
	}

	public void stopWorking() {
		for (GetRunnable run : runnables) {
			run.stop();
		}
		synchronized (jobs) {
			jobs.notifyAll();
		}
	}

	public int getWaitingProcesses() {
		int value;
		synchronized (jobs) {
			value = jobs.size();
		}
		return value;
	}

	private class GetRunnable implements Runnable {

		private boolean running = true;

		public void stop() {
			running = false;
		}

		@Override
		public void run() {
			while (running) {
				Runnable r = null;
				synchronized (jobs) {
					if (!jobs.isEmpty()) {
						r = jobs.remove(0);
					} else {
						try {
							ThreadPool.this.setChanged();
							ThreadPool.this.notifyObservers();
							jobs.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if (r != null) {
					r.run();
				}
			}
		}

	}

	@Override
	public boolean isRunning() {
		synchronized (jobs) {
			return !jobs.isEmpty();
		}
	}

	@Override
	public Collection<WorkingInfo> getWorkingInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
