package de.tubs.isf.guido.verification.systems.key;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.key_project.util.collection.ImmutableArray;
import org.key_project.util.collection.ImmutableSet;

import de.tubs.isf.guido.core.analysis.JMLContractAnalyzer;
import de.tubs.isf.guido.core.analysis.JavaSourceCodeAnalyzer;
import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.uka.ilkd.key.control.KeYEnvironment;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.logic.Choice;
import de.uka.ilkd.key.logic.op.IObserverFunction;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.init.JavaProfile;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.proof.mgt.SpecificationRepository;
import de.uka.ilkd.key.settings.ChoiceSettings;
import de.uka.ilkd.key.settings.ProofSettings;
import de.uka.ilkd.key.settings.StrategySettings;
import de.uka.ilkd.key.speclang.Contract;
import de.uka.ilkd.key.strategy.StrategyProperties;
import de.uka.ilkd.key.strategy.definition.AbstractStrategyPropertyDefinition;
import de.uka.ilkd.key.strategy.definition.StrategyPropertyValueDefinition;
import de.uka.ilkd.key.strategy.definition.StrategySettingsDefinition;

public class ExampleBasedKeyControl implements IProofControl {
	List<IDataBasisElement> kdb = new ArrayList<IDataBasisElement>();

	JavaSourceCodeAnalyzer jsca = null;
	private static final Map<String, Map<String, String>> VALUES = new HashMap<>();
	private static final Map<String, String> PROPERTIES = new HashMap<>();

	private static final Map<String, Map<String, String>> BACKWARDS_VALUES = new HashMap<>();
	private static final Map<String, String> BACKWARDS_PROPERTIES = new HashMap<>();

	static {
		StrategySettingsDefinition ssd = JavaProfile.DEFAULT.getSettingsDefinition();
		ssd.getProperties().forEach(action -> analyzeStratProp(action));

		PROPERTIES.forEach((property, keyProperty) -> BACKWARDS_PROPERTIES.put(keyProperty, property));
		VALUES.forEach((property, values) -> {
			Map<String, String> backwards_values = new HashMap<>();
			BACKWARDS_VALUES.put(PROPERTIES.get(property), backwards_values);
			values.forEach((value, keyValue) -> backwards_values.put(keyValue, value));
		});
	}

	public List<IDataBasisElement> getResultForProof(File source, File classPath, String className, String methodName,
			SettingsObject so) {
		return getResultForProof(source, classPath, className, methodName, -1, so);

	}

	public List<IDataBasisElement> getResultForProof(File source, File classPath, String className, String methodName,
			int contractNumber, SettingsObject so) {
		return getResultForProof(source, classPath, className, methodName, null, contractNumber, so);
	}

	public List<IDataBasisElement> getResultForProof(File source, File classPath, String className, String methodName,
			String[] parameters, SettingsObject so) {
		return getResultForProof(source, classPath, className, methodName, parameters, -1, so);
	}

	public int getNumberOfContracts(File source, File classPath, String className, String methodName) {
		return getNumberOfContracts(source, classPath, className, methodName, null);
	}

	/**
	 * Ermittelt die Namen der mitgegebenen Strategie-Option, aller Unterstrategien
	 * und die der dazugehoerigen Auswahlmoeglichkeiten. Speichert diese in VALUES
	 * und PROPERTIES ab.
	 * 
	 * @param stratProp Die zu analysierende Strategie-Option
	 */
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

	/**
	 * Ermittelt die Auswahlmoeglichkeiten zu einer Strategie und speichert die
	 * Werte in der mitgegebenen Map.
	 * 
	 * @param stratValue Der zu analysierende Wert
	 * @param valueMap   Die Map, in die der Name und der Key-Name hinzugefuegt
	 *                   werden sollen
	 */
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

	/**
	 * Mappt die Optionen aus Key zurueck auf die lesbaren Optionen und gibt diese
	 * zurueck.
	 * 
	 * @param props Die fuer einen Beweis verwendeten Optionen
	 * @return
	 */
	protected Map<String, String> createSmallReadableOptionMap(StrategyProperties props) {
		Map<String, String> result = new HashMap<>();

		props.forEach((key, value) -> {
			String outputKey = getBackwardsProperties().get(key);
			if (outputKey == null) {
				return;
			}
			String outputValue = getBackwardsValues().get(key).get(value);
			if (outputValue != null) {
				result.put(outputKey, outputValue);
			}
		});

		return result;
	}

	/**
	 * Wendet die Settings auf den aktuell ausgewaehlten Befehl an.
	 * 
	 * @param m
	 * @param so
	 */
	protected void applySettings(Proof p, KeySettingsObject so) {
		StrategySettingsDefinition ssd = JavaProfile.DEFAULT.getSettingsDefinition();
		ProofSettings ps = p.getSettings();
		StrategySettings ss = ps.getStrategySettings();
		StrategyProperties sp = ssd.getDefaultPropertiesFactory().createDefaultStrategyProperties();
		so.getSettingsMap().forEach((key, value) -> {
			try {
				sp.setProperty(getProperties().get(key), getValues().get(key).get(value));
			} catch (NullPointerException e) {
				throw new IllegalArgumentException(
						key + " is not a valid option or " + value + " is not a valid choice.");
			}
		});
		ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setMaxSteps((int) so.getMaxEffort());
		ss.setMaxSteps((int) so.getMaxEffort());
		ss.setActiveStrategyProperties(sp);
		ps.saveSettings();
		p.setActiveStrategy(p.getServices().getProfile().getDefaultStrategyFactory().create(p, sp));
	}

	protected HashMap<String, String> applyDefaultTaclets(SettingsObject so) {
		ChoiceSettings cs = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
		HashMap<String, String> tacletMap = cs.getDefaultChoices();
		HashMap<String, String> newSettings = new HashMap<String, String>(tacletMap);
		so.getSettingsMap().forEach((key, value) -> newSettings.put(key, value));
		cs.setDefaultChoices(newSettings);
		return newSettings;
	}

	protected void applyTaclets(Proof p, SettingsObject so) {
		HashMap<String, String> tacletMap = applyDefaultTaclets(so);
		p.getSettings().getChoiceSettings().setDefaultChoices(tacletMap);
		p.getSettings().saveSettings();
	}

	private IObserverFunction getCorrectIObserverFunction(String methodName, String[] parameter,
			SpecificationRepository specRepo, KeYJavaType kjt) {
		ImmutableSet<IObserverFunction> targets = specRepo.getContractTargets(kjt);
		top: for (IObserverFunction iObserverFunction : targets) {
			boolean b = iObserverFunction.name().toString().endsWith("::" + methodName);
			if (b) {
				if (parameter == null) {
					return iObserverFunction;
				} else {
					ImmutableArray<KeYJavaType> kjts = iObserverFunction.getParamTypes();
					if (kjts.size() == parameter.length) {
						for (int i = 0; i < parameter.length; i++) {
							if (!kjts.get(i).getFullName().equals(parameter[i]))
								continue top;
						}
						return iObserverFunction;
					}
				}
			}
		}
		throw new IllegalArgumentException("Could not find contract for method " + methodName);
	}

	protected List<Contract> getCorrectContract(String methodName, String[] parameter, SpecificationRepository specRepo,
			KeYJavaType kjt) {

		List<Contract> proofContracts = new LinkedList<Contract>();
		IObserverFunction function = getCorrectIObserverFunction(methodName, parameter, specRepo, kjt);
		ImmutableSet<Contract> contracts = specRepo.getContracts(kjt, function);
		for (Contract contract : contracts) {
			proofContracts.add(contract);
		}
		return proofContracts;
	}

	protected KeyDataBasisElement createResult(Contract contract, Proof p, List<String> languageConstructs) {
		StrategySettings ss = p.getSettings().getStrategySettings();
		StrategyProperties sp = ss.getActiveStrategyProperties();
		ImmutableSet<Choice> immTacletChoices = p.getSettings().getChoiceSettings().getDefaultChoicesAsSet();
		Map<String, String> tacletChoices = new HashMap<>(immTacletChoices.size());
		immTacletChoices.forEach(choice -> tacletChoices.put(choice.category(), choice.name().toString()));

		return new KeyDataBasisElement(contract.toString(), contract.getName(), p.closed(), p.countNodes(),
				p.getStatistics().timeInMillis, languageConstructs, createSmallReadableOptionMap(sp), tacletChoices);
	}

	public List<IDataBasisElement> getResultForProof(File source, File classPath, String className, String methodName,
			String[] parameters, int contractNumber, SettingsObject so) {

		File dir = source;
		if (dir.isFile())
			dir = source.getParentFile();

		jsca = new JavaSourceCodeAnalyzer(dir, className, methodName, parameters);
		jsca.setContractAnalyzer(new JMLContractAnalyzer());

		KeySettingsObject so1 = (KeySettingsObject) so;

		List<IDataBasisElement> res = new ArrayList<IDataBasisElement>();
		if (!ProofSettings.isChoiceSettingInitialised()) {
			KeYEnvironment<?> env = null;
			try {
				env = KeYEnvironment.load(source, null, classPath, null);
			} catch (ProblemLoaderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			env.dispose();
		}
		applyDefaultTaclets(so1);
		KeYEnvironment<?> env = null;
		try {
			env = KeYEnvironment.load(source, null, classPath, null);
		} catch (ProblemLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			KeYJavaType type = env.getJavaInfo().getKeYJavaType(className);
			final List<Contract> proofContracts = getCorrectContract(methodName, parameters,
					env.getSpecificationRepository(), type);
			if (contractNumber == -1) {
				for (Contract contract : proofContracts) {
					res.add(getResult(env, contract, so1));
				}
			} else {
				res.add(getResult(env, proofContracts.get(contractNumber), so1));
			}
		} finally {
			env.dispose();
		}
		return res;
	}

	private KeyDataBasisElement getResult(KeYEnvironment<?> env, Contract contract, KeySettingsObject so) {
		Proof proof = null;
		try {
			proof = env.createProof(contract.createProofObl(env.getInitConfig(), contract));
			applySettings(proof, so);
			env.getUi().getProofControl().startAndWaitForAutoMode(proof);
			if (jsca.analyze() == null)
				return createResult(contract, proof, new ArrayList<String>());
			return createResult(contract, proof,
					jsca.analyze().stream().map(l -> l.getLanguageConstruct()).collect(Collectors.toList()));
		} catch (ProofInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (proof != null) {
				proof.dispose(); // Ensure always that all instances of Proof
									// are disposed
			}
		}
		return null;
	}

	public int getNumberOfContracts(File source, File classPath, String className, String methodName,
			String[] parameters) {
		KeYEnvironment<?> env = null;
		try {
			env = KeYEnvironment.load(source, null, classPath, null);
		} catch (ProblemLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			KeYJavaType type = env.getJavaInfo().getKeYJavaType(className);
			List<Contract> proofContracts = getCorrectContract(methodName, parameters, env.getSpecificationRepository(),
					type);
			return proofContracts.size();
		} finally {
			env.dispose();
		}
	}

	@Override
	public IDataBasisElement performProof(IJob job) {

		KeyJavaJob keyjob = (KeyJavaJob) job;
		KeyCodeContainer kcc = (KeyCodeContainer) job.getCodeContainer();

		return getResultForProof(new File(kcc.getSource()), new File(kcc.getClasspath()), kcc.getClazz(),
				kcc.getMethod(), job.getSo()).get(0);

	}

}
