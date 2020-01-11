package de.tubs.isf.guido.verification.systems.cpachecker.generators;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
		translateModelToStrategies("/media/marlen/54AFF99F466B2AED/eclipse-workspace/pa-marlen-herter-bernier/FeatureModel/CPAChecker-Model.xml");
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
		
		Document doc = readFile(file);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("alt");
		//for (int temp = 0; temp < nList.getLength(); temp++) {
		for(int temp = 0; temp < 1; temp++) {
			LinkedList<String> values = new LinkedList<String>();
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				optionName = eElement.getAttribute("name");
				NodeList featureList = eElement.getElementsByTagName("feature");
				for (int i = 0; i < featureList.getLength(); i++) {
					Element featureElement= (Element) featureList.item(i);
					String name = featureElement.getAttribute("name");
					name = name.substring(name.indexOf("_")+1);
					values.add(name);
				}
			}
			writeOptionJavaFile(optionName,values);
		System.out.println(optionName);
		for(String names : values) {
			System.out.println(names);
		}
		}

	}
	
	public static void writeOptionJavaFile(String name, LinkedList<String> values) {
		String FilePath = "/options/strategies/"+name+".java";
		  
	        try {
	        	File file = new File(FilePath);
				if(file.createNewFile()){
				    System.out.println(FilePath+" File Created");
				}
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
