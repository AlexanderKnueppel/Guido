package de.tu.bs.guido.network;

import java.io.Serializable;
import java.util.List;

import de.tu.bs.guido.key.simulator.Result;




public class ResultCommunication implements Serializable{

	private static final long serialVersionUID = 6078326954303589087L;
	private final Job job;
	private final List<Result> results;
	public Job getJob() {
		return job;
	}
	public List<Result> getResults() {
		return results;
	}
	public ResultCommunication(Job job, List<Result> results) {
		super();
		this.job = job;
		this.results = results;
	}
	
}
