package de.tubs.isf.guido.key.pooling.distributed;

import java.util.Observable;

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
public class PoolStatus extends Observable {

	private boolean running;

	public PoolStatus(boolean running) {
		super();
		this.running = running;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void setAndNotify(boolean running) {
		setRunning(running);

		setChanged();
		notifyObservers(running);
	}

}
