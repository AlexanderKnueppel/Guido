package de.tubs.isf.guido.core.analyzer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.tubs.isf.core.verifier.Optionable;
import de.tubs.isf.core.verifier.OptionableContainer;



public class Analyzer {

	private ProvedContracts contracts;
	private Hypotheses hypotheses;
	private Map<String, Parameter> parameter = new HashMap<>();

	
	public Analyzer(File file1, File file2){
		contracts = new ProvedContracts(file1);
		hypotheses = new Hypotheses(file2);
		createParameterObject();
	}
	
	public void analyze(){
		System.out.println("Analyze hypotheses file");
		parameter = hypotheses.analyze(parameter);
		System.out.println("Analyze results of proved contracts");
		parameter = contracts.analyze(parameter);
	}
	
	private void createParameterObject(){
		for(OptionableContainer parameterObject: OptionableContainer.values()){
			Parameter param = new Parameter(parameterObject.getValue());
			for(Optionable option: parameterObject.getOptions()){
				param.setOption(option.getValue());
				param.setProvabilityHyp(param.getOptions().get(option.getValue()).getOption(), 0.0);
				param.setVerificationEffortHyp(param.getOptions().get(option.getValue()).getOption(), 0.0);
			}
			parameter.put(parameterObject.getValue(), param);
		}
		for(OptionableContainer parameterObject: OptionableContainer.values()){
			Parameter param = new Parameter(parameterObject.getValue());
			for(Optionable option: parameterObject.getOptions()){
				param.setOption(option.getValue());
				param.setProvabilityHyp(param.getOptions().get(option.getValue()).getOption(), 0.0);
				param.setVerificationEffortHyp(param.getOptions().get(option.getValue()).getOption(), 0.0);
			}
			parameter.put(parameterObject.getValue(), param);
		}
	}
	
	public Map<String, Parameter> getParameter(){
		return parameter;
	}

	public void analyzeForGuido() {
//		System.out.println("Analyze hypotheses file");
//		parameter = hypotheses.analyze(parameter);
//		System.out.println("Analyze results of proved contracts");
//		parameter = contracts.analyze(parameter);
		
		double alpha = 0.05;
		
		for(Hypothesis hyp: hypotheses.getHypotheses()){
			
			double weight = (alpha-hyp.getPValue());
			//update
			if(hyp.isAboutProvability() && parameter.containsKey(hyp.getParameter())) {
				String option =  hyp.getParameter();
				double oldvalue = parameter.get(hyp.getParameter()).getProvabilityHyp(hyp.getBetterOption());
				
				if(oldvalue==-10.0) {
					System.out.print("#1 Expected: " + hyp.getBetterOption() + "But not found\n" );
				}
				
				String better = hyp.getBetterOption();
				String worse = hyp.getWorseOption();
				parameter.get(hyp.getParameter()).setProvabilityHyp(hyp.getBetterOption(), oldvalue+weight);
				oldvalue = parameter.get(hyp.getParameter()).getProvabilityHyp(hyp.getWorseOption());
				
				if(oldvalue==-10.0) {
					System.out.print("#2 Expected: " + hyp.getBetterOption() + "But not found\n" );
				}
				
				parameter.get(hyp.getParameter()).setProvabilityHyp(hyp.getWorseOption(), oldvalue-weight);
			}
			
			if(hyp.isAboutVerificationEffort() && parameter.containsKey(hyp.getParameter())) {
				double oldvalue = parameter.get(hyp.getParameter()).getVerificationEffortHyp(hyp.getBetterOption());
				if(oldvalue==-10.0) {
					System.out.print("#2 Expected: " + hyp.getBetterOption() + "But not found\n" );
				}
				
				String better = hyp.getBetterOption();
				String worse = hyp.getWorseOption();
				
				parameter.get(hyp.getParameter()).setVerificationEffortHyp(hyp.getBetterOption(), oldvalue+weight);
				oldvalue = parameter.get(hyp.getParameter()).getVerificationEffortHyp(hyp.getWorseOption());
				
				if(oldvalue==-10.0) {
					System.out.print("#2 Expected: " + hyp.getBetterOption() + "But not found\n" );
				}
				
				parameter.get(hyp.getParameter()).setVerificationEffortHyp(hyp.getWorseOption(), oldvalue-weight);
			}
//			
//			if(!hyp.hasProperty() && hyp.isAboutProvability() && parameter.containsKey(hyp.getParameter())){
//				parameter.get(hyp.getParameter()).setProvabilityHyp(hyp.getOptionA(), hyp.getValueForOptionA());
//				parameter.get(hyp.getParameter()).setProvabilityHyp(hyp.getOptionB(), hyp.getValueForOptionB());
//			}
//			else if(!hyp.hasProperty() && hyp.isAboutVerificationEffort() && parameter.containsKey(hyp.getParameter())){
//				parameter.get(hyp.getParameter()).setVerificationEffortHyp(hyp.getOptionA(), hyp.getValueForOptionA());
//				parameter.get(hyp.getParameter()).setVerificationEffortHyp(hyp.getOptionB(), hyp.getValueForOptionB());
//			}
//			else { //hypothesis is about a certain property
//				parameter.get(hyp.getParameter()).setProperty(hyp.getOptionA(), hyp.getProperty(), hyp.getRequirement(), hyp.getValueForOptionA());
//				parameter.get(hyp.getParameter()).setProperty(hyp.getOptionB(), hyp.getProperty(), hyp.getRequirement(), hyp.getValueForOptionB());
//			}
		}
	}
}
