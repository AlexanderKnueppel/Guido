package de.tubs.isf.guido.core.statistics.tests;

import java.util.List;
import java.util.Optional;

import de.tubs.isf.guido.core.experiments.AExperiment;
import de.tubs.isf.guido.core.experiments.AExperiment.DataPoint;
import de.tubs.isf.guido.core.experiments.PairExperiment;
import javanpst.data.structures.dataTable.DataTable;
import javanpst.tests.oneSample.signTest.SignTest;
import javanpst.tests.oneSample.wilcoxonTest.WilcoxonTest;

public class PairedWilcoxon implements ISignificanceTest {

	@Override
	public Optional<Double> computeP(final AExperiment experiment) {

		if (!(experiment instanceof PairExperiment))
			throw new IllegalArgumentException("Paired wilcoxon test needs a paired experiment...");

		PairExperiment pexp = (PairExperiment) experiment;
		
		if(pexp.getNumberOfRows() == 0) {
			return Optional.empty();
		}

		double samples[][] = new double[pexp.getNumberOfRows()][2];

		for (int i = 0; i < pexp.getNumberOfRows(); ++i) {
			List<DataPoint> row = pexp.getRow(i);
			double effortA = (double) row.stream()
					.filter(dp -> dp.getLabel().equals(PairExperiment.BaseLabel.EXPERIMENT_EFFORT_A)).findFirst().get()
					.getValue();
			double effortB = (double) row.stream()
					.filter(dp -> dp.getLabel().equals(PairExperiment.BaseLabel.EXPERIMENT_EFFORT_B)).findFirst().get()
					.getValue();

			samples[i][0] = effortA;
			samples[i][1] = effortB;
		}

		DataTable data = new DataTable(samples);
		WilcoxonTest test = new WilcoxonTest(data);
		test.doTest();

		return Optional.of(test.getDoublePValue());
	}

	public static void main(String args[]) {

		/**
		 * Sample of two populations
		 */
		double samples[][] = { { 51.2, 45.8 }, { 46.5, 41.3 }, { 24.1, 15.8 }, { 10.2, 11.1 }, { 65.3, 58.5 },
				{ 92.1, 70.3 }, { 30.3, 31.6 }, { 49.2, 35.4 }, };

		// Create data structure
		DataTable data = new DataTable(samples);

		// Create test.
		SignTest test = new SignTest(data);
		WilcoxonTest test2 = new WilcoxonTest(data);

		// Run procedure
		test.doTest();

		// Print results
		System.out.println("\nResults of Sign test:\n" + test.printReport());

		// Run procedure
		test2.doTest();

		// Print results
		System.out.println("\nResults of Wilcoxon test:\n" + test2.printReport());
	}
}
