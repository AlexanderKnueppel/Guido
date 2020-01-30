package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sosy_lab.cpachecker.core.CPAcheckerResult;

import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.verifier.SettingsObject;


public abstract class AbstractCPACheckerControl implements IProofControl{
	private static final Map<String, Map<String, String>> VALUES = new HashMap<>();
	private static final Map<String, String> PROPERTIES = new HashMap<>();

	private static final Map<String, Map<String, String>> BACKWARDS_VALUES = new HashMap<>();
	private static final Map<String, String> BACKWARDS_PROPERTIES = new HashMap<>();
	

	public List<IDataBasisElement> getResultForProof(String configFilePath, File binary, File source, 
			 SettingsObject so) {
		return getResultForProof(configFilePath, binary, source, null,  so);
	}

	abstract List<IDataBasisElement> getResultForProof(String configFilePath, File binary, File source, 
			String[] parameters,int num, SettingsObject so);

	public List<IDataBasisElement> getResultForProof(String configFilePath, File binary, File source, 
			String[] parameters, SettingsObject so) {
		return getResultForProof(configFilePath, binary, source, parameters,  so);
	}
	/**
	 * Ermittelt die Namen der mitgegebenen Strategie-Option, aller Unterstrategien
	 * und die der dazugehoerigen Auswahlmoeglichkeiten. Speichert diese in VALUES
	 * und PROPERTIES ab.
	 * 
	 * @param stratProp Die zu analysierende Strategie-Option
	
	private static void analyzeStratProp(AbstractStrategyPropertyDefinition stratProp) {
		try {
			// Sonderlocke fuer Expand local queries -_-
			String propName = stratProp.getName().replace(":", "");
			final Map<String, String> valueMap = new HashMap<>();
			PROPERTIES.put(propName, stratProp.getApiKey());
			VALUES.put(propName, valueMap);

			Field f = stratProp.getClass().getDeclaredField("values");
			f.setAccessible(true);
			ImmutableArray<?> values = (ImmutableArray<?>) f.get(stratProp);
			values.forEach(value -> analyzeStratValue(value, valueMap));

			stratProp.getSubProperties().forEach(subProp -> analyzeStratProp(subProp));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 */
	/**
	 * Ermittelt die Auswahlmoeglichkeiten zu einer Strategie und speichert die
	 * Werte in der mitgegebenen Map.
	 * 
	 * @param stratValue Der zu analysierende Wert
	 * @param valueMap   Die Map, in die der Name und der Key-Name hinzugefuegt
	 *                   werden sollen
	 
	private static void analyzeStratValue(Object stratValue, Map<String, String> valueMap) {
		if (stratValue instanceof StrategyPropertyValueDefinition) {
			try {
				// StrategyPropertyValueDefinition
				// spvd =
				// (StrategyPropertyValueDefinition)
				// value;
				// Does not work, so I have to use
				// reflection. What the serious
				// f***?
				Method method = stratValue.getClass().getMethod("getValue");
				String value = (String) method.invoke(stratValue);
				method = stratValue.getClass().getMethod("getApiValue");
				String apiValue = (String) method.invoke(stratValue);
				valueMap.put(value, apiValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("StratValue is not a StrategyPropertyValueDefinition");
		}
	}
*/
	private static Map<String, Map<String, String>> UNMOD_VALUES;

	private Map<String, Map<String, String>> getValues() {
		synchronized (VALUES) {
			if (UNMOD_VALUES == null) {
				UNMOD_VALUES = Collections.unmodifiableMap(VALUES);
			}
		}
		return UNMOD_VALUES;
	}

	private static Map<String, String> UNMOD_PROPERTIES;

	private Map<String, String> getProperties() {
		synchronized (PROPERTIES) {
			if (UNMOD_PROPERTIES == null) {
				UNMOD_PROPERTIES = Collections.unmodifiableMap(PROPERTIES);
			}
		}
		return UNMOD_PROPERTIES;
	}

	private static Map<String, Map<String, String>> UNMOD_BACKWARDS_VALUES;

	private Map<String, Map<String, String>> getBackwardsValues() {
		synchronized (BACKWARDS_PROPERTIES) {
			if (UNMOD_BACKWARDS_VALUES == null) {
				UNMOD_BACKWARDS_VALUES = Collections.unmodifiableMap(BACKWARDS_VALUES);
			}
		}
		return UNMOD_BACKWARDS_VALUES;
	}

	private static Map<String, String> UNMOD_BACKWARDS_PROPERTIES;

	private Map<String, String> getBackwardsProperties() {
		synchronized (BACKWARDS_PROPERTIES) {
			if (UNMOD_BACKWARDS_PROPERTIES == null) {
				UNMOD_BACKWARDS_PROPERTIES = Collections.unmodifiableMap(BACKWARDS_PROPERTIES);
			}
		}
		return UNMOD_BACKWARDS_PROPERTIES;
	}

	protected CPACheckerDataBasisElement createResult( CPAcheckerResult result, List<String> languageConstructs, Map<String,String> optionMap) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Long timeInMillis = 0L;
		Long totalTime = 0L;
		Long CPUTime = 0L;
		boolean bool = false;
		Long totalVirtMem = 0L;
		try {
			PrintStream ps = new PrintStream(baos, true, "UTF-8");
			result.printStatistics(ps);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String data = new String(baos.toByteArray(), StandardCharsets.UTF_8);
		data = data.substring(data.indexOf("CPAchecker general statistics"));
		String[] dataArray = data.split("\n");
		for(String d : dataArray) {
			if(d.startsWith("Time for Analysis:  ")) {
				timeInMillis = getTimeFromString(d,"Time for Analysis:  ");
			}
			if(d.startsWith("Total time for CPAchecker:  ")) {
				totalTime = getTimeFromString(d,"Total time for CPAchecker:  ");
			}
			if(d.startsWith("Total CPU time for CPAchecker:  ")) {
				CPUTime = getTimeFromString(d,"Total CPU time for CPAchecker:  ");
			}
			if(d.startsWith("Total process virtual memory:")) {
				String[] memStringArray = d.split("MB");
				String memString = memStringArray[0].replace("Total process virtual memory:","");
				memString = memString.replace(" ", "");
				memString = memString.replace("MB", "");
				totalVirtMem = Long.valueOf(memString);
			}
		}
		
		
		if(result.getResultString().contains("TRUE")) {
			System.out.println("CPAChecker finished with true");
			bool = true;
		}
		return new CPACheckerDataBasisElement("CPAChecker",bool, 
				timeInMillis, CPUTime, totalTime,totalVirtMem,languageConstructs,optionMap);
	}
	private Long getTimeFromString (String s, String statistic) {
		String timeInSec = s.replace(statistic, "").trim();
		timeInSec = timeInSec.replace("s","");
		timeInSec = timeInSec.replace(".","");
		return Long.valueOf(timeInSec);			
	}

}
