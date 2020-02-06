package de.tubs.isf.guido.verification.systems.cpachecker;

import static com.google.common.base.Preconditions.checkArgument;
import static org.sosy_lab.common.collect.Collections3.transformedImmutableSetCopy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.sosy_lab.common.ShutdownManager;
import org.sosy_lab.common.ShutdownNotifier;
import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.configuration.ConfigurationBuilder;
import org.sosy_lab.common.configuration.FileOption;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.common.configuration.Option;
import org.sosy_lab.common.configuration.Options;
import org.sosy_lab.common.configuration.FileOption.Type;
import org.sosy_lab.common.configuration.converters.FileTypeConverter;
import org.sosy_lab.common.io.IO;
import org.sosy_lab.common.log.BasicLogManager;
import org.sosy_lab.common.log.LogManager;
import org.sosy_lab.common.log.LoggingOptions;
import org.sosy_lab.cpachecker.cfa.Language;
import org.sosy_lab.cpachecker.core.CPAchecker;
import org.sosy_lab.cpachecker.core.CPAcheckerResult;
import org.sosy_lab.cpachecker.core.algorithm.pcc.ProofGenerator;
import org.sosy_lab.cpachecker.core.counterexample.ReportGenerator;
import org.sosy_lab.cpachecker.cpa.automaton.AutomatonGraphmlParser;
import org.sosy_lab.cpachecker.cpa.testtargets.TestTargetType;
import org.sosy_lab.cpachecker.util.Property;
import org.sosy_lab.cpachecker.util.SpecificationProperty;
import org.sosy_lab.cpachecker.util.Property.CommonCoverageType;
import org.sosy_lab.cpachecker.util.Property.CommonPropertyType;
import org.sosy_lab.cpachecker.util.automaton.AutomatonGraphmlCommon.WitnessType;
import org.sosy_lab.cpachecker.util.resources.ResourceLimitChecker;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;


public class MainClass {
	static final int ERROR_EXIT_CODE = 1;
	private static final String SPECIFICATION_OPTION = "specification";
	public static final String APPROACH_NAME_OPTION = "analysis.name";
	private static final ImmutableMap<String, String> EXTERN_OPTION_DEFAULTS = ImmutableMap.of("log.level",
			Level.INFO.toString());
	private static final ImmutableSet<? extends Property> MEMSAFETY_PROPERTY_TYPES = Sets.immutableEnumSet(
			CommonPropertyType.VALID_DEREF, CommonPropertyType.VALID_FREE, CommonPropertyType.VALID_MEMTRACK);
	private static final ImmutableMap<Property, TestTargetType> TARGET_TYPES = ImmutableMap
			.<Property, TestTargetType>builder().put(CommonCoverageType.COVERAGE_BRANCH, TestTargetType.ASSUME)
			.put(CommonCoverageType.COVERAGE_CONDITION, TestTargetType.ASSUME)
			.put(CommonCoverageType.COVERAGE_ERROR, TestTargetType.ERROR_CALL)
			.put(CommonCoverageType.COVERAGE_STATEMENT, TestTargetType.STATEMENT).build();

	@SuppressWarnings("resource") // We don't close LogManager
	public static void main(String[] args) {
		Map<String,String> settings = new HashMap<String, String>();
		settings.put("analysis.restartAfterUnknown", "true");
		settings.put("cfa.useCFACloningForMultiThreadedPrograms", "false");
		settings.put("cpa.smg.memoryErrors", "false");
		settings.put("cpa.smg.enableMallocFail", "false");
		settings.put("cpa.smg.unknownOnUndefined", "false");
		settings.put("cpa.smg.runtimeCheck", "full");
		settings.put("analysis.traversal.order", "rand");
		settings.put("analysis.traversal.usePostorder", "true");
		settings.put("analysis.traversal.summaryEdges", "false");
		settings.put("cpa.callstack.skipRecursion", "true");
		settings.put("cfa.simplifyCfa", "false");
		settings.put("cfa.implifyConstExpressions", "false");
		settings.put("cfa.findLiveVariables", "false");
		settings.put("cpa.predicate.handleStringLiteralInitializers", "false");
		settings.put("analysis.useParallelAnalyses", "false");
		settings.put("cpa.invariants.abstractionStateFactory", "always");
		getCPAcheckerResult("config/default.properties","/media/marlen/54AFF99F466B2AED/eclipse-workspace/pa-marlen-herter-bernier/jobfiles/byte_add_1-2.i" ,"",settings );
	}

