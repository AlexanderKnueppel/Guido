package de.tubs.isf.guido.core.analysis;

import java.util.ArrayList;
import java.util.List;

public class JMLContractAnalyzer extends AContractAnalyzer {

	public JMLContractAnalyzer(String contract) {
		super(contract);
		currentConstructs = new ArrayList<LanguageConstruct>();
	}

	public JMLContractAnalyzer() {
		this("");
	}

	@Override
	public List<LanguageConstruct> analyze() {
		List<LanguageConstruct> lcs = new ArrayList<LanguageConstruct>();

		if (contract == null) {
			return lcs;
		}

		linesOfContract = contract.split(System.getProperty("line.separator")).length;
		hasQuantifiers(lcs);
		hasAssignable(lcs);
		hasImplication(lcs);
		hasPreconditions(lcs);
		hasPostconditions(lcs);
		behavior(lcs);
		isLoopinvariant(lcs);
		currentConstructs = lcs;

		return lcs;
	}

	public void isLoopinvariant(List<LanguageConstruct> lcs) {
		if (contract.toString().contains("loop_invariant")) {
			lcs.add(ContractConstruct.IS_LOOP_INVARIANT);
		}
	}

	public void hasQuantifiers(List<LanguageConstruct> lcs) {
		if (contract.toString().contains("\\forall") || contract.toString().contains("\\exists")) {
			lcs.add(ContractConstruct.QUANTIFIERS_TRUE);
		} else {
			lcs.add(ContractConstruct.QUANTIFIERS_FALSE);
		}

	}

	public void hasImplication(List<LanguageConstruct> lcs) {
		if (contract.toString().contains("==>")) {
			lcs.add(ContractConstruct.IMPLICATION_TRUE);
		} else {
			lcs.add(ContractConstruct.IMPLICATION_FALSE);
		}
	}

	public void behavior(List<LanguageConstruct> lcs) {
		if (contract.toString().contains(" normal_behavior")) {
			lcs.add(ContractConstruct.BEHAVIOR_NORMAL);
		} else if (contract.toString().contains(" exceptional_behavior")) {
			lcs.add(ContractConstruct.BEHAVIOR_EXECPTIONAL);
		} else {
			lcs.add(ContractConstruct.BEHAVIOR_NORMAL);
		}
	}

	public void hasAssignable(List<LanguageConstruct> lcs) {
		if (contract.toString().contains("assignable ")) {
			lcs.add(ContractConstruct.ASSIGNABLE_TRUE);
		} else {
			lcs.add(ContractConstruct.ASSIGNABLE_FALSE);
		}

	}

	public void hasPreconditions(List<LanguageConstruct> lcs) {
		if (countStringInString(contract.toString(), "requires ") > 0) {
			lcs.add(ContractConstruct.PRECONDITION_TRUE);
		} else {
			lcs.add(ContractConstruct.PRECONDITION_FALSE);
		}
	}

	public void hasPostconditions(List<LanguageConstruct> lcs) {
		if (countStringInString(contract.toString(), "ensures ") > 0) {
			lcs.add(ContractConstruct.POSTCONDITION_TRUE);
		} else {
			lcs.add(ContractConstruct.POSTCONDITION_FALSE);
		}
	}

	private int countStringInString(String str, String findStr) {
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {

			lastIndex = str.indexOf(findStr, lastIndex);

			if (lastIndex != -1) {
				count++;
				lastIndex += findStr.length();
			}
		}
		return count;
	}

	@Override
	public String toString() {
		String str = "[Result of last analysis]\n";
		if (currentConstructs.isEmpty()) {
			return str + "None";
		}

		str += "Contract: " + this.contract + "\n";

		str += "Contract Constructs: [";
		for (LanguageConstruct lc : currentConstructs)
			str += lc.toString() + ", ";
		if (currentConstructs != null && !currentConstructs.isEmpty())
			str = str.substring(0, str.length() - 2);
		str += "]\n";

		return str;
	}

	@Override
	public boolean valid() {
		if (this.contract != null && (this.contract.trim().startsWith("/*@") || this.contract.startsWith("//@"))) {
			if (contract.contains("ensures ")) { // more specific; at least a postcondition has to be provided
				return true;
			}
		}
		return false;
	}

}
