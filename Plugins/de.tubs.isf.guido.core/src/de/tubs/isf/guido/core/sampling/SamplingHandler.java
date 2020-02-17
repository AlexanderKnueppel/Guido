package de.tubs.isf.guido.core.sampling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.prop4j.Literal;

import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.impl.ConfigurationFactoryManager;
import de.ovgu.featureide.fm.core.base.impl.DefaultConfigurationFactory;
import de.ovgu.featureide.fm.core.base.impl.DefaultFeatureModelFactory;
import de.ovgu.featureide.fm.core.job.monitor.ConsoleMonitor;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

public class SamplingHandler implements Runnable {
	private int maxSolutionCount = Integer.MAX_VALUE;
	private long timeOutBorder = 8 * 60 * 60 * 1000;
	private String output;
	private IConfigurationGenerator gen;
	private SampleWriter sw;
	private String currentFolderPath;
	private IMonitor<List<LiteralSet>> monitor = null;

	public SamplingHandler(String baseSamplePath, String output, IConfigurationGenerator gen) {
		this.output = output;
		this.sw = new SampleWriter(baseSamplePath);
		this.gen = gen;
		this.monitor = new ConsoleMonitor<List<LiteralSet>>();

		ConfigurationFactoryManager.getInstance().addExtension(DefaultConfigurationFactory.getInstance());
	}

	void setMaxSamples(int max) {
		maxSolutionCount = max;
	}

	void setTimeOut(long timeout) {
		timeOutBorder = timeout;
	}

	@Override
	public void run() {
		currentFolderPath = sw.writeFolder(output);
		gen.setMaxSamples(maxSolutionCount);

		try {
			gen.execute(monitor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int[] idx = { 0 };
		gen.getConfigurations().stream().forEach(c -> {
			sw.writeConfiguration(currentFolderPath, c, (int) idx[0]++);
		});
	}

	public static void main(String[] args) {
		String fmPath = "testData/cpachecker/CPAChecker-feature-model.xml";
		String baseSamplePath = "testData/cpachecker/cpachecker-samples";

		IFeatureModel fm = FeatureIDEUtil.getFeatueModel(Paths.get(fmPath));

		List<String> blacklist = Arrays.asList("analysis_2_restartAfterUnknown_1__1_true",
				"analysis_2_restartAfterUnknown_1__1_false", "analysis_2_traversal_2_usePostorder_1__1_true",
				"analysis_2_traversal_2_usePostorder_1__1_false");

//		for (IFeature feature : fm.getFeatures()) {
//			if (feature.getStructure().isAbstract())
//				continue;
//
//			if (blacklist.contains(feature.getName()))
//				continue;
//
//			IConstraint c1 = DefaultFeatureModelFactory.getInstance().createConstraint(fm,
//					new Literal(feature.getName()));
//			fm.addConstraint(c1);
//
//			String output = feature.getName().replace("_2_", ".").replace("_1__1_", "-");
//			IConfigurationGenerator gen = new ICPLConfigurationGenerator(fm, 3, 1000);
//			new SamplingHandler(baseSamplePath, output, gen).run();
//
//			fm.removeConstraint(c1);
//		}

		// write Job file
		String content = "<job >\r\n" + "	<Codeunit Configuration=\"config/default.properties\">	";

		File cpacheckersamples = new File("testData/cpachecker/cpachecker-samples");
		File cpacheckersubjects = new File("testData/cpachecker/testFiles2");

		List<String> filelist = Arrays.asList("byte_add_1-2.c", "chunk1.c", "cstrcpy_malloc.c", "mapavg2.c",
				"dancing.c", "rlim_invariant.c.p+cfa-reducer.c", "dll-01-2.c", "dll-simple-white-blue-1.c",
				"filter1.c.v+nlh-reducer.c", "interpolation.c.p+cfa-reducer.c", "mergeSort.c",
				"system-with-recursion.c", "s3_clnt.blast.01.i.cil-1.c", "bAnd1.c", "bor1.c", "array-realloc-1.c",
				"double_req_bl_0220a.c", "float_req_bl_1271a.c", "newton_1_1.c", "calendar.c", "hash_fun.c",
				"test-0513_1.c", "test_locks_6.c", "c.03-alloca-2.c", "list_and_tree_cnstr-1.c", "merge_sort-1.c",
				"tree-1.c", "cast_union_tight.c", "sqrt_Householder_pseudoconstant.c", "standard_palindrome_ground.c");
		
//		for(String name : filelist) {
//			boolean found = false;
//			for(File file : cpacheckersubjects.listFiles()) {
//				if(file.getName().equals(name)) {
//					found = true;
//					break;
//				}
//			}
//			
//			if(!found)
//				System.out.println(name  + " not found...");
//		}

		for (File samples : cpacheckersamples.listFiles()) {

			for (String file : filelist) {
				if (!file.endsWith(".c"))
					continue;

				content += System.lineSeparator();
				content += "		<Problem Source=\"cpacheckerprojects/testFiles/" + file + "\" \r\n"
						+ "				Binary=\"cpacheckerprojects/testFiles/"
						+ file.substring(0, file.length() - 1) + "i\" \r\n"
						+ "	 			Parameters=\"\"\r\n"
						+ "	 			SPLSampleFile=\"cpacheckerprojects/cpachecker-samples/" + samples.getName()
						+ "\" \r\n" + "	 	/>";
			}
		}

		content += "	</Codeunit>\r\n" + "</job>";

		try {
			Files.write(Paths.get("testData/cpachecker/cpa_job.xml"), content.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
