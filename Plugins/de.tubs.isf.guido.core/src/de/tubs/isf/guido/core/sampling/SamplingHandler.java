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
		File cpacheckersubjects = new File("testData/cpachecker/testFiles");

		for(File samples :  cpacheckersamples.listFiles()) {
			
			for(File file : cpacheckersubjects.listFiles()) {
				if(!file.getName().endsWith(".c"))
					continue;
				
				content += "		<Problem Source=\"cpacheckerprojects/testFiles/"+ file.getName() + "\" \r\n" + 
						"				Binary=\"cpacheckerprojects/testFiles/"+file.getName().substring(0, file.getName().length()-1)+"i\" \r\n" + 
						"	 			Parameters=\"\"\r\n" + 
						"	 			SPLSampleFile=\"cpacheckerprojects/cpachecker-samples/"+samples.getName()+"\" \r\n" + 
						"	 	/>";
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
