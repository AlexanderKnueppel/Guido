package de.tu.bs.guido.key.network;

import java.io.Serializable;
import java.util.List;

import analyzer.Result;
import automaticProof.GuidanceSystemResult;


public class ResultCommunication implements Serializable{

	private static final long serialVersionUID = 6078326954303589087L;
	private final Job job;
	private final GuidanceSystemResult result;
	public Job getJob() {
		return job;
	}
	public GuidanceSystemResult getResults() {
		return result;
	}

	public ResultCommunication(Job job, GuidanceSystemResult results) {
		super();
		this.job = job;
		this.result = results;
	}
	
	
}
