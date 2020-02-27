
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.tubs.isf.guido.core.analysis.AnalysisCombinator;
import de.tubs.isf.guido.core.analysis.IAnalyzer;
import de.tubs.isf.guido.core.analysis.JMLContractAnalyzer;
import de.tubs.isf.guido.core.analysis.JavaSourceCodeAnalyzer;
import de.tubs.isf.guido.verification.systems.key.KeyDataBasisElement;

public class KeyDatabasisConverter {
	private static final String METHOD_CONST = "Method";
	private static final String NAME_CONST = "Name";
	private static final String FEATURE_SAMPLE = "FeatureSample";
	private static final String SPL_SAMPLE_FILE = "SPLSampleFile";
	private static final String PARAMETERS_CONST = "Parameters";
	private static final String CODE_CONST = "Code";

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		String kdPath = "C:\\Users\\Dede\\git\\Guido\\Plugins\\de.tubs.isf.guido.ui\\zwischenergebnisse.txt";
		String jobPath = "C:\\Users\\Dede\\eclipse-workspace\\Guido\\ExampleProject\\Job.xml";

		reworkList(readDBFromFile(new File(kdPath)), new File(jobPath));

	}

	public static String[] getParameters(String parametersText) {
		return parametersText.isEmpty() ? new String[0] : parametersText.split(",");
	}

	public static List<KeyDataBasisElement> reworkList(List<KeyDataBasisElement> kdb, File f)
			throws ParserConfigurationException, SAXException, IOException {

		for (KeyDataBasisElement kdbelement : kdb) {

			Pattern pattern = Pattern.compile("(.+)\\[(.+)\\](.+)(contract\\.)([0-9])+");
			Matcher matcher = pattern.matcher(kdbelement.getName());
			String[] holder = null;
			String[] para = null;
			String methodRes = null;
			if (matcher.find()) {

//			    System.out.println(matcher.group(2));
				holder = matcher.group(2).split("::");

				Pattern pattern2 = Pattern.compile("(.+)\\((.*)\\)");
				Matcher matcher2 = pattern2.matcher(holder[1]);
				if (matcher2.find()) {
					methodRes = matcher2.group(1);
					/*
					 * para keine Ahnug wie der Gesplittet wird in der Job datei
					 * 
					 * 
					 * 
					 */
					para = matcher2.group(2).split(",");

				}

			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(f);
			Element root = doc.getDocumentElement();
			root.getAttribute(SPL_SAMPLE_FILE);
			root.getAttribute(FEATURE_SAMPLE);
			String rootCode = root.getAttribute(CODE_CONST);
			NodeList codeunits = root.getElementsByTagName("Codeunit");
			for (int i = 0; i < codeunits.getLength(); i++) {
				Element codeunit = (Element) codeunits.item(i);
				codeunit.getAttribute("Source");
				String classpath = codeunit.getAttribute("Classpath");
				if (classpath.equals(""))
					classpath = "./../../Resources/JavaRedux";
				codeunit.getAttribute(SPL_SAMPLE_FILE);
				codeunit.getAttribute(FEATURE_SAMPLE);
				String classpathCode = rootCode;
				if (codeunit.hasAttribute(CODE_CONST))
					classpathCode = codeunit.getAttribute(CODE_CONST);
				NodeList clazzes = codeunit.getElementsByTagName("Class");
				for (int j = 0; j < clazzes.getLength(); j++) {
					Element clazz = (Element) clazzes.item(j);
					String name = clazz.getAttribute(NAME_CONST);
					clazz.getAttribute(SPL_SAMPLE_FILE);
					clazz.getAttribute(FEATURE_SAMPLE);
					String classCode = classpathCode;
					if (clazz.hasAttribute(CODE_CONST))
						classpathCode = clazz.getAttribute(CODE_CONST);
					NodeList methods = clazz.getElementsByTagName(METHOD_CONST);
					for (int k = 0; k < methods.getLength(); k++) {
						Element method = (Element) methods.item(k);
						String methodName = method.getAttribute(NAME_CONST);

						if (method.hasAttribute(CODE_CONST))
							classpathCode = method.getAttribute(CODE_CONST);

						boolean definesParameters = method.hasAttribute(PARAMETERS_CONST);
						String[] parameters = definesParameters ? getParameters(method.getAttribute(PARAMETERS_CONST))
								: null;

						if (holder != null)
							if (name.equals(holder[0]) && methodName.equals(methodRes)) {
								boolean test = true;
								if (parameters != null && para != null) {

									for (int l = 0; l < parameters.length && l < para.length; l++) {
										if (!para[l].equals(parameters[l])) {
											test = false;
										}
									}
								}
								if (test) {
									JavaSourceCodeAnalyzer jsca = new JavaSourceCodeAnalyzer(new File(classpath), name,
											methodName, parameters);
									jsca.setContractAnalyzer(new JMLContractAnalyzer());
									jsca.analyze();
									JMLContractAnalyzer ca = new JMLContractAnalyzer(jsca.getComment().toString());
									ca.analyze();
									kdbelement.getLanguageConstructs().clear();
									List<IAnalyzer.LanguageConstruct> result = AnalysisCombinator.and(jsca, ca);
									for (IAnalyzer.LanguageConstruct re : result) {
										kdbelement.getLanguageConstructs().add(re.toString());
									}

								}
							}

					}
				}

			}

		}

		return kdb;

	}

	public static List<KeyDataBasisElement> readDBFromFile(File f) {
		List<KeyDataBasisElement> kj = new ArrayList<KeyDataBasisElement>();

		f.getParentFile().mkdirs();
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			Gson gson = new GsonBuilder().create();
			while ((line = br.readLine()) != null) {
				kj.add(gson.fromJson(line, KeyDataBasisElement.class));

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return kj;
	}
}
