package de.tubs.isf.guido.core.statistics.correction;

import de.tubs.isf.guido.core.statistics.Hypotheses;

public class SidakCorrection implements IAlphaCorrection {

	@Override
	public double apply(Hypotheses hypotheses, double uncompensatedAlpha) {
		// TODO Auto-generated method stub
		return 1 - Math.pow(1-uncompensatedAlpha, 1.0/hypotheses.getHypotheses().size());
	}

}
