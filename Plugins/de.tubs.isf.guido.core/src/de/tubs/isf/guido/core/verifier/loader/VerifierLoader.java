package de.tubs.isf.guido.core.verifier.loader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;

public class VerifierLoader {

	private static List<VerifierDescription<Object>> VerifierList = new ArrayList<>();

	private static List<VerifierDescription<String>> registerClasses() {
		List<VerifierDescription<String>> verifiers = new ArrayList<>();
		try {
			Enumeration<URL> urls = ClassLoader.getSystemResources("info_verifier.xml");
			
			if(!urls.hasMoreElements()) {
				System.out.println("No verification systems found! Add systems to the class path!");
			}
			
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(url.openStream());

				String name = "";
				String desc = "";
				String handle = "";

				// Get name
				NodeList nameNode = doc.getElementsByTagName("name");
				name = nameNode.item(0).getTextContent();

				// Get Desc.
				NodeList descNode = doc.getElementsByTagName("description");
				desc = descNode.item(0).getTextContent();

				// Get Handle.
				NodeList handleNode = doc.getElementsByTagName("handle");
				handle = handleNode.item(0).getTextContent();

				// Get interface(s)

				/** Get all interfaces inside JAR **/
				NodeList nlInterfaces = doc.getElementsByTagName("interface");

				if (nlInterfaces.getLength() < 1) {
					throw new RuntimeException("No interfaces specified...");
				}

				if (nlInterfaces.getLength() > 1) {
					throw new RuntimeException("Too many interfaces specified...");
				}

				Node interfaceNode = nlInterfaces.item(0);
				if (interfaceNode.getNodeType() == Node.ELEMENT_NODE) {
					String interfaceName = ((Element) interfaceNode).getAttribute("id");
					/** Get implementing class **/

					NodeList nlClasses = interfaceNode.getChildNodes();
					String clazz = "";
					for (int j = 0; j < nlClasses.getLength(); j++) {
						Node classNode = nlClasses.item(j);
						if (classNode.getNodeType() == Node.ELEMENT_NODE) {
							Element e = (Element) classNode;
							clazz = classNode.getTextContent();
						}
					}

					/** Update map at key **/
					verifiers.add(new VerifierDescription<String>(clazz, name, handle, desc));
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return verifiers;
	}

	static {
		/** Get information about all classes **/
		List<VerifierDescription<String>> classes = registerClasses();
		for (VerifierDescription<String> clazz : classes) {
			// List<Object> classInstanceList = new ArrayList<>();
			ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

//			URL[] urls = ((URLClassLoader)systemClassLoader).getURLs();
//
//	        for(URL url: urls){
//	        	System.out.println(url.getFile());
//	        }

			try {
				Class<?> loadClass = systemClassLoader.loadClass(clazz.getVerifier());
				VerifierList.add(new VerifierDescription<>(loadClass.newInstance(), clazz.getName(), clazz.getHandle(),
						clazz.getDescription()));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Loads classes implementing the given interface
	 * 
	 * @param cl Class implementing the interface <b>T</b>
	 * @return List
	 */
	public static <T> List<VerifierDescription<T>> load(Class<T> cl) {
		final List<VerifierDescription<T>> list = new ArrayList<>();
		for (VerifierDescription<Object> desc : VerifierList) {
			list.add(new VerifierDescription<T>(cl.cast(desc.getVerifier()), desc.getName(), desc.getHandle(),
					desc.getDescription()));
		}
		return list;
	}

	public static void main(String[] args) {
		List<VerifierDescription<AVerificationSystemFactory>> systems = VerifierLoader
				.load(AVerificationSystemFactory.class);

		for (VerifierDescription<AVerificationSystemFactory> system : systems) {
			Map<String, String> x = system.getVerifier().createSettingsObject().getSettingsMap();

			System.out.println("Name: " + system.getName());
			System.out.println("Handle: " + system.getHandle());
			System.out.println("Desc: " + system.getDescription());
			System.out.println("Ref: " + system.getVerifier());

		}
	}
}