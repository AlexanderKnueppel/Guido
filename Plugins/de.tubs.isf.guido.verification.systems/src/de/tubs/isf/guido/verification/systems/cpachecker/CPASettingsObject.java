package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.tubs.isf.guido.core.verifier.ACodeContainer;
import de.tubs.isf.guido.core.verifier.Optionable;
import de.tubs.isf.guido.core.verifier.OptionableContainer;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.tubs.isf.guido.verification.systems.cpachecker.options.specification.CPACheckerSpecificationOptions;
import de.tubs.isf.guido.verification.systems.cpachecker.options.strategies.CPACheckerKonfigurationOptions;
import de.tubs.isf.guido.verification.systems.cpachecker.options.strategies.KonfigurationOptionable;

public class CPASettingsObject extends SettingsObject implements Serializable{
	private static final long serialVersionUID = -5974976146446740045L;
	private int debugNumber;
	protected CPACheckerCodeContainer cc;
	

	public CPASettingsObject() {
		this.cc = new CPACheckerCodeContainer(null, null, null, 0,null, null,null, 0);
	}
	public void reinitialize() {}
	
	public Map<String, String> getSettingsMap() {
		return Collections.unmodifiableMap(settingsMap);
	}

	public Optionable getParameterOption(OptionableContainer o) {
		String key = o.getValue();
		if (o instanceof CPACheckerKonfigurationOptions) {
			String value = settingsMap.get(o.getValue());
			return CPACheckerKonfigurationOptions.getOption(key, value);
		}	
		throw new IllegalArgumentException();
	}

	public void setParameter(Optionable o) {
		if (o instanceof KonfigurationOptionable) {
			setOption((KonfigurationOptionable) o);
		}else {
			throw new IllegalArgumentException(o + " is unknown, so it can not be determined to be option ");
		}
	}

	public void setParameter(String option, String value) {
		if (CPACheckerKonfigurationOptions.isOption(option)) {
			setOption(option, value);
		//} else if (CPACheckerStrategyOptions.isOption(option)) {
		} else {
			throw new IllegalArgumentException(
					option + " is unknown, so it can not be determined to be option");
		}
	}

	public void setOption(KonfigurationOptionable v) {
		setOption(v.getType(), v.getValue());
	}

	
	public void setOption(CPACheckerKonfigurationOptions option, String value) {
		setOption(option.getValue(), value);
	}

	public void setOption(String option, String value) {
		settingsMap.put(option, value);
	}


	public int getDebugNumber() {
		return debugNumber;
	}

	public void setDebugNumber(int debugNumber) {
		this.debugNumber = debugNumber;
	}

	private static int mapHashValue(Map<String, String> map) {
		if (map == null) {
			return 0;
		} else {
			int sum = 0;
			for (Entry<String, String> entry : map.entrySet()) {
				sum += entry.getKey().hashCode();
				sum += entry.getValue().hashCode();
			}
			return sum;
		}
	}

	private static boolean mapEquals(Map<String, String> thisMap, Map<String, String> thatMap) {
		if (thisMap.size() != thatMap.size()) {
			return false;
		}

		for (Entry<String, String> entry : thisMap.entrySet()) {
			String thatMapValue = thatMap.get(entry.getKey());
			if (!thatMapValue.equals(entry.getValue())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + maxEffort);
		result = prime * result + mapHashValue(settingsMap);
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
		CPASettingsObject other = (CPASettingsObject) obj;
		if (cc == null) {
			if (other.cc != null)
				return false;
		} else if (!cc.equals(other.cc))
			return false;
		if (maxEffort != other.maxEffort)
			return false;
		if (settingsMap == null) {
			if (other.settingsMap != null)
				return false;
		} else if (!mapEquals(settingsMap, other.settingsMap))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SettingsObject [maxSteps=" + maxEffort + ", settingsMap=" + settingsMap + "]";
	}

	public CPASettingsObject clone() throws CloneNotSupportedException {
		CPASettingsObject so = new CPASettingsObject();
		so.maxEffort = maxEffort;
		so.settingsMap = new HashMap<>(settingsMap);
		return so;
	}

	@Override
	public OptionableContainer[] getAllPossibleSettings() {
		int aLen = CPACheckerKonfigurationOptions.values().length;
		int bLen = CPACheckerSpecificationOptions.values().length;
		OptionableContainer[] result = new OptionableContainer[aLen + bLen];
		System.arraycopy(CPACheckerKonfigurationOptions.values(), 0, result, 0, aLen);
		System.arraycopy(CPACheckerSpecificationOptions.values(), 0, result, aLen, bLen);
		return result;

	}

	@Override
	public double getMaxEffort() {
		// TODO Auto-generated method stub
		return maxEffort;
	}

	@Override
	public void setMaxEffort(double maxEffort) {
		// TODO Auto-generated method stub
		this.maxEffort = maxEffort;
	}

	@Override
	public ACodeContainer getCc() {
		// TODO Auto-generated method stub
		return cc;
	}

	@Override
	public void setCc(ACodeContainer cc) {
		this.cc = (CPACheckerCodeContainer) cc;
	}
}
