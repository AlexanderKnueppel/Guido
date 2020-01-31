package de.tubs.isf.guido.core.statistics.tests;

import java.util.List;
import java.util.stream.Collectors;

import de.tubs.isf.guido.core.experiments.AExperiment;
import de.tubs.isf.guido.core.experiments.AExperiment.DataPoint;
import de.tubs.isf.guido.core.experiments.PairExperiment;
import de.tubs.isf.guido.core.experiments.SingleExperiment;
import de.tubs.isf.guido.core.statistics.Hypothesis;
import javanpst.data.structures.dataTable.DataTable;
import javanpst.tests.countData.contingencyCoefficient.ContingencyCoefficient;
import javanpst.tests.oneSample.signTest.SignTest;
import javanpst.tests.oneSample.wilcoxonTest.WilcoxonTest;

public class SingleWilcoxonTest implements ISignificanceTest {

	@Override
	public double computeP(final AExperiment experiment) {

		if (!(experiment instanceof SingleExperiment))
			throw new IllegalArgumentException("Single wilcoxon test needs a single experiment...");

		SingleExperiment exp = (SingleExperiment) experiment;

		double samples[][] = new double[exp.getNumberOfRows()][1];

		for (int i = 0; i < exp.getNumberOfRows(); ++i) {
			List<DataPoint> row = exp.getRow(i);
			double effort = (double) row.stream()
					.filter(dp -> dp.getLabel().equals(SingleExperiment.BaseLabel.EXPERIMENT_EFFORT)).findFirst().get()
					.getValue();

			samples[i][0] = effort;
		}
		
		DataTable data = new DataTable(samples);
		WilcoxonTest test = new WilcoxonTest(data);
		test.doTest();
		
		return test.getDoublePValue();
	}
	
	public static void main(String args []){
		
		/**
		 * Contingency matrix   
		 */
		double samples [][] = {{114,37},{0,142}};
	
		//Data is formatted
		DataTable data = new DataTable(samples);
	
		//Create test
		ContingencyCoefficient test = new ContingencyCoefficient(data);
	
		//Run Contingency Coefficient analysis
		test.doTest();
		
		//Print Contingency analysis
		System.out.println("Results of Contingency analysis:\n"+test.printReport());
		
	}//end-method
}
