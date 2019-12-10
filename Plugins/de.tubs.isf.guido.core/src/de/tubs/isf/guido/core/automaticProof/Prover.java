package de.tubs.isf.guido.core.automaticProof;

import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.key_project.util.collection.ImmutableArray;
import org.key_project.util.collection.ImmutableSet;
import org.xml.sax.SAXException;

import com.github.javaparser.ast.comments.Comment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.tubs.isf.guido.core.analyzer.Analyzer;
import de.tubs.isf.guido.core.analyzer.Parameter;
import de.tubs.isf.guido.core.analyzer.Result;
import de.tubs.isf.guido.core.analyzer.contract.ContractAnalyzer;
import de.tubs.isf.guido.core.analyzer.contract.SourceCodeAnalyzer;
import de.tubs.isf.guido.core.automaticProof.proofFile.Job;
import de.tubs.isf.guido.core.automaticProof.proofFile.XMLReader;
import de.tubs.isf.guido.core.keYHandler.ProofControl;
import de.tubs.isf.guido.core.keYHandler.ProofControlGUI;
import de.tubs.isf.guido.core.verifier.ASystemFactory;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.uka.ilkd.key.control.KeYEnvironment;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.logic.op.IObserverFunction;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.proof.mgt.SpecificationRepository;
import de.uka.ilkd.key.speclang.Contract;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LPWizard;
import scpsolver.problems.LPWizardConstraint;

public class Prover {

	private File previousResultsFile;
	private File hypothesesFile;
	private File rulesFile;
	private long time;

	private Ruler ruler;

	public Prover() { // used for client-server-system
		File forGeneratingRules = new File("./results.txt");
		File hypotheses = new File("./hypotheses.txt");
		File rules = new File("./rules.txt");
		this.previousResultsFile = forGeneratingRules;
		this.hypothesesFile = hypotheses;
		this.rulesFile = rules;
		this.time = 3 * 60 * 1000;
		ruler = new Ruler();
	}

	public Prover(File previousResults, File hypotheses, File rules, long time) {
		this.previousResultsFile = previousResults;
		this.hypothesesFile = hypotheses;
		this.rulesFile = rules;
		this.time = time;

		ruler = new Ruler();
	}

	public List<GuidanceSystemResult> proofAllContractInFile(File provingContractsFile, PrintWriter pw)
			throws ProofInputException, SAXException, IOException, ParserConfigurationException,
			ProblemLoaderException {
		System.out.println("Proof all contracts mentioned in " + provingContractsFile.toString());
		List<Job> jobs = new XMLReader().generateJobFromXML(provingContractsFile);
		List<GuidanceSystemResult> results = new ArrayList<GuidanceSystemResult>();
		for (Job job : jobs) {
			System.out.println("----------------------------------------------");
			System.out.println("Prove: " + job.getClazz() + "; " + job.getMethod() + "; " + job.getParameter() + "; "
					+ job.getContractNumber());
			GuidanceSystemResult r = null;
			if(Main.RQ4)
				r = getResultForProofRQ4(new File(job.getSource()), new File(job.getClasspath()),
						job.getClazz(), job.getMethod(), job.getParameter(), job.getContractNumber());
			else {
				r = getResultForProof(new File(job.getSource()), new File(job.getClasspath()),
					job.getClazz(), job.getMethod(), job.getParameter(), job.getContractNumber());
			}
			//GuidanceSystemResult r = getResultForProofWithoutTimeout(new File(job.getSource()), new File(job.getClasspath()),
					//job.getClazz(), job.getMethod(), job.getParameter(), job.getContractNumber());
			//GuidanceSystemResult r = getResultForProofDefault(new File(job.getSource()), new File(job.getClasspath()), job.getClazz(),
					//job.getMethod(), job.getParameter(), job.getContractNumber());
			results.add(r);
			if (pw != null) {
				pw.append(r.getName() + "," + (r.isClosed() ? "1" : "0") + "," + r.getSteps() + ","
						+ r.getTimeInMillis() + "\n");
				pw.flush();
			}
			
		}

		return results;
	}

