package de.tubs.isf.guido.verification.systems.cpachecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.tubs.isf.guido.core.verifier.BatchXMLHelper;
import de.tubs.isf.guido.core.verifier.GetJobs;
import de.tubs.isf.guido.core.verifier.IJob;
import de.tubs.isf.guido.core.verifier.SettingsObject;

public class CPACheckerBatchXmlHelper extends BatchXMLHelper {
	private static final String CONFIG_FILE = "Configuration";
	private static final String FEATURE_SAMPLE = "FeatureSample";
	private static final String SPL_SAMPLE_FILE = "SPLSampleFile";
	private static final String PARAMETERS_CONST = "Parameters";
	private static final String BINARY_CONST = "Binary";
	private Map<String, List<SettingsObject>> parsedSamples = new HashMap<>();
	private Map<SearchParameter, Integer> alreadyLoadedProofs = new HashMap<>();

	
	@Override
	public ArrayList<IJob> generateJobFromXML(File xml) throws SAXException, IOException, ParserConfigurationException {
		final ArrayList<IJob> result = new ArrayList<>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(xml);
		Element root = doc.getDocumentElement();
		String rootSPLSampleFile = root.getAttribute(SPL_SAMPLE_FILE);
		String rootFeatureSampleFile = root.getAttribute(FEATURE_SAMPLE);
		String nonSense = "";

		NodeList codeunits = root.getElementsByTagName("Codeunit");
		for (int i = 0; i < codeunits.getLength(); i++) {
			Element codeunit = (Element) codeunits.item(i);			
			String configuration = codeunit.getAttribute(CONFIG_FILE);
			
			NodeList problems = codeunit.getElementsByTagName("Problem");
			for (int j = 0; j < problems.getLength(); j++) {
				Element problem = (Element) problems.item(j);
				String source = problem.getAttribute("Source");
				String binary = problem.getAttribute(BINARY_CONST);
				
				String splSampleFile = problem.getAttribute(SPL_SAMPLE_FILE);
				String featureSampleFile = problem.getAttribute(FEATURE_SAMPLE);
				String sampleType = cleanEmpty(featureSampleFile) == null ? SPL_SAMPLE_FILE : FEATURE_SAMPLE;
				String sampleFile = sampleType == SPL_SAMPLE_FILE ? splSampleFile : featureSampleFile;
				
				boolean definesParameters = problem.hasAttribute(PARAMETERS_CONST);
				String[] parameters = definesParameters ? getParameters(problem.getAttribute(PARAMETERS_CONST))
						: null;
				result.addAll(getJobsForMethod(binary, cleanEmpty(source), cleanEmpty(configuration), nonSense, nonSense, parameters, sampleFile, sampleType));
			}
			
		}
		return result;
	}

	@Override
	protected String[] getParameters(String parametersText) {
		return parametersText.isEmpty() ? new String[0] : parametersText.split(",");
	}

	/**
	 * Here are two variables called nonSense , which had to be implemented, because of the inheritance BatchXMLHelper
	 */
	@Override
	protected List<IJob> getJobsForMethod(String binary, String source, String configurationPath, String nonSense, String nonSense2, String[] parameters, String sampleFile, String sampleType) throws IOException {
		List<IJob> result = new ArrayList<>();
		SearchParameter ksp = new SearchParameter(configurationPath, source, parameters);
		Integer noc = alreadyLoadedProofs.get(ksp);

			List<SettingsObject> al = getSampleForFile(sampleFile, sampleType);
			for (int i = 0; i < al.size(); i++) {
			CPASettingsObject ks = (CPASettingsObject) al.get(i);

			result.add(new CPACJob(cleanEmpty(configurationPath), binary, cleanEmpty(source), ks.getDebugNumber(), parameters, ks, 1));
			}		
		return result;
	}

	@Override
	protected String cleanEmpty(String s) {
		return s == null ? null : s.isEmpty() ? null : s;
	}

	@Override
	protected String chooseSampleFile(String rootSampleFile, String codeSampleFile, String classSampleFile,
			String methodSampleFile) {
		return cleanEmpty(methodSampleFile) != null ? methodSampleFile
				: cleanEmpty(classSampleFile) != null ? classSampleFile
						: cleanEmpty(codeSampleFile) != null ? codeSampleFile : rootSampleFile;
	}

	public List<SettingsObject> getSampleForFile(String sampleFile, String type) throws IOException {
		
		if(sampleFile.equals("")) {
			return new ArrayList<SettingsObject>(Arrays.asList(new CPASettingsObject()));
		}
		
		List<SettingsObject> result = parsedSamples.get(sampleFile);
		if (result == null) {
			if (type == SPL_SAMPLE_FILE) {
				result = Collections.unmodifiableList(new CPACheckerSampleHelper().readSPLSamples(new File(sampleFile)));
			} else {				
				result = Collections
						.unmodifiableList(new CPACheckerSampleHelper().readFeatureIDESamples(new File(sampleFile)));
			}
			parsedSamples.put(sampleFile, result);
		}
		return result;
	}
	private static final class SearchParameter {

		private final String codeunit;
		private final String configuration;
		private final String[] parameter;

		public SearchParameter(String codeunit, String configuration, String[] parameter) {
			super();
			this.codeunit = codeunit;
			this.configuration = configuration;
			this.parameter = parameter;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((configuration == null) ? 0 : configuration.hashCode());
			result = prime * result + ((codeunit == null) ? 0 : codeunit.hashCode());
			result = prime * result + Arrays.hashCode(parameter);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SearchParameter other = (SearchParameter) obj;
			if (configuration == null) {
				if (other.configuration != null)
					return false;
			} else if (!configuration.equals(other.configuration))
				return false;
			if (codeunit == null) {
				if (other.codeunit != null)
					return false;
			} else if (!codeunit.equals(other.codeunit))
				return false;
			if (!Arrays.equals(parameter, other.parameter))
				return false;
			return true;
		}
	}
}
