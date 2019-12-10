package de.tubs.isf.guido.core.automaticProof;

import java.io.File;
import java.util.concurrent.Callable;

import de.tubs.isf.core.verifier.SettingsObject;
import de.tubs.isf.guido.core.keYHandler.ProofControl;

public class ProverTask implements Callable<ProofControl> {

	Prover prover;
	File sourcePath;
	File reduxPath;
	String provingClass;
	String provingMethod;
	String[] parameter;
	int contract;
	String callingMethod;
	ProofControl pc;
	SettingsObject so;
	
	public ProverTask(ProofControl pc, SettingsObject so){//String callingMethod, Prover prover, File sourcePath, File reduxPath, String provingClass, String provingMethod, String[] parameter, int contract){
		this.pc = pc;
		this.so = so;
		/*this.prover = prover;
		this.sourcePath = sourcePath;
		this.reduxPath = reduxPath;
		this.provingClass = provingClass;
		this.provingMethod = provingMethod;
		this.parameter = parameter;
		this.contract = contract;
		this.callingMethod = callingMethod;*/
	}
	
	    @Override
	    public ProofControl call() throws Exception {
	    	pc.performProof(so);	
	    	return pc;
	    }
	    	/*if(callingMethod.startsWith("G") || callingMethod.startsWith("N") || callingMethod.startsWith("F")){
	    		return prover.getResultForProof(sourcePath, reduxPath, provingClass, provingMethod, parameter, contract);
	    	}
	    	else if(callingMethod.startsWith("M")){
		    	prover.proving(sourcePath, reduxPath, provingClass, provingMethod, parameter, contract);
	    	}
	    	else if(callingMethod.startsWith("D")){
	    		prover.getResultForProofDefault(sourcePath, reduxPath, provingClass, provingMethod, parameter, contract);
	    	}
	        return null;
	    }*/
	
}
