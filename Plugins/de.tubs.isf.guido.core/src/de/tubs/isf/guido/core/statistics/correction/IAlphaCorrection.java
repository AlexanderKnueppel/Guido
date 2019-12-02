package de.tubs.isf.guido.core.statistics.correction;

import de.tubs.isf.guido.core.statistics.Hypotheses;

public interface IAlphaCorrection {
	double apply(final Hypotheses hypotheses, final double uncompensatedAlpha);
}
