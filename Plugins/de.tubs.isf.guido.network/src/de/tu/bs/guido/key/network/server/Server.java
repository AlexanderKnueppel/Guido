package de.tu.bs.guido.key.network.server;

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
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.tu.bs.guido.key.network.Job;
import de.tu.bs.guido.key.network.KeyProofRunnable;
import de.tu.bs.guido.key.network.file.server.FileServer;
import de.tu.bs.guido.key.pooling.WorkingPool;
import de.tu.bs.guido.key.pooling.distributed.DistributedWorkingPool;
import de.tu.bs.guido.key.pooling.distributed.NewResultNotifier;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import keYHandler.options.SettingsObject;

public class Server implements Observer {

	public static final int PORT = 24508;
	private static final int FILE_SERVER_PORT = PORT + 1;
	private static final File DONE_FILE = new File("done.txt");
	private static final File OPEN_FILE = new File("open.txt");

	public static void main(String[] args) throws IOException, SAXException,
			ParserConfigurationException, ProofInputException,
			ProblemLoaderException {
		List<Job> jobs;
		int argsLength = args.length;
		if (argsLength == 1) {
			System.out.println("Going to read jobs...");
			String testArgsInput = args[0]; //"./../../VerificationData/VerificationData_AutomatedVerification/exampleJob.xml";
			jobs = new BatchXMLHelper().generateJobFromXML(new File(testArgsInput));
			filterListForDuplicates(jobs);
			
			/*
			System.out.println("So many jobs ... " + jobs.size());
			List<Job> doJobs = new ArrayList<Job>();
			try (BufferedReader br = new BufferedReader(new FileReader("Evaluation/trainingAndTest/"+"10"+"/"+"10"))) {
				String line;
				Gson gson = new GsonBuilder().create();
				while ((line = br.readLine()) != null) {
					doJobs.add(gson.fromJson(line, Job.class));
				}
			}
			List<Job> jobs2 = new ArrayList<Job>();
			for(Job job: jobs){
				for(Job doJob: doJobs){
					if((doJob.getClazz().equals(job.getClazz()) 
							&& doJob.getMethod().equals(job.getMethod())
							&& doJob.getContractNumber() == job.getContractNumber())){
						jobs2.add(job);
					}
				}
			}
			jobs = jobs2;
			*/
			
			System.out.println("So many jobs read... " + jobs.size());
		} else {
			throw new IllegalArgumentException(
					"Please pass for parameters: classpath, class, method and samples");
		}
		

		if (DONE_FILE.exists()) {
			List<Job> doneJobs = readDoneJobs(DONE_FILE);
			jobs.removeAll(doneJobs);
			System.out.println("Already done some jobs... " + jobs.size()
					+ " left");
		}
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

	private static void filterListForDuplicates(List<Job> joblist) {
		for (int i = 0; i < joblist.size(); i++) {
			Job a = joblist.get(i);
			for (int j = i + 1; j < joblist.size(); j++) {
				Job b = joblist.get(j);
				if (a.equals(b)) {
					joblist.remove(j);
					j--;
					a.setCode(a.getCode() + ", " + b.getCode());
					a.getExperiments().putAll(b.getExperiments());
				}
			}
		}
		if (joblist instanceof ArrayList) {
			((ArrayList<?>) joblist).trimToSize();
		}
	}

	private static List<Job> readDoneJobs(File f) throws FileNotFoundException,
			IOException {
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

	private final KeyResultObserver zo;
	private final NewResultNotifier nrn;
	private WorkingPool pool;

	public Server() throws IOException {
		nrn = new NewResultNotifier();
		pool = new DistributedWorkingPool(PORT, nrn);
		zo = new KeyResultObserver(new File("zwischenergebnisse.txt"),
				DONE_FILE, pool, FILE_SERVER_PORT);
		nrn.addObserver(zo);
	}

	public void start(List<Job> jobs) throws IOException {
		for (Job job : jobs) {
			pool.addJob(new KeyProofRunnable(job, FILE_SERVER_PORT));
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
