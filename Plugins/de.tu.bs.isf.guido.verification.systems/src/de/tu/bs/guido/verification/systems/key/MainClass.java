package de.tu.bs.guido.verification.systems.key;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.tu.bs.guido.verification.system.Control;
import de.tu.bs.guido.verification.system.Result;
import de.tu.bs.guido.verification.systems.key.options.Optionable;
import de.tu.bs.guido.verification.systems.key.options.SettingsObject;
import de.tu.bs.guido.verification.systems.key.options.strategies.ArithmeticTreatmentOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.AutoInductionOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.BlockTreatmentOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.ClassAxiomRulesOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.DependencyContractsOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.ExpandLocalQueriesOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.LoopTreatmentOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.MergePointStatementsOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.MethodTreatmentOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.OneStepSimplificationOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.ProofSplittingOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.QuantifierTreatmentOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.QueryTreatmentOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.StopAtOptions;
import de.tu.bs.guido.verification.systems.key.options.taclets.AssertionsTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.BigIntTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.InitialisationTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.IntRulesTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.IntegerSimplificationRulesTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.JavaCardTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.MergeGenerateIsWeakeningGoalTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.ModelFieldsTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.MoreSeqRulesTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.PermissionsTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.ProgramRulesTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.ReachTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.RuntimeExceptionsTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.SequencesTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.StringsTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.WdChecksTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.WdOperatorTaclet;

public class MainClass {

	public static File sourcePath = new File(
			"./../VerificationData/VerificationData_ThreeWiseSampling/ReduxProblemSolved");
	public static File reduxPath = new File("./../Resources/JavaRedux");
	public static Control kc = new ExampleBasedKeyControl();

	public static void calc(File folder) {

		// File folder = new
		// File("./../../VerificationData/VerificationData_AutomatedVerification/Samplings");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());

				Path path = Paths.get(listOfFiles[i].getAbsolutePath());
				Charset charset = StandardCharsets.UTF_8;

				String content;
				try {
					content = new String(Files.readAllBytes(path), charset);
					String contents = content.replaceAll("Block_2_treatment_1__1_Contract",
							"Block_2_treatment_1__1_External_2_Contract");
					Files.write(path, contents.getBytes(charset));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
				calc(listOfFiles[i]);
			}
		}

	}

	public static void main(String[] args)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		try {
			reduxPath.mkdirs();
			reduxPath.createNewFile();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sourcePath.mkdirs();
			sourcePath.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File folder = new File("./../VerificationData/VerificationData_ThreeWiseSampling/Samplings");

		// calc(folder);

		Optionable[] opts = new Optionable[] { MergePointStatementsOptions.MERGE, StopAtOptions.UNCLOSABLE,
				ProofSplittingOptions.FREE, LoopTreatmentOptions.NONE, BlockTreatmentOptions.EXTERNALCONTRACT,
				MethodTreatmentOptions.CONTRACT, DependencyContractsOptions.ON, QueryTreatmentOptions.ON,
				ExpandLocalQueriesOptions.OFF, ArithmeticTreatmentOptions.MODEL_SEARCH, QuantifierTreatmentOptions.FREE,
				ClassAxiomRulesOptions.DELAYED, AutoInductionOptions.OFF, OneStepSimplificationOptions.ENABLED,
				AssertionsTaclet.SAFE, InitialisationTaclet.DISABLE_STATIC_INITIALISATION,
				IntRulesTaclet.JAVA_SEMANTICS, ProgramRulesTaclet.JAVA, RuntimeExceptionsTaclet.BAN, JavaCardTaclet.OFF,
				StringsTaclet.ON, ModelFieldsTaclet.TREAT_AS_AXIOM, BigIntTaclet.ON, SequencesTaclet.OFF,
				ReachTaclet.OFF, IntegerSimplificationRulesTaclet.FULL, PermissionsTaclet.OFF, WdOperatorTaclet.L,
				WdChecksTaclet.OFF, MergeGenerateIsWeakeningGoalTaclet.OFF, MoreSeqRulesTaclet.ON };
		Optionable[] defaultOpts = new Optionable[] {};

		outPutProofResults(
				proof("ElevatorSystem.Elevator", "timeShift", new String[] { "EmailSystem.Client", "EmailSystem.Mail" },
						0, createDesiredSettingsObject(opts, 1000000)));

	}

	private static void outPutProofResults(List<? extends Result> res) {
		res.forEach(result -> {
			System.out.println(result.getName());
			System.out.println(result.isClosed() ? result.getSteps() : "notClosed!");
			System.out.println("____________________________________________");
		});
	}

	public static SettingsObject createDesiredSettingsObject(Optionable[] opts, int maxSteps) {
		SettingsObject so = new SettingsObject();
		for (Optionable opt : opts) {
			so.setParameter(opt);
		}
		so.setMaxSteps(maxSteps);
		return so;
	}

	private static List<? extends Result> proof(String classname, String methodname, String[] methodparameter, int contractNumber,
			SettingsObject so) {
		return kc.getResultForProof(sourcePath, reduxPath, classname, methodname, methodparameter, contractNumber, so);
	}

	private static List<? extends Result> proofWithOwnSourceFile(File source, String classname, String methodname,
			String[] methodparameter, int contractNumber, SettingsObject so) {
		return kc.getResultForProof(source, reduxPath, classname, methodname, methodparameter, contractNumber, so);
	}

}