	public List<GuidanceSystemResult> proofAllContractInFile(File provingContractsFile) throws ProofInputException,
			SAXException, IOException, ParserConfigurationException, ProblemLoaderException {
		return proofAllContractInFile(provingContractsFile, null);
	}

	public GuidanceSystemResult getResultForProof(File source, File classPath, String className, String methodName)
			throws ProblemLoaderException, ProofInputException {
		return getResultForProof(source, classPath, className, methodName, null, -1);
	}

	public GuidanceSystemResult getResultForProofDefault(File sourcePath, File reduxPath, String provingClass,
			String provingMethod, String[] parameter, int contract) {

		// Proof!
		System.out.println("Set proof environment");
		ProofControl pc = new ProofControl(sourcePath, reduxPath, provingClass, provingMethod, parameter, contract);
		long startTime = System.currentTimeMillis();
		SettingsObject so = ASystemFactory.getAbst().createSettingsObject();
		System.out.println("Proof is not closed yet - Create SettingsObject - Default Settings");
		so = ASystemFactory.getAbst().createSettingsObject();
		so.setMaxSteps(1000000);
		System.out.println("Start Proof!");
		pc.performProof(so);
		long elapsedTime = (new Date()).getTime() - startTime;
		int steps = pc.getCurrentResults().get(0).getSteps();
		Result r = pc.getCurrentResults().get(0);
		return new GuidanceSystemResult(r.getName(), r.isClosed(), r.getSteps(), r.getTimeInMillis(), 1, elapsedTime,
				steps, so.getSettingsMap(), so.getTacletMap());

	}

	public GuidanceSystemResult getResultForProofWithoutTimeout(File sourcePath, File reduxPath, String provingClass,
			String provingMethod, String[] parameter, int contract) {

		// generatingRules();
		SourceCodeAnalyzer sourceCodeAnalyzer = analyzeCode(sourcePath, provingClass, provingMethod, parameter,
				contract);
		ContractAnalyzer contractAnalyzer = analyzeContract(sourceCodeAnalyzer.getContract());

		// Proof!
		System.out.println("Set proof environment");
		ProofControl pc = new ProofControl(sourcePath, reduxPath, provingClass, provingMethod, parameter, contract);
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		int proofPerformed = 0;
		int steps = 0;
		SettingsObject so = ASystemFactory.getAbst().createSettingsObject();
		so.setMaxSteps(1000000);
		System.out.println("Proof is not closed yet - Create SettingsObject");
		//so = createDesiredSettingsObjectGuido(proofPerformed, sourceCodeAnalyzer, contractAnalyzer, so);
		
		
//		so = new SettingsObject();
//		so.setMaxSteps(1000000);
		
		
		System.out.println("Start Proof!");
		ProofControl pcCurrent = ProverThread.startProof(pc, so, this.time);
		proofPerformed++;
		elapsedTime = (new Date()).getTime() - startTime;
		if (pcCurrent != null) {
			pc = pcCurrent;
		}
		if (pc.getCurrentResults() != null && pc.getCurrentResults().size() > 0) {
			steps = steps + pc.getCurrentResults().get(0).getSteps();
		}

		penaltyMap.clear();
		objectiveList.clear();

		if (pc.getCurrentResults() != null && pc.getCurrentResults().size() > 0) {
			Result r = pc.getCurrentResults().get(0);
			return new GuidanceSystemResult(r.getName(), r.isClosed(), r.getSteps(), r.getTimeInMillis(),
					proofPerformed, elapsedTime, steps, so.getSettingsMap(), so.getTacletMap());
		} else {
			return new GuidanceSystemResult(provingClass + ":" + provingMethod + ":" + contract, false, 0, time,
					proofPerformed, elapsedTime, steps, so.getSettingsMap(), so.getTacletMap());
		}

	}
	
