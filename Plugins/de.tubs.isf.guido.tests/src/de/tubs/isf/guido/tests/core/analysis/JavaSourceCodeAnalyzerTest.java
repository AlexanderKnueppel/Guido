package de.tubs.isf.guido.tests.core.analysis;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import de.tubs.isf.guido.core.analysis.ASourceCodeAnalyzer.SourceCodeConstruct;
import de.tubs.isf.guido.core.analysis.IAnalyzer.LanguageConstruct;
import de.tubs.isf.guido.core.analysis.JavaSourceCodeAnalyzer;

class JavaSourceCodeAnalyzerTest {

	public static boolean equals(Set<?> set1, Set<?> set2) {
		if (set1 == null || set2 == null) {
			return false;
		}

		if (set1.size() != set2.size()) {
			return false;
		}

		return set1.containsAll(set2);
	}

	@Test
	void test() {
		JavaSourceCodeAnalyzer jsca = new JavaSourceCodeAnalyzer(new File("./testdata/java/Sort.java"),
				"Examples_from_Chapter_16.Sort", "max", new String[] { "int" });

		Set<SourceCodeConstruct> lcs = new HashSet<SourceCodeConstruct>();
		lcs.add(SourceCodeConstruct.ARGUMENTS_TRUE);
		lcs.add(SourceCodeConstruct.BRANCHING_TRUE);
		lcs.add(SourceCodeConstruct.LOOPS_TRUE);
		lcs.add(SourceCodeConstruct.METHOD_CALLS_FALSE);
		lcs.add(SourceCodeConstruct.METHOD_CALLS_SPEC_FALSE);
		lcs.add(SourceCodeConstruct.HAS_RETURN);
		lcs.add(SourceCodeConstruct.GEQ_SLOC);

		assertTrue("Sets have to be equal", equals(jsca.analyze().stream().collect(Collectors.toSet()), lcs));
		
		jsca = new JavaSourceCodeAnalyzer(new File("./testData/Sort.java"), "Examples_from_Chapter_16.Sort", "sort",
				new String[] {});
		
		lcs.clear();
		lcs.add(SourceCodeConstruct.ARGUMENTS_FALSE);
		lcs.add(SourceCodeConstruct.BRANCHING_FALSE);
		lcs.add(SourceCodeConstruct.LOOPS_TRUE);
		lcs.add(SourceCodeConstruct.METHOD_CALLS_TRUE);
		lcs.add(SourceCodeConstruct.METHOD_CALLS_SPEC_TRUE);
		lcs.add(SourceCodeConstruct.GEQ_SLOC);
		
		assertTrue("Sets have to be equal", equals(jsca.analyze().stream().collect(Collectors.toSet()), lcs));


	}

}
