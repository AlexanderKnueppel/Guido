package de.tu.bs.guido.verification.system;

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

public abstract class BatchXMLHelper {


	public abstract ArrayList<IJob> generateJobFromXML(File xml) throws SAXException, IOException, ParserConfigurationException;

	protected abstract String[] getParameters(String parametersText);

	protected abstract List<IJob> getJobsForMethod(String code, String source, String classpath, String className,
			String methodName, String[] parameters, String sampleFile, String sampleType) throws IOException;

	protected  abstract String cleanEmpty(String s);

	protected  abstract String chooseSampleFile(String rootSampleFile, String codeSampleFile, String classSampleFile,
			String methodSampleFile);

	protected  abstract List<SettingsObject> getSampleForFile(String sampleFile, String type) throws IOException;

	
}