	public GuidanceSystemResult getResultForProofRQ4(File sourcePath, File reduxPath, String provingClass,
			String provingMethod, String[] parameter, int contract) {

		// generatingRules();
		SourceCodeAnalyzer sourceCodeAnalyzer = analyzeCode(sourcePath, provingClass, provingMethod, parameter,
				contract);
		ContractAnalyzer contractAnalyzer = analyzeContract(sourceCodeAnalyzer.getContract());

		// Proof!
		System.out.println("Set proof environment");
		ProofControl pc = new ProofControl(sourcePath, reduxPath, provingClass, provingMethod, parameter, contract);
		Result bestresult = null;
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		int proofPerformed = 0;
		int steps = 0;
		SettingsObject so = ASystemFactory.getAbst().createSettingsObject();
		so.setMaxSteps(1000000);
		while (elapsedTime < this.time) { // 1*60*1000
			if(pc.isClosed()) {
				if(pc.getCurrentResults() != null && pc.getCurrentResults().size() > 0) {
					if(bestresult != null && pc.getCurrentResults().get(0).getSteps() < bestresult.getSteps()) {
						bestresult = pc.getCurrentResults().get(0);
						System.out.println("Effort improved!");
					} else if(bestresult == null) {
						bestresult = pc.getCurrentResults().get(0);
					}
				}
			}
				
			System.out.println("Start Proof!");
			ProofControl pcCurrent = ProverThread.startProof(pc, so, this.time);
			proofPerformed++;
			elapsedTime = (new Date()).getTime() - startTime;
			if (pcCurrent != null) {
				pc = pcCurrent;
			}
			if (pc.getCurrentResults() != null && pc.getCurrentResults().size() > 0) {
				steps = steps + pc.getCurrentResults().get(0).getSteps();
			}
			
			System.out.println("Proof is not closed yet - Create SettingsObject");
			if(Main.Random) {
				
			} else
				so = createDesiredSettingsObjectGuido(proofPerformed, sourceCodeAnalyzer, contractAnalyzer, so);
		}

		penaltyMap.clear();
		objectiveList.clear();

		if (bestresult != null) {
			Result r = bestresult;
			return new GuidanceSystemResult(r.getName(), r.isClosed(), r.getSteps(), r.getTimeInMillis(),
					proofPerformed, elapsedTime, steps, so.getSettingsMap(), so.getTacletMap());
		} else {
			return new GuidanceSystemResult(provingClass + ":" + provingMethod + ":" + contract, false, 0, time,
					proofPerformed, elapsedTime, steps, so.getSettingsMap(), so.getTacletMap());
		}

	}

	public GuidanceSystemResult getResultForProof(File sourcePath, File reduxPath, String provingClass,
			String provingMethod, String[] parameter, int contract) {

		// generatingRules();
		SourceCodeAnalyzer sourceCodeAnalyzer = analyzeCode(sourcePath, provingClass, provingMethod, parameter,
				contract);
		ContractAnalyzer contractAnalyzer = analyzeContract(sourceCodeAnalyzer.getContract());

		// Proof!
		System.out.println("Set proof environment");
		ProofControl pc = new ProofControl(sourcePath, reduxPath, provingClass, provingMethod, parameter, contract);
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		int proofPerformed = 0;
		int steps = 0;
		SettingsObject so = ASystemFactory.getAbst().createSettingsObject();
		so.setMaxSteps(1000000);
		while (!pc.isClosed() && elapsedTime < this.time) { // 1*60*1000
			System.out.println("Proof is not closed yet - Create SettingsObject");
			so = createDesiredSettingsObjectGuido(proofPerformed, sourceCodeAnalyzer, contractAnalyzer, so);
			System.out.println("Start Proof!");
			ProofControl pcCurrent = ProverThread.startProof(pc, so, this.time);
			proofPerformed++;
			elapsedTime = (new Date()).getTime() - startTime;
			if (pcCurrent != null) {
				pc = pcCurrent;
			}
			if (pc.getCurrentResults() != null && pc.getCurrentResults().size() > 0) {
				steps = steps + pc.getCurrentResults().get(0).getSteps();
			}
			// so = createDesiredSettingsObject(proofPerformed, sourceCodeAnalyzer,
			// contractAnalyzer, so);
		}

		penaltyMap.clear();
		objectiveList.clear();

		if (pc.getCurrentResults() != null && pc.getCurrentResults().size() > 0) {
			Result r = pc.getCurrentResults().get(0);
			return new GuidanceSystemResult(r.getName(), r.isClosed(), r.getSteps(), r.getTimeInMillis(),
					proofPerformed, elapsedTime, steps, so.getSettingsMap(), so.getTacletMap());
		} else {
			return new GuidanceSystemResult(provingClass + ":" + provingMethod + ":" + contract, false, 0, time,
					proofPerformed, elapsedTime, steps, so.getSettingsMap(), so.getTacletMap());
		}

	}

