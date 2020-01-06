package de.tubs.isf.guido.verification.systems.cpachecker.generators;



public class FeatureIdeTranslator {
	
	/**
	public static String encode(String name){
		String intermediate = name;

		return intermediate;
	}**/
	
	
	public static String[] decode(String name){
		String[] intermediate = name.split("_");
		return intermediate;
		
	}
}
