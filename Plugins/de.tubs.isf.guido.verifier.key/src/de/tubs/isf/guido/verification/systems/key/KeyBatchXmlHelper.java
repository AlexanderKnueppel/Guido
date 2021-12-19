package de.tubs.isf.guido.verification.systems.key;

import java.io.File;
import java.io.IOException;
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

public class KeyBatchXmlHelper extends BatchXMLHelper {

	private static final String METHOD_CONST = "Method";
	private static final String NAME_CONST = "Name";
	private static final String FEATURE_SAMPLE = "FeatureSample";
	private static final String SPL_SAMPLE_FILE = "SPLSampleFile";
	private static final String PARAMETERS_CONST = "Parameters";
	private static final String CODE_CONST = "Code";
	private Map<String, List<SettingsObject>> parsedSamples = new HashMap<>();
	private Map<SearchParameter, Integer> alreadyLoadedProofs = new HashMap<>();

	public ArrayList<IJob> generateJobFromXML(File xml) throws SAXException, IOException, ParserConfigurationException {
		final ArrayList<IJob> result = new ArrayList<>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(xml);
		Element root = doc.getDocumentElement();
		String rootSPLSampleFile = root.getAttribute(SPL_SAMPLE_FILE);
		String rootFeatureSampleFile = root.getAttribute(FEATURE_SAMPLE);
		String rootCode = root.getAttribute(CODE_CONST);

		NodeList codeunits = root.getElementsByTagName("Codeunit");
		for (int i = 0; i < codeunits.getLength(); i++) {
			Element codeunit = (Element) codeunits.item(i);
			String source = codeunit.getAttribute("Source");
			String classpath = codeunit.getAttribute("Classpath");
			if (classpath.equals(""))
				classpath = "./../../Resources/JavaRedux";
			String classpathSPLSampleFile = codeunit.getAttribute(SPL_SAMPLE_FILE);
			String classpathFeatureSampleFile = codeunit.getAttribute(FEATURE_SAMPLE);
			String classpathCode = rootCode;
			if (codeunit.hasAttribute(CODE_CONST))
				classpathCode = codeunit.getAttribute(CODE_CONST);
			NodeList clazzes = codeunit.getElementsByTagName("Class");
			for (int j = 0; j < clazzes.getLength(); j++) {
				Element clazz = (Element) clazzes.item(j);
				String name = clazz.getAttribute(NAME_CONST);
				String classSPLSampleFile = clazz.getAttribute(SPL_SAMPLE_FILE);
				String classFeatureSampleFile = clazz.getAttribute(FEATURE_SAMPLE);
				String classCode = classpathCode;
				if (clazz.hasAttribute(CODE_CONST))
					classpathCode = clazz.getAttribute(CODE_CONST);
				NodeList methods = clazz.getElementsByTagName(METHOD_CONST);
				for (int k = 0; k < methods.getLength(); k++) {
					Element method = (Element) methods.item(k);
					String methodName = method.getAttribute(NAME_CONST);
					String methodSPLSampleFile = method.getAttribute(SPL_SAMPLE_FILE);
					String methodFeatureSampleFile = method.getAttribute(FEATURE_SAMPLE);
					String methodCode = classCode;
					if (method.hasAttribute(CODE_CONST))
						classpathCode = method.getAttribute(CODE_CONST);

					String splSampleFile = chooseSampleFile(rootSPLSampleFile, classpathSPLSampleFile,
							classSPLSampleFile, methodSPLSampleFile);
					String featureSampleFile = chooseSampleFile(rootFeatureSampleFile, classpathFeatureSampleFile,
							classFeatureSampleFile, methodFeatureSampleFile);

					String sampleType = cleanEmpty(featureSampleFile) == null ? SPL_SAMPLE_FILE : FEATURE_SAMPLE;
					String sampleFile = sampleType == SPL_SAMPLE_FILE ? splSampleFile : featureSampleFile;
					boolean definesParameters = method.hasAttribute(PARAMETERS_CONST);
					String[] parameters = definesParameters ? getParameters(method.getAttribute(PARAMETERS_CONST))
							: null;
					result.addAll(getJobsForMethod(methodCode, cleanEmpty(source), cleanEmpty(classpath), name,
							methodName, parameters, sampleFile, sampleType));

				}
			}

		}
		return result;
	}

