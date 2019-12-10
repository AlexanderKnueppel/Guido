package de.tubs.isf.guido.core.proof;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.tubs.isf.core.verifier.SettingsObject;
import de.tubs.isf.guido.core.proof.controller.IProofControl;

public class ProverThread {
	public static IProofControl startProof(IProofControl pc, SettingsObject so, long time) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<IProofControl> future = executor.submit(new ProverTask(pc, so));
		try {
			System.out.println("Started Proof");
			pc = future.get(time, TimeUnit.MILLISECONDS);
			System.out.println("Finished Proof!");
			// return pc;
		} catch (TimeoutException e) {
			System.out.println("Terminated Proof! Timeout! Future done? " + future.isDone());
			future.cancel(true);
			// pc = null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			executor.shutdownNow();
		}

		return pc;
	}
}