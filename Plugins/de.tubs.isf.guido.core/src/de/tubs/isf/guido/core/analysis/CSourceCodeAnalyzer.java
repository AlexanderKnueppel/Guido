package de.tubs.isf.guido.core.analysis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.IASTConditionalExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTVisibilityLabel;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.core.runtime.CoreException;

/**
 * Analyzer for C/(C++?) code using CDT. see
 * https://github.com/ricardojlrufino/eclipse-cdt-standalone-astparser/blob/master/src/main/java/ParserExample.java
 * and
 * https://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.cdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fcdt%2Fcore%2Fdom%2Fast%2FASTVisitor.html
 * 
 * @author User
 *
 */
public class CSourceCodeAnalyzer extends ASourceCodeAnalyzer {

	public CSourceCodeAnalyzer(File f) {
		super(f);

	}
	
	public static void main(String[] args) {
		CSourceCodeAnalyzer csca = new CSourceCodeAnalyzer(new File("./testData/analyses/array_pattern.c"));
		System.out.println(csca.analyze());
	}

	@Override
	public List<LanguageConstruct> analyze() {
		List<LanguageConstruct> lcs = new ArrayList<LanguageConstruct>();
		FileContent fileContent = FileContent.createForExternalFileLocation(sourceFile.getPath());

		Map<String, String> definedSymbols = new HashMap<String, String>();
		String[] includePaths = new String[0];
		IScannerInfo info = new ScannerInfo(definedSymbols, includePaths);
		IParserLogService log = new DefaultLogService();
		IncludeFileContentProvider emptyIncludes = IncludeFileContentProvider.getEmptyFilesProvider();

		int opts = 8;

		IASTTranslationUnit translationUnit = null;
		try {
			translationUnit = GPPLanguage.getDefault().getASTTranslationUnit(fileContent, info, emptyIncludes, null,
					opts, log);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Includes:
//		IASTPreprocessorIncludeStatement[] includes = translationUnit.getIncludeDirectives();
//		for (IASTPreprocessorIncludeStatement include : includes) {
//			System.out.println("include - " + include.getName());
//		}

		ASTVisitor visitor = new ASTVisitor() {
			public int visit(IASTName name) {
				if ((name.getParent() instanceof CPPASTFunctionDeclarator)) {
					System.out.println("IASTName: " + name.getClass().getSimpleName() + "(" + name.getRawSignature()
							+ ") - > parent: " + name.getParent().getClass().getSimpleName());
					System.out.println("-- isVisible: " + CSourceCodeAnalyzer.isVisible(name));
				}

				return 3;
			}

			public int visit(IASTStatement statement) {
				if (statement instanceof IASTDoStatement || statement instanceof IASTForStatement
						|| statement instanceof IASTWhileStatement) {
					lcs.add(SourceCodeConstruct.LOOPS_TRUE);
				} else if (statement instanceof IASTIfStatement || statement instanceof IASTSwitchStatement) {
					lcs.add(SourceCodeConstruct.BRANCHING_TRUE);
				} else if (statement instanceof IASTReturnStatement) {
					lcs.add(SourceCodeConstruct.HAS_RETURN);
				}

				return 3;
			}

			public int visit(IASTDeclarator declarator) {
				if (declarator instanceof IASTArrayDeclarator) {
					lcs.add(SourceCodeConstruct.ARRAYS_TRUE);
				}

				return 3;
			}

			public int visit(IASTArrayModifier modifier) {
				lcs.add(SourceCodeConstruct.ARRAYS_TRUE);

				return 3;
			}

			public int visit(IASTExpression expression) {
				if (expression instanceof IASTConditionalExpression) {
					lcs.add(SourceCodeConstruct.BRANCHING_TRUE);
				} else if (expression instanceof IASTFunctionCallExpression) {
					lcs.add(SourceCodeConstruct.METHOD_CALLS_TRUE);
				}

				return 3;
			}
		};

		visitor.shouldVisitNames = true;
		visitor.shouldVisitDeclarations = true;
		visitor.shouldVisitDeclarators = true;
		visitor.shouldVisitAttributes = true;
		visitor.shouldVisitStatements = true;
		visitor.shouldVisitTypeIds = false;

		translationUnit.accept(visitor);
		complement(lcs);

		// remove duplicates
		return lcs.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
	}
	
	private void complement(List<LanguageConstruct> lcs) {
		if(!lcs.contains(SourceCodeConstruct.METHOD_CALLS_TRUE)) {
			lcs.add(SourceCodeConstruct.METHOD_CALLS_FALSE);
		}
		if(!lcs.contains(SourceCodeConstruct.LOOPS_TRUE)) {
			lcs.add(SourceCodeConstruct.LOOPS_FALSE);
		}
		if(!lcs.contains(SourceCodeConstruct.BRANCHING_TRUE)) {
			lcs.add(SourceCodeConstruct.BRANCHING_FALSE);
		}
	}

	public static boolean isVisible(IASTNode current) {
		IASTNode declator = current.getParent().getParent();
		IASTNode[] children = declator.getChildren();

		for (IASTNode iastNode : children) {
			if ((iastNode instanceof ICPPASTVisibilityLabel)) {
				return 1 == ((ICPPASTVisibilityLabel) iastNode).getVisibility();
			}
		}

		return false;
	}
}