	public static CPAcheckerResult getCPAcheckerResult(String configFile,String programFile,String parameters,Map<String,String> settings) {
		Locale.setDefault(Locale.US);

		/**String configFile = "-predicateAnalysis";
		String programFile = "testProg/bAnd1.i";
		String stats = "-stats";
		String options = "";
		**/
		String[] cmd = new String[5+settings.size()*2];
		cmd[0] = "-config";
		cmd[1] = configFile;
		cmd[2] = programFile;
		cmd[3] = "-nolog";
		cmd[4] = parameters;
		int i = 5;
		for(Map.Entry<String,String> entry: settings.entrySet()) {
			cmd[i] = "-setprop";
			cmd[i+1] = entry.getKey() + "=" + entry.getValue();
			i = i+2;
		}

		Configuration cpaConfig = null;
		LoggingOptions logOptions = null;
		LogManager logManager;
		Set<SpecificationProperty> properties = null;
		try {
			try {
				Config p = createConfiguration(cmd);
				cpaConfig = p.configuration;
				properties = p.properties;
			} catch (IOException e) {
				System.out.printf("Could not read config file %s", e.getMessage());
			}

			logOptions = new LoggingOptions(cpaConfig);

		} catch (InvalidConfigurationException e) {
			System.out.printf("Invalid configuration: %s", e.getMessage());
		}

		logManager = BasicLogManager.create(logOptions);
		final ShutdownManager shutdownManager = ShutdownManager.create();
		final ShutdownNotifier shutdownNotifier = shutdownManager.getNotifier();
		CPAchecker cpachecker = null;
		ProofGenerator proofGenerator = null;
		ResourceLimitChecker limits = null;
		MainOptions options = new MainOptions();

		try {
			cpaConfig.inject(options);
			if (options.programs.isEmpty()) {
				throw new InvalidConfigurationException("Please specify a program to analyze on the command line.");
			}
			dumpConfiguration(options, cpaConfig, logManager);

			// generate correct frontend based on file language
			cpaConfig = detectFrontendLanguageIfNecessary(options, cpaConfig, logManager);

			limits = ResourceLimitChecker.fromConfiguration(cpaConfig, logManager, shutdownManager);
			limits.start();

			cpachecker = new CPAchecker(cpaConfig, logManager, shutdownManager);
			if (options.doPCC) {
				proofGenerator = new ProofGenerator(cpaConfig, logManager, shutdownNotifier);
			}
	

		} catch (InvalidConfigurationException e) {
			logManager.logUserException(Level.SEVERE, e, "Invalid configuration");
			System.exit(ERROR_EXIT_CODE);
		}
		CPAcheckerResult result = cpachecker.run(options.programs, properties);
		if (proofGenerator != null) {
			proofGenerator.generateProof(result);
		}
		System.out.flush();
		System.err.flush();
		logManager.flush();
		
		return result;

	}

