package de.tu.bs.guido.key.simulator.generators;

import java.io.File;
import java.util.Arrays;

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

import de.tu.bs.masterthesis.key.simulator.options.Optionable;
import de.tu.bs.masterthesis.key.simulator.options.OptionableContainer;
import de.tu.bs.masterthesis.key.simulator.options.strategy.AutoInductionOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.BlockTreatmentOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.KeyStrategyOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.LoopTreatmentOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.MethodTreatmentOptions;
import de.tu.bs.masterthesis.key.simulator.options.taclets.AssertionsTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.InitialisationTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.IntRulesTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.IntegerSimplificationRulesTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.JavaCardTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.KeyTacletOptions;
import de.tu.bs.masterthesis.key.simulator.options.taclets.MergeGenerateIsWeakeningGoalTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.ModelFieldsTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.MoreSeqRulesTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.PermissionsTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.ProgramRulesTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.RuntimeExceptionsTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.WdChecksTaclet;
import de.tu.bs.masterthesis.key.simulator.options.taclets.WdOperatorTaclet;

public class FeatureIdeOpltionGenerator {

	private static final String RULE = "rule";
	private static final String VARIABLE = "var";
	private static final String OR = "disj";
	private static final String IMPLIES = "imp";

	public static void main(String[] args) throws ParserConfigurationException,
			TransformerException {
		Document doc = generateDoc();
		writeOut(doc);
	}

	private static Document generateDoc() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.newDocument();
		Element root = doc.createElement("featureModel");
		doc.appendChild(root);

		Element struct = doc.createElement("struct");
		root.appendChild(struct);
		Element constraints = doc.createElement("constraints");
		root.appendChild(constraints);
		appendCalculations(root, doc);
		appendElement(root, doc, "comments");
		appendFeatureOrder(root, doc);

		Element config = appendStructSub(struct, doc, "Config");
		Element taclet = appendStructSub(config, doc, "Taclet");
		Element searchStrategy = appendStructSub(config, doc, "SearchStrategy");
		
		Arrays.asList(KeyStrategyOptions.values()).forEach(
				kso -> appendConfigOptions(kso, searchStrategy, doc));
		Arrays.asList(KeyTacletOptions.values()).forEach(
				kso -> appendConfigOptions(kso, taclet, doc));
		
		forceImpliesViaConstraint(constraints, doc, IntRulesTaclet.JAVA_SEMANTICS, IntegerSimplificationRulesTaclet.FULL);

//		forceOptionViaConstraint(constraints, doc, OneStepSimplificationOptions.ENABLED);
		forceOptionViaConstraint(constraints, doc, MethodTreatmentOptions.CONTRACT);
		forceOptionViaConstraint(constraints, doc, BlockTreatmentOptions.EXTERNALCONTRACT);
		forceOptionOrViaConstraint(constraints, doc, LoopTreatmentOptions.INVARIANT, LoopTreatmentOptions.LOOP_SCOPE_INVARIANT);
		forceOptionViaConstraint(constraints, doc, AutoInductionOptions.OFF);

