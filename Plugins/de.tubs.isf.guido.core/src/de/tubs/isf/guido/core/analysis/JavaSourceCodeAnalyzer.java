package de.tubs.isf.guido.core.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

public class JavaSourceCodeAnalyzer extends ASourceCodeAnalyzer {

	private String clazz;
	private String pack;
	private String method;
	private String[] arguments;
	private BodyDeclaration<?> methodBody;
	private CompilationUnit cu;
	
	// Java specific (OOP)
	private int nArguments;
	
	public static void main(String[] args) {
		JavaSourceCodeAnalyzer jsca = new JavaSourceCodeAnalyzer(new File("./testData"), "Examples_from_Chapter_16.Sort", "max", new String[] {"int"});
		jsca.analyze();
		System.out.println(jsca);
		
		JMLContractAnalyzer ca = new JMLContractAnalyzer(jsca.getComment().toString());
		ca.analyze();
		System.out.println(ca);
		
		jsca = new JavaSourceCodeAnalyzer(new File("./testData/Sort.java"), "Examples_from_Chapter_16.Sort", "sort", new String[] {});
		jsca.analyze();
		System.out.println(jsca);
		
		ca = new JMLContractAnalyzer(jsca.getComment().toString());
		ca.analyze();
		System.out.println(ca);
		
		System.out.println(AnalysisCombinator.and(jsca,ca));
	}
	
	public JavaSourceCodeAnalyzer(File f, String clazz, String method, String[] arguments) {
		super(f);
		this.clazz = getClassName(clazz);
		this.pack = getPackageName(clazz);
		this.arguments = arguments != null ? arguments : new String[]{};
		this.method = method;
		
		this.cu = generateCompilationUnit(sourceFile);
		this.methodBody = cu != null ? getMethod(cu.getClassByName(this.clazz).get()) : null;
		
		currentConstructs = new ArrayList<LanguageConstruct>();
	}

	@Override
	public List<LanguageConstruct> analyze() {
		List<LanguageConstruct> lcs = null;
		if(methodBody != null) {
			// Get lines of code
			this.loc = methodBody.getEnd().get().line - methodBody.getBegin().get().line + 1; 
			// Get number of arguments of respective method
			if(methodBody.isMethodDeclaration()){
				this.nArguments = methodBody.asMethodDeclaration().getParameters().size();
			}
			else if(methodBody.isConstructorDeclaration()){
				this.nArguments = methodBody.asConstructorDeclaration().getParameters().size();
			}
			else {
				this.nArguments = arguments.length;
			}

			lcs =  new ArrayList<LanguageConstruct>();
			hasLoops(lcs);
			hasBranching(lcs);
			sloc(lcs);
			hasReturn(lcs);
			hasMethodCalls(lcs);
			calledMethodHasContract(lcs);
			
			currentConstructs = lcs;
		}
		return lcs;
	}
	
	private void hasLoops(List<LanguageConstruct> lcs) {
		if(methodBody.findFirst(ForStmt.class).isPresent() 
				|| methodBody.findFirst(ForeachStmt.class).isPresent() 
				|| methodBody.findFirst(WhileStmt.class).isPresent()) {
			lcs.add(SourceCodeConstruct.LOOPS_TRUE);
		} else {
			lcs.add(SourceCodeConstruct.LOOPS_FALSE);
		}
	}

	private void hasBranching(List<LanguageConstruct> lcs) {
		if(methodBody.findFirst(IfStmt.class).isPresent() 
				|| methodBody.findFirst(SwitchStmt.class).isPresent()) {
			lcs.add(SourceCodeConstruct.BRANCHING_TRUE);
		} else {
			lcs.add(SourceCodeConstruct.BRANCHING_FALSE);
		}
	}
	
	private void sloc(List<LanguageConstruct> lcs) {
		if(methodBody.getEnd().get().line - methodBody.getBegin().get().line + 1 >= (int)SourceCodeConstruct.GEQ_SLOC.getParameters().get(0)) {
			lcs.add(SourceCodeConstruct.GEQ_SLOC);
		} 
		if(methodBody.getEnd().get().line - methodBody.getBegin().get().line + 1 < (int)SourceCodeConstruct.LT_SLOC.getParameters().get(0)) {
			lcs.add(SourceCodeConstruct.LT_SLOC);
		} 
	}
	
