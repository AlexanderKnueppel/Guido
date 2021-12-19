package de.tubs.isf.guido.verification.systems.cpachecker.options.specification;

import java.util.HashMap;
import java.util.Map;
import de.tubs.isf.guido.core.verifier.Specification;


public enum CPACheckerSpecificationOptions implements Specification{
	ASSERTION("assertion","config/specification/assertion.spc"),
	DEFAULT("default","config/specification/default.spc"),
	OVERFLOW("overflow","config/specification/overflow.spc");
	
	private static final Map<String, String> optionMap = new HashMap<>();
		
	static {
		for (CPACheckerSpecificationOptions kto : CPACheckerSpecificationOptions.values()) {
			optionMap.put(kto.getValue(), kto.getPath());
		}
	}
	
	public static boolean isSpecification(String value) {
		return optionMap.containsKey(value);
	}
	public static String getPath(String value){
		return optionMap.get(value);
	}
	
	private final String value;
	private final String specificationpath;
	
	CPACheckerSpecificationOptions(String value, String specificationpath){
		this.value = value;
		this.specificationpath = specificationpath;
	}
	
	public String getValue() {
		return value;
	}
	public String getPath() {
		return specificationpath;
	}

}
