package de.tu.bs.guido.key.simulator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.tu.bs.guido.key.simulator.control.ExampleBasedKeyControl;
import de.tu.bs.guido.key.simulator.control.KeyControl;
import de.tu.bs.guido.key.simulator.options.Optionable;
import de.tu.bs.guido.key.simulator.options.SettingsObject;
import de.tu.bs.guido.key.simulator.options.strategy.ArithmeticTreatmentOptions;
import de.tu.bs.guido.key.simulator.options.strategy.AutoInductionOptions;
import de.tu.bs.guido.key.simulator.options.strategy.BlockTreatmentOptions;
import de.tu.bs.guido.key.simulator.options.strategy.ClassAxiomRulesOptions;
import de.tu.bs.guido.key.simulator.options.strategy.DependencyContractsOptions;
import de.tu.bs.guido.key.simulator.options.strategy.ExpandLocalQueriesOptions;
import de.tu.bs.guido.key.simulator.options.strategy.KeyStrategyOptions;
import de.tu.bs.guido.key.simulator.options.strategy.LoopTreatmentOptions;
import de.tu.bs.guido.key.simulator.options.strategy.MergePointStatementsOptions;
import de.tu.bs.guido.key.simulator.options.strategy.MethodTreatmentOptions;
import de.tu.bs.guido.key.simulator.options.strategy.OneStepSimplificationOptions;
import de.tu.bs.guido.key.simulator.options.strategy.ProofSplittingOptions;
import de.tu.bs.guido.key.simulator.options.strategy.QuantifierTreatmentOptions;
import de.tu.bs.guido.key.simulator.options.strategy.QueryTreatmentOptions;
import de.tu.bs.guido.key.simulator.options.strategy.StopAtOptions;
import de.tu.bs.guido.key.simulator.options.taclets.AssertionsTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.BigIntTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.InitialisationTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.IntRulesTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.IntegerSimplificationRulesTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.JavaCardTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.MergeGenerateIsWeakeningGoalTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.ModelFieldsTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.MoreSeqRulesTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.PermissionsTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.ProgramRulesTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.ReachTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.RuntimeExceptionsTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.SequencesTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.StringsTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.WdChecksTaclet;
import de.tu.bs.guido.key.simulator.options.taclets.WdOperatorTaclet;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;


public class MainClass {

	
	
	public static File sourcePath = new File("./../VerificationData/VerificationData_ThreeWiseSampling/ReduxProblemSolved");
	public static File reduxPath = new File("./../Resources/JavaRedux");
	public static KeyControl kc = new ExampleBasedKeyControl();

	
	public static void calc(File folder){
		
		//File folder = new File("./../../VerificationData/VerificationData_AutomatedVerification/Samplings");
		File[] listOfFiles = folder.listFiles();
		
		
		for (int i = 0; i < listOfFiles.length; i++) {
			  if (listOfFiles[i].isFile()) {
			    System.out.println("File " + listOfFiles[i].getName());
			    
			    Path path = Paths.get(listOfFiles[i].getAbsolutePath());
				Charset charset = StandardCharsets.UTF_8;

				String content;
				try {
					content = new String(Files.readAllBytes(path), charset);
					String contents = content.replaceAll("Block_2_treatment_1__1_Contract", "Block_2_treatment_1__1_External_2_Contract");
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
	
	
	public static void main(String[] args) throws ProblemLoaderException,
			NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, ProofInputException {
		
		try {
			sourcePath.mkdirs();
			sourcePath.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File folder = new File("./../VerificationData/VerificationData_ThreeWiseSampling/Samplings");

		//calc(folder);
		
		Optionable[] opts = new Optionable[]{
				MergePointStatementsOptions.MERGE,
				StopAtOptions.UNCLOSABLE,
				ProofSplittingOptions.FREE,
				LoopTreatmentOptions.NONE,
				BlockTreatmentOptions.EXTERNALCONTRACT,
				MethodTreatmentOptions.CONTRACT,
				DependencyContractsOptions.ON,
				QueryTreatmentOptions.ON,
				ExpandLocalQueriesOptions.OFF,
				ArithmeticTreatmentOptions.MODEL_SEARCH,
				QuantifierTreatmentOptions.FREE,
				ClassAxiomRulesOptions.DELAYED,
				AutoInductionOptions.OFF,
				OneStepSimplificationOptions.ENABLED,
				AssertionsTaclet.SAFE,
				InitialisationTaclet.DISABLE_STATIC_INITIALISATION,
				IntRulesTaclet.JAVA_SEMANTICS,
				ProgramRulesTaclet.JAVA,
				RuntimeExceptionsTaclet.BAN,
				JavaCardTaclet.OFF,
				StringsTaclet.ON,
				ModelFieldsTaclet.TREAT_AS_AXIOM,
				BigIntTaclet.ON,
				SequencesTaclet.OFF,
				ReachTaclet.OFF,
				IntegerSimplificationRulesTaclet.FULL,
				PermissionsTaclet.OFF,
				WdOperatorTaclet.L,
				WdChecksTaclet.OFF,
				MergeGenerateIsWeakeningGoalTaclet.OFF,
				MoreSeqRulesTaclet.ON
				};
		Optionable[] defaultOpts = new Optionable[]{};
		
		outPutProofResults(proof("ElevatorSystem.Elevator", "timeShift", new String[]{"EmailSystem.Client", "EmailSystem.Mail"}, 0, createDesiredSettingsObject(opts, 1000000)));
	
		
	}

	private static void outPutProofResults(List<Result> res) {
		res.forEach(result -> {
			System.out.println(result.getName());
			System.out.println(result.isClosed() ? result.getSteps()
					: "notClosed!");
			System.out.println("____________________________________________");
		});
	}
	
	public static SettingsObject createDesiredSettingsObject(Optionable[] opts, int maxSteps){
		SettingsObject so = new SettingsObject();
		for(Optionable opt: opts){
			so.setParameter(opt);
		}
		so.setMaxSteps(maxSteps);
		return so;
	}
	
	private static List<Result> proof(String classname, String methodname, String[] methodparameter, int contractNumber, SettingsObject so) throws ProofInputException, ProblemLoaderException{
		return kc.getResultForProof(sourcePath, reduxPath, classname,
				methodname, methodparameter, contractNumber, so);
	}
	
	
	private static List<Result> proofWithOwnSourceFile(File source, String classname, String methodname, String[] methodparameter, int contractNumber, SettingsObject so) throws ProofInputException, ProblemLoaderException{
		return kc.getResultForProof(source, reduxPath, classname,
				methodname, methodparameter, contractNumber, so);
	}
	
}
