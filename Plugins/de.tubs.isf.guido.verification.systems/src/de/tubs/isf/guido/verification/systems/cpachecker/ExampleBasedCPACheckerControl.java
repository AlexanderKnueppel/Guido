package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	public List<IDataBasisElement> getResultForProof(File source, File configFile, String className, String methodName,
			String[] parameters, SettingsObject so) {

		File dir = source;
		if (dir.isFile())
			dir = source.getParentFile();

		csca = new CSourceCodeAnalyzer(dir);

		CPASettingsObject so1 = (CPASettingsObject) so;

		List<IDataBasisElement> res = new ArrayList<IDataBasisElement>();
		res.add(getResult(configFile, source,methodName, parameters, so1));

		return res;
	}

	private CPACheckerDataBasisElement getResult(File configFile, File source, String methodName, String[] parameters, CPASettingsObject so) {
		Map<String,String> settings = so.getSettingsMap();
		String option = "";
		for(Map.Entry<String,String> entry: settings.entrySet()) {
			option = option + " " + entry.getKey() + "=" + entry.getValue();
		}
		String parameter = "";
		for(String param: parameters) {
			parameter = parameter +" "+ param;
		}
		CPAcheckerResult result = MainClass.main(configFile.getAbsolutePath(), source.getAbsolutePath(), parameter, option);
		return createResult(methodName, result,	
				csca.analyze().stream().map(l -> l.getLanguageConstruct()).collect(Collectors.toList()),
				settings);
	}

	@Override
	public void performProof(SettingsObject so) {
		CPASettingsObject cso = (CPASettingsObject) so;
		CPACheckerCodeContainer ccc = (CPACheckerCodeContainer) cso.getCc();

		cdb.addAll(getResultForProof(new File(ccc.getSource()), new File(ccc.getConfigFilePath()), ccc.getClazz(),
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
