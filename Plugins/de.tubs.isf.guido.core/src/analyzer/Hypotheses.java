package analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Hypotheses {
	List<Hypothesis> hypotheses;

	Hypotheses(File f) {
		readInput(f);
	}

	// reads hypotheses.txt and write it to class variables
	public void readInput(File f) {
		hypotheses = new ArrayList<Hypothesis>();
		
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
				hypotheses.add(gson.fromJson(line, Hypothesis.class));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Hypothesis> getHypotheses() {
		return hypotheses;
	}

	public Map<String, Parameter> analyze(Map<String, Parameter> parameter){
		for(Hypothesis hyp: hypotheses){
		
			if(!hyp.hasProperty() && hyp.isAboutProvability() && parameter.containsKey(hyp.getParameter())){
				parameter.get(hyp.getParameter()).setProvabilityHyp(hyp.getOptionA(), hyp.getValueForOptionA());
				parameter.get(hyp.getParameter()).setProvabilityHyp(hyp.getOptionB(), hyp.getValueForOptionB());
			}
			else if(!hyp.hasProperty() && hyp.isAboutVerificationEffort() && parameter.containsKey(hyp.getParameter())){
				parameter.get(hyp.getParameter()).setVerificationEffortHyp(hyp.getOptionA(), hyp.getValueForOptionA());
				parameter.get(hyp.getParameter()).setVerificationEffortHyp(hyp.getOptionB(), hyp.getValueForOptionB());
			}
			else { //hypothesis is about a certain property
				parameter.get(hyp.getParameter()).setProperty(hyp.getOptionA(), hyp.getProperty(), hyp.getRequirement(), hyp.getValueForOptionA());
				parameter.get(hyp.getParameter()).setProperty(hyp.getOptionB(), hyp.getProperty(), hyp.getRequirement(), hyp.getValueForOptionB());
			}
		}
		return parameter;
	}

}
