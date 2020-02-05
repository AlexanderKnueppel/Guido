package de.tubs.isf.guido.core.sampling;

import java.nio.file.Paths;
import java.util.List;
import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.base.impl.ConfigurationFactoryManager;
import de.ovgu.featureide.fm.core.base.impl.DefaultConfigurationFactory;
import de.ovgu.featureide.fm.core.job.monitor.ConsoleMonitor;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

public class SamplingHandler implements Runnable {
	private int maxSolutionCount = Integer.MAX_VALUE; 
	private long timeOutBorder = 8 * 60 * 60 * 1000;
	private String fmPath; 
	private IConfigurationGenerator gen; 
	private SampleWriter sw;
	private String currentFolderPath;
	private IMonitor<List<LiteralSet>> monitor = null; 
	
	public SamplingHandler(String fmPath, String baseSamplePath, IConfigurationGenerator gen) {
		this.fmPath = fmPath;
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

	@Override public void run() {
		currentFolderPath = sw.writeFolder("samples");
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
		String fmPath = "testData/model2.xml";
		String baseSamplePath = "testData";
		
		IConfigurationGenerator gen = new ICPLConfigurationGenerator(FeatureIDEUtil.getFeatueModel(Paths.get(fmPath)), 3, 1000);
		new SamplingHandler(fmPath, baseSamplePath, gen).run();

//		Thread t1 = new Thread( new SamplingHandler(fmPath, baseSamplePath, gen) );
//		t1.start();
	}
}
