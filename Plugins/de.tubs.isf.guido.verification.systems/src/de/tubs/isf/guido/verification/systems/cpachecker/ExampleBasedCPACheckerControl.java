package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.sosy_lab.cpachecker.core.CPAcheckerResult;

import de.tubs.isf.guido.core.analysis.CSourceCodeAnalyzer;
import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.verifier.SettingsObject;


public class ExampleBasedCPACheckerControl extends AbstractCPACheckerControl implements IProofControl{
	List<IDataBasisElement> cdb = new ArrayList<IDataBasisElement>();
	
	CSourceCodeAnalyzer csca = null;
	
	@Override
	public List<IDataBasisElement> getResultForProof(File source, File classPath, String className, String methodName,
			String[] parameters, int contractNumber, SettingsObject so) {

		File dir = source;
		if (dir.isFile())
			dir = source.getParentFile();

		csca = new CSourceCodeAnalyzer(dir);

		CPASettingsObject so1 = (CPASettingsObject) so;

		List<IDataBasisElement> res = new ArrayList<IDataBasisElement>();

		try {
			final List<Contract> proofContracts = getCorrectContract(methodName, parameters,
					env.getSpecificationRepository(), type);
			if (contractNumber == -1) {
				for (Contract contract : proofContracts) {
					res.add(getResult(env, contract, so1));
				}
			} else {
				res.add(getResult( proofContracts.get(contractNumber), so1));
			}
		} finally {
			env.dispose();
		}
		return res;
	}

	private CPACheckerDataBasisElement getResult( CPASettingsObject so) {
		
		CPAcheckerResult result = MainClass.main(configFile, programFile, stats, option);
		return createResult(result,
					csca.analyze().stream().map(l -> l.getLanguageConstruct()).collect(Collectors.toList()));

		return null;
	}

	@Override
	public void performProof(SettingsObject so) {
		CPASettingsObject cso = (CPASettingsObject) so;
		CPACheckerCodeContainer ccc = (CPACheckerCodeContainer) cso.getCc();

		cdb.addAll(getResultForProof(new File(ccc.getSource()), new File(ccc.getSpecificationPath()), ccc.getClazz(),
				ccc.getMethod(), cso));

	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IDataBasisElement> getCurrentResults() {
		return cdb;
	}
}
