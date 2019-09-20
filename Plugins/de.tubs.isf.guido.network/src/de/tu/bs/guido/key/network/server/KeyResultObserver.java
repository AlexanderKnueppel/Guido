package de.tu.bs.guido.key.network.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import analyzer.Result;
import de.tu.bs.guido.key.network.Job;
import de.tu.bs.guido.key.network.KeyProofRunnable;
import de.tu.bs.guido.key.network.ResultCommunication;
import de.tu.bs.guido.key.pooling.WorkingPool;

public class KeyResultObserver implements Observer {

	private BufferedWriter ergebnisse;
	private BufferedWriter doneJob;
	private Gson gson = new GsonBuilder().create();
	private final WorkingPool pool;
	private int port;

	public KeyResultObserver(File result, File done, WorkingPool pool, int port)
			throws IOException {
		ergebnisse = new BufferedWriter(new FileWriter(result, true));
		doneJob = new BufferedWriter(new FileWriter(done, true));
		this.pool = pool;
		this.port = port;
	}

	public void close() throws IOException {
		ergebnisse.close();
	}

	@Override
	public void update(Observable o, Object arg) {
		ResultCommunication resCom = (ResultCommunication) arg;
			synchronized (ergebnisse) {
				try {
						ergebnisse.write(gson.toJson(resCom.getResults()));
						ergebnisse.newLine();
					
					doneJob.write(gson.toJson(resCom.getJob()));
					doneJob.newLine();
					ergebnisse.flush();
					doneJob.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
}
