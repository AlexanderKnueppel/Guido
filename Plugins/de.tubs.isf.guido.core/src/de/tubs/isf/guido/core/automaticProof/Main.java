package de.tubs.isf.guido.core.automaticProof;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.tubs.isf.guido.core.ui.GuidanceGUI;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;


public class Main {

	private static Scanner scanner;
	public static boolean SPL = false;
	public static int RUN = 5;
	public static boolean RQ4 = true;
	public static boolean Random = true;
	
	public static int Mechanism = 1; //0: penalty, 1: adjust, 2: next
	
	public static void run() {
		File forGeneratingRules = null;
		File hypotheses = null;
		File rules = null;
		Prover prover = null;
		long time = 1*60*1000;
		//todo generate directories
		forGeneratingRules = new File("test/results.txt"); //data base
		hypotheses = new File("test/hypotheses.txt"); //accepted hypotheses
		rules = new File("test/rules.txt"); //current score
		//time = 3*60*1000; //1 minute
		
		prover = new Prover(forGeneratingRules, hypotheses, rules, time);
		
		File toProve = new File("test/splconqueror2.xml");
		
		if(SPL) {
			System.out.println("Proof with SPLConqueror!");
		}else {
			System.out.println("Proof with Guido! Generate new score first...");
			prover.generateScore();			
		}

//
    	//ProverThread.startProvingFileThread(prover, "F", toProve);
		try {
			//for(RUN = 1; RUN <= 10; ++RUN) {
				String writeTo = (SPL)? "./test/resultsOfSPLConqueror.txt" : "./test/resultsOfGuido.txt";
				//writeTo = "./test/resultsDefault.txt";
			
				writeTo = "./test/resultsGuido_y1_"+RUN+"_y2_"+(10-RUN)+"_next_hypo3.txt";
				
				if(RQ4) {
					if(Random)
						writeTo ="test/randomEffortRQ4.txt";
					else
						writeTo = "test/guidoEffortRQ4.txt";
				}
				
				System.out.println("Write to : " + writeTo);
				File f = new File(writeTo);
				f.getParentFile().mkdirs();
				
				if (!f.exists()) {
				    f.createNewFile();
				} 
				System.out.println(f.getAbsolutePath());
				System.out.println(toProve.getAbsolutePath());
				FileWriter fileWriter = new FileWriter(f,true);
				
								
				
				PrintWriter printWriter = new PrintWriter(fileWriter);
				printWriter.append("Name," + ("closed?") + "," + "Steps" + "," + "Time" + "\n");
				List<GuidanceSystemResult> results = prover.proofAllContractInFile(toProve,printWriter);
				//to excel file?
				
	//			for(GuidanceSystemResult r : results) {
	//				printWriter.append(r.getName() + "," + (r.isClosed()? "1" : "0") + "," + r.getSteps() + "," + r.getTimeInMillis() + "\n");
	//			}
				printWriter.close();
			//}
		} catch (ProofInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProblemLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
//		for(int i = 0; i <= 1; i++) {
			run();
//			SPL = false;
//		}
		

//		scanner = new Scanner(System.in);
//		boolean performing = true;
//		boolean configuration = true;
//		File forGeneratingRules = null;
//		File hypotheses = null;
//		File rules = null;
//		Prover prover = null;
//		long time = 1*60*1000;
//		
//		try {
//            while (performing) {
//            	
//            	if(configuration){
//                	System.out.println("Configure Prover!");
//        			System.out.println("Insert 'D' if default file should be used.");
//        			
//        			forGeneratingRules = inputFile(scanner, "results file", "./test/results.txt");
//        			hypotheses = inputFile(scanner, "hypotheses file", "./test/hypotheses.txt");
//        			rules = inputFile(scanner, "rules file", "./test/rules.txt");
//        			time = Long.parseLong(input(scanner, "time in s")) * 1000;
//        			if(time < 1000){
//        				time = 1*60*1000;
//        			}
//        			prover = new Prover(forGeneratingRules, hypotheses, rules, time);
//
//                	configuration = false;
//            	}
//
//                System.out.println("Insert 'G' for Starting the GUI, 'F' for verifying all methods in a file or 'M' for veryfing one method");
//                System.out.println("To close this, insert 'Q'");
//                String line = scanner.nextLine();
//                
//                if(line.startsWith("G")){
//                	GuidanceGUI guidanceGUI = new GuidanceGUI(prover);
//                	guidanceGUI.show();
//                }
//                else if(line.startsWith("F")){
//                	System.out.println("Configure what to prove!");
//        			File toProve = inputFile(scanner, "file to prove", "");
//
//                	//ProverThread.startProvingFileThread(prover, "F", toProve);
//					prover.proofAllContractInFile(toProve);
//
//                }
//                else if(line.startsWith("M")){
//        		
//                	System.out.println("Configure what to prove!");
//
//        			//Example: File sourcePath = new File("./../studySetup/VerificationData/VerificationData_ThreeWiseSampling/ReduxProblemSolved/BankAccount");
//                	File sourcePath = new File(input(scanner, "sourcePath"));
//                	String redux = input(scanner, "reduxPath - type Q to avoid it");
//                	File reduxPath = redux.equals("Q") ? null: new File(redux);
//                	String classWithPackage = input(scanner, "class with package");
//                	String method = input(scanner, "method");
//                	String parameters = input(scanner, "parameter - type Q to not specify it");
//                	String[] parameter = parameters.equals("Q") ? null : (parameters.isEmpty() ? null : parameters.split(","));
//                	int contractNumber = Integer.parseInt(input(scanner, "contractNumber"));
//                	
//                	//ProverThread.startProvingThread("M", prover, sourcePath, reduxPath, classWithPackage, method, parameter, contractNumber);
//                	prover.proving(sourcePath, reduxPath, classWithPackage, method, parameter, contractNumber);
//        			//Example: prover.proving(sourcePath, reduxPath, "BankAccount.Account", "update", new String[]{"int"}, 0);
//
//                }
//                else if(line.startsWith("Q")){
//                    performing = false;
//                }
//                else {
//                	System.out.println("Wrong input!");
//                }
//            }
//        } catch(IllegalStateException | NoSuchElementException e) {
//            // System.in has been closed
//            System.out.println("System.in was closed; exiting");
//        } catch (ProofInputException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ProblemLoaderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static File inputFile(Scanner scanner, String text, String defaultFile){
		File f = new File("");
		while(!f.isFile()){
			System.out.print(text + ": ");
	        String line = scanner.nextLine();
	        String input = line.replace("\n", "").replace("\r", "");
	        if(input.equals("D")){
	        	f = new File(defaultFile);
	        }
	        else {
		        f = new File(input);
	        }
		}
		return f;
	}
	
	public static String input(Scanner scanner, String text){
		System.out.print(text + ": ");
	    String line = scanner.nextLine();
	    String input = line.replace("\n", "").replace("\r", "");
	    
		return input;
	}


}
