package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.sosy_lab.cpachecker.core.CPAcheckerResult;

import de.tubs.isf.guido.core.analysis.CSourceCodeAnalyzer;
import de.tubs.isf.guido.core.analysis.IAnalyzer.LanguageConstruct;
import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.SettingsObject;


public class ExampleBasedCPACheckerControl extends AbstractCPACheckerControl implements IProofControl{
	List<IDataBasisElement> cdb = new ArrayList<IDataBasisElement>();
	
	CSourceCodeAnalyzer csca = null;
	

	@Override
	List<IDataBasisElement> getResultForProof(String configFilePath, File binary, File source, String[] parameters,
			int num, SettingsObject so) {
		return null;
	}
	public List<IDataBasisElement> getResultForProof(String configFilePath, File binary, File source, String[] parameters,
			SettingsObject so) {

		File dir = source;
		if (dir.isFile()) {
			csca = new CSourceCodeAnalyzer(dir);
		}else {
			System.out.println("The given C-file is not a file.");
		}
		
		CPASettingsObject so1 = (CPASettingsObject) so;

		List<IDataBasisElement> res = new ArrayList<IDataBasisElement>();
		res.add(getResult(configFilePath, binary,source,parameters, so1));

		return res;
	}

	private CPACheckerDataBasisElement getResult(String configFile,File binary, File source, String[] parameters, CPASettingsObject so) {
		Map<String,String> settings = so.getSettingsMap();
		String option = "";
		for(Map.Entry<String,String> entry: settings.entrySet()) {
			option = option + " " + entry.getKey() + "=" + entry.getValue();
		}
		String parameter = "";
		
		for(String param: parameters) {
			parameter = parameter +" "+ param;
		}
		CPAcheckerResult result = MainClass.main(configFile, binary.getAbsolutePath(), parameter, option);
		List<LanguageConstruct> clc = csca.analyze();
		return createResult(result,	
				clc.stream().map(l -> l.getLanguageConstruct()).collect(Collectors.toList()),
				settings);
	}

	@Override
	public IDataBasisElement performProof(IJob job) {
		CPASettingsObject cso = (CPASettingsObject) job.getSo();
		CPACheckerCodeContainer ccc = (CPACheckerCodeContainer) job.getCodeContainer();
		
		return getResultForProof(ccc.getConfigFilePath(), new File(ccc.getBinary()),new File(ccc.getSource()), ccc.getParameter(),cso).get(0);

	}

	public int getNumberOfJobs(String methodName) {
		return 0;
	}



}
