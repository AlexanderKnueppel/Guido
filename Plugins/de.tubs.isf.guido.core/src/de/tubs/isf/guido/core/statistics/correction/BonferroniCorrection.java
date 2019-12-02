package de.tubs.isf.guido.core.statistics.correction;

import de.tubs.isf.guido.core.statistics.Hypotheses;

public class BonferroniCorrection implements IAlphaCorrection {

	@Override
	public double apply(Hypotheses hypotheses, double uncompensatedAlpha) {
		final int K = hypotheses.getHypotheses().size();
        final int N = K * (K-1)/2;
        return adjust(uncompensatedAlpha, N);
	}

	public static double adjust(double uncompensatedAlpha, int numComparisons) {
		return uncompensatedAlpha / numComparisons;
	}
}
