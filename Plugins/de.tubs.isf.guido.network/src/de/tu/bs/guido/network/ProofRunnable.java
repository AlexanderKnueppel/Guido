package de.tu.bs.guido.network;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import de.tu.bs.guido.key.pooling.distributed.ResultRunnable;
import de.tu.bs.guido.network.client.FileClient;
import de.tu.bs.guido.verification.system.ASystemFactory;
import de.tu.bs.guido.verification.system.Control;
import de.tu.bs.guido.verification.system.IJob;
import de.tu.bs.guido.verification.system.Result;

public class ProofRunnable implements ResultRunnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6790934214721419957L;
	
	private final int fileServerPort;
	private final IJob job;
	private Object result;
	private transient String ip;

	public ProofRunnable(IJob job, int fileServerPort) {
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
			//here Factory adding 
			Control kc = ASystemFactory.getAbst().createControl();
			FileClient fc = new FileClient(ip, fileServerPort);
			String source = job.getSource();
			String classpath = job.getClasspath();
			File sourceFile = getFileForName(fc, source, localTemp);
			File classpathFile = getFileForName(fc, classpath, localTemp);
			
			List<? extends Result> intermediate;
			intermediate = kc.getResultForProof(sourceFile, classpathFile, job.getClazz(),
					job.getMethod(), job.getParameter(), job.getContractNumber(), job.getSo());
			for (Result result : intermediate) {
				result.setCode(job.getCode());
				result.setExperiments(job.getExperiments());
			}
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
