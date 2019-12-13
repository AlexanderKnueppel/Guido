package de.tu.bs.guido.verification.systems.key.options;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.tu.bs.guido.verification.system.SettingsObject;
import de.tu.bs.guido.verification.systems.key.options.Optionable;
import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;
import de.tu.bs.guido.verification.systems.key.options.strategies.KeyStrategyOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.StrategyOptionable;
import de.tu.bs.guido.verification.systems.key.options.taclets.KeyTacletOptions;
import de.tu.bs.guido.verification.systems.key.options.taclets.TacletOptionable;

public class KeySettingsObject extends SettingsObject {

	private static final long serialVersionUID = -5974976146446740045L;

	
	public KeySettingsObject() {
		for (KeyStrategyOptions option : KeyStrategyOptions.values()) {
			setOption(option.getDefault());
		}
		for (KeyTacletOptions taclet : KeyTacletOptions.values()) {
			setTaclet(taclet.getDefault());
		}
	}
	@Override
	public void reinitialize() {
		for (KeyStrategyOptions option : KeyStrategyOptions.values()) {
			if (!settingsMap.containsKey(option.getValue()))
				setOption(option.getDefault());
		}
		for (KeyTacletOptions taclet : KeyTacletOptions.values()) {
			if (!tacletMap.containsKey(taclet.getValue()))
				setTaclet(taclet.getDefault());
		}
	}
	@Override
	public int getMaxSteps() {
		return maxSteps;
	}
	@Override
	public void setMaxSteps(int maxSteps) {
		this.maxSteps = maxSteps;
	}
	@Override
	public Map<String, String> getSettingsMap() {
		return Collections.unmodifiableMap(settingsMap);
	}
	@Override
	public Map<String, String> getTacletMap() {
		return Collections.unmodifiableMap(tacletMap);
	}
	@Override
	public Optionable getOption(OptionableContainer o) {
		String key = o.getValue();
		if (o instanceof KeyStrategyOptions) {
			String value = settingsMap.get(o.getValue());
			return KeyStrategyOptions.getOption(key, value);
		} else if (o instanceof KeyTacletOptions) {
			String value = tacletMap.get(o.getValue());
			return KeyTacletOptions.getOption(key, value);
		}
		throw new IllegalArgumentException();
	}
	@Override
	public void setParameter(Optionable o) {
		if (o instanceof StrategyOptionable) {
			setOption((StrategyOptionable) o);
		} else if (o instanceof TacletOptionable) {
			setTaclet((TacletOptionable) o);
		} else {
			throw new IllegalArgumentException(o + " is unknown, so it can not be determined to be option or taclet");
		}
	}
	@Override
	public void setParameter(String option, String value) {
		if (KeyStrategyOptions.isOption(option)) {
			setOption(option, value);
		} else if (KeyTacletOptions.isTaclet(option)) {
			setTaclet(option, value);
		} else {
			throw new IllegalArgumentException(
					option + " is unknown, so it can not be determined to be option or taclet");
		}
	}
	@Override
	public void setOption(StrategyOptionable v) {
		setOption(v.getType(), v.getValue());
	}
	@Override
	public void setOption(KeyStrategyOptions option, String value) {
		setOption(option.getValue(), value);
	}
	@Override
	public void setOption(String option, String value) {
		settingsMap.put(option, value);
	}
	@Override
	public void setTaclet(TacletOptionable v) {
		setTaclet(v.getType(), v.getValue());
	}
	@Override
	public void setTaclet(KeyTacletOptions option, String value) {
		setTaclet(option.getValue(), value);
	}
	@Override
	public void setTaclet(String option, String value) {
		tacletMap.put(option, value);
	}
	@Override
	public int getDebugNumber() {
		return debugNumber;
	}
	@Override
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
		result = prime * result + maxSteps;
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
		KeySettingsObject other = (KeySettingsObject) obj;
		if (maxSteps != other.maxSteps)
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
		return "SettingsObject [maxSteps=" + maxSteps + ", settingsMap=" + settingsMap + ", tacletMap=" + tacletMap
				+ "]";
	}

	public KeySettingsObject clone() throws CloneNotSupportedException {
		KeySettingsObject so = new KeySettingsObject();
		so.maxSteps = maxSteps;
		so.settingsMap = new HashMap<>(settingsMap);
		so.tacletMap = new HashMap<>(tacletMap);
		return so;
	}
}