package de.tu.bs.guido.network.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gson.Gson;
import de.tu.bs.guido.key.pooling.WorkingPool;
import de.tu.bs.guido.key.pooling.distributed.DistributedWorkingPool;
import de.tu.bs.guido.key.pooling.distributed.NewResultNotifier;
import de.tu.bs.guido.network.ProofRunnable;
import de.tu.bs.guido.network.file.server.FileServer;
import de.tu.bs.guido.verification.system.BatchXMLHelper;
import de.tu.bs.guido.verification.system.Job;
import de.tu.bs.guido.verification.system.SampleHelper;
import de.tu.bs.guido.verification.system.factory.AbstractFactory;
import de.tu.bs.guido.verification.system.factory.KeyFactory;
import de.tu.bs.guido.verification.systems.key.KeyJob;
import de.tu.bs.guido.verification.systems.key.options.SettingsObject;

public class Server implements Observer {

	public static final int PORT = 24508;
	public static Mode mode = Mode.Key;

	public enum Mode {
		Key, ModelChecker, Unknown
	};

	private static final int FILE_SERVER_PORT = PORT + 1;
	private static final File DONE_FILE = new File("done.txt");
	private static final File OPEN_FILE = new File("open.txt");
	private static final File PUNISHMENT_FILE = new File("punishments.txt");

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
		ArrayList<Job> jobs;
		int argsLength = args.length;
		if (argsLength == 4) { // NOT USABLE SINCE EXCPETION IS THROWN! (no reason is mentioned for it)
			String source = args[0];
			String clazz = args[1];
			String method = args[2];
			File samples = new File(args[3]);

			List<SettingsObject> sos = SampleHelper.readSPLSamples(samples);
			jobs = new ArrayList<>(sos.size());
			
			sos.forEach(so -> jobs.add(new KeyJob("", -1, source, null, clazz, method, null, so)));
			throw new IllegalArgumentException("not yet updated...");
		} else if (argsLength == 1) {
			if (!args[0].equals("key")) {
				mode = Mode.Key;
				AbstractFactory.setAbst(new KeyFactory());
			}
			System.out.println("Going to read jobs...");
			String testArgsInput = args[0]; // "./../../VerificationData/VerificationData_AutomatedVerification/exampleJob.xml";
			jobs = new BatchXMLHelper().generateJobFromXML(new File(testArgsInput));
			filterListForDuplicates(jobs);
//			filterListForMe(jobs);
			System.out.println("So many jobs read... " + jobs.size());
		} else if (argsLength == 2) {
			if (!args[1].equals("key")) {
				mode = Mode.Key;
				AbstractFactory.setAbst(new KeyFactory());
			}
			System.out.println("Going to read jobs...");
			String testArgsInput = args[0]; // "./../../VerificationData/VerificationData_AutomatedVerification/exampleJob.xml";
			jobs = AbstractFactory.getAbst().createBatchXMLHelper().generateJobFromXML(new File(testArgsInput));
			filterListForDuplicates(jobs);
//			filterListForMe(jobs);
			System.out.println("So many jobs read... " + jobs.size());
		} else {
			throw new IllegalArgumentException("Please pass for parameters: classpath, class, method and samples");
		}
		PunishmentTracker pt = new PunishmentTracker(PUNISHMENT_FILE);
		if (PUNISHMENT_FILE.exists()) {
			pt.updatePunishments(jobs);
			if (DONE_FILE.exists()) {
				List<Job> doneJobs = readDoneJobs(DONE_FILE);
				for (Job job : doneJobs) {
					job.reinitialize();
				}
				jobs.removeAll(doneJobs);
				System.out.println("Already done some jobs... " + jobs.size() + " left");
			}
			System.out.println("Sorting jobs by step size...");
			Collections.shuffle(jobs);
			/*
			 * Collections.sort(jobs, new CodeComparator(KeyStrategyOptions.STOP_AT,
			 * KeyStrategyOptions.ONE_STEP_SIMPLIFICATION,
			 * KeyStrategyOptions.PROOF_SPLITTING, KeyStrategyOptions.LOOP_TREATMENT));
			 */
			Collections.sort(jobs, new StepSizeComparator());
			System.out.println("Going to write open file");
			OPEN_FILE.delete();
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(OPEN_FILE))) {
				Gson gson = new Gson();
				for (Job j : jobs) {
					bw.write(gson.toJson(j));
					bw.newLine();
				}
			}
			System.out.println("Open file writen");
			Set<String> whiteLists = new HashSet<>();
			for (Job j : jobs) {
				whiteLists.add(j.getSource());
				whiteLists.add(j.getClasspath());
			}
			File temp = new File(new File("Temp"), "Server");
			temp.mkdirs();
			new FileServer(FILE_SERVER_PORT, temp, whiteLists);
			new Server().start(jobs);
			System.out.println("Done entering jobs... waiting for clients");
		}
	}

	private static void filterListForDuplicates(List<Job> jobs) {
		for (int i = 0; i < jobs.size(); i++) {
			Job a = jobs.get(i);
			for (int j = i + 1; j < jobs.size(); j++) {
				Job b = jobs.get(j);
				if (a.equals(b)) {
					jobs.remove(j);
					j--;
					a.setCode(a.getCode() + ", " + b.getCode());
					a.getExperiments().putAll(b.getExperiments());
				}
			}
		}
		if (jobs instanceof ArrayList) {
			((ArrayList<?>) jobs).trimToSize();
		}
	}
	@Deprecated
	private static void filterListForMe(List<Job> joblist) {
		for (Iterator<Job> iterator = joblist.iterator(); iterator.hasNext();) {
			Job job = iterator.next();
			if (job.getCode().equals("Basic"))
				iterator.remove();
			else if (!job.getMethod().equals("max"))
				iterator.remove();
			else if (job.getContractNumber() != 0)
				iterator.remove();
		}
	}

	private static List<Job> readDoneJobs(File f) throws FileNotFoundException, IOException {
		List<Job> doneJobs = new ArrayList<>();
	
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			Gson gson = new Gson();
			while ((line = br.readLine()) != null) {
				Job j = gson.fromJson(line, Job.class);
				doneJobs.add(j);
			}
		}
		return doneJobs;
	}

	private final ResultObserver zo;
	private final NewResultNotifier nrn;
	private WorkingPool pool;

	// private ConsoleThread console;

	public Server() throws IOException {
		nrn = new NewResultNotifier();
		pool = new DistributedWorkingPool(PORT, nrn);
		zo = new ResultObserver(new File("zwischenergebnisse.txt"), DONE_FILE, pool, FILE_SERVER_PORT,
				new PunishmentTracker(PUNISHMENT_FILE));
		nrn.addObserver(zo);
	}

	public void start(List<Job> jobs) throws IOException {
		for (Job job : jobs) {
			pool.addJob(new ProofRunnable(job, FILE_SERVER_PORT));
		}
		pool.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		pool.stopWorking();
		try {
			zo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
