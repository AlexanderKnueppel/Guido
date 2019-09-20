package de.tu.bs.guido.key.pooling;

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
public class WorkingInfo {

	private final String identifier;
	private final Runnable currentJob;

	public WorkingInfo(String identifier, Runnable currentJob) {
		super();
		this.identifier = identifier;
		this.currentJob = currentJob;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Runnable getCurrentJob() {
		return currentJob;
	}

}
