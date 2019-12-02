package de.tubs.isf.guido.core.sampling;

import java.util.List;
import java.util.Set;

import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

public interface IConfigurationGenerator {
	public void setMaxSamples(int max);
	public List<Set<String>> execute(IMonitor<List<LiteralSet>> monitor) throws Exception;
	public List<Set<String>> getConfigurations();
}
