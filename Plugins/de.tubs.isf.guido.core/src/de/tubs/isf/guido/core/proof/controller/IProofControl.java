package de.tubs.isf.guido.core.proof.controller;

import java.util.List;

import de.tu.bs.guido.verification.system.SettingsObject;
import de.tubs.isf.guido.core.databasis.IDataBasisElement;

public interface IProofControl {
	public void performProof(SettingsObject so);
	public boolean isClosed();
	public List<IDataBasisElement> getCurrentResults();
}
