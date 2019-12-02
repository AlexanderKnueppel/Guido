package de.tubs.isf.guido.core.statistics.tests;

import javanpst.data.structures.dataTable.DataTable;
import javanpst.tests.oneSample.signTest.SignTest;
import javanpst.tests.oneSample.wilcoxonTest.WilcoxonTest;

public class PairedWilcoxonTest {
	public static void main(String args []){
		
		/**
		  *  Sample of two populations
		  */
		double samples [][] = {{51.2,45.8},
								{46.5,41.3},
								{24.1,15.8},
								{10.2,11.1},
								{65.3,58.5},
								{92.1,70.3},
								{30.3,31.6},
								{49.2,35.4},
								}; 
		
		//Create data structure
		DataTable data = new DataTable(samples);

		//Create test.
		SignTest test = new SignTest(data);
		WilcoxonTest test2 = new WilcoxonTest(data);
		
		//Run procedure
		test.doTest();
		
		//Print results 
		System.out.println("\nResults of Sign test:\n"+test.printReport());
		
		//Run procedure
		test2.doTest();
		
		//Print results 
		System.out.println("\nResults of Wilcoxon test:\n"+test2.printReport());				
	}
}
