package de.tubs.isf.guido.dsl.toJson;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;

import de.tubs.isf.guido.core.statistics.Hypotheses;
import de.tubs.isf.guido.dsl.dsl.AbstractElement;
import de.tubs.isf.guido.dsl.dsl.Constructs;
import de.tubs.isf.guido.dsl.dsl.Dsl;
import de.tubs.isf.guido.dsl.dsl.Hypothesis;
import de.tubs.isf.guido.dsl.dsl.Import;
import de.tubs.isf.guido.dsl.dsl.Option;
import de.tubs.isf.guido.dsl.dsl.Parameter;
import de.tubs.isf.guido.dsl.dsl.Type;
import de.tubs.isf.guido.dsl.dsl.Databases;
import de.tubs.isf.guido.dsl.dsl.Definition;
import de.tubs.isf.guido.dsl.dsl.Description;


public class DslReader {
/**
 * 
 * @param args
 */
	public static void main(String[] args) {
        Hypotheses container = new Hypotheses();
        
		for(String file: args) {
			XtextParser parser = new XtextParser();
			Resource resource = parser.setupParser(file);
			Dsl dsl =  (Dsl) resource.getContents().get(0);
			de.tubs.isf.guido.core.statistics.Hypothesis hyp = fillHypothesis(dsl);
			container.addHypothesis(hyp);
		}
		
		container.writeToFile(new File("file.json"));
	}
	
	/**
	 * Example: Definition: {A : true} is more efficient than {A : false, B: true} ;	
	 * @param dsl
	 * @return
	 */
	static de.tubs.isf.guido.core.statistics.Hypothesis fillHypothesis(Dsl dsl){
		Constructs constructs = null;
		Definition definition = null;
		Description description = null;
		EList<Parameter> parametersA = null;
		EList<Parameter> parametersB = null;
		EList<Option> optionsA = null;
		EList<Option> optionsB = null;
		Type type = null;
		Import importString = null;
		Databases databases = null;
		String property = "";
		Hypothesis hypothesis = null;
		de.tubs.isf.guido.dsl.dsl.System system = null;	

		EList<AbstractElement> elements = dsl.getElements();

		for(int i = 0 ; i < elements.size(); i++) {
			AbstractElement element = elements.get(i);
			if(element instanceof Hypothesis) {
				hypothesis = ((Hypothesis) element);
				constructs = hypothesis.getConstructs();
				definition = ((Hypothesis) element).getDefinition();
				description = ((Hypothesis) element).getDescription();
				parametersA = definition.getParameterA(); // all Parameters in first Part of Definition : (A)
				parametersB = definition.getParameterB(); // all Parameters in second Part of Definition : (A,B)	
				optionsA = definition.getOptionsA(); // all Options in first Part of Definition (true)
				optionsB = definition.getOptionsB(); // all Options in second Part of Definition (false, true)
				property = definition.getProperty();
				
			}
			if(element instanceof de.tubs.isf.guido.dsl.dsl.System) {
				system = ((de.tubs.isf.guido.dsl.dsl.System) element);
			}
			if(element instanceof Databases) {
				databases = ((Databases) element);
			}
			if(element instanceof Import) {
				importString = ((Import) element);
			}
		}
		String dependency = "";
		if(property.equals("more efficient")||property.equals("more effective")) {
			dependency = ">=";
		}
		if(property.equals("less efficient")||property.equals("less effective")) {
			dependency = "<=";
		}
		return new de.tubs.isf.guido.core.statistics.Hypothesis(
				hypothesis.getName(),parametersA.get(0).getName(),optionsA.get(0).getName(),optionsB.get(0).getName(),"VE" ,dependency,new ArrayList<String>()); 

	}
}
