package de.tubs.isf.guido.core.proof;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.verifier.IJob;

public class ProverThread {
	public static IDataBasisElement startProof(IProofControl pc, IJob job, long time) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<IDataBasisElement> future = executor.submit(new ProverTask(pc, job));
		
		IDataBasisElement result = null;
		
		try {
			System.out.println("Started Proof");
			result = future.get(time, TimeUnit.MILLISECONDS);
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

		return result;
	}
}