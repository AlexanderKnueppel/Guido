package de.tubs.isf.guido.core.proof.controller;

import java.util.List;

import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.verifier.SettingsObject;

public interface IProofControl {
	public void performProof(SettingsObject so);
	public boolean isClosed();
	public List<IDataBasisElement> getCurrentResults();
}
