package de.tubs.isf.guido.core.experiments;

import java.util.stream.Collectors;

public class PairExperiment extends AExperiment {

	protected enum BaseLabel implements Label {
		EXPERIMENT_NUMBER("ExpNum", ""), EXPERIMENT_CLOSED_A("Closed - ", "A"), EXPERIMENT_CLOSED_B("Closed - ", "B"),
		EXPERIMENT_EFFORT_A("Effort - ", "A"), EXPERIMENT_EFFORT_B("Effort - ", "B");

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

	PairExperiment(String name) {
		super(name);
	}

	@Override
	protected void addColumns() {		
		addColumn(new DataColumn<Integer>(BaseLabel.EXPERIMENT_NUMBER));
		addColumn(new DataColumn<Boolean>(BaseLabel.EXPERIMENT_CLOSED_A, (dp)->{return (boolean)dp.getValue()==true ? "X" : "";}));
		addColumn(new DataColumn<Boolean>(BaseLabel.EXPERIMENT_CLOSED_B));
		addColumn(new DataColumn<Integer>(BaseLabel.EXPERIMENT_EFFORT_A));
		addColumn(new DataColumn<Integer>(BaseLabel.EXPERIMENT_EFFORT_B));
	}
	
	public void setOptions(String optionA, String optionB) {
		((BaseLabel)getColumn(BaseLabel.EXPERIMENT_CLOSED_A).getLabel()).setOption(optionA);
		((BaseLabel)getColumn(BaseLabel.EXPERIMENT_CLOSED_B).getLabel()).setOption(optionB);
		((BaseLabel)getColumn(BaseLabel.EXPERIMENT_EFFORT_A).getLabel()).setOption(optionA);
		((BaseLabel)getColumn(BaseLabel.EXPERIMENT_EFFORT_B).getLabel()).setOption(optionB);
		
	}

	public static void main(String[] args) {
		PairExperiment be = new PairExperiment("Exp1");
		
		be.addRow(1, true, false, 10, 20);
		be.addRow(2, false, false, 12, 20);
		be.addRow(3, true, false, 10, 123);
		
		be.setOptions("On", "Off");

		System.out.println(be.getHeader().stream().collect(Collectors.joining(" | ")));
		be.getRows().stream().forEach(row -> {
			System.out.println(row.stream().map(r -> r.toString()).collect(Collectors.joining(" | ")));
		});
	}
}
