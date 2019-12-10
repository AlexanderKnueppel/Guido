package de.tubs.isf.guido.core.proof.controller;

import java.util.List;

import de.tu.bs.guido.verification.system.SettingsObject;
import de.tubs.isf.guido.core.databasis.IDataBasisElement;

public interface IProofControl {
	public List<IDataBasisElement> getResultForProof(SettingsObject so);
	public boolean isClosed();
}
