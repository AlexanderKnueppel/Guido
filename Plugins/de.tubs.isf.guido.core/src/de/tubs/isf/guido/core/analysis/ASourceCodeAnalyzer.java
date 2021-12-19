package de.tubs.isf.guido.core.analysis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ASourceCodeAnalyzer implements IAnalyzer {
	protected File sourceFile = null;
	protected List<LanguageConstruct> currentConstructs;
	protected int loc = -1;

	public enum SourceCodeConstruct implements LanguageConstruct {
		ARRAYS_TRUE("arrays_true"), LOOPS_TRUE("loops_true"), LOOPS_FALSE("loops_false"), HAS_LOOP_INVARIANTS_TRUE("loop_invariants_true"),
		HAS_LOOP_INVARIANTS_FALSE("loop_invariants_false"), BRANCHING_TRUE("branching_true"),
		BRANCHING_FALSE("branching_false"), ARGUMENTS_TRUE("arguments_true"), ARGUMENTS_FALSE("arguments_false"),
		METHOD_CALLS_TRUE("methodCalls_true"), METHOD_CALLS_FALSE("methodCalls_false"), RECURSIVE("recursive"),
		METHOD_CALLS_SPEC_TRUE("methodCallsContract_true"), METHOD_CALLS_SPEC_FALSE("methodCallsContract_false"),
		GEQ_SLOC("geqSloc", new Integer(10)), LT_SLOC("ltSloc", new Integer(10)), HAS_RETURN("hasReturn"),
		GEQ_CALL_DEPTH("geqCallDepth", new Integer(5));

		private String representation = "";
		private List<Object> values = null;

		SourceCodeConstruct(String construct, Object... n) {
			this.representation = construct;
			for (Object v : n) {
				if (values == null)
					values = new ArrayList<Object>();
				values.add(v);
			}
		}

		SourceCodeConstruct(String construct) {
			this.representation = construct;
		}

		public String getLanguageConstruct() {
			return representation;
		}

		public boolean isParameterized() {
			return values != null && !values.isEmpty();
		}

		public List<Object> getParameters() {
			return values;
		}

		public String toString() {
			return this.representation;
		}
	}

	public ASourceCodeAnalyzer() {
		sourceFile = null;
	}

	public ASourceCodeAnalyzer(File f) {
		sourceFile = f;
	}

	public void setFile(File f) {
		sourceFile = f;
	}

	public boolean hasLanguageConstruct(LanguageConstruct lc) {
		return currentConstructs.contains(lc);
	}

	public int getLinesOfCode() {
		return loc;
	}

	protected List<File> listAllFilesInDirectory(String directoryName, List<File> files) {
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
}
