package de.tubs.isf.guido.core.proof;

import java.util.concurrent.Callable;

import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.verifier.SettingsObject;

public class ProverTask implements Callable<IProofControl> {
	IProofControl pc;
	SettingsObject so;

	public ProverTask(IProofControl pc, SettingsObject so) {
		this.pc = pc;
		this.so = so;
	}

	@Override
	public IProofControl call() throws Exception {	
		pc.performProof(so);
		return pc;
	}
}
