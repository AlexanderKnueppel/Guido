package de.tu.bs.guido.verification.systems.key.generators;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.tu.bs.guido.verification.systems.key.options.Optionable;
import de.tu.bs.guido.verification.systems.key.options.OptionableContainer;
import de.tu.bs.guido.verification.systems.key.options.strategies.AutoInductionOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.BlockTreatmentOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.KeyStrategyOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.LoopTreatmentOptions;
import de.tu.bs.guido.verification.systems.key.options.strategies.MethodTreatmentOptions;
import de.tu.bs.guido.verification.systems.key.options.taclets.AssertionsTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.InitialisationTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.IntRulesTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.IntegerSimplificationRulesTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.JavaCardTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.KeyTacletOptions;
import de.tu.bs.guido.verification.systems.key.options.taclets.MergeGenerateIsWeakeningGoalTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.ModelFieldsTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.PermissionsTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.ProgramRulesTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.RuntimeExceptionsTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.WdChecksTaclet;
import de.tu.bs.guido.verification.systems.key.options.taclets.WdOperatorTaclet;

public class SPL2OpltionGenerator {

	private static final String OR = " | ";
	private static final String IMPLIES = " => ";

	public static void main(String[] args) throws ParserConfigurationException,
			TransformerException {
		Document doc = generateDoc();
		writeOut(doc);
	}

	private static Document generateDoc() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.newDocument();
		Element root = doc.createElement("vm");
		root.setAttribute("name", "KeY");
		doc.appendChild(root);

		Element binaryOptions = doc.createElement("binaryOptions");
		root.appendChild(binaryOptions);
		Element numericOptions = doc.createElement("numericOptions");
		root.appendChild(numericOptions);
		Element booleanConstraints = doc.createElement("booleanConstraints");
		root.appendChild(booleanConstraints);
		Element nonBooleanConstraints = doc
				.createElement("nonBooleanConstraints");
		root.appendChild(nonBooleanConstraints);

		appendDefaultConfig(binaryOptions, doc);
//		Arrays.asList(KeyStrategyOptions.values()).forEach(
//				kso -> appendConfigOptions(kso, binaryOptions, doc));
////		Arrays.asList(KeyTacletOptions.values()).forEach(
////				kso -> appendConfigOptions(kso, binaryOptions, doc));
//
//		Arrays.asList(KeyStrategyOptions.values()).forEach(
//				kso -> appendConstraint(kso, booleanConstraints, doc));
////		Arrays.asList(KeyTacletOptions.values()).forEach(
////				kso -> appendConstraint(kso, booleanConstraints, doc));
//
////		forceOptionViaConstraint(JavaCardTaclet.OFF, booleanConstraints, doc);
		Arrays.asList(KeyStrategyOptions.values()).forEach(
				kso -> appendConfigOptions(kso, binaryOptions, doc));
		Arrays.asList(KeyTacletOptions.values()).forEach(
				kso -> appendConfigOptions(kso, binaryOptions, doc));
		
		forceImpliesViaConstraint(booleanConstraints, doc, IntRulesTaclet.JAVA_SEMANTICS, IntegerSimplificationRulesTaclet.FULL);

//		forceOptionViaConstraint(constraints, doc, OneStepSimplificationOptions.ENABLED);
		forceOptionViaConstraint(booleanConstraints, doc, MethodTreatmentOptions.CONTRACT);
		forceOptionViaConstraint(booleanConstraints, doc, BlockTreatmentOptions.EXTERNALCONTRACT);
		forceOrViaConstraint(booleanConstraints, doc, LoopTreatmentOptions.INVARIANT, LoopTreatmentOptions.LOOP_SCOPE_INVARIANT);
		forceOptionViaConstraint(booleanConstraints, doc, AutoInductionOptions.OFF);