	public void proving(File sourcePath, File reduxPath, String provingClass, String provingMethod, String[] parameter,
			int contract) {

		// Proof!
		GuidanceSystemResult res = getResultForProof(sourcePath, reduxPath, provingClass, provingMethod, parameter,
				contract);
		if (res.isClosed()) {
			System.out.println("Proof Closed - need to be performed " + res.getExecution() + " times");
		} else {
			ProofControlGUI pcGUI = new ProofControlGUI(sourcePath, reduxPath, provingClass, provingMethod, parameter,
					contract, true);
			try {
				pcGUI.performProof(createDesiredSettingsObject(res));
			} catch (ProofInputException e) {
				e.printStackTrace();
			}
		}

	}

	public SourceCodeAnalyzer analyzeCode(File sourcePath, String provingClass, String provingMethod,
			String[] parameter, int contract) {
		System.out.println("Analyze source code");
		SourceCodeAnalyzer sourceCodeAnalyzer = new SourceCodeAnalyzer(sourcePath, provingClass, provingMethod,
				parameter);
		sourceCodeAnalyzer.analyzeSourceCode();

		return sourceCodeAnalyzer;
	}

	public ContractAnalyzer analyzeContract(Comment contract) {
		System.out.println("Analyze contract");
		ContractAnalyzer contractAnalyzer = new ContractAnalyzer(contract);
		contractAnalyzer.analyzeContract();

		return contractAnalyzer;
	}

	public void generatingRules() {
		System.out.println("Check if rules need to be generated");

		if (needOfGeneratingNewRules()) {
			System.out.println("New rules need to be generated");
			generateNewRules();
		} else {
			System.out.println("No new rules need to be generated");
		}
	}

	public SettingsObject createDesiredSettingsObject(GuidanceSystemResult res) {
		SettingsObject so = ASystemFactory.getAbst().createSettingsObject();
		for (Entry<String, String> tac : res.getTaclets().entrySet()) {
			so.setParameter(tac.getKey(), tac.getValue());
		}
		return so;
	}

	public SettingsObject createSPLConquerorSettingsObject() {
		SettingsObject so = ASystemFactory.getAbst().createSettingsObject();
		return so;
	}

	public SettingsObject createDesiredSettingsObject(int proofPerformed, SourceCodeAnalyzer sourceCode,
			ContractAnalyzer contract, SettingsObject soOld) {
		SettingsObject so = ASystemFactory.getAbst().createSettingsObject();
		// TODO create here the strategy....
		for (Rule rule : ruler.getRules()) {
			so.setParameter(rule.getParameter(), rule.getBestOption(proofPerformed, sourceCode, contract));
		}
		so.setMaxSteps(soOld.getMaxSteps());
		if (so == soOld) { // it does not make sense to prove a proof attempt twice with the same settings!
			System.out.println("SettingsObjects are the same - create new one!");
			so = createDesiredSettingsObject(proofPerformed + 1, sourceCode, contract, so);
		}
		// so.setMaxSteps(10000 *(proofPerformed + 1));
		// so.setMaxSteps(10000);
		so.setMaxSteps(1000000); // Evaluation said this is the best!
		return so;
	}

