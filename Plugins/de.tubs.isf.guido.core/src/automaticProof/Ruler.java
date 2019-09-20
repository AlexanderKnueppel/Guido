package automaticProof;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import analyzer.Parameter;

public class Ruler implements Serializable{
	private static final long serialVersionUID = 4015855254309825043L;

	private ArrayList<Rule> rules;
	private long previousResultsFileModified;
	private long hypothesesFileModified;
	
	public Ruler() {
		super();
		rules = new ArrayList<Rule>();
	}
	
	public Ruler(long previousResultsFileModified, long hypothesesFileModified) {
		super();
		this.previousResultsFileModified = previousResultsFileModified;
		this.hypothesesFileModified = hypothesesFileModified;
		rules = new ArrayList<Rule>();
	}
	
	public void setPreviousResultsFileModified(long modified){
		this.previousResultsFileModified = modified;
	}
	
	public void setHypothesesFileModified(long modified){
		this.hypothesesFileModified = modified;
	}
	
	public void deleteOldRules(){
		this.rules.clear();
	}
	
	public void setRules(ArrayList<Rule> rules){
		this.rules = rules;
	}
	
	public void setRule(Parameter param){
		rules.add(new Rule(param.getParameter(), param.getOptions()));
	}
	
	public ArrayList<Rule> getRules(){
		return rules;
	}
	
	public long getPreviousResultsFileModified(){
		return previousResultsFileModified;
	}
	
	public long getHypothesesFileModified(){
		return hypothesesFileModified;
	}
	
	public void writeToFile(File file){
		BufferedWriter BW;
		try {
			BW = new BufferedWriter(new FileWriter(file));
			Gson gson = new GsonBuilder().create();
		
			JsonParser parser = new JsonParser();
			JsonObject o = parser.parse("{\"previousResultsFileModified\": \"" 
					+ previousResultsFileModified 
					+ "\",\"hypothesesFileModified\": \""
					+ hypothesesFileModified 
					+ "\"}").getAsJsonObject();
			BW.append(gson.toJson(o));
			BW.newLine();

			for(Rule rule: rules){
				BW.append(gson.toJson(rule));
				BW.newLine();
			}
			
			BW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
