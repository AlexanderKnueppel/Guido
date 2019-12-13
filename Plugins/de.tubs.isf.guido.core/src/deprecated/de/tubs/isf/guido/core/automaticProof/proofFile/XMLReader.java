package deprecated.de.tubs.isf.guido.core.automaticProof.proofFile;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import deprecated.de.tubs.isf.guido.core.automaticProof.Prover;

public class XMLReader {

	private static final String METHOD_CONST = "Method";
	private static final String NAME_CONST = "Name";
	private static final String PARAMETERS_CONST = "Parameters";
	private static final String CODE_CONST = "Code";
	private Map<KeYSearchParameter, Integer> alreadyLoadedProofs = new HashMap<>();

	public List<Job> generateJobFromXML(File xml) throws SAXException,
			IOException, ParserConfigurationException, ProofInputException,
			ProblemLoaderException {
		final List<Job> result = new ArrayList<>();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		if(!xml.exists()) {
			xml.createNewFile();
		}
		System.out.println(xml.getAbsolutePath());
		Document doc = db.parse(xml);
		Element root = doc.getDocumentElement();
		String rootCode = root.getAttribute(CODE_CONST);

		NodeList codeunits = root.getElementsByTagName("Codeunit");
		for (int i = 0; i < codeunits.getLength(); i++) {
			Element codeunit = (Element) codeunits.item(i);
			String source = codeunit.getAttribute("Source");
			String classpath = codeunit.getAttribute("Classpath");
			if(classpath.equals("")) classpath = "./Resources/JavaRedux";
			String classpathCode = rootCode;
			if (codeunit.hasAttribute(CODE_CONST))
				classpathCode = codeunit.getAttribute(CODE_CONST);
			NodeList clazzes = codeunit.getElementsByTagName("Class");
			for (int j = 0; j < clazzes.getLength(); j++) {
				Element clazz = (Element) clazzes.item(j);
				String name = clazz.getAttribute(NAME_CONST);
				String classCode = classpathCode;
				if (clazz.hasAttribute(CODE_CONST))
					classpathCode = clazz.getAttribute(CODE_CONST);
				NodeList methods = clazz.getElementsByTagName(METHOD_CONST);
				for (int k = 0; k < methods.getLength(); k++) {
					Element method = (Element) methods.item(k);
					String methodName = method.getAttribute(NAME_CONST);
					String methodCode = classCode;
					if (method.hasAttribute(CODE_CONST))
						classpathCode = method.getAttribute(CODE_CONST);

					boolean definesParameters = method
							.hasAttribute(PARAMETERS_CONST);
					String[] parameters = definesParameters ? getParameters(method
							.getAttribute(PARAMETERS_CONST)) : null;
					result.addAll(getJobsForMethod(methodCode,
							cleanEmpty(source), cleanEmpty(classpath), name,
							methodName, parameters, null, null));
				}
			}

		}

		return result;
	}

	private String[] getParameters(String parametersText) {
		return parametersText.isEmpty() ? new String[0] : parametersText
				.split(",");
	}

	private List<Job> getJobsForMethod(String code, String source,
			String classpath, String className, String methodName,
			String[] parameters, String sampleFile, String sampleType)
			throws ProofInputException, ProblemLoaderException, IOException {
		List<Job> result = new ArrayList<>();
		KeYSearchParameter ksp = new KeYSearchParameter(source, className,
				methodName, parameters);
		Integer noc = alreadyLoadedProofs.get(ksp);
		int numberOfContracts;
		if (noc == null) {
			numberOfContracts = new Prover()
					.getNumberOfContracts(source == null ? null : new File(
							source), classpath == null ? null : new File(
							classpath), className, methodName, parameters);
			alreadyLoadedProofs.put(ksp, numberOfContracts);
		} else {
			numberOfContracts = noc;
		}
		
		for (int num = 0; num < numberOfContracts; num++)
			result.add(new Job(code, 0, cleanEmpty(source),
					cleanEmpty(classpath), className, methodName,
					parameters, num));
		
		return result;
	}

	private String cleanEmpty(String s) {
		return s == null ? null : s.isEmpty() ? null : s;
	}

	private static final class KeYSearchParameter {

		private final String codeunit;
		private final String clazz;
		private final String method;
		private final String[] parameter;

		public KeYSearchParameter(String codeunit, String clazz, String method,
				String[] parameter) {
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
			result = prime * result
					+ ((codeunit == null) ? 0 : codeunit.hashCode());
			result = prime * result
					+ ((method == null) ? 0 : method.hashCode());
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
			KeYSearchParameter other = (KeYSearchParameter) obj;
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