	private void hasReturn(List<LanguageConstruct> lcs) {
		if(methodBody.findFirst(ReturnStmt.class).isPresent()) {
			lcs.add(SourceCodeConstruct.HAS_RETURN);
		} 
	}
	
	private void hasMethodCalls(List<LanguageConstruct> lcs) {
		if(methodBody.findFirst(MethodCallExpr.class).isPresent() ) {
			lcs.add(SourceCodeConstruct.METHOD_CALLS_TRUE);
		} else {
			lcs.add(SourceCodeConstruct.METHOD_CALLS_FALSE);
		}
	}
	
	private void calledMethodHasContract(List<LanguageConstruct> lcs) {
		boolean existsWithContract = false;
		for(MethodCallExpr expr : methodBody.findAll(MethodCallExpr.class)) {
			//TODO get real method
			if(expr.getComment() != null && (expr.getComment().toString().startsWith("/*@") || expr.getComment().toString().startsWith("//@"))){
				existsWithContract = true;
			}
		}
		if(existsWithContract) {
			lcs.add(SourceCodeConstruct.METHOD_CALLS_SPEC_TRUE);
		} else {
			lcs.add(SourceCodeConstruct.METHOD_CALLS_SPEC_FALSE);
		}
	}

	@Override
	public String toString(){
		String str = "[Result of last analysis]\n";
		if(currentConstructs.isEmpty()) {
			return str + "None";
		}
		
		str += "Method: " + pack + "." + clazz + "." + method + "(";
		for(String arg : arguments)
			str += arg + ", ";
		if(arguments != null && arguments.length > 0)
			str = str.substring(0, str.length()-2);
		str += ")\n";
		
		str += "Source Code Constructs: [";
		for(LanguageConstruct lc: currentConstructs)
			str += lc.toString() + ", ";
		if(currentConstructs != null && !currentConstructs.isEmpty())
			str = str.substring(0, str.length()-2);
		str += "]\n";
		
		return str;
	}
	
	public Comment getComment(){
		if(methodBody != null && methodBody.getComment().isPresent()){
			return methodBody.getComment().get();
		}
		return null;
	}
	
	public int getNumberOfArguments() {
		return nArguments;
	}
	
	private String getClassName(String clazz){
		return clazz.substring(clazz.lastIndexOf('.')+1, clazz.length());
	}
	
	private String getPackageName(String clazz){
		return clazz.substring(0, clazz.lastIndexOf('.'));
	}
	
	private boolean hasCorrectClass(CompilationUnit comp){
		return comp.getClassByName(this.clazz).isPresent();
	}
	
	private boolean hasCorrectPackage(CompilationUnit comp){
		return comp.getPackageDeclaration().get().getNameAsString().equals(this.pack);
	}
	
	private BodyDeclaration<?> getMethod(ClassOrInterfaceDeclaration classDeclaration){

		List<MethodDeclaration> methodList = classDeclaration.getMethodsBySignature(this.method, this.arguments);		
		if(!methodList.isEmpty()){
			return methodList.get(0);
		}
		else if(classDeclaration.getConstructorByParameterTypes(this.arguments).isPresent()){
			return classDeclaration.getConstructorByParameterTypes(this.arguments).get();
		}
		else {
			methodList = classDeclaration.getMethodsByName(this.method);
			for(MethodDeclaration method: methodList){
				if(method.getNameAsString().equals(this.method) && methodList.size() == 1){
					System.out.println("Arguments are wrong, but only one method exists with this name - we'll take it");
					return method;
				}
			}

			System.out.println(this.method + ": Method not found");
			return null;
		}
	}
	
	private CompilationUnit generateCompilationUnit(File code) {
		List<File> files = new ArrayList<File>();
		if(code.isFile()){
			files = listAllFilesInDirectory(code.getParentFile().getAbsolutePath(), files);
		}
		else {
			files = listAllFilesInDirectory(code.getAbsolutePath(), files);
		}

		if (files != null) {
			for (File child : files) {
				CompilationUnit compilationUnit;
				if(child.isFile() && child.getAbsolutePath().endsWith(".java")){
					try {
						compilationUnit = JavaParser.parse(child);
						if(hasCorrectPackage(compilationUnit) && hasCorrectClass(compilationUnit)){
							return compilationUnit;
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("Desired class or packacke not found");
		return null;
	}

}
