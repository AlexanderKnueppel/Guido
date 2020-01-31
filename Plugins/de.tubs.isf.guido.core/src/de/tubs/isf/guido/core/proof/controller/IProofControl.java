package de.tubs.isf.guido.core.proof.controller;

import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.verifier.IJob;

public interface IProofControl {
	public IDataBasisElement performProof(IJob job);
	//public boolean isClosed();
	//public List<IDataBasisElement> getCurrentResults();
}
