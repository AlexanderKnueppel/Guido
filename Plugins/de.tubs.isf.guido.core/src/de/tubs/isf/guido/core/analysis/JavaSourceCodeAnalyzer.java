package de.tubs.isf.guido.core.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.utils.SourceRoot;

import de.tubs.isf.guido.core.analysis.AContractAnalyzer.ContractConstruct;

public class JavaSourceCodeAnalyzer extends ASourceCodeAnalyzer {

	private String clazz;
	private String pack;
	private String method;
	private String[] arguments;
	private BodyDeclaration<?> methodBody;
	private CompilationUnit cu;
	private List<CompilationUnit> cus;
	private SourceRoot sourceRoot;
	private AContractAnalyzer ca;

	// Java specific (OOP)
	private int nArguments;

	public static void main(String[] args) {

		JavaSourceCodeAnalyzer jsca = new JavaSourceCodeAnalyzer(new File("./testData"),
				"Examples_from_Chapter_16.Sort", "max", new String[] { "int" });
		jsca.setContractAnalyzer(new JMLContractAnalyzer());
		jsca.analyze();
		System.out.println(jsca);

		JMLContractAnalyzer ca = new JMLContractAnalyzer(jsca.getComment().toString());
		ca.analyze();
		System.out.println(ca);

		jsca = new JavaSourceCodeAnalyzer(new File("./testData/Examples_from_Chapter_16"),
				"Examples_from_Chapter_16.Sort", "sort", new String[] {});
		jsca.analyze();
		System.out.println(jsca);
//
//		ca = new JMLContractAnalyzer(jsca.getComment().toString());
//		ca.analyze();
//		System.out.println(ca);
//
//		System.out.println(AnalysisCombinator.and(jsca, ca));
	}

	public JavaSourceCodeAnalyzer(File baseDir, String clazz, String method, String[] arguments) {
		super(baseDir);
		this.clazz = getClassName(clazz);
		this.pack = getPackageName(clazz);
		this.arguments = arguments != null ? arguments : new String[] {};
		this.method = method;
		this.ca = null;

		// this.cu = generateCompilationUnit(sourceFile);
		this.cus = generateCompilationUnits(sourceFile);
		this.methodBody = cu != null ? getMethod(cu.getClassByName(this.clazz).get()) : null;

		currentConstructs = new ArrayList<LanguageConstruct>();
	}

	@Override
	public List<LanguageConstruct> analyze() {
		List<LanguageConstruct> lcs = null;
		if (methodBody != null) {
			// Get lines of code
			this.loc = methodBody.getEnd().get().line - methodBody.getBegin().get().line + 1;
			// Get number of arguments of respective method
			if (methodBody.isMethodDeclaration()) {
				this.nArguments = methodBody.asMethodDeclaration().getParameters().size();
			} else if (methodBody.isConstructorDeclaration()) {
				this.nArguments = methodBody.asConstructorDeclaration().getParameters().size();
			} else {
				this.nArguments = arguments.length;
			}

			lcs = new ArrayList<LanguageConstruct>();
			hasLoops(lcs);
			hasBranching(lcs);
			sloc(lcs);
			hasReturn(lcs);
			hasMethodCalls(lcs);
			calledMethodHasContract(lcs);
			hasArguments(lcs);
			hasLoopInvariants(lcs);

			currentConstructs = lcs;
		}
		return lcs;
	}

	public void setContractAnalyzer(AContractAnalyzer ca) {
		this.ca = ca;
	}

	private void hasArguments(List<LanguageConstruct> lcs) {
		if (nArguments > 0)
			lcs.add(SourceCodeConstruct.ARGUMENTS_TRUE);
		else
			lcs.add(SourceCodeConstruct.ARGUMENTS_FALSE);
	}

	private void hasLoops(List<LanguageConstruct> lcs) {
		if (methodBody.findFirst(ForStmt.class).isPresent() || methodBody.findFirst(ForEachStmt.class).isPresent()
				|| methodBody.findFirst(WhileStmt.class).isPresent()) {
			lcs.add(SourceCodeConstruct.LOOPS_TRUE);
		} else {
			lcs.add(SourceCodeConstruct.LOOPS_FALSE);
		}
	}

	private void hasLoopInvariants(List<LanguageConstruct> lcs) {
		if (ca == null)
			return;

		methodBody.findAll(Statement.class).forEach(st -> {
			if (st instanceof ForStmt || st instanceof ForEachStmt || st instanceof WhileStmt) {
				if (st.getComment() != null) {
					ca.setContract(st.getComment().toString());
					ca.analyze();
					if (ca.hasLanguageConstruct(ContractConstruct.IS_LOOP_INVARIANT)
							&& !lcs.contains(SourceCodeConstruct.HAS_LOOP_INVARIANTS_TRUE))
						lcs.add(SourceCodeConstruct.HAS_LOOP_INVARIANTS_TRUE);
				}
			}
		});
		if (!lcs.contains(SourceCodeConstruct.HAS_LOOP_INVARIANTS_TRUE))
			lcs.add(SourceCodeConstruct.HAS_LOOP_INVARIANTS_FALSE);
	}

	private void hasBranching(List<LanguageConstruct> lcs) {
		if (methodBody.findFirst(IfStmt.class).isPresent() || methodBody.findFirst(SwitchStmt.class).isPresent()) {
			lcs.add(SourceCodeConstruct.BRANCHING_TRUE);
		} else {
			lcs.add(SourceCodeConstruct.BRANCHING_FALSE);
		}
	}

