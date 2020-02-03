package de.tubs.isf.guido.core.statistics.tests;

import java.util.Optional;

import de.tubs.isf.guido.core.experiments.AExperiment;

public interface ISignificanceTest {
	public Optional<Double> computeP(final AExperiment experiment);
}
