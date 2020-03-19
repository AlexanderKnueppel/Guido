package de.tubs.isf.guido.dsl.toJson;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import com.google.inject.Injector;
import de.tubs.isf.guido.dsl.DslStandaloneSetup;

public class XtextParser {

	/**
	 * Gets the DSL-Filepath and creates with StandaloneSetup and Injection
	 * @param dslFile
	 * @return Resource 
	 */
    public Resource setupParser(String dslFile) {
    	Injector injector = new DslStandaloneSetup().createInjectorAndDoEMFRegistration();
    	XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
    	resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
    	Resource resource = resourceSet.getResource(URI.createURI("file:" + dslFile), true);
    	return resource;
    } 
}
