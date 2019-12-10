package de.tu.bs.guido.network;

import java.io.Serializable;
import java.util.List;

import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.Result;




public class ResultCommunication implements Serializable{

	private static final long serialVersionUID = 6078326954303589087L;
	private final IJob job;
	private final List<? extends Result> results;
	public IJob getJob() {
		return job;
	}
	public List<? extends Result> getResults() {
		return results;
	}
	public ResultCommunication(IJob job, List<? extends Result> intermediate) {
		super();
		this.job = job;
		this.results = intermediate;
	}
	
}
