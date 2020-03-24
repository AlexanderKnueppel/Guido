package de.tubs.isf.guido.core.statistics;

import java.io.Serializable;
import java.util.List;

public class Parameter implements Serializable {
	protected String parameter;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	protected String option;

}
