package de.tubs.isf.guido.verification.systems.key;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.uka.ilkd.key.control.KeYEnvironment;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.settings.ProofSettings;
import de.uka.ilkd.key.speclang.Contract;

public class ExampleBasedKeyControl extends AbstractKeyControl implements IProofControl {
	List<IDataBasisElement> kdb = new ArrayList<IDataBasisElement>();
	@Override
	public List<IDataBasisElement> getResultForProof(File source, File classPath, String className, String methodName,
			String[] parameters, int contractNumber, SettingsObject so) {
		KeySettingsObject so1 = (KeySettingsObject) so;
		
		List<IDataBasisElement> res = new ArrayList<IDataBasisElement>();
		if (!ProofSettings.isChoiceSettingInitialised()) {
			KeYEnvironment<?> env = null;
			try {
				env = KeYEnvironment.load(source, null, classPath, null);
			} catch (ProblemLoaderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			env.dispose();
		}
		applyDefaultTaclets(so1);
		KeYEnvironment<?> env = null;
		try {
			env = KeYEnvironment.load(source, null, classPath, null);
		} catch (ProblemLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			KeYJavaType type = env.getJavaInfo().getKeYJavaType(className);
			final List<Contract> proofContracts = getCorrectContract(methodName, parameters,
					env.getSpecificationRepository(), type);
			if (contractNumber == -1) {
				for (Contract contract : proofContracts) {
					res.add(getResult(env, contract, so1));
				}
			} else {
				res.add(getResult(env, proofContracts.get(contractNumber), so1));
			}
		} finally {
			env.dispose();
		}
		return res;
	}

	private KeyDataBasis getResult(KeYEnvironment<?> env, Contract contract, KeySettingsObject so) {
		Proof proof = null;
		try {
			proof = env.createProof(contract.createProofObl(env.getInitConfig(), contract));
			applySettings(proof, so);
			env.getUi().getProofControl().startAndWaitForAutoMode(proof);
			return createResult(contract, proof);
		} catch (ProofInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (proof != null) {
				proof.dispose(); // Ensure always that all instances of Proof
									// are disposed
			}
		}
		return null;
	}

	@Override
	public int getNumberOfContracts(File source, File classPath, String className, String methodName,
			String[] parameters) {
		KeYEnvironment<?> env = null;
		try {
			env = KeYEnvironment.load(source, null, classPath, null);
		} catch (ProblemLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			KeYJavaType type = env.getJavaInfo().getKeYJavaType(className);
			List<Contract> proofContracts = getCorrectContract(methodName, parameters, env.getSpecificationRepository(),
					type);
			return proofContracts.size();
		} finally {
			env.dispose();
		}
	}

	@Override
	public void performProof(SettingsObject so) {
		KeySettingsObject kso = (KeySettingsObject) so;
		KeyCodeContainer kcc = (KeyCodeContainer) kso.getCc();
		
		kdb.addAll(getResultForProof(new File(kcc.getSource()), new File(kcc.getClasspath()), kcc.getClazz(), kcc.getMethod(), kso));
		
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IDataBasisElement> getCurrentResults() {
		return kdb;
	}

}
