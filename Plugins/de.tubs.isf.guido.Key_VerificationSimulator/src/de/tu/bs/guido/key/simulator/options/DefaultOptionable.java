package de.tu.bs.guido.key.simulator.options;

public class DefaultOptionable implements Optionable{
	
	@Override
	public String getValue() {
		return "root";
	}

	@Override
	public OptionableContainer getOptionableContainer() {
		return null;
	}

	@Override
	public String getOutputString(){
		return getValue();
	}

	@Override
	public String getType(){
		return getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultOptionable other = (DefaultOptionable) obj;
		return true;
	}

	
}
