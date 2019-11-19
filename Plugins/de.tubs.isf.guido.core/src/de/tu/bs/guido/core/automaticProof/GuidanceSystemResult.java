package automaticProof;

import java.io.Serializable;
import java.util.Map;

public class GuidanceSystemResult implements Serializable {

	private static final long serialVersionUID = 4015855254309825043L;
	
	private final String name;
	private final boolean closed;
	private final int steps;
	private final long timeInMillis;
	private int execution;
	private long executionTime;
	private int executionSteps;
	private final Map<String, String> options;
	private final Map<String, String> taclets;
	
	public GuidanceSystemResult(String name, boolean closed, int steps, long timeInMillis2, int execution, long elapsedTime,
			int executionSteps, Map<String, String> options, Map<String, String> taclets) {
		super();
		this.executionTime = elapsedTime;
		this.execution = execution;
		this.name = name;
		this.closed = closed;
		this.steps = steps;
		this.timeInMillis = timeInMillis2;
		this.options = options;
		this.taclets = taclets;
		this.executionSteps = executionSteps;
	}
	
	public String getName() {
		return name;
	}
	public boolean isClosed() {
		return closed;
	}
	public int getSteps() {
		return steps;
	}
	
	public void update(int executionSteps, long executionTime ){
		this.executionSteps = executionSteps;
		//this.execution++;
		this.executionTime += executionTime;
	}
	
	public long getTimeInMillis() {
		return timeInMillis;
	}
	public Map<String, String> getOptions() {
		return options;
	}
	public Map<String, String> getTaclets() {
		return taclets;
	}
	public int getExecution() {
		return execution;
	}

	public long getExecutionTime() {
		return executionTime;
	}
	
	public String getHtml(){
		return  this.name
				+ "\n"
				+ "closed?: " + this.closed
				+ "\n"
				+ "Execution: " + this.executionSteps + " steps and "+ this.execution + " ms"
				+ "\n"
				+ "Time: " + this.executionTime
				+ "\n"
				+ "Latest execution: " + this.steps + " steps and " + this.timeInMillis + " ms\n";
	}

	public int getExecutionSteps() {
		return executionSteps;
	}
	
}