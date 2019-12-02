package de.tu.bs.guido.core.keYHandler;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.key_project.util.collection.ImmutableArray;
import org.key_project.util.collection.ImmutableSet;

import de.tu.bs.guido.core.analyzer.Result;
import de.tu.bs.guido.verification.system.SettingsObject;
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

public class ProofControl {
	List<Contract> contracts;
	int contractNumber;
	File classPath;
	File source;
	String className;
	String methodName;
	String[] parameters;
	List<Result> currentResults;

	public ProofControl(File source, File classPath, String className, String methodName, String[] parameters,
			int contractNumber) {
		this.contractNumber = contractNumber;
		this.source = source;
		this.classPath = classPath;
		this.className = className;
		this.methodName = methodName;
		this.parameters = parameters;
		currentResults = new ArrayList<>();
	}

	public boolean isClosed() {
		boolean closed = false;
		for (Result result : currentResults) {
			if (!result.isClosed()) {
				return false; // as soon as one proof attempt could not be
								// closed - return false
			} else {
				closed = true;
			}
		}
		return closed;
	}
	
	public List<Result> getCurrentResults(){
		return currentResults;
	}

	public void performProof(SettingsObject so){
		currentResults = new ArrayList<>(); // remove old results before every
											// new performing proof
		if (!ProofSettings.isChoiceSettingInitialised()) {
			KeYEnvironment<?> env;
			try {
				env = KeYEnvironment.load(source, null,
						classPath, null);
				env.dispose();
			} catch (ProblemLoaderException e) {
				e.printStackTrace();
			}
		}
		applySettings(so);
		KeYEnvironment<?> env;
		try {
			env = KeYEnvironment.load(source, null, classPath,
					null);
			try {
				KeYJavaType type = env.getJavaInfo().getKeYJavaType(className);
				final List<Contract> proofContracts = getCorrectContract(
						methodName, parameters, env.getSpecificationRepository(), type);
				if (contractNumber == -1) {
					for (Contract contract : proofContracts) {
						currentResults.add(getResult(env, contract, so));
					}
				} else {
					currentResults.add(getResult(env, proofContracts.get(contractNumber), so));
				}
			} catch (ProofInputException e) {
				e.printStackTrace();
			} finally {
				env.dispose();
			}
		} catch (ProblemLoaderException e) {
			e.printStackTrace();
		}
		
	}

	private List<Contract> getCorrectContract(String methodName, String[] parameter, SpecificationRepository specRepo,
			KeYJavaType kjt) {

		List<Contract> proofContracts = new LinkedList<Contract>();
		IObserverFunction function = getCorrectIObserverFunction(methodName, parameter, specRepo, kjt);
		ImmutableSet<Contract> contracts = specRepo.getContracts(kjt, function);
		for (Contract contract : contracts) {
			proofContracts.add(contract);
		}
		return proofContracts;
	}

	private static IObserverFunction getCorrectIObserverFunction(String methodName, String[] parameter,
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

	public Result getResult(KeYEnvironment<?> env, Contract c, SettingsObject so)
			throws ProofInputException {
		Proof proof = null;
		try {
			proof = env.createProof(c.createProofObl(
					env.getInitConfig(), c));
			applySettings(proof, so);
			env.getUi().getProofControl().startAndWaitForAutoMode(proof);
			System.out.println("Proof performed - Create result");
			return createResult(c, proof);
		} finally {
			if (proof != null) {
				proof.dispose(); // Ensure always that all instances of Proof
									// are disposed
			}
		}
	}

	private Result createResult(Contract contract, Proof p) {
		ImmutableSet<Choice> immTacletChoices = p.getSettings().getChoiceSettings().getDefaultChoicesAsSet();
		Map<String, String> tacletChoices = new HashMap<>(immTacletChoices.size());
		immTacletChoices.forEach(choice -> tacletChoices.put(choice.category(), choice.name().toString()));
		System.out.println("Closed: " + p.closed());
		System.out.println("Nodes: " + p.countNodes());
		return new Result(contract.toString(), contract.getName(), p.closed(), p.countNodes(),
				p.getStatistics().timeInMillis, null, tacletChoices);
	}

	private void applySettings(SettingsObject so) {
		ChoiceSettings cs = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
		HashMap<String, String> tacletMap = cs.getDefaultChoices();
		HashMap<String, String> newSettings = new HashMap<String, String>(
				tacletMap);
		so.getTacletMap().forEach((key, value) -> newSettings.put(key, value));
		cs.setDefaultChoices(newSettings);
	}

	protected void applySettings(Proof p, SettingsObject so) {
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
		ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setMaxSteps(so.getMaxSteps());
		ss.setMaxSteps(so.getMaxSteps());
		ss.setActiveStrategyProperties(sp);
		ps.saveSettings();
		p.setActiveStrategy(p.getServices().getProfile().getDefaultStrategyFactory().create(p, sp));
	}

	private static final Map<String, Map<String, String>> BACKWARDS_VALUES = new HashMap<>();
	private static final Map<String, String> BACKWARDS_PROPERTIES = new HashMap<>();
	private static final Map<String, Map<String, String>> VALUES = new HashMap<>();
	private static final Map<String, String> PROPERTIES = new HashMap<>();
	private static Map<String, Map<String, String>> UNMOD_VALUES;

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

	private static Map<String, Map<String, String>> getValues() {
		synchronized (VALUES) {
			if (UNMOD_VALUES == null) {
				UNMOD_VALUES = Collections.unmodifiableMap(VALUES);
			}
		}
		return UNMOD_VALUES;
	}

	private static Map<String, String> UNMOD_PROPERTIES;

	private static Map<String, String> getProperties() {
		synchronized (PROPERTIES) {
			if (UNMOD_PROPERTIES == null) {
				UNMOD_PROPERTIES = Collections.unmodifiableMap(PROPERTIES);
			}
		}
		return UNMOD_PROPERTIES;
	}

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

}
