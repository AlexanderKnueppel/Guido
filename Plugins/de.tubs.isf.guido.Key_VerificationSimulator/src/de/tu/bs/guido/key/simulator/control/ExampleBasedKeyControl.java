package de.tu.bs.guido.key.simulator.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.tu.bs.guido.key.simulator.Result;
import de.tu.bs.masterthesis.key.simulator.options.SettingsObject;
import de.uka.ilkd.key.control.KeYEnvironment;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.settings.ProofSettings;
import de.uka.ilkd.key.speclang.Contract;

public class ExampleBasedKeyControl extends AbstractKeyControl {

	@Override
	public List<Result> getResultForProof(File source, File classPath,
			String className, String methodName, String[] parameters, int contractNumber,
			SettingsObject so) throws ProblemLoaderException,
			ProofInputException {
		List<Result> res = new ArrayList<Result>();
		if (!ProofSettings.isChoiceSettingInitialised()) {
			KeYEnvironment<?> env = KeYEnvironment.load(source, null,
					classPath, null);
			env.dispose();
		}
		applyDefaultTaclets(so);
		KeYEnvironment<?> env = KeYEnvironment.load(source, null, classPath,
				null);
		try {
			KeYJavaType type = env.getJavaInfo().getKeYJavaType(className);
			final List<Contract> proofContracts = getCorrectContract(
					methodName, parameters, env.getSpecificationRepository(), type);
			if (contractNumber == -1) {
				for (Contract contract : proofContracts) {
					res.add(getResult(env, contract, so));
				}
			} else {
				res.add(getResult(env, proofContracts.get(contractNumber), so));
			}
		} finally {
			env.dispose();
		}
		return res;
	}

	private Result getResult(KeYEnvironment<?> env, Contract contract,
			SettingsObject so) throws ProofInputException {
		Proof proof = null;
		try {
			proof = env.createProof(contract.createProofObl(
					env.getInitConfig(), contract));
			applySettings(proof, so);
			env.getUi().getProofControl().startAndWaitForAutoMode(proof);
			return createResult(contract, proof);
		} finally {
			if (proof != null) {
				proof.dispose(); // Ensure always that all instances of Proof
									// are disposed
			}
		}
	}

	@Override
	public int getNumberOfContracts(File source, File classPath,
			String className, String methodName, String[] parameters)
			throws ProblemLoaderException, ProofInputException {
		KeYEnvironment<?> env = KeYEnvironment.load(source, null, classPath,
				null);
		try {
			KeYJavaType type = env.getJavaInfo().getKeYJavaType(className);
			List<Contract> proofContracts = getCorrectContract(methodName, parameters,
					env.getSpecificationRepository(), type);
			return proofContracts.size();
		} finally {
			env.dispose();
		}
	}

}