	private Map<String, Double> penaltyMap = new HashMap<String, Double>();
	private ArrayList<Double> objectiveList = new ArrayList<Double>();

	public Map<String, String> solveWithLP(double gamma1, double gamma2, double k, boolean next) {
		Map<String, Double> costsP = new HashMap<String, Double>();// {{
		Map<String, Double> costsVE = new HashMap<String, Double>();

		String toRead = Main.SPL ? "./test/rulesSPLC.txt" : "./test/rules.txt";
		File ruleFile = new File(toRead);
		ruleFile.getParentFile().mkdirs();
		try {
			ruleFile.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		Ruler ruler = Prover.readRules2(new File(toRead));
		ruler.getRules().stream().forEach(x -> {
			costsP.putAll(x.getOptions("P").entrySet().stream()
					.collect(Collectors.toMap(e -> x.getParameter() + "___" + e.getKey(), e -> e.getValue())));
			costsVE.putAll(x.getOptions("VE").entrySet().stream()
					.collect(Collectors.toMap(e -> x.getParameter() + "___" + e.getKey(), e -> e.getValue())));
		});

		if (penaltyMap.isEmpty()) {
			ruler.getRules().stream().forEach(x -> {
				penaltyMap.putAll(x.getOptions("P").entrySet().stream()
						.collect(Collectors.toMap(e -> x.getParameter() + "___" + e.getKey(), e -> 0.0)));
			});
		}

		LPWizard lpw = new LPWizard();

		List<String> options = new ArrayList<String>();

		costsP.entrySet().stream().forEach(x -> {
			Random r = new Random();
			double score = r.nextDouble()*2.0 -1.0;
			
			lpw.plus(x.getKey(), 
					 Main.Random && Main.RQ4 ? score :  x.getValue() * gamma1 + penaltyMap.get(x.getKey()));
			options.add(x.getKey());
		});
		costsVE.entrySet().stream().forEach(x -> {
			Random r = new Random();
			double score = r.nextDouble()*2.0 -1.0;
			
			lpw.plus(x.getKey(), Main.Random && Main.RQ4 ? score :  x.getValue() * gamma2 + penaltyMap.get(x.getKey()));
			options.add(x.getKey());
		});
		ruler.getRules().stream().forEach(x -> {
			LPWizardConstraint c1 = lpw.addConstraint("c" + lpw.getLP().getConstraints().size(), 1, "<=");
			LPWizardConstraint c2 = lpw.addConstraint("c" + lpw.getLP().getConstraints().size(), 1, ">=");
			LPWizardConstraint c3 = lpw.addConstraint("c" + lpw.getLP().getConstraints().size(), 1, "<=");
			LPWizardConstraint c4 = lpw.addConstraint("c" + lpw.getLP().getConstraints().size(), 1, ">=");
			x.getOptions("P").entrySet().stream().forEach(y -> {
				c1.plus(x.getParameter() + "___" + y.getKey());
				c2.plus(x.getParameter() + "___" + y.getKey());
			});
			x.getOptions("VE").entrySet().stream().forEach(y -> {
				c3.plus(x.getParameter() + "___" + y.getKey());
				c4.plus(x.getParameter() + "___" + y.getKey());
			});
			c1.setAllVariablesBoolean();
			c2.setAllVariablesBoolean();
			c3.setAllVariablesBoolean();
			c4.setAllVariablesBoolean();
		});

		if (next && objectiveList.size() > 0) {
			double epsilon = 0.00001;
			LPWizardConstraint nextConstraint = lpw.addConstraint("next",
					objectiveList.get(objectiveList.size() - 1) - epsilon, "<=");
			costsP.entrySet().stream().forEach(x -> {
				nextConstraint.plus(x.getKey(), x.getValue() * gamma1 + penaltyMap.get(x.getKey()));
			});
			costsVE.entrySet().stream().forEach(x -> {
				nextConstraint.plus(x.getKey(), x.getValue() * gamma2 + penaltyMap.get(x.getKey()));
			});

			System.out.println(nextConstraint);
		}

		lpw.setMinProblem(false);
		LinearProgramSolver solver = SolverFactory.newDefault();
		double[] sol = solver.solve(lpw.getLP());

		Map<String, String> result = new HashMap<String, String>();
		double objective = 0.0;

		for (int i = 0; i < sol.length; ++i) {
			if (sol[i] > 0) {
				result.put(options.get(i).split("___")[0], options.get(i).split("___")[1]);
				penaltyMap.put(options.get(i), k);
				// System.out.println(options.get(i));
				objective += lpw.getLP().getC()[i];
			}
		}

		if (next) {
			System.out.println("Objective: " + objective);
			objectiveList.add(objective);
		}

		return result;
	}

//	public SettingsObject createDesiredSettingsObjectGuido(int proofPerformed, SourceCodeAnalyzer sourceCode, ContractAnalyzer contract, SettingsObject soOld) {
//		SettingsObject so = new SettingsObject();
//		//TODO create here the strategy....
////		for (Rule rule : ruler.getRules()) {
////			so.setParameter(rule.getParameter(), rule.getBestOption(proofPerformed, sourceCode, contract));
////		
////		
////		}
//		double gamma1 = 0.5 + 0.1*proofPerformed, gamma2 = 0.5 - 0.1*proofPerformed;
//		if(gamma1 > 1.0) {
//			gamma1 = 1.0; gamma2 = 0.5;
//		}
//		
//		for(Entry<String, String> e : solveWithLP(gamma1, gamma2, 0.0).entrySet()) {
//			so.setParameter(e.getKey(), e.getValue());
//		}
//		so.setMaxSteps(soOld.getMaxSteps());
//		if(so == soOld){ //it does not make sense to prove a proof attempt twice with the same settings!
//			System.out.println("SettingsObjects are the same - create new one!");
//			so = createDesiredSettingsObjectGuido(proofPerformed+1, sourceCode, contract, so);
//		}
//		//so.setMaxSteps(10000 *(proofPerformed + 1));
//		//so.setMaxSteps(10000);
//		so.setMaxSteps(1000000); //Evaluation said this is the best!
//		return so;
	// }

	public SettingsObject createDesiredSettingsObjectGuido(int proofPerformed, SourceCodeAnalyzer sourceCode,
			ContractAnalyzer contract, SettingsObject soOld) {
		SettingsObject so = ASystemFactory.getAbst().createSettingsObject();

		double gamma1, gamma2, k;
		boolean next;
		
		if(Main.Mechanism == 1) {
			// Adjust
			gamma1 = (Main.RUN / 10.0) + 0.1*proofPerformed;
			gamma2 = 1.0 - (Main.RUN / 10.0) - 0.1*proofPerformed;
			if(gamma1 > 1.0) {
				gamma1 = 1.0; gamma2 = 0.0;
			}
			k = 0.0;
			next = false;
		} else if(Main.Mechanism == 0) {
			//Penalty
			gamma1 = (Main.RUN / 10.0); 
			gamma2 = 1.0 - (Main.RUN / 10.0);
			k = -0.005 * (proofPerformed - 1);
			next = false;
		} else {
			//next
			gamma1 = (Main.RUN / 10.0); 
			gamma2 = 1.0 - (Main.RUN / 10.0);
			k = 0.0;
			next = true;
		}


		// Penalty
		System.out.println("gammas: " + gamma1 + ", " + gamma2 + ", k = " + k);
		// Next

		for (Entry<String, String> e : solveWithLP(gamma1, gamma2, k, next).entrySet()) {
			so.setParameter(e.getKey(), e.getValue());
		}
		so.setMaxSteps(soOld.getMaxSteps());
		if (so == soOld) { // it does not make sense to prove a proof attempt twice with the same settings!
			System.out.println("SettingsObjects are the same - create new one!");
			so = createDesiredSettingsObjectGuido(proofPerformed + 1, sourceCode, contract, so);
		}
		// so.setMaxSteps(10000 *(proofPerformed + 1));
		// so.setMaxSteps(10000);
		so.setMaxSteps(1000000); // Evaluation said this is the best!
		return so;
	}

	private void generateNewRules() {
		Analyzer analyzer = new Analyzer(previousResultsFile, hypothesesFile);
		ruler.setHypothesesFileModified(hypothesesFile.lastModified());
		ruler.setPreviousResultsFileModified(previousResultsFile.lastModified());
		analyzer.analyze();

		System.out.println("Write new generated rules to ruler");

		ruler.deleteOldRules();
		Map<String, Parameter> parameter = analyzer.getParameter();
		for (Parameter param : parameter.values()) {
			ruler.setRule(param);
		}

		System.out.println("Write rules to file");
		ruler.writeToFile(rulesFile);
	}

	public void generateScore() {
		Analyzer analyzer = new Analyzer(previousResultsFile, hypothesesFile);
		ruler.setHypothesesFileModified(hypothesesFile.lastModified());
		ruler.setPreviousResultsFileModified(previousResultsFile.lastModified());
		analyzer.analyzeForGuido();

		System.out.println("Write new generated rules to ruler");

		ruler.deleteOldRules();
		Map<String, Parameter> parameter = analyzer.getParameter();
		for (Parameter param : parameter.values()) {
			ruler.setRule(param);
		}

		System.out.println("Write rules to file");
		ruler.writeToFile(rulesFile);
	}

	private boolean needOfGeneratingNewRules() {
		if (!rulesFile.exists()) { // no rules file -> rules need to be
									// generated
			return true;
		} else {
			readRules();
			if ((hypothesesFile.exists() && ruler.getHypothesesFileModified() != hypothesesFile.lastModified())
					|| (previousResultsFile.exists()
							&& ruler.getPreviousResultsFileModified() != previousResultsFile.lastModified())) {
				return true; // files are modified -> generate new rules!
			}
			return false; // no rules need to be generated
		}
	}

	public static Ruler readRules2(File rulesFile) {
		Ruler ruler = new Ruler();
		try (BufferedReader br = new BufferedReader(new FileReader(rulesFile))) {
			ArrayList<Rule> rules = new ArrayList<Rule>();
			String line;
			Gson gson = new GsonBuilder().create();
			if ((line = br.readLine()) != null) {
				ruler = (gson.fromJson(line, Ruler.class));
			}
			while ((line = br.readLine()) != null) {
				rules.add(gson.fromJson(line, Rule.class));
			}
			ruler.setRules(rules);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ruler;
	}

	private void readRules() {

		try (BufferedReader br = new BufferedReader(new FileReader(rulesFile))) {
			ArrayList<Rule> rules = new ArrayList<Rule>();
			String line;
			Gson gson = new GsonBuilder().create();
			if ((line = br.readLine()) != null) {
				ruler = (gson.fromJson(line, Ruler.class));
			}
			while ((line = br.readLine()) != null) {
				rules.add(gson.fromJson(line, Rule.class));
			}
			ruler.setRules(rules);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNumberOfContracts(File source, File classPath, String className, String methodName,
			String[] parameters) throws ProblemLoaderException, ProofInputException {
		KeYEnvironment<?> env = KeYEnvironment.load(source, null, classPath, null);
		try {
			KeYJavaType type = env.getJavaInfo().getKeYJavaType(className);
			List<Contract> proofContracts = getCorrectContract(methodName, parameters, env.getSpecificationRepository(),
					type);
			return proofContracts.size();
		} finally {
			env.dispose();
		}
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
}
