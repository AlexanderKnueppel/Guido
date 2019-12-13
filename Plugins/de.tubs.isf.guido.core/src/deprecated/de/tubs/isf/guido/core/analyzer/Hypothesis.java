package deprecated.de.tubs.isf.guido.core.analyzer;

import java.io.Serializable;
import java.math.BigDecimal;

public class Hypothesis implements Serializable{
	private static final long serialVersionUID = 4015855254309825043L;

	private final String parameter;
	private final String optionA;
	private final String optionB;
	private final String requirement;
	private final String dependency;
	private final BigDecimal pValue;
	private final String property;
	
	public Hypothesis(String property, String parameter, String optionA, String optionB, String requirement, String dependency, String pValue) {
		super();
		this.pValue = new BigDecimal(pValue);
		this.parameter = parameter;
		this.optionA = optionA;
		this.optionB = optionB;
		this.requirement = requirement;
		this.dependency = dependency;
		this.property = property;
	}
	
	public boolean isAboutProvability(){
		return requirement.equals("P") ? true : false;
	}
	
	public boolean isAboutVerificationEffort(){
		return requirement.equals("VE") ? true : false;
	}
	
	public String getRequirement(){
		return requirement;
	}
	
	public String getParameter(){
		return parameter;
	}

	public String getOptionA() {
		return optionA;
	}

	public String getOptionB() {
		return optionB;
	}
	
	public boolean hasProperty(){
		return property != null;
	}
	
	public String getProperty(){
		return property;
	}
	
	public boolean isAboutProperty(String property){
		return property.equals(this.property);
	}
	
	public double getPValue(){
		return pValue.doubleValue();
	}
	
	public String getBetterOption(){
		if(dependency.equals("<=")){
			return isAboutProvability() ? getOptionB() : getOptionB();
		}
		else if(dependency.equals(">=")){
			return isAboutProvability() ? getOptionA() : getOptionA();
		}
		else if(dependency.equals("<>")){
			return getOptionA();
		}
		return getOptionA();
	}
	
	public String getWorseOption(){
		if(dependency.equals("<=")){
			return isAboutProvability() ? getOptionA() : getOptionA();
		}
		else if(dependency.equals(">=")){
			return isAboutProvability() ? getOptionB() : getOptionB();
		}
		else if(dependency.equals("<>")){
			return getOptionB();
		}
		return getOptionB();
	}
	
	public double getValueForOptionA(){
		if(dependency.equals("<=")){
			return isAboutProvability() ? pValue.doubleValue() : 1-pValue.doubleValue();
		}
		else if(dependency.equals(">=")){
			return isAboutProvability() ? 1-(pValue.doubleValue()): pValue.doubleValue();
		}
		else if(dependency.equals("<>")){
			return 1-pValue.doubleValue();
		}
		return 0.0;
	}
	
	public double getValueForOptionB(){
		if(dependency.equals("<=")){
			return isAboutProvability() ? 1-(pValue.doubleValue()): pValue.doubleValue();
		}
		else if(dependency.equals(">=")){
			return isAboutProvability() ? (pValue.doubleValue()): 1-pValue.doubleValue();
		}
		else if(dependency.equals("<>")){
			return 1-pValue.doubleValue();
		}
		return 0.0;
	}
	
	@Override
	public String toString(){
		String property = this.property!=null ? "; property: " + this.property : "";
		return "parameter: " + parameter + "; optionA: " + optionA + "; optionB: " + optionB
				+ "; requirement: " + requirement + "; dependency: " + dependency + property;
	}
}
