package analyzer.contract;

import com.github.javaparser.ast.comments.Comment;


public class ContractAnalyzer {
	Comment contract;
	int linesOfContract;
	boolean quantifiers;
	boolean implication;
	String behavior;
	boolean assignable;
	int preconditions;
	int postconditions;

	public ContractAnalyzer(Comment comment){
		if(comment != null && (comment.toString().startsWith("/*@") || comment.toString().startsWith("//@"))){
			contract = comment;
		}
	}	
	
	public boolean hasProperty(String property){
		switch(property){
		
		case "quantifiers_true": return quantifiers;
		case "quantifiers_false": return !quantifiers;
		case "linesOfContract_moreThan3Lines": return linesOfContract >3;
		case "linesOfContract_lessThan3Lines": return linesOfContract <=3;
		case "implication_true": return implication;
		case "implication_false": return !implication;
		case "behavior_normal": return behavior.equals("normal");
		case "behavior_exceptional": return behavior.equals("exceptional");
		case "assignable_true": return assignable;
		case "assignable_false": return !assignable;
		case "preconditions_true": return preconditions>0;
		case "preconditions_false": return preconditions<=0;
		case "postconditions_true": return postconditions>0;
		case "postconditions_false": return postconditions<=0;
		default: return false;
		}
	}
	
	public int getLinesOfContract() {
		return linesOfContract;
	}

	public boolean hasQuantifiers() {
		return quantifiers;
	}

	public boolean hasImplication() {
		return implication;
	}

	public String getBehavior() {
		return behavior;
	}

	public boolean hasAssignable() {
		return assignable;
	}

	public int getPreconditions() {
		return preconditions;
	}

	public int getPostconditions() {
		return postconditions;
	}

	public void setLinesOfContract(){
		linesOfContract = contract.getEnd().get().line - contract.getBegin().get().line + 1; 
		// +1 otherwise last line will be ignored
	}

	public void setQuantifiers() {
		this.quantifiers = (contract.toString().contains("\\forall") 
				|| contract.toString().contains("\\exists"))
				? true : false;
	}

	public void setImplication() {
		this.implication = contract.toString().contains("==>") ? true : false;
	}

	public void setBehavior() {
		if(contract.toString().contains(" normal_behavior")){
			this.behavior = "normal";
		}
		else if(contract.toString().contains(" exceptional_behavior")){
			this.behavior = "exceptional";
		}
		else {
			this.behavior = "";
		}
	}

	public void setAssignable() {
		this.assignable = contract.toString().contains("assignable ") ? true : false;
	}

	public void setPreconditions() {
		this.preconditions = countStringInString(contract.toString(), "requires ");
	}

	public void setPostconditions() {
		this.postconditions = countStringInString(contract.toString(), "ensures ");
	}
	
	private int countStringInString(String str, String findStr){
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
	public String toString(){
		return "loC: " + getLinesOfContract() + ", " +
				"quantifiers: " + hasQuantifiers() + ", " +
				"implication: " + hasImplication() + ", " +
				"assignable: " + hasAssignable() + ", " +
				"preconditions: " + getPreconditions() + ", " +
				"postconditions: " + getPostconditions();
	}
	
	
	public void analyzeContract(){
		if(contract != null){
			setLinesOfContract();
			setQuantifiers();
			setImplication();
			setBehavior();
			setAssignable();
			setPreconditions();
			setPostconditions();

			System.out.println(this.toString());
		}
	}
}
