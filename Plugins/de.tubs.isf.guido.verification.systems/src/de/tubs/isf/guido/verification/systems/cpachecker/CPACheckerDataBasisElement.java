package de.tubs.isf.guido.verification.systems.cpachecker;

import java.util.List;
import java.util.Map;

import de.tubs.isf.guido.core.databasis.DefaultDataBasisElement;

public class CPACheckerDataBasisElement  extends DefaultDataBasisElement {

	private static final long serialVersionUID = -1935265156680654042L;

	private long CPUTime;
	private long totalTime;
	private long totalVirtMem;
	
	public CPACheckerDataBasisElement(String name, boolean closed, long timeInMillis,
			long CPUTime, long totalTime, long totalVirtMem,List<String> languageConstructs, Map<String, String> options) {
		super(name, closed, timeInMillis, languageConstructs, options);
		this.CPUTime = CPUTime;
		this.totalTime = totalTime;
		this.totalVirtMem = totalVirtMem;
	}
	public long getCpuTime() {
		return CPUTime;
	}

	public void setCPUTime(long CPUTime) {
		this.CPUTime = CPUTime;
	}
	
	public long getTotalTime() {
		return totalTime;
	}
	
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	public long gettotalVirtMem() {
		return totalVirtMem;
	}
	
	public void settotalVirtMem(long totalVirtMem) {
		this.totalVirtMem = totalVirtMem;
	}

	@Override
	public boolean isProvable() {
		// TODO Auto-generated method stub
		return isClosed();
	}

}