	public String[] getParameters(String parametersText) {
		return parametersText.isEmpty() ? new String[0] : parametersText.split(",");
	}

	public List<IJob> getJobsForMethod(String code, String source, String classpath, String className,
			String methodName, String[] parameters, String sampleFile, String sampleType) throws IOException {
		List<IJob> result = new ArrayList<>();
		SearchParameter ksp = new SearchParameter(source, className, methodName, parameters);
		Integer noc = alreadyLoadedProofs.get(ksp);
		int numberOfContracts;
		if (noc == null) {

			numberOfContracts = new ExampleBasedKeyControl().getNumberOfContracts(
					source == null ? null : new File(source), classpath == null ? null : new File(classpath), className,
					methodName, parameters);
		} else {
			numberOfContracts = noc;
		}
		
		List<SettingsObject> al = getSampleForFile(sampleFile, sampleType);
		for (int i = 0; i < al.size(); i++) {
			KeySettingsObject ks = (KeySettingsObject) al.get(i);
			for (int num = 0; num < numberOfContracts; num++) {
				try {
					result.add(new KeyJavaJob(code, ks.getDebugNumber(), cleanEmpty(source), cleanEmpty(classpath),
							className, methodName, parameters, ks.clone(), num));
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
//		getSampleForFile(sampleFile, sampleType).forEach( setting -> {
//			KeySettingsObject ks = (KeySettingsObject) setting;
//			
//			for (int num = 0; num < numberOfContracts; num++)
//				result.add(new KeyJavaJob(code, ks.getDebugNumber(), cleanEmpty(source), cleanEmpty(classpath),
//						className, methodName, parameters, setting, num));
//		});
		return result;
	}

	public String cleanEmpty(String s) {
		return s == null ? null : s.isEmpty() ? null : s;
	}

	public String chooseSampleFile(String rootSampleFile, String codeSampleFile, String classSampleFile,
			String methodSampleFile) {
		return cleanEmpty(methodSampleFile) != null ? methodSampleFile
				: cleanEmpty(classSampleFile) != null ? classSampleFile
						: cleanEmpty(codeSampleFile) != null ? codeSampleFile : rootSampleFile;
	}

	public List<SettingsObject> getSampleForFile(String sampleFile, String type) throws IOException {
		
		if(sampleFile.equals("")) {
			return new ArrayList<SettingsObject>(Arrays.asList(new KeySettingsObject()));
		}
		
		List<SettingsObject> result = parsedSamples.get(sampleFile);
		if (result == null) {
			if (type == SPL_SAMPLE_FILE) {
				result = Collections.unmodifiableList(new KeySampleHelper().readSPLSamples(new File(sampleFile)));
			} else {
				result = Collections
						.unmodifiableList(new KeySampleHelper().readFeatureIDESamples(new File(sampleFile)));
			}
			parsedSamples.put(sampleFile, result);
		}
		return result;
	}

	private static final class SearchParameter {

		private final String codeunit;
		private final String clazz;
		private final String method;
		private final String[] parameter;

		public SearchParameter(String codeunit, String clazz, String method, String[] parameter) {
			super();
			this.codeunit = codeunit;
			this.clazz = clazz;
			this.method = method;
			this.parameter = parameter;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
			result = prime * result + ((codeunit == null) ? 0 : codeunit.hashCode());
			result = prime * result + ((method == null) ? 0 : method.hashCode());
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
			if (clazz == null) {
				if (other.clazz != null)
					return false;
			} else if (!clazz.equals(other.clazz))
				return false;
			if (codeunit == null) {
				if (other.codeunit != null)
					return false;
			} else if (!codeunit.equals(other.codeunit))
				return false;
			if (method == null) {
				if (other.method != null)
					return false;
			} else if (!method.equals(other.method))
				return false;
			if (!Arrays.equals(parameter, other.parameter))
				return false;
			return true;
		}
	}

}
