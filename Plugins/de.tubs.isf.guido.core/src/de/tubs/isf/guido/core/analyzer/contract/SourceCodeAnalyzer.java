package de.tubs.isf.guido.core.analyzer.contract;

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
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

public class SourceCodeAnalyzer {
	String provingPackage;
	String provingClass;
	String provingMethod;
	String[] parameter;
	CompilationUnit cu;
	BodyDeclaration<?> method;
	
	boolean loops;
	boolean ifConstructs;
	int linesOfCode;
	int parameterValue;
	boolean returnValue;
	boolean methodCalls;
	boolean methodCallsContract;

	public SourceCodeAnalyzer(File code, String provingClass, String provingMethod, String[] parameter) {

		this.provingClass = getClassName(provingClass);
		this.provingPackage = getPackageName(provingClass);
		this.parameter = parameter != null ? parameter : new String[]{};
		this.provingMethod = provingMethod;
		cu = generateCompilationUnit(code);
		method = cu != null ? getMethod(cu.getClassByName(this.provingClass).get()) : null;
	}

	public boolean hasProperty(String property){
		switch(property){
		case "ifConstructs_true": return ifConstructs;
		case "ifConstructs_false": return !ifConstructs;
		case "loops_true": return loops;
		case "loops_false": return !loops;
		case "parameter_true": return parameterValue>0;
		case "parameter_false": return parameterValue <=0;
		case "methodCalls_true": return methodCalls;
		case "methodCalls_false": return !methodCalls;
		case "returnValue_true": return returnValue;
		case "returnValue_false": return !returnValue;
		case "methodCallsContract_true": return methodCallsContract;
		case "methodCallsContract_false": return !methodCallsContract;
		default: return false;
		}
	}
	
	public List<File> listAllFilesInDirectory(String directoryName, List<File> files) {
	    File directory = new File(directoryName);
	    File[] fList = directory.listFiles();

	    for (File file : fList) {
	        if (file.isFile()) {
	        	files.add(file);
	        } else if (file.isDirectory()) {
	        	listAllFilesInDirectory(file.getAbsolutePath(), files);
	        }
	    }
	    return files;
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


	private String getClassName(String provingClass){
		return provingClass.substring(provingClass.lastIndexOf('.')+1, provingClass.length());
	}
	
	private String getPackageName(String provingClass){
		return provingClass.substring(0, provingClass.lastIndexOf('.'));
	}
	
	private boolean hasCorrectClass(CompilationUnit comp){
		return comp.getClassByName(provingClass).isPresent();
	}
	
	private boolean hasCorrectPackage(CompilationUnit comp){
		return comp.getPackageDeclaration().get().getNameAsString().equals(provingPackage);
	}
	
	private BodyDeclaration<?> getMethod(ClassOrInterfaceDeclaration classDeclaration){

		List<MethodDeclaration> methodList = classDeclaration.getMethodsBySignature(provingMethod, parameter);		
		if(!methodList.isEmpty()){
			return methodList.get(0);
		}
		else if(classDeclaration.getConstructorByParameterTypes(parameter).isPresent()){
			return classDeclaration.getConstructorByParameterTypes(parameter).get();
		}
		else {
			methodList = classDeclaration.getMethodsByName(provingMethod);
			for(MethodDeclaration method: methodList){
				if(method.getNameAsString().equals(provingMethod) && methodList.size() == 1){
					System.out.println("parameter are wrong but only one method exists with this name - take it");
					return method;
				}
			}

			System.out.println("Desired method not found");
			return null;
		}
	}
	
	public Comment getContract(){
		if(method != null && method.getComment().isPresent()){
			return method.getComment().get();
		}
		return null;
	}
	

	public boolean hasLoops() {
		return loops;
	}

	public boolean hasIfConstructs() {
		return ifConstructs;
	}

	public int getLinesOfCode() {
		return linesOfCode;
	}

	public int getParameterValue() {
		return parameterValue;
	}

	public boolean hasReturnValue() {
		return returnValue;
	}

	public boolean hasMethodCalls() {
		return methodCalls;
	}

	public boolean hasMethodCallsContract() {
		return methodCallsContract;
	}

	public void setLoops() {
		this.loops = method.findFirst(ForStmt.class).isPresent() 
				|| method.findFirst(ForEachStmt.class).isPresent() 
				|| method.findFirst(WhileStmt.class).isPresent() ? true : false;
	}

	public void setIfConstructs() {
		this.ifConstructs = method.findFirst(IfStmt.class).isPresent() 
				|| method.findFirst(SwitchStmt.class).isPresent() ? true : false;
	}

	public void setLinesOfCode() {
		this.linesOfCode = method.getEnd().get().line - method.getBegin().get().line + 1; 
	}

	public void setParameterValue() {
		if(method.isMethodDeclaration()){
			this.parameterValue = method.asMethodDeclaration().getParameters().size();
		}
		else if(method.isConstructorDeclaration()){
			this.parameterValue = method.asConstructorDeclaration().getParameters().size();
		}
		else {
			this.parameterValue = parameter.length;
		}
	}

	public void setReturnValue() {
		this.returnValue = method.findFirst(ReturnStmt.class).isPresent() ? true : false;
	}

	public void setMethodCalls() {
		this.methodCalls = method.findFirst(MethodCallExpr.class).isPresent() ? true: false;
	}

	public void setMethodCallsContract() {
		this.methodCallsContract = false; //TODO is it possible to check if a called method has a contract itself?
	}

	@Override
	public String toString(){
		return "loops: " + hasLoops() + ", " +
				"ifConstructs: " + hasIfConstructs() + ", " +
				"linesOfCode: " + getLinesOfCode() + ", " +
				"parameterValue: " + getParameterValue() + ", " +
				"returnValue: " + hasReturnValue() + ", " +
				"methodCalls: " + hasMethodCalls() + ", " +
				"methodCallsContract: " + hasMethodCallsContract();
	}
		
	public void analyzeSourceCode() {
		if(method != null){
			setLoops();
			setIfConstructs();
			setLinesOfCode();
			setParameterValue();
			setReturnValue();
			setMethodCalls();
			setMethodCallsContract();
			System.out.println(this.toString());
		}
	}
}
