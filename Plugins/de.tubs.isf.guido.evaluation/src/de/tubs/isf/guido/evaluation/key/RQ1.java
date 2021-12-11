package de.tubs.isf.guido.evaluation.key;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.tubs.isf.guido.core.analysis.AnalysisCombinator;
import de.tubs.isf.guido.core.analysis.IAnalyzer.LanguageConstruct;
import de.tubs.isf.guido.core.analysis.JMLContractAnalyzer;
import de.tubs.isf.guido.core.analysis.JavaSourceCodeAnalyzer;
import de.tubs.isf.guido.core.costs.CostNetwork;
import de.tubs.isf.guido.core.costs.Parameter;
import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.generator.GuidoConfigurationGenerator;
import de.tubs.isf.guido.core.generator.GuidoConfigurationGenerator.Mechanism;
import de.tubs.isf.guido.core.proof.controller.IProofControl;
import de.tubs.isf.guido.core.statistics.Hypotheses;
import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;
import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.tubs.isf.guido.evaluation.util.Helper;
import de.tubs.isf.guido.verification.systems.key.KeyCodeContainer;
import de.tubs.isf.guido.verification.systems.key.KeyFactory;

public class RQ1 {

	public static Map<IJob, IDataBasisElement> eval(Hypotheses accepted, double gamma, List<IJob> jobs,
			Mechanism cont) {

		IProofControl verifier = AVerificationSystemFactory.getFactory().createProofControl();
		List<Parameter> parameters = Helper.convertTo(AVerificationSystemFactory.getFactory().createSettingsObject());

		Map<IJob, IDataBasisElement> result = new HashMap<IJob, IDataBasisElement>();

		int i = 1;
		for (IJob job : jobs) {
			KeyCodeContainer cc = (KeyCodeContainer) job.getCodeContainer();

			File dir = new File(cc.getSource());
			if (dir.isFile())
				dir = dir.getParentFile();

			JavaSourceCodeAnalyzer jsca = new JavaSourceCodeAnalyzer(dir, cc.getClazz(), cc.getMethod(),
					cc.getParameter());

			JMLContractAnalyzer ca = new JMLContractAnalyzer(jsca.getCommentString());
			List<LanguageConstruct> lcs = AnalysisCombinator.and(jsca, ca);

			GuidoConfigurationGenerator generator = new GuidoConfigurationGenerator(
					new CostNetwork(accepted, lcs, parameters), cont, gamma);
			
			SettingsObject so = generator.computeNext();
			so.setMaxEffort(10000);
			job.setSo(so);

			System.out.println("Job " + (i++) + "/" + jobs.size() + ": " + cc.getClass() + "." + cc.getMethod() + (cc.getParameter() != null ? " with parameters" : "") + " (Contract " + cc.getContractNumber() + ")...");
			result.put(job, verifier.performProof(job));
			System.out.println(result.get(job).isProvable() ? ("Successful closed with " + result.get(job).getEffort() + " steps") : "Not closed...!");
		}

		return result;
	}

	public static void main(String[] args) {
		String testArgsInput = "testData/keyproject/job.xml";// args[0];

		List<IJob> jobs = null;

		AVerificationSystemFactory.setFactory(new KeyFactory());

		Hypotheses accepted = new Hypotheses(new File("testData/keyproject/R/acceptedHypotheses.txt"), true);

		Map<Double, Map<IJob, IDataBasisElement>> results = new HashMap<Double, Map<IJob, IDataBasisElement>>();

		try {
			jobs = AVerificationSystemFactory.getFactory().createBatchXMLHelper()
					.generateJobFromXML(new File(testArgsInput));

			for (double d = 1.0; d >= 0.0; d -= 0.1) {
				results.put(d, RQ1.eval(accepted, d, jobs, Mechanism.NONE));
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String content = "\t category\t version\t n\t\n";
		int nr = 1;
		for (Entry<Double, Map<IJob, IDataBasisElement>> elem : results.entrySet()) {
			double ratio = elem.getValue().entrySet().stream().mapToInt(e -> (e.getValue().isProvable() ? 1 : 0)).sum()
					/ (double) jobs.size();
			content += (nr++) + "\t closed\t " + elem.getKey() + "\t " + (int) (ratio * 100) + "\t\n";
		}

		System.out.println(content);

		for (Entry<Double, Map<IJob, IDataBasisElement>> elem : results.entrySet()) {
			int fastest = 0;

			for (Entry<IJob, IDataBasisElement> subject : elem.getValue().entrySet()) {
				
				List<IDataBasisElement> dbelist = results.entrySet().stream().filter(e -> e.getKey() != elem.getKey())
						.map(x -> x.getValue().get(subject.getKey())).collect(Collectors.toList());
				
				if(dbelist.stream().filter(e -> e.getEffort() <= subject.getValue().getEffort()).count() == 0) {
					fastest++;
				}
			}
			
			double ratio = fastest / (double)(jobs.size());

			content += (nr++) + "\t step\t " + elem.getKey() + "\t " + (int) (ratio * 100) + "\t\n";
		}
	}
}
