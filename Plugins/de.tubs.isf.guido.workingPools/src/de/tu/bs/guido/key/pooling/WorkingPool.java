package de.tu.bs.guido.key.pooling;

import java.util.Collection;
import java.util.Observer;

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
public interface WorkingPool {
	void addJob(Runnable r);

	void stopWorking();

	boolean isRunning();

	int getWaitingProcesses();

	void addObserver(Observer o);

	Collection<WorkingInfo> getWorkingInfo();
}
