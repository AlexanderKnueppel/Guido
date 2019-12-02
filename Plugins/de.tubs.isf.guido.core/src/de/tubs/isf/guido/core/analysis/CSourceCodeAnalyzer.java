package de.tubs.isf.guido.core.analysis;

import java.io.File;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;

public class CSourceCodeAnalyzer extends ASourceCodeAnalyzer {

	public CSourceCodeAnalyzer(File f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<LanguageConstruct> analyze() {
		List<LanguageConstruct> lcs = null;
		//see https://github.com/ricardojlrufino/eclipse-cdt-standalone-astparser/blob/master/src/main/java/ParserExample.java
		//IASTTranslationUnit translationUnit = GPPLanguage.getDefault().getASTTranslationUnit(fileContent, info, emptyIncludes, null, opts, log);
		
		hasLoops(lcs);
		hasBranching(lcs);
		sloc(lcs);
		hasReturn(lcs);
		hasMethodCalls(lcs);
		
		return lcs;
	}

	private void hasMethodCalls(List<LanguageConstruct> lcs) {
		// TODO Auto-generated method stub
		
	}

	private void hasReturn(List<LanguageConstruct> lcs) {
		// TODO Auto-generated method stub
		
	}

	private void sloc(List<LanguageConstruct> lcs) {
		// TODO Auto-generated method stub
		
	}

	private void hasBranching(List<LanguageConstruct> lcs) {
		// TODO Auto-generated method stub
		
	}

	private void hasLoops(List<LanguageConstruct> lcs) {
		// TODO Auto-generated method stub
		
	}

}
