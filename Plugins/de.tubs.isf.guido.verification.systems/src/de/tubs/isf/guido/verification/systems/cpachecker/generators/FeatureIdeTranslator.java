package de.tubs.isf.guido.verification.systems.cpachecker.generators;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FeatureIdeTranslator {
	
	public static void main(String[]args) {
		translateModelToStrategies("/media/marlen/54AFF99F466B2AED/eclipse-workspace/pa-marlen-herter-bernier/FeatureModel/CPAChecker-Model-default.xml");
	}
	
	/**
	public static String encode(String name){
		String intermediate = name;

		return intermediate;
	}**/
	
	
	public static String[] decode(String name){
		String[] intermediate = name.split("_");
		return intermediate;
		
	}
	
	public static void translateModelToStrategies(String file) {
		String optionName = "";
		String stringForCongiFile ="";
		Document doc = readFile(file);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("alt");
		for (int temp = 0; temp < nList.getLength(); temp++) {
		//for(int temp = 0; temp < 2; temp++) {
			LinkedList<String> values = new LinkedList<String>();
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				optionName = eElement.getAttribute("name");
				NodeList featureList = eElement.getElementsByTagName("feature");
				for (int i = 0; i < featureList.getLength(); i++) {
					Element featureElement= (Element) featureList.item(i);
					String name = featureElement.getAttribute("name");
					name = name.substring(name.lastIndexOf("_1_")+3);
					values.add(name);
				}
			}
			String methodName = "";
			String option = "";
			//create string for the java files
			String[] tempt = optionName.split("\\.");
			
			for(int j = 0; j < tempt.length;j++) {
				String firstLetter = tempt[j].substring(0, 1).toUpperCase();
				methodName = methodName + firstLetter + tempt[j].substring(1);
				option = option + "_" + tempt[j].toUpperCase();					
			}
			option = option.substring(1,option.length()).trim();
			methodName = methodName.trim() + "Options";
			
			
			writeOptionJavaFile(methodName,option,values);
			
			stringForCongiFile = stringForCongiFile + option +
						"(\""+ optionName +"\","+ methodName+".values(), "+
						methodName + "." + values.getFirst().toUpperCase()+
						"),\n";
		}
		stringForCongiFile = stringForCongiFile.substring(0, stringForCongiFile.length()-2)+ ";";
		writeCPACheckerKongigurationOptionsFile(stringForCongiFile);
	}
	public static void writeCPACheckerKongigurationOptionsFile(String line) {
		//changing the CPACheckerKongiguration file to at the enums
		String CPAKonfigFilePath = "src/de/tubs/isf/guido/verification/systems/cpachecker/options/strategies/CPACheckerKonfigurationOptions.java"; 
	   				
	    FileOutputStream fos ;
		File file = new File(CPAKonfigFilePath);
	    BufferedReader bufferedReader;
	    
		try {				
			bufferedReader = new BufferedReader(new FileReader(file));
			String allData = "";
		     String st;
		     while ((st = bufferedReader.readLine()) != null) {		 		   	 
	    	 	if(st.contains("implements OptionableContainer {")) {
	    	 		allData = allData + "\n" + st + "\n" + line+ "\n\n" ;	
	    	 		while(!st.contains("private static final Map")) {		
	    	 				st = bufferedReader.readLine();			    	 			
	    	 		}			    	 					    	 		
	    	 	}
	    	 	allData = allData + st + "\n";	
		     }
		     bufferedReader.close();
		     file.delete();
		     fos = new FileOutputStream(CPAKonfigFilePath);
		     fos.write(allData.getBytes());
		     fos.close();			     

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}
	
	public static void writeOptionJavaFile(String methodName, String option, LinkedList<String> values) {
		String valuesString = "";
		for(String val:values) {
			valuesString = valuesString + val.toUpperCase() + "(\"" + val + "\"),";
		}
		valuesString = valuesString.substring(0,valuesString.length()-1);
		
		String filePath ="src/de/tubs/isf/guido/verification/systems/cpachecker/options/strategies/" + methodName +".java";
 
		String fileData = "package de.tubs.isf.guido.verification.systems.cpachecker.options.strategies;\n\n" +
				"import de.tubs.isf.guido.core.verifier.OptionableContainer;\n\n "+
				"public enum " +  methodName+ " implements KonfigurationOptionable{\n"+
				"\t"+ valuesString + ";\n"+
				"\t"+ "private final String value;\n\n"+
				"\t"+ methodName +"(String value) {\n"+
				"\t\t"+ "this.value = value;\n"+
				"\t"+ "}\n"+
				"\t"+ "public String getValue() {\n"+
				"\t\t"+ "return value;\n"+
				"\t"+ "}\n"+
				"\t"+ "@Override\n"+
				"\t"+ "public OptionableContainer getOptionableContainer() {\n"+
				"\t\t"+ "return CPACheckerKonfigurationOptions."+ option +";\n"+
				"\t"+ "}\n"+
				"}\n";
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(fileData.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Document readFile(String file){
        if (!file.endsWith(".xml")) {
        	throw new IllegalArgumentException("The File is not an XML File.");
        }
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(file));
            return doc;
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        } catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
