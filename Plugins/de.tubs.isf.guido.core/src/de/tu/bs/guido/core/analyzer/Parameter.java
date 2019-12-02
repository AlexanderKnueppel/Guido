package de.tu.bs.guido.core.analyzer;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
	private String parameter;
	private Map<String, Option> options;
	
	public Parameter(String parameter){
		this.parameter = parameter;
		this.options = new HashMap<>();
	}
	
	public void setOption(String name){
		options.put(name, new Option(name));
	}
	
	public Map<String, Option> getOptions(){
		return options;
	}
	
	public void setOptions(Map<String, Option> opt){
		options = opt;
	}
	
	public void setProperty(String option, String property, String requirement, Double value){
		if(options.containsKey(option))
			options.get(option).addPropertyToPropertiesHyp(property, value, requirement);
	}

	public void setVerificationEffortDB(String option, double verificationEffort){
		if(options.containsKey(option))
			options.get(option).setVerificationEffortDB(verificationEffort);
	}
	public void setVerificationEffortHyp(String option, double verificationEffort){
		if(options.containsKey(option))
			options.get(option).setVerificationEffortHyp(verificationEffort);
	}
	public void setProvabilityDB(String option, double provability){
		if(options.containsKey(option))
			options.get(option).setProvabilityDB(provability);
	}
	public void setProvabilityHyp(String option, double provability){
		if(options.containsKey(option))
			options.get(option).setProvabilityHyp(provability);
	}
	
	public double getProvabilityHyp(String option) {
		if(options.containsKey(option))
			return options.get(option).getProvability();
		return -10;
	}
	public double getVerificationEffortHyp(String option) {
		if(options.containsKey(option))
			return options.get(option).getVerificationEffort();
		return -10;
	}
	
	public String getParameter(){
		return parameter;
	}
	
	@Override
	public String toString(){
		String optionString = "";
		for(Option option: options.values()){
			optionString += "[" + option.getOption() + " - Hypothesis: P=" + option.getProvabilityHyp() + "; VE=" + option.getVerificationEffortHyp()
			+ "| Data basis: P=" + option.getProvabilityDB() + "; VE=" + option.getVerificationEffortDB() + "]";
		}
		return parameter + ": " + optionString;
	}
}
