package analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ResultsOfAllClosedContracts {
	
	public Map<String, Double> getPercentageOfBestProofs(List<Result> results){
		// sort by proof
		Map<String, List<Result>> resultsByProof = sortyByProof(results);

		//examine all configurations that lead to a none closed proof
		Map<String, Result> bestConfigsOfEachProof = getBestConfigsOfEachProofMappedToProof(sortValueByConfig(resultsByProof));
		return getPercentages(bestConfigsOfEachProof);
	}
	
	public Map<String, Double> getPercentageOfEachProof(List<Result> results){
		// sort by proof
		Map<String, List<Result>> resultsByProof = sortyByProof(results);

		//examine all configurations that lead to a none closed proof
		Map<String, Result> bestConfigsOfEachProof = getProvedConfigsOfEachProofMappedToProof(resultsByProof);
		return getPercentages(bestConfigsOfEachProof);
	}
	
	static Map<String, List<Result>> sortValueByConfig(Map<String, List<Result>> unsortMap) {

		for (Map.Entry<String, List<Result>> entry : unsortMap.entrySet()) {
			List<Result> results = entry.getValue();
			Collections.sort(results, new Comparator<Result>() {
				@Override
				public int compare(Result res1, Result res2) {
					
					int stepsDifference = res1.getSteps() - res2.getSteps();
					if(stepsDifference != 0)
						return stepsDifference;
						
					return (int) (res1.getTimeInMillis() - res2.getTimeInMillis());
				}
			});
			unsortMap.put(entry.getKey(), results);
		}

		return unsortMap;
	}
	
	private Map<String, List<Result>> sortyByProof(List<Result> results) {
		Map<String, List<Result>> resultsByProof = new HashMap<String, List<Result>>();

		for (int i = 0; i < results.size(); i++) {

			String nameOfProof = results.get(i).getName();

			if (results.get(i).isClosed()) { //only closed proofs are relevant
				if (i == 0) {
					resultsByProof.put(nameOfProof, Arrays.asList(results.get(i)));
				} else {
					if (resultsByProof.containsKey(nameOfProof)) {
						List<Result> newValue = new ArrayList<Result>();
						newValue.addAll(resultsByProof.get(nameOfProof));
						newValue.add(results.get(i));
						resultsByProof.put(nameOfProof, newValue);
					} else {
						resultsByProof.put(nameOfProof, Arrays.asList(results.get(i)));
					}
				}
			}
		}
        Map<String, List<Result>> treeMapResults = new TreeMap<String, List<Result>>(resultsByProof);
		return treeMapResults;
	}
	
	private Map<String, Result> getProvedConfigsOfEachProofMappedToProof(Map<String, List<Result>> results){
		Map<String, Result> provedConfigsOfEachProof = new HashMap<String, Result>();
		
		for (Map.Entry<String, List<Result>> proof : results.entrySet()) {
			provedConfigsOfEachProof.put(proof.getKey(), proof.getValue().get(0));
			int i = 1;
			while(i<proof.getValue().size()){
				provedConfigsOfEachProof.put(proof.getKey()+i, proof.getValue().get(i));
				i++;
			}
		}
		return provedConfigsOfEachProof;
	}
	
	private Map<String, Double> getPercentages(Map<String, Result> results){
		
		Map<String, Double> percentages = new HashMap<String, Double>();
		Map<String, Integer> amountOfOptions = sortByAmount(countOptions(results));
		int amountOfProofs = results.size();
		
		for(Map.Entry<String, Integer> value: amountOfOptions.entrySet()){
			Double percent = ((double)value.getValue()/(double)amountOfProofs);
			percentages.put(value.getKey().toString(), percent);
		}
		
		return percentages;
	}
	
	private Map<String, Integer> sortByAmount(Map<String, Integer> unsortMap){
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        return sortedMap;
	}
	
	private Map<String, Integer> countOptions(Map<String, Result> config){
		Map<String, Integer> amountOfOptions = new HashMap<String, Integer>();
		
		for(Map.Entry<String, Result> entry: config.entrySet()){
			Map<String, String> options = entry.getValue().getOptions();
			Map<String, String> taclets = entry.getValue().getTaclets();
			Map<String, String> optionsAndTaclets = new HashMap<String, String>();
			optionsAndTaclets.putAll(taclets);
			optionsAndTaclets.putAll(options);
			
			for(Map.Entry<String, String> option: optionsAndTaclets.entrySet()){
				if(amountOfOptions.containsKey(option.toString())){
					int oldAmount = amountOfOptions.get(option.toString());
					amountOfOptions.put(option.toString(), oldAmount+1);
				}
				else {
					amountOfOptions.put(option.toString(), 1);
				}
			}
		}
		return amountOfOptions;
	}
	
	static Map<String, Result> getBestConfigsOfEachProofMappedToProof(Map<String, List<Result>> results){
		Map<String, Result> bestConfigsOfEachProof = new HashMap<String, Result>();
		
		for (Map.Entry<String, List<Result>> proof : results.entrySet()) {
			bestConfigsOfEachProof.put(proof.getKey(), proof.getValue().get(0));
			int bestSteps = proof.getValue().get(0).getSteps();
			int i = 1;
			int currentSteps = proof.getValue().get(i).getSteps();
			while(i<proof.getValue().size() && currentSteps == bestSteps){
				bestConfigsOfEachProof.put(proof.getKey()+i, proof.getValue().get(i));
				i++;
				currentSteps = proof.getValue().get(i).getSteps();	
			}
		}
		return bestConfigsOfEachProof;
	}
}
