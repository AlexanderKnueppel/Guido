package de.tubs.isf.guido.core.sampling;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.NoAbstractCNFCreator;
import de.ovgu.featureide.fm.core.analysis.cnf.generator.configuration.SPLCAToolConfigurationGenerator;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.job.LongRunningWrapper;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

public class ICPLConfigurationGenerator implements IConfigurationGenerator {
	
	private class Consumer implements Runnable {

		private final SPLCAToolConfigurationGenerator gen;
		private final Configuration configuration = new Configuration(new FeatureModelFormula(fm));
		private boolean run = true;

		public Consumer(SPLCAToolConfigurationGenerator gen) {
			this.gen = gen;
		}

		@Override
		public void run() {
			final LinkedBlockingQueue<LiteralSet> resultQueue = gen.getResultQueue();
			while (run) {
				try {
					generateConfiguration(resultQueue.take());
				} catch (final InterruptedException e) {
					break;
				}
			}
			//setConfigurationNumber(gen.getResult().getResult().size());
			for (final LiteralSet c : resultQueue) {
				generateConfiguration(c);
			}
		}

		public void stop() {
			run = false;
		}

		private void generateConfiguration(LiteralSet solution) {
			configuration.resetValues();
			for (final int selection : solution.getLiterals()) {
				final String name = cnf.getVariables().getName(selection);
				configuration.setManual(name, selection > 0 ? Selection.SELECTED : Selection.UNSELECTED);
			}
			configurations.add(configuration.clone());
		}

	}

	private IFeatureModel fm;
	private CNF cnf;
	private int t;
	private int maxSampleSize = 1000;
	private List<Configuration> configurations = new ArrayList<Configuration>();
	

	public ICPLConfigurationGenerator(IFeatureModel fm, int t, int maxSampleSize) {
		this.cnf = new FeatureModelFormula(fm).getElement(new NoAbstractCNFCreator());
		this.fm = fm;
		this.t = t;
		this.maxSampleSize = maxSampleSize;
	}
	
	@Override
	public List<Set<String>> execute(IMonitor<List<LiteralSet>> monitor) throws Exception {
		final SPLCAToolConfigurationGenerator gen =  new SPLCAToolConfigurationGenerator(cnf, "ICPL", t, maxSampleSize);
		final Consumer consumer = new Consumer(gen);
		final Thread thread = new Thread(consumer);
		thread.start();
		try {
			LongRunningWrapper.runMethod(gen, monitor.subTask(1));
		} catch (final Exception e) {
			throw e;
		}
		consumer.stop();
		thread.interrupt();
		return null;
	}

	/*Abstract class?*/
	@Override
	public List<Set<String>> getConfigurations() {
		List<Set<String>> result = new ArrayList<Set<String>>();

		configurations.stream().forEach(c -> {
			result.add(FeatureIDEUtil.ConfigurationToString(c));
		});

		return result;
	}

	@Override
	public void setMaxSamples(int max) {
		maxSampleSize = max;
	}
}