	private static Config createConfiguration(String[] args) throws InvalidConfigurationException, IOException {
		// if there are some command line arguments, process them
		Map<String, String> cmdLineOptions = null;
	
		cmdLineOptions = CmdLineArguments.processArguments(args);

		boolean secureMode = cmdLineOptions.remove("secureMode") != null;
		if (secureMode) {
			Configuration.enableSecureModeGlobally();
		}
		// Read property file if present and adjust cmdline options
		Set<SpecificationProperty> properties = ImmutableSet.of();

		// get name of config file (may be null)
		// and remove this from the list of options (it's not a real option)
		String configFile = cmdLineOptions.remove("configuration.file");

		// create initial configuration
		// from default values, config file, and command-line arguments
		ConfigurationBuilder configBuilder = Configuration.builder();
		configBuilder.setOptions(EXTERN_OPTION_DEFAULTS);
		if (configFile != null) {
			configBuilder.setOption(APPROACH_NAME_OPTION, extractApproachNameFromConfigName(configFile));
			configBuilder.loadFromFile(configFile);
		}
		configBuilder.setOptions(cmdLineOptions);

		Configuration config = configBuilder.build();

		// We want to be able to use options of type "File" with some additional
		// logic provided by FileTypeConverter, so we create such a converter,
		// add it to our Configuration object and to the the map of default converters.
		// The latter will ensure that it is used whenever a Configuration object
		// is created.
		FileTypeConverter fileTypeConverter = secureMode ? FileTypeConverter.createWithSafePathsOnly(config)
				: FileTypeConverter.create(config);
		String outputDirectory = fileTypeConverter.getOutputDirectory();
		Configuration.getDefaultConverters().put(FileOption.class, fileTypeConverter);

		config = Configuration.builder().copyFrom(config).addConverter(FileOption.class, fileTypeConverter).build();

		try {
			config = handleWitnessOptions(config, cmdLineOptions);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Read witness file if present, switch to appropriate config and adjust cmdline
		// options

		BootstrapOptions options = new BootstrapOptions();
		config.inject(options);

		// Switch to appropriate config depending on property (if necessary)
		config = handlePropertyOptions(config, options, cmdLineOptions, properties);
/*
		if (options.printUsedOptions) {
			config.dumpUsedOptionsTo(System.out);
		}
*/
		return new Config(config, outputDirectory, properties);
	}

	private static class Config {

		private final Configuration configuration;

		private final String outputPath;

		private final Set<SpecificationProperty> properties;

		public Config(Configuration pConfiguration, String pOutputPath, Set<SpecificationProperty> pProperties) {
			configuration = pConfiguration;
			outputPath = pOutputPath;
			properties = ImmutableSet.copyOf(pProperties);
		}
	}

	private static String extractApproachNameFromConfigName(String configFilename) {
		String filename = Paths.get(configFilename).getFileName().toString();
		// remove the extension (most likely ".properties")
		return filename.contains(".") ? filename.substring(0, filename.lastIndexOf(".")) : filename;
	}

	private static Configuration handlePropertyOptions(Configuration config, BootstrapOptions options,
			Map<String, String> cmdLineOptions, Set<SpecificationProperty> pProperties)
			throws InvalidConfigurationException, IOException {
		Set<Property> properties = transformedImmutableSetCopy(pProperties, SpecificationProperty::getProperty);

		final Path alternateConfigFile;

		if (!Collections.disjoint(properties, MEMSAFETY_PROPERTY_TYPES)) {
			alternateConfigFile = check(options.memsafetyConfig, "memory safety", "memorysafety.config");
		} else if (properties.contains(CommonPropertyType.VALID_MEMCLEANUP)) {
			alternateConfigFile = check(options.memcleanupConfig, "memory cleanup", "memorycleanup.config");
		} else if (properties.contains(CommonPropertyType.OVERFLOW)) {
			alternateConfigFile = check(options.overflowConfig, "overflows", "overflow.config");
		} else if (properties.contains(CommonPropertyType.TERMINATION)) {
			alternateConfigFile = check(options.terminationConfig, "termination", "termination.config");
		} else if (properties.contains(CommonCoverageType.COVERAGE_ERROR)
				|| properties.contains(CommonCoverageType.COVERAGE_BRANCH)
				|| properties.contains(CommonCoverageType.COVERAGE_CONDITION)
				|| properties.contains(CommonCoverageType.COVERAGE_STATEMENT)) {
			return Configuration.builder().copyFrom(config)
					.setOption("testcase.targets.type", TARGET_TYPES.get(properties.iterator().next()).name()).build();
		} else {
			alternateConfigFile = null;
		}

		if (alternateConfigFile != null) {
			return Configuration.builder().loadFromFile(alternateConfigFile).setOptions(cmdLineOptions)
					.clearOption("memorysafety.config").clearOption("memorycleanup.config")
					.clearOption("overflow.config").clearOption("termination.config").clearOption("output.disable")
					.clearOption("output.path").clearOption("rootDirectory").clearOption("witness.validation.file")
					.build();
		}
		return config;
	}

	private static Path check(Path config, String verificationTarget, String optionName)
			throws InvalidConfigurationException {
		if (config == null) {
			throw new InvalidConfigurationException(String.format(
					"Verifying %s is not supported if option %s is not specified.", verificationTarget, optionName));
		}
		return config;
	}

	@Options
	private static class WitnessOptions {
		@Option(secure = true, name = "witness.validation.file", description = "The witness to validate.")
		@FileOption(Type.OPTIONAL_INPUT_FILE)
		private @Nullable Path witness = null;

		@Option(secure = true, name = "witness.validation.violation.config", description = "When validating a violation witness, "
				+ "use this configuration file instead of the current one.")
		@FileOption(Type.OPTIONAL_INPUT_FILE)
		private @Nullable Path violationWitnessValidationConfig = null;

		@Option(secure = true, name = "witness.validation.correctness.config", description = "When validating a correctness witness, "
				+ "use this configuration file instead of the current one.")
		@FileOption(Type.OPTIONAL_INPUT_FILE)
		private @Nullable Path correctnessWitnessValidationConfig = null;
	}

	@Options
	private static class BootstrapOptions {
		@Option(secure = true, name = "memorysafety.config", description = "When checking for memory safety properties, "
				+ "use this configuration file instead of the current one.")
		@FileOption(Type.OPTIONAL_INPUT_FILE)
		private @Nullable Path memsafetyConfig = null;

		@Option(secure = true, name = "memorycleanup.config", description = "When checking for memory cleanup properties, "
				+ "use this configuration file instead of the current one.")
		@FileOption(Type.OPTIONAL_INPUT_FILE)
		private @Nullable Path memcleanupConfig = null;

		@Option(secure = true, name = "overflow.config", description = "When checking for the overflow property, "
				+ "use this configuration file instead of the current one.")
		@FileOption(Type.OPTIONAL_INPUT_FILE)
		private @Nullable Path overflowConfig = null;

		@Option(secure = true, name = "termination.config", description = "When checking for the termination property, "
				+ "use this configuration file instead of the current one.")
		@FileOption(Type.OPTIONAL_INPUT_FILE)
		private @Nullable Path terminationConfig = null;

		@Option(secure = true, name = "log.usedOptions.export", description = "all used options are printed")
		private boolean printUsedOptions = false;
	}

	static void putIfNotExistent(final Map<String, String> properties, final String key, final String value) {
		properties.put(key, value);
	}

	@Options
	protected static class MainOptions {
		@Option(secure = true, name = "analysis.programNames",
				// required=true, NOT required because we want to give a nicer user message
				// ourselves
				description = "A String, denoting the programs to be analyzed")
		private ImmutableList<String> programs = ImmutableList.of();

		@Option(secure = true, description = "Programming language of the input program. If not given explicitly, "
				+ "auto-detection will occur")
		// keep option name in sync with {@link CFACreator#language}, value might differ
		private Language language = null;

		@Option(secure = true, name = "configuration.dumpFile", description = "Dump the complete configuration to a file.")
		@FileOption(FileOption.Type.OUTPUT_FILE)
		private Path configurationOutputFile = Paths.get("UsedConfiguration.properties");

		@Option(secure = true, name = "statistics.export", description = "write some statistics to disk")
		private boolean exportStatistics = true;

		@Option(secure = true, name = "statistics.file", description = "write some statistics to disk")
		@FileOption(FileOption.Type.OUTPUT_FILE)
		private Path exportStatisticsFile = Paths.get("Statistics.txt");

		@Option(secure = true, name = "statistics.print", description = "print statistics to console")
		private boolean printStatistics = false;

		@Option(secure = true, name = "pcc.proofgen.doPCC", description = "Generate and dump a proof")
		private boolean doPCC = false;
	}

	private static void dumpConfiguration(MainOptions options, Configuration config, LogManager logManager) {
		if (options.configurationOutputFile != null) {
			try {
				IO.writeFile(options.configurationOutputFile, Charset.defaultCharset(), config.asPropertiesString());
			} catch (IOException e) {
				logManager.logUserException(Level.WARNING, e, "Could not dump configuration to file");
			}
		}
	}

	private static Language detectFrontendLanguageFromFileEndings(ImmutableList<String> pPrograms)
			throws InvalidConfigurationException {
		checkArgument(!pPrograms.isEmpty(), "Empty list of programs");
		Language frontendLanguage = null;
		for (String program : pPrograms) {
			Language language;
			String suffix = program.substring(program.lastIndexOf(".") + 1);
			switch (suffix) {
			case "ll":
			case "bc":
				language = Language.LLVM;
				break;
			case "c":
			case "i":
			case "h":
			default:
				language = Language.C;
				break;
			}
			Preconditions.checkNotNull(language);
			if (frontendLanguage == null) { // first iteration
				frontendLanguage = language;
			}
			if (frontendLanguage != language) { // further iterations: check for conflicting endings
				throw new InvalidConfigurationException(
						String.format("Differing file formats detected: %s and %s files are declared for analysis.",
								frontendLanguage, language) + LANGUAGE_HINT);
			}
		}
		return frontendLanguage;
	}

	private static final String LANGUAGE_HINT = String.format(
			" Please specify a language directly with the option 'language=%s'.", Arrays.toString(Language.values()));

	static Configuration detectFrontendLanguageIfNecessary(MainOptions pOptions, Configuration pConfig,
			LogManager pLogManager) throws InvalidConfigurationException {
		if (pOptions.language == null) {
			// if language was not specified by option, we determine the best matching
			// language
			Language frontendLanguage;

			frontendLanguage = detectFrontendLanguageFromFileEndings(pOptions.programs);
			Preconditions.checkNotNull(frontendLanguage);
			ConfigurationBuilder configBuilder = Configuration.builder();
			configBuilder.copyFrom(pConfig);
			configBuilder.setOption("language", frontendLanguage.name());
			pConfig = configBuilder.build();
			pOptions.language = frontendLanguage;
			//pLogManager.logf(Level.INFO, "Language %s detected and set for analysis", frontendLanguage);
		}
		Preconditions.checkNotNull(pOptions.language);
		return pConfig;
	}

	private static Configuration handleWitnessOptions(Configuration config, Map<String, String> overrideOptions)
			throws InvalidConfigurationException, IOException, InterruptedException {
		WitnessOptions options = new WitnessOptions();
		config.inject(options);
		if (options.witness == null) {
			return config;
		}

		WitnessType witnessType = AutomatonGraphmlParser.getWitnessType(options.witness);
		final Path validationConfigFile;
		switch (witnessType) {
		case VIOLATION_WITNESS:
			validationConfigFile = options.violationWitnessValidationConfig;
			String specs = overrideOptions.get(SPECIFICATION_OPTION);
			String witnessSpec = options.witness.toString();
			specs = specs == null ? witnessSpec : Joiner.on(',').join(specs, witnessSpec.toString());
			overrideOptions.put(SPECIFICATION_OPTION, specs);
			break;
		case CORRECTNESS_WITNESS:
			validationConfigFile = options.correctnessWitnessValidationConfig;
			overrideOptions.put("invariantGeneration.kInduction.invariantsAutomatonFile", options.witness.toString());
			break;
		default:
			throw new InvalidConfigurationException(
					"Witness type " + witnessType + " of witness " + options.witness + " is not supported");
		}
		if (validationConfigFile == null) {
			throw new InvalidConfigurationException(
					"Validating (violation|correctness) witnesses is not supported if option witness.validation.(violation|correctness).config is not specified.");
		}
		return Configuration.builder().copyFrom(config).loadFromFile(validationConfigFile).setOptions(overrideOptions)
				.clearOption("witness.validation.file").clearOption("witness.validation.violation.config")
				.clearOption("witness.validation.correctness.config").clearOption("output.path")
				.clearOption("rootDirectory").build();
	}
}