	private void sloc(List<LanguageConstruct> lcs) {
		if (methodBody.getEnd().get().line - methodBody.getBegin().get().line
				+ 1 >= (int) SourceCodeConstruct.GEQ_SLOC.getParameters().get(0)) {
			lcs.add(SourceCodeConstruct.GEQ_SLOC);
		}
		if (methodBody.getEnd().get().line - methodBody.getBegin().get().line
				+ 1 < (int) SourceCodeConstruct.LT_SLOC.getParameters().get(0)) {
			lcs.add(SourceCodeConstruct.LT_SLOC);
		}
	}

	private void hasReturn(List<LanguageConstruct> lcs) {
		if (methodBody.findFirst(ReturnStmt.class).isPresent()) {
			lcs.add(SourceCodeConstruct.HAS_RETURN);
		}
	}

	private void hasMethodCalls(List<LanguageConstruct> lcs) {
		if (methodBody.findFirst(MethodCallExpr.class).isPresent()) {
			lcs.add(SourceCodeConstruct.METHOD_CALLS_TRUE);
		} else {
			lcs.add(SourceCodeConstruct.METHOD_CALLS_FALSE);
		}
	}

	private void calledMethodHasContract(List<LanguageConstruct> lcs) {
		if (ca == null)
			return;

		for (MethodCallExpr expr : methodBody.findAll(MethodCallExpr.class)) {
			String method = expr.getNameAsString();

			// for (CompilationUnit cu : cus) {}

			// TODO: not implemented,... resolving method calls fails in Javaparser... could
			// be a problem with settings
			// Needs to be resolved asap, as this is a very important characteristic
			if (expr.getComment() == null)
				break;

			ca.setContract(expr.getComment().toString());

			if (ca.valid()) {
				lcs.add(SourceCodeConstruct.METHOD_CALLS_SPEC_TRUE);
				return;
			}
		}

		lcs.add(SourceCodeConstruct.METHOD_CALLS_SPEC_FALSE);
		// Currently result is always SourceCodeConstruct.METHOD_CALLS_SPEC_FALSE...
	}

	@Override
	public String toString() {
		String str = "[Result of last analysis]\n";
		if (currentConstructs.isEmpty()) {
			return str + "None";
		}

		str += "Method: " + pack + "." + clazz + "." + method + "(";
		for (String arg : arguments)
			str += arg + ", ";
		if (arguments != null && arguments.length > 0)
			str = str.substring(0, str.length() - 2);
		str += ")\n";

		str += "Source Code Constructs: [";
		for (LanguageConstruct lc : currentConstructs)
			str += lc.toString() + ", ";
		if (currentConstructs != null && !currentConstructs.isEmpty())
			str = str.substring(0, str.length() - 2);
		str += "]\n";

		return str;
	}

	public Comment getComment() {
		if (methodBody != null && methodBody.getComment().isPresent()) {
			return methodBody.getComment().get();
		}
		return null;
	}
	
	public String getCommentString() {
		return getComment().toString();
	}

	public int getNumberOfArguments() {
		return nArguments;
	}

	private String getClassName(String clazz) {
		return clazz.substring(clazz.lastIndexOf('.') + 1, clazz.length());
	}

	private String getPackageName(String clazz) {
		return clazz.substring(0, clazz.lastIndexOf('.'));
	}

	private boolean hasCorrectClass(CompilationUnit comp) {
		return comp.getClassByName(this.clazz).isPresent();
	}

	private boolean hasCorrectPackage(CompilationUnit comp) {
		return comp.getPackageDeclaration().get().getNameAsString().equals(this.pack);
	}

	private BodyDeclaration<?> getMethod(ClassOrInterfaceDeclaration classDeclaration) {
		return getMethod(classDeclaration, this.method, this.arguments);
	}

	private BodyDeclaration<?> getMethod(ClassOrInterfaceDeclaration classDeclaration, String method,
			String[] arguments) {
		List<MethodDeclaration> methodList = classDeclaration.getMethodsBySignature(method, arguments);
		if (!methodList.isEmpty()) {
			return methodList.get(0);
		} else if (classDeclaration.getConstructorByParameterTypes(arguments).isPresent()) {
			return classDeclaration.getConstructorByParameterTypes(arguments).get();
		} else {
			methodList = classDeclaration.getMethodsByName(method);
			for (MethodDeclaration m : methodList) {
				if (m.getNameAsString().equals(method) && methodList.size() == 1) {
					System.out
							.println("Arguments are wrong, but only one method exists with this name - we'll take it");
					return m;
				}
			}

			System.out.println(this.method + ": Method not found");
			return null;
		}
	}

	private TypeSolver getTypeSolver(File dir) throws IOException {
		CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
		combinedTypeSolver.add(new ReflectionTypeSolver(false));
		return combinedTypeSolver;
	}

	private List<CompilationUnit> generateCompilationUnits(File code) {
		SourceRoot sourceRoot = null;
		try {
			sourceRoot = new SourceRoot(code.toPath(),
					new ParserConfiguration().setSymbolResolver(new JavaSymbolSolver(getTypeSolver(code))));
			sourceRoot.tryToParse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();
		for (CompilationUnit cu : compilationUnits) {
			if (hasCorrectPackage(cu) && hasCorrectClass(cu)) {
				this.cu = cu;
				return sourceRoot.getCompilationUnits();
			}
		}
		System.out.println("Desired class or package not found");
		return null;
	}
}
