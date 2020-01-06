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
import de.tubs.isf.guido.verification.systems.cpachecker.options.specification.SpecificationOptionable;
import de.tubs.isf.guido.verification.systems.cpachecker.options.strategies.CPACheckerStrategyOptions;
import de.tubs.isf.guido.verification.systems.cpachecker.options.strategies.StrategyOptionable;

public class CPASettingsObject extends SettingsObject implements Serializable{
	private static final long serialVersionUID = -5974976146446740045L;
	private int debugNumber;
	protected CPACheckerCodeContainer cc;
	
	private Map<String, String> tacletMap = new HashMap<>();
	
	public CPASettingsObject() {
		this.cc = new CPACheckerCodeContainer(null, 0, null, null, null, null, null, 0);
	}
	public void reinitialize() {}
	
	public Map<String, String> getSettingsMap() {
		return Collections.unmodifiableMap(settingsMap);
	}

	public Map<String, String> getTacletMap() {
		return Collections.unmodifiableMap(tacletMap);
	}

	public Optionable getParameterOption(OptionableContainer o) {
		String key = o.getValue();
		if (o instanceof CPACheckerStrategyOptions) {
			String value = settingsMap.get(o.getValue());
			return CPACheckerStrategyOptions.getOption(key, value);
		}	/** else if (o instanceof CPACheckerSpecificationOptions) {
			String value = tacletMap.get(o.getValue());
			return CPACheckerSpecificationOptions;
		}**/
		throw new IllegalArgumentException();
	}

	public void setParameter(Optionable o) {
		if (o instanceof StrategyOptionable) {
			setOption((StrategyOptionable) o);
		}/** else if (o instanceof TacletOptionable) {
			setTaclet((TacletOptionable) o);
		} **/else {
			throw new IllegalArgumentException(o + " is unknown, so it can not be determined to be option or taclet");
		}
	}

	public void setParameter(String option, String value) {
		if (CPACheckerStrategyOptions.isOption(option)) {
			setOption(option, value);
		//} else if (CPACheckerStrategyOptions.isOption(option)) {
		//	setTaclet(option, value);
		} else {
			throw new IllegalArgumentException(
					option + " is unknown, so it can not be determined to be option");
		}
	}

	public void setOption(StrategyOptionable v) {
		setOption(v.getType(), v.getValue());
	}

	public void setOption(CPACheckerStrategyOptions option, String value) {
		setOption(option.getValue(), value);
	}

	public void setOption(String option, String value) {
		settingsMap.put(option, value);
	}

	public void setSpecification(SpecificationOptionable v) {
		setTaclet(v.getType(), v.getValue());
	}

	public void setTaclet(CPACheckerSpecificationOptions option, String value) {
		setTaclet(option.getValue(), value);
	}

	public void setTaclet(String option, String value) {
		tacletMap.put(option, value);
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
		result = prime * result + mapHashValue(tacletMap);
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
		if (tacletMap == null) {
			if (other.tacletMap != null)
				return false;
		} else if (!mapEquals(tacletMap, other.tacletMap))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "SettingsObject [maxSteps=" + maxEffort + ", settingsMap=" + settingsMap + ", tacletMap=" + tacletMap
				+ "]";
	}

	public CPASettingsObject clone() throws CloneNotSupportedException {
		CPASettingsObject so = new CPASettingsObject();
		so.maxEffort = maxEffort;
		so.settingsMap = new HashMap<>(settingsMap);
		so.tacletMap = new HashMap<>(tacletMap);
		return so;
	}

	@Override
	public OptionableContainer[] getAllPossibleSettings() {
		int aLen = CPACheckerStrategyOptions.values().length;
		int bLen = CPACheckerSpecificationOptions.values().length;
		OptionableContainer[] result = new OptionableContainer[aLen + bLen];
		System.arraycopy(CPACheckerStrategyOptions.values(), 0, result, 0, aLen);
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
