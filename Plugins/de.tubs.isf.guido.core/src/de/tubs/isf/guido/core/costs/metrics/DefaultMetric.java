package de.tubs.isf.guido.core.costs.metrics;

public class DefaultMetric implements IMetric {

	private double alpha = 0.05;
	
	public DefaultMetric() {
	}
	
	public DefaultMetric(double alpha) {
		this.alpha = alpha;
	}
	@Override
	public double compute(double p) {
		// TODO Auto-generated method stub
		return alpha-p;
	}

}