		forceOptionViaConstraint(constraints, doc, JavaCardTaclet.OFF);
//		forceOptionViaConstraint(constraints, doc, StringsTaclet.ON);
		forceOptionViaConstraint(constraints, doc, AssertionsTaclet.SAFE);
//		forceOptionViaConstraint(constraints, doc, BigIntTaclet.ON);
		forceOptionViaConstraint(constraints, doc, InitialisationTaclet.DISABLE_STATIC_INITIALISATION);
		forceOptionViaConstraint(constraints, doc, IntRulesTaclet.ARITHMETICS_SEMANTICS_IGNORING_OF);
//		forceOptionViaConstraint(constraints, doc, IntegerSimplificationRulesTaclet.FULL);
		forceOptionViaConstraint(constraints, doc, MergeGenerateIsWeakeningGoalTaclet.OFF);
		forceOptionViaConstraint(constraints, doc, ModelFieldsTaclet.TREAT_AS_AXIOM);
//		forceOptionViaConstraint(constraints, doc, MoreSeqRulesTaclet.OFF);
		forceOptionViaConstraint(constraints, doc, PermissionsTaclet.OFF);
		forceOptionViaConstraint(constraints, doc, ProgramRulesTaclet.JAVA);
//		forceOptionViaConstraint(constraints, doc, ReachTaclet.ON);
		forceOptionViaConstraint(constraints, doc, RuntimeExceptionsTaclet.BAN);
//		forceOptionViaConstraint(constraints, doc, SequencesTaclet.ON);
		forceOptionViaConstraint(constraints, doc, WdChecksTaclet.OFF);
		forceOptionViaConstraint(constraints, doc, WdOperatorTaclet.L);
		return doc;
	}

	private static String generateName(Optionable o) {
//		return generateName(o.getType().concat(o.getValue()));
		return FeatureIdeTranslator.encode(o.getOutputString().replaceAll(";", ""));
	}

	private static String generateName(String value) {
		String[] words = value.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String string : words) {
			char[] chars = string.toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			sb.append(chars);
		}
		return sb.toString().replaceAll(":", "_");
	}
	
	private static Element appendStructSub(Element struct, Document doc, String name){
		Element elem = doc.createElement("and");
		struct.appendChild(elem);

		elem.setAttribute("abstract", "true");
		elem.setAttribute("mandatory", "true");
		elem.setAttribute("name", name);
		return elem;
	}
	
	private static Element appendCalculations(Element struct, Document doc){
		Element elem = appendElement(struct, doc, "calculations");

		elem.setAttribute("Auto", "true");
		elem.setAttribute("Constraints", "true");
		elem.setAttribute("Features", "true");
		elem.setAttribute("Redundant", "true");
		elem.setAttribute("Tautology", "true");
		return elem;
	}

	private static Element appendFeatureOrder(Element struct, Document doc){
		Element elem = appendElement(struct, doc, "featureOrder");

		elem.setAttribute("userDefined", "false");
		return elem;
	}
	
	private static Element appendElement(Element struct, Document doc, String name){
		Element root = doc.createElement(name);
		struct.appendChild(root);
		return root;
	}
	
	private static void appendConfigOptions(OptionableContainer opc,
			Element option, Document doc) {
		String optionName = generateName(opc.getValue());
		Element elem = doc.createElement("alt");
		option.appendChild(elem);

		elem.setAttribute("abstract", "true");
		elem.setAttribute("mandatory", "true");
		elem.setAttribute("name", optionName);
		
		Arrays.asList(opc.getOptions()).forEach(
				value -> appendConfigChildren(elem, doc, generateName(value)));
		
	}

	private static void appendConfigChildren(Element option, Document doc, String name) {		
		Element feature = doc.createElement("feature");
		option.appendChild(feature);

		feature.setAttribute("mandatory", "true");
		feature.setAttribute("name", name);

	}

	private static void forceOptionViaConstraint(Element parent, Document doc, Optionable option) {
		Element rule = doc.createElement(RULE);
		Element var = doc.createElement(VARIABLE);
		var.setTextContent(generateName(option));
		rule.appendChild(var);
		parent.appendChild(rule);
	}

	private static void forceImpliesViaConstraint(Element parent, Document doc, Optionable optionOne, Optionable optionTwo) {
		Element rule = doc.createElement(RULE);
		Element imp = doc.createElement(IMPLIES);
		Element var1 = doc.createElement(VARIABLE);
		Element var2 = doc.createElement(VARIABLE);
		var1.setTextContent(generateName(optionOne));
		var2.setTextContent(generateName(optionTwo));
		imp.appendChild(var1);
		imp.appendChild(var2);
		rule.appendChild(imp);
		parent.appendChild(rule);
	}

	private static void forceOptionOrViaConstraint(Element parent, Document doc, Optionable... options) {
		Element rule = doc.createElement(RULE);
		Element or = doc.createElement(OR);
		for (Optionable optionable : options) {
			Element var = doc.createElement(VARIABLE);
			var.setTextContent(generateName(optionable));
			or.appendChild(var);
		}
		rule.appendChild(or);
		parent.appendChild(rule);
	}

	private static void writeOut(Document doc) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
		DOMSource source = new DOMSource(doc);
		File f = new File("file.xml");
		f.delete();
		StreamResult result = new StreamResult(f);

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	}

}
