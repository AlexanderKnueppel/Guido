package automaticProof;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import automaticProof.proofFile.Job;
import automaticProof.proofFile.XMLReader;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import keYHandler.ProofControl;
import keYHandler.options.SettingsObject;

public class ProverThread {

	/*public static GuidanceSystemResult startProvingFileThread(Prover prover, String callingMethod, File provingContractsFile){
		GuidanceSystemResult res = null;
		System.out.println("Proof all contracts mentioned in " + provingContractsFile.toString());
		List<Job> jobs;
		try {
			jobs = new XMLReader().generateJobFromXML(provingContractsFile);
			for(Job job: jobs){
				System.out.println("----------------------------------------------");
				String param = ""; 
				if(job.getParameter() != null){
					for(String params : job.getParameter()) param = param + ", " + params;
				}
				System.out.println("Prove: " + job.getClazz() + "; " + job.getMethod() + "; " + param + "; " + job.getContractNumber());
				res = startProvingThread(callingMethod, prover, new File(job.getSource()), new File(job.getClasspath()), job.getClazz(),
						job.getMethod(), job.getParameter(), job.getContractNumber());
			}
		} catch (ProofInputException | SAXException | IOException | ParserConfigurationException
				| ProblemLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return res;
    }*/
	
	public static ProofControl startProof(ProofControl pc, SettingsObject so, long time){
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<ProofControl> future = executor.submit(new ProverTask(pc, so));
        try {
            System.out.println("Started Proof");
            pc = future.get(time, TimeUnit.MILLISECONDS);
            System.out.println("Finished Proof!");
            //return pc;
        } catch (TimeoutException e) {
        	System.out.println("Terminated Proof! Timeout! Future done? " + future.isDone());
            future.cancel(true);
            //pc = null;
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
	
	
	/*public static GuidanceSystemResult startProvingThread(String callingMethod, Prover prover, File sourcePath, File reduxPath, String provingClass, String provingMethod, String[] parameter, int contract){
		ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<GuidanceSystemResult> future = executor.submit(new ProverTask(callingMethod, prover, sourcePath, reduxPath, provingClass, provingMethod, parameter, contract));
        GuidanceSystemResult res = null;
        try {
            System.out.println("Started Proof");
            res = future.get(300, TimeUnit.SECONDS);
            System.out.println("Finished Proof!");
            return res;
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("Terminated Proof!");
                      	 String parameterString = null;
                 if(parameter != null){
                 	for(String param: parameter) parameterString = param + ",";
                 }
                 return new GuidanceSystemResult("[" + provingClass + "::" + provingMethod + "(" + parameterString + ")]" + contract , false, -1, -1, -1, 100, -1, null, null);
            
           } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        executor.shutdownNow();
        return res;
    }*/
	
}