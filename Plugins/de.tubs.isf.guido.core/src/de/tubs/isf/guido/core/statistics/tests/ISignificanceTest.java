package de.tubs.isf.guido.core.statistics.tests;

import de.tubs.isf.guido.core.experiments.AExperiment;

public interface ISignificanceTest {
	public double computeP(final AExperiment experiment);
}
