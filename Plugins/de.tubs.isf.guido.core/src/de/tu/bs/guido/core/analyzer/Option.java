package de.tu.bs.guido.core.analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Option {
	private String option;
	private Double provabilityHyp;
	private Double verificationEffortHyp; // is set to null if no hypothesis is used for this option
	private double provabilityDB;
	private double verificationEffortDB; //is set to 0.0 if it is not used in data base
	
	//Property-Results
	private Map<String, Double> properties_provabilityHyp;
	private Map<String, Double> properties_verificationEffortHyp;

	public Option(String option){
		this.setOption(option);
		definePropertyMaps();
	}
	
	public Option(String option, double provabilityHyp, double verificationEffortHyp, double provabilityDB, double verificationEffortDB){
		this.setOption(option);
		this.setProvabilityHyp(provabilityHyp);
		this.setVerificationEffortHyp(verificationEffortHyp);
		this.setProvabilityDB(provabilityDB);
		this.setVerificationEffortDB(verificationEffortDB);
		definePropertyMaps();
	}
	
	public Map<String, Double> getProperties_provabilityHyp() {
		return properties_provabilityHyp;
	}

	public Map<String, Double> getProperties_verificationEffortHyp() {
		return properties_verificationEffortHyp;
	}

	private void definePropertyMaps(){
		this.properties_provabilityHyp = new HashMap<String, Double>();
		this.properties_verificationEffortHyp = new HashMap<String, Double>();
	}
	
	public void addPropertyToPropertiesHyp(String property, Double value, String requirement){
		if(requirement.equals("P")){
			properties_provabilityHyp.put(property, value);
		}
		else if(requirement.equals("VE")){
			properties_verificationEffortHyp.put(property, value);
		}
		else {
			System.out.println("addPropertyToPropertiesHyp: Requirement is not handled!");
		}
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	private Double getMean(Double a, Double b){
		if(a !=null && b !=null)
			return (a+b)/2;
		else if(b != null)
			return b;
		else if(a !=null)
			return a;
		else
			return null;
	}

	public Double getProvabilityHyp() {
		return provabilityHyp;
	}

	public void setProvabilityHyp(double provabilityHyp) {
		this.provabilityHyp = getMean(provabilityHyp, this.provabilityHyp);
	}

	public Double getVerificationEffortHyp() {
		return verificationEffortHyp;
	}

	public void setVerificationEffortHyp(double verificationEffortHyp) {
		this.verificationEffortHyp = getMean(verificationEffortHyp, this.verificationEffortHyp);
	}

	public double getProvabilityDB() {
		return provabilityDB;
	}

	public void setProvabilityDB(double provabilityHypDB) {
		this.provabilityDB = provabilityHypDB;
	}

	public double getVerificationEffortDB() {
		return verificationEffortDB;
	}

	public void setVerificationEffortDB(double verificationEffortDB) {
		this.verificationEffortDB = verificationEffortDB;
	}
	
	public Double getProvabilityForProperty(String property){
		return properties_provabilityHyp.containsKey(property) ?
				properties_provabilityHyp.get(property) : null; //do not consider DB as well (see below)
	}
	
	/*
	 * if properties would be considered: "normal" percentages wouldn't be used anymore.
	 * if properties would be considered: "normal" hypotheses wouldn't be considered anymore.
	 * It is important to consider them as well!
	 * It is important to not consider closed contracts for a property if the amount is really less
	 * */
	
	public Double getVerificationEffortForProperty(String property){
		return properties_verificationEffortHyp.containsKey(property) ? 
				properties_verificationEffortHyp.get(property) : null;//do not consider DB as well (see above)
	}
	
	public Set<String> getSetProperties(){
		Set<String> properties = new HashSet<String>();
		properties.addAll(properties_verificationEffortHyp.keySet());
		properties.addAll(properties_provabilityHyp.keySet());
		return properties;
	}
	
	public double getProvability(){
		double provability;
		if(getProvabilityHyp() == null)
			provability = getProvabilityDB();
		else
			provability = (getProvabilityHyp() + getProvabilityDB())/2;
		return provability;
	}
	
	public double getVerificationEffort(){
		double verificationEffort;
		if(getVerificationEffortHyp() == null)
			verificationEffort = getVerificationEffortDB();
		else
			verificationEffort = (getVerificationEffortDB() + getVerificationEffortHyp())/2;
		return verificationEffort;
	}


}
