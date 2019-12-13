package de.tu.bs.guido.network.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;
import de.tubs.isf.guido.core.verifier.IJob;

public class PunishmentTracker {
	private static final int MAXIMUM_MAX_STEPS = 1000000;
	private static final int MAX_STEPS_GROWTH_FACTOR = 5;

	private final File punishments;

	public PunishmentTracker(File punishments) {
		super();
		this.punishments = punishments;
	}

	private List<IJob> readPunishments() throws FileNotFoundException, IOException {
		List<IJob> punishments = new ArrayList<IJob>();
		try (BufferedReader br = new BufferedReader(new FileReader(this.punishments))) {
			String line;
			Gson gson = new Gson();
			while ((line = br.readLine()) != null) {
				IJob j = AVerificationSystemFactory.getFactory().parseJobWithGson(line);
				punishments.add(j);
			}
		}
		return punishments;
	}

	public void updatePunishments(List<IJob> jobs) throws FileNotFoundException, IOException {
		List<IJob> otherList = readPunishments();
		for (int i = 0; i < otherList.size(); i += 2) {
			IJob oldOne = otherList.get(i);
			if (jobs.contains(oldOne)) {
				jobs.remove(oldOne);
				jobs.add(otherList.get(i + 1));
			}
		}
	}

	public IJob punish(IJob j) throws IOException {
		synchronized (punishments) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(punishments, true))) {
				IJob newJob = null;
				try {
					newJob = j.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				double maxSteps = newJob.getSo().getMaxEffort();
				if (maxSteps >= MAXIMUM_MAX_STEPS) {
					System.err.println("Maxmium punishment reached for job !_!");
				} else {
					double newMaxSteps = MAX_STEPS_GROWTH_FACTOR * maxSteps;
					if (newMaxSteps > MAXIMUM_MAX_STEPS)
						newMaxSteps = MAXIMUM_MAX_STEPS;
					newJob.getSo().setMaxEffort(newMaxSteps);
					System.out.println("PUNISHED!");

					Gson gson = new Gson();
					bw.write(gson.toJson(j));
					bw.newLine();
					bw.write(gson.toJson(newJob));
					bw.newLine();
				}
				j = newJob;
			}
		}
		return j;
	}

}
