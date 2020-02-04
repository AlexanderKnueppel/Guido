package de.tubs.isf.guido.core.statistics.tests;

import java.util.List;
import java.util.Optional;

import de.tubs.isf.guido.core.experiments.AExperiment;
import de.tubs.isf.guido.core.experiments.AExperiment.DataPoint;
import de.tubs.isf.guido.core.experiments.PairExperiment;
import javanpst.data.structures.dataTable.DataTable;
import javanpst.tests.countData.mcNemarTest.McNemarTest;
import javanpst.tests.oneSample.signTest.SignTest;
import javanpst.tests.oneSample.wilcoxonTest.WilcoxonTest;

public class McNemar implements ISignificanceTest {

	@Override
	public Optional<Double> computeP(final AExperiment experiment) {

		if (!(experiment instanceof PairExperiment))
			throw new IllegalArgumentException("McNemar test needs a paired experiment...");

		PairExperiment pexp = (PairExperiment) experiment;

		if (pexp.getNumberOfRows() == 0) {
			return Optional.empty();
		}

		double samples[][] = new double[pexp.getNumberOfRows()][2];

		/*
		 * B closed | open A closed | 0/0 | 0/1 open | 1/0 | 1/1
		 */
		int table[][] = { { 0, 0 }, { 0, 0 } };

		for (int i = 0; i < pexp.getNumberOfRows(); ++i) {
			List<DataPoint> row = pexp.getRow(i);
			boolean closedA = (boolean) row.stream()
					.filter(dp -> dp.getLabel().equals(PairExperiment.BaseLabel.EXPERIMENT_CLOSED_A)).findFirst().get()
					.getValue();
			boolean closedB = (boolean) row.stream()
					.filter(dp -> dp.getLabel().equals(PairExperiment.BaseLabel.EXPERIMENT_CLOSED_B)).findFirst().get()
					.getValue();

			samples[i][0] = closedA ? 1 : 0;
			samples[i][1] = closedB ? 1 : 0;

			if (closedA && closedB) {
				table[0][0]++;
			} else if (closedA && !closedB) {
				table[0][1]++;
			} else if (!closedA && closedB) {
				table[1][0]++;
			} else {
				table[1][1]++;
			}
		}

		DataTable data = new DataTable(samples);
		McNemarTest test = new McNemarTest(data);
		test.doTest();

		if (table[0][0] + table[0][1] != table[0][0] + table[1][0]
				&& table[1][0] + table[1][1] != table[0][1] + table[1][1])
			return Optional.of(test.getExactPValue());

		return Optional.empty();
	}
}
