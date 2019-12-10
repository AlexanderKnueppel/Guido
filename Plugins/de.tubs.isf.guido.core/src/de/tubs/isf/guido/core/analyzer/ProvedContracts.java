package de.tubs.isf.guido.core.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ProvedContracts {
	List<Result> results;
	Map<String, Double> percentagesP;
	Map<String, Double> percentagesVE;
	
	ProvedContracts(File f) {
		readInput(f);
		ResultsOfAllClosedContracts rOACC = new ResultsOfAllClosedContracts(); //TODO: consider properties?
		/*
		 * if properties would be considered: "normal" percentages wouldn't be used anymore.
		 * if properties would be considered: "normal" hypotheses wouldn't be considered anymore.
		 * It is important to consider them as well!
		 * It is important to not consider closed contracts for a property if the amount is really less
		 * */
		percentagesP = rOACC.getPercentageOfEachProof(results);
		percentagesVE = rOACC.getPercentageOfBestProofs(results);
	}

	// reads zwischenergebnisse.txt and write it to class variables
	public void readInput(File f) {
		results = new ArrayList<Result>();
		f.getParentFile().mkdirs();
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			Gson gson = new GsonBuilder().create();
			while ((line = br.readLine()) != null) {
				results.add(gson.fromJson(line, Result.class));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public Map<String, Parameter> analyze(Map<String, Parameter> parameter){
		for(Entry<String, Double> percent: percentagesP.entrySet()){
			String option = percent.getKey().substring(percent.getKey().indexOf("=")+1);
			String param = percent.getKey().substring(0, percent.getKey().indexOf("="));
			if(parameter.containsKey(param))
				parameter.get(param).setProvabilityDB(option, percent.getValue());
		}
		for(Entry<String, Double> percent: percentagesVE.entrySet()){
			String option = percent.getKey().substring(percent.getKey().indexOf("=")+1);
			String param = percent.getKey().substring(0, percent.getKey().indexOf("="));
			if(parameter.containsKey(param))
				parameter.get(param).setVerificationEffortDB(option, percent.getValue());
		}
		return parameter;
	}

}