		forceOptionViaConstraint(booleanConstraints, doc, JavaCardTaclet.OFF);
//		forceOptionViaConstraint(booleanConstraints, doc, StringsTaclet.ON);
		forceOptionViaConstraint(booleanConstraints, doc, AssertionsTaclet.SAFE);
//		forceOptionViaConstraint(booleanConstraints, doc, BigIntTaclet.ON);
		forceOptionViaConstraint(booleanConstraints, doc, InitialisationTaclet.DISABLE_STATIC_INITIALISATION);
//		forceOptionViaConstraint(booleanConstraints, doc, IntRulesTaclet.ARITHMETICS_SEMANTICS_IGNORING_OF);
//		forceOptionViaConstraint(booleanConstraints, doc, IntegerSimplificationRulesTaclet.FULL);
		forceOptionViaConstraint(booleanConstraints, doc, MergeGenerateIsWeakeningGoalTaclet.OFF);
		forceOptionViaConstraint(booleanConstraints, doc, ModelFieldsTaclet.TREAT_AS_AXIOM);
//		forceOptionViaConstraint(booleanConstraints, doc, MoreSeqRulesTaclet.OFF);
		forceOptionViaConstraint(booleanConstraints, doc, PermissionsTaclet.OFF);
		forceOptionViaConstraint(booleanConstraints, doc, ProgramRulesTaclet.JAVA);
//		forceOptionViaConstraint(booleanConstraints, doc, ReachTaclet.ON);
		forceOptionViaConstraint(booleanConstraints, doc, RuntimeExceptionsTaclet.BAN);
//		forceOptionViaConstraint(booleanConstraints, doc, SequencesTaclet.ON);
		forceOptionViaConstraint(booleanConstraints, doc, WdChecksTaclet.OFF);
		forceOptionViaConstraint(booleanConstraints, doc, WdOperatorTaclet.L);
		return doc;
	}

	private static String generateName(Optionable o) {
//		return generateName(o.getType().concat(o.getValue()));
		return FeatureIdeTranslator.encode(o.getOutputString().replaceAll(";", ""));
	}

	private static String generateName(OptionableContainer oc) {
		return FeatureIdeTranslator.encode(oc.getValue().replaceAll(";", ""));
	}

	@SuppressWarnings("unused")
	private static String generateName(String value) {
		String[] words = value.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String string : words) {
			char[] chars = string.toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			sb.append(chars);
		}
		return sb.toString();
	}

	private static void appendDefaultConfig(Element option, Document doc) {
		List<String> children = new ArrayList<>();
		Arrays.asList(KeyStrategyOptions.values()).forEach(
				value -> Arrays.asList(value.getOptions()).forEach(a -> children.add(generateName(a))));
		Arrays.asList(KeyTacletOptions.values()).forEach(
				value -> Arrays.asList(value.getOptions()).forEach(a -> children.add(generateName(a))));

		appendConfig(option, doc, "root", null, null, null, null, children,
				null, null, "Selected", false);
	}

	private static void appendConfigOptions(OptionableContainer opc,
			Element option, Document doc) {
		List<String> children = new ArrayList<>();
		Arrays.asList(opc.getOptions()).forEach(
				value -> children.add(generateName(value)));
//		String optionName = generateName(opc);
//		appendConfig(option, doc, optionName, null, null, null, "root",
//				children, null, null, "Selected", false);
		Arrays.asList(opc.getOptions()).forEach(
				so -> {
					List<Optionable> options = new ArrayList<>(Arrays
							.asList(opc.getOptions()));
					options.remove(so);
					List<String> excludes = new ArrayList<>();
					options.forEach(optionable -> excludes
							.add(generateName(optionable)));
					String subOptionName = generateName(so);
					String outputString = null;
					appendConfig(option, doc, subOptionName, outputString,
							null, null, "root", null, null, excludes,
							"Selected", true);
				});
	}

	private static void appendConfig(Element option, Document doc, String name,
			String outputString, String prefix, String postfix, String parent,
			List<String> children, List<String> impliedOptions,
			List<String> excludedOptions, String defaultValue, boolean optional) {
		Element elem = doc.createElement("configurationOption");
		option.appendChild(elem);

		appendConfigElement(elem, doc, "name", name);
		appendConfigElement(elem, doc, "outputString", outputString);
		appendConfigElement(elem, doc, "prefix", prefix);
		appendConfigElement(elem, doc, "postfix", postfix);
		appendConfigElement(elem, doc, "parent", parent);
		appendConfigList(elem, doc, "children", "option", children);
		appendConfigList(elem, doc, "impliedOptions", "option", impliedOptions);
		appendConfigList(elem, doc, "excludedOptions", "options",
				excludedOptions);
		appendConfigElement(elem, doc, "defaultValue", defaultValue);
		appendConfigElement(elem, doc, "optional", optional == false ? "False"
				: "True");

	}

	private static void appendConfigElement(Element parent, Document doc,
			String name, String value) {
		Element element = doc.createElement(name);
		element.setTextContent(value == null ? "" : value);
		parent.appendChild(element);
	}

	private static void appendConfigList(Element parent, Document doc,
			String name, String optionName, List<String> values) {
		Element element = doc.createElement(name);
		if (values != null) {
			values.forEach(value -> {
				Element child = doc.createElement(optionName);
				child.setTextContent(value);
				element.appendChild(child);
			});
		}
		parent.appendChild(element);
	}

	@SuppressWarnings("unused")
	private static void appendConstraint(OptionableContainer opc,
			Element parent, Document doc) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Optionable value : opc.getOptions()) {
			sb.append((first ? "" : OR) + generateName(value));
			first = false;
		}
		Element e = doc.createElement("constraint");
		e.setTextContent(sb.toString());
		parent.appendChild(e);
	}

	private static void forceOptionViaConstraint(Element parent, Document doc, Optionable option) {
		Element e = doc.createElement("constraint");
		e.setTextContent(generateName(option));
		parent.appendChild(e);
	}

	private static void forceOrViaConstraint(Element parent, Document doc, Optionable option1, Optionable option2) {
		Element e = doc.createElement("constraint");
		e.setTextContent(generateName(option1) + OR + generateName(option2));
		parent.appendChild(e);
	}

	private static void forceImpliesViaConstraint(Element parent, Document doc, Optionable option1, Optionable option2) {
		Element e = doc.createElement("constraint");
		e.setTextContent(generateName(option1) + IMPLIES + generateName(option2));
		parent.appendChild(e);
	}

	private static void writeOut(Document doc) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
		DOMSource source = new DOMSource(doc);
		File f = new File("splFile.xml");
		f.delete();
		StreamResult result = new StreamResult(f);

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	}

}
