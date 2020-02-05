package de.tubs.isf.guido.core.experiments;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

public class SingleExperiment extends AExperiment {

	public enum BaseLabel implements Label {
		EXPERIMENT_NUMBER("ExpNum", ""), EXPERIMENT_IDENTIFIER("Identifier", ""), EXPERIMENT_CLOSED("Closed - ", "A"),
		EXPERIMENT_EFFORT("Effort - ", "A");

		private String label = "";
		private String option = "";

		BaseLabel(String label, String option) {
			this.label = label;
			this.option = option;
		}

		public void setOption(String option) {
			this.option = option;
		}

		public String getLabel() {
			return label;
		}

		public String toString() {
			return this.label + option;
		}
	}

	SingleExperiment(String name) {
		super(name);
	}

	@Override
	protected void addColumns() {
		addColumn(new DataColumn<Integer>(BaseLabel.EXPERIMENT_NUMBER));
		addColumn(new DataColumn<Integer>(BaseLabel.EXPERIMENT_IDENTIFIER));
		addColumn(new DataColumn<Boolean>(BaseLabel.EXPERIMENT_CLOSED, (dp) -> {
			return (boolean) dp.getValue() == true ? "X" : "";
		}, (str) -> {
			return str.trim().equals("X") ? true : false;
		}));
		addColumn(new DataColumn<Integer>(BaseLabel.EXPERIMENT_EFFORT));
	}

	public void setOption(String option) {
		((BaseLabel) getColumn(BaseLabel.EXPERIMENT_CLOSED).getLabel()).setOption(option);
		((BaseLabel) getColumn(BaseLabel.EXPERIMENT_EFFORT).getLabel()).setOption(option);
	}

	public static void main(String[] args) {
		SingleExperiment be = new SingleExperiment("Exp1");

		be.addRow(1, "", true, 20);
		be.addRow(2, "", false, 20);
		be.addRow(3, "", true, 123);

		be.setOption("On");

		System.out.println(be.getHeader().stream().collect(Collectors.joining(" | ")));
		be.getRows().stream().forEach(row -> {
			System.out.println(row.stream().map(r -> r.toString()).collect(Collectors.joining(" | ")));
		});
		
		try {
			be.writeToFile(new File("testData/ExpSingle.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			AExperiment ae = AExperiment.readFromFile(new File("testData/ExpSingle.txt"));

			if (ae instanceof SingleExperiment) {
				System.out.println("It works!");
			}

			System.out.println(ae.getHeader().stream().collect(Collectors.joining("|")));
			ae.getRows().stream().forEach(row -> {
				System.out.println(row.stream().map(r -> r.toString()).collect(Collectors.joining("|")));
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
