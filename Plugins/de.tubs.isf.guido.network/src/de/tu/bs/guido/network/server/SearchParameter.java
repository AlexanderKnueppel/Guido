package de.tu.bs.guido.network.server;

import java.util.Arrays;

public class SearchParameter {

	private final String codeunit;
	private final String clazz;
	private final String method;
	private final String[] parameter;

	public SearchParameter(String codeunit, String clazz, String method,
			String[] parameter) {
		super();
		this.codeunit = codeunit;
		this.clazz = clazz;
		this.method = method;
		this.parameter = parameter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result
				+ ((codeunit == null) ? 0 : codeunit.hashCode());
		result = prime * result
				+ ((method == null) ? 0 : method.hashCode());
		result = prime * result + Arrays.hashCode(parameter);
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
		SearchParameter other = (SearchParameter) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (codeunit == null) {
			if (other.codeunit != null)
				return false;
		} else if (!codeunit.equals(other.codeunit))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (!Arrays.equals(parameter, other.parameter))
			return false;
		return true;
	}
}