package de.tu.bs.guido.key.network;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import analyzer.Result;
import automaticProof.GuidanceSystemResult;
import automaticProof.Prover;
import automaticProof.ProverThread;
import de.tu.bs.guido.key.network.client.FileClient;
import de.tu.bs.guido.key.pooling.distributed.ResultRunnable;

public class KeyProofRunnable implements ResultRunnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6790934214721419957L;
	
	private final int fileServerPort;
	private final Job job;
	private Object result;
	private transient String ip;

	public KeyProofRunnable(Job job, int fileServerPort) {
		super();
		this.job = job;
		this.fileServerPort = fileServerPort;
	}

	@Override
	public void run() {
		try {
			File localTemp = new File(new File("Temp"),"Client");
			localTemp.mkdirs();
			
			System.out.println("Running: "+job);				
			Prover prover = new Prover();

			FileClient fc = new FileClient(ip, fileServerPort);
			String source = job.getSource();
			String classpath = job.getClasspath();
			File sourceFile = getFileForName(fc, source, localTemp);
			File classpathFile = getFileForName(fc, classpath, localTemp);
			
			GuidanceSystemResult intermediate;
			intermediate = /*ProverThread.startProvingThread("N", prover, sourceFile, classpathFile, job.getClazz(),
					job.getMethod(), job.getParameter(), job.getContractNumber());*/
					prover.getResultForProof(sourceFile, classpathFile, job.getClazz(),
					job.getMethod(), job.getParameter(), job.getContractNumber());
			ResultCommunication resultComm = new ResultCommunication(job, intermediate);
			result = resultComm;
		} catch (Exception e) {
			result = e;
		}
	}

	private static File getFileForName(FileClient fc, String name, File temp) throws ClassNotFoundException, IOException{
		if(name == null)
			return null;
		
		String localSourceName = fc.getFilename(name);
		File localSourceFile = new File(temp,localSourceName);
		if(!localSourceFile.exists())
			fc.getFile(localSourceName, temp);
		return localSourceFile;
	}
	
	@Override
	public Object getResult() {
		return result;
	}

	@Override
	public void setIPAdress(String ip) {
		this.ip = ip;
	}

}
