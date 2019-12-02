package de.tu.bs.guido.core.automaticProof;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tu.bs.guido.core.analyzer.Option;
import de.tu.bs.guido.core.analyzer.contract.ContractAnalyzer;
import de.tu.bs.guido.core.analyzer.contract.SourceCodeAnalyzer;

public class Rule implements Serializable{
	private static final long serialVersionUID = 4015855254309825043L;
	private String parameter;
	private Map<String, Map<String, Double>> optionCosts; //OPTION <P/VE, COST>
	private Map<String, Map<String, Double>> propertyCostsP; //OPTION <PROPERTY, COST>
	private Map<String, Map<String, Double>> propertyCostsVE; //OPTION <PROPERTY, COST>
	
	public Rule() {
		super();
	}
	
	public Rule(String parameter, Map<String, Option> options){
		this.setParameter(parameter);
		this.optionCosts = new HashMap<String, Map<String, Double>>();
		this.propertyCostsP = new HashMap<String, Map<String, Double>>();
		this.propertyCostsVE = new HashMap<String, Map<String, Double>>();

		for(Option opt: options.values()){
			Map<String, Double> costs = new HashMap<String, Double>(2);
			costs.put("P", opt.getProvability());
			costs.put("VE", opt.getVerificationEffort());
			optionCosts.put(opt.getOption(), costs);
			
			for(String property: opt.getSetProperties()){
				System.out.println(property);
				Map<String, Double> property_costsP = new HashMap<String, Double>(2);
				Map<String, Double> property_costsVE = new HashMap<String, Double>(2);

				if(opt.getProvabilityForProperty(property) != null){
					property_costsP.put(property, opt.getProvabilityForProperty(property));
					if(propertyCostsP.get(opt.getOption()) != null){
						property_costsP.putAll(propertyCostsP.get(opt.getOption()));
					}
					propertyCostsP.put(opt.getOption(), property_costsP);
				}

				if(opt.getVerificationEffortForProperty(property) != null){
					property_costsVE.put(property, opt.getVerificationEffortForProperty(property));
					if(propertyCostsVE.get(opt.getOption()) != null){
						property_costsVE.putAll(propertyCostsVE.get(opt.getOption()));
					}
					propertyCostsVE.put(opt.getOption(), property_costsP);
				}
			}
			
		}
	}

	private List<Double> getCurrentPropertyCost(Map<String, Double> costs, String requirement, String option, Double value, SourceCodeAnalyzer sourceCode, ContractAnalyzer contract){
		List<Double> currentPropertyCost = new ArrayList<Double>();
		if(costs != null){
			for(Entry<String, Double> propertyCost: costs.entrySet()){
				if(sourceCode.hasProperty(propertyCost.getKey()) || contract.hasProperty(propertyCost.getKey())){
					System.out.println(option + ": Consulting " + requirement 
							+ " costs for property " + propertyCost.getKey());
					currentPropertyCost.add(propertyCost.getValue());
				}
			}
		}
		if(currentPropertyCost.isEmpty()){ //consider "normal" value only if no value is considered regarding properties
			currentPropertyCost.add(value);
		}
		
		return currentPropertyCost;
	}

	public String getBestOption(int proofPerformed, SourceCodeAnalyzer sourceCode, ContractAnalyzer contract){
		String bestOption = null;
		Double bestCost = null;
		
		 /*Random rand = new Random();
		 List<String> list = new ArrayList<String>();
		 for(Entry<String, Map<String, Double>> option: optionCosts.entrySet()){
			 list.add(option.getKey());
		 }
		 
		 if(!KeyTacletOptions.isTaclet(parameter)){
			 System.out.println(parameter);
		 bestOption = list.get(rand.nextInt(list.size()));
		 }
		 else{*/
			 
			 
		 for(Entry<String, Map<String, Double>> option: optionCosts.entrySet()){
			Map<String, Double> value = option.getValue();
			List<Double> costP = getCurrentPropertyCost(propertyCostsP.get(option.getKey()), "P", option.getKey(), value.get("P"), sourceCode, contract);
			List<Double> costVE = getCurrentPropertyCost(propertyCostsVE.get(option.getKey()), "VE", option.getKey(), value.get("VE"), sourceCode, contract);
			
			Double currentCost = getCost(costP, costVE, proofPerformed);
			
			if(bestCost == null){
				bestOption = option.getKey();
				bestCost = currentCost;
			}
			else if(bestCost > currentCost) {
				bestOption = option.getKey();
				bestCost = currentCost;
			}
		}
		return bestOption;
	}
	
	public Double calculateMean(List<Double> cost){
		Double mean = 0.0;
		for(Double c : cost){
			mean += c;
		}
		return mean/cost.size();
	}
	
	public Double getCost(List<Double> costP, List<Double> costVE, int proofPerformed){
		double provability = calculateMean(costP);
		double verificationEffort = calculateMean(costVE);
		
		//Focus P; VE increases // Evaluation said this is the best
		double pInfluence = 100 - proofPerformed*10;
		double veInfluence = 100 - pInfluence;
		
		//Focus VE; P increases
		/*double veInfluence = 100 - proofPerformed*10;
		double pInfluence = 100 - veInfluence;*/

		if(verificationEffort == 1.0 && provability == 1.0){
			return 0.0; //otherwise, result is wrong
		}
		if(verificationEffort == 0.0 && provability == 0.0){
			return 1.0; //otherwise, result is wrong
		}
		
		return 1-((pInfluence*provability+veInfluence*(verificationEffort))/100);
	}
	
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	public Map<String, Double> getOptions(String crit) {
		HashMap<String, Double> result = new HashMap<String, Double>();
		optionCosts.entrySet().stream().forEach(x -> {result.put(x.getKey(), x.getValue().get(crit));});
		
		return result;
	}
}
