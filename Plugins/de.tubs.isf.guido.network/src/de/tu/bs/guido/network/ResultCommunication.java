package de.tu.bs.guido.network;

import java.io.Serializable;
import java.util.List;

import de.tu.bs.guido.verification.system.Job;
import de.tu.bs.guido.verification.system.Result;




public class ResultCommunication implements Serializable{

	private static final long serialVersionUID = 6078326954303589087L;
	private final Job job;
	private final List<? extends Result> results;
	public Job getJob() {
		return job;
	}
	public List<? extends Result> getResults() {
		return results;
	}
	public ResultCommunication(Job job, List<? extends Result> intermediate) {
		super();
		this.job = job;
		this.results = intermediate;
	}
	
}
