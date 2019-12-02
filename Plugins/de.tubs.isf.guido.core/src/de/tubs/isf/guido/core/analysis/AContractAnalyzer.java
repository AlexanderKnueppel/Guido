package de.tubs.isf.guido.core.analysis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.tubs.isf.guido.core.analysis.IAnalyzer.LanguageConstruct;

public abstract class AContractAnalyzer implements IAnalyzer {
	
	protected File sourceFile = null;
	protected List<LanguageConstruct> currentConstructs;
	protected int linesOfContract = -1;
	protected String contract;

	protected enum ContractConstruct implements LanguageConstruct {
		QUANTIFIERS_TRUE("quantifiers_true"), QUANTIFIERS_FALSE("quantifiers_false"),
		GEQ_LOS("geq_linesOfSpec", new Integer(4)), LT_LOS("lt_linesOfSpec", new Integer(3)),
		IMPLICATION_TRUE("implication_true"), IMPLICATION_FALSE("implication_false"),
		ASSIGNABLE_TRUE("assignable_true"), ASSIGNABLE_FALSE("assignable_false"),
		BEHAVIOR_NORMAL("behavior_normal"), BEHAVIOR_EXECPTIONAL("behavior_exceptional"),
		PRECONDITION_TRUE("precondition_true"), PRECONDITION_FALSE("precondition_false"),
		POSTCONDITION_TRUE("postcondition_true"), POSTCONDITION_FALSE("postcondition_false")
		;

		private String representation = "";
		private List<Object> values = null;

		ContractConstruct(String construct, Object... n) {
			this.representation = construct;
			for (Object v : n) {
				if (values == null)
					values = new ArrayList<Object>();
				values.add(v);
			}
		}

		ContractConstruct(String construct) {
			this.representation = construct;
		}

		public String getLanguageConstruct() {
			return representation;
		}

		public boolean isParameterized() {
			return values != null && !values.isEmpty();
		}

		public List<Object> getParameters() {
			return values;
		}

		public String toString() {
			return this.representation;
		}
	}

	public AContractAnalyzer(String contract) {
		this.contract = contract;
	}

	public int getLinesOfCode() {
		return linesOfContract;
	}

	@Override
	public boolean hasLanguageConstruct(LanguageConstruct lc) {
		// TODO Auto-generated method stub
		return currentConstructs.contains(lc);
	}
}
