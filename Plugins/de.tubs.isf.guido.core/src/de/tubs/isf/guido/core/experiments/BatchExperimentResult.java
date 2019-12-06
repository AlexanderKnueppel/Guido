package de.tubs.isf.guido.core.experiments;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import de.tubs.isf.guido.core.statistics.Hypotheses;
import de.tubs.isf.guido.core.statistics.Hypothesis;

public class BatchExperimentResult {
	private List<AExperiment> experiments;
	private Map<AExperiment, List<Hypothesis>> mapping;

	BatchExperimentResult(List<AExperiment> experiments, Map<AExperiment, List<Hypothesis>> mapping) {
		this.experiments = experiments;
		this.mapping = mapping;
	}

	public List<AExperiment> getExperiments() {
		return Collections.unmodifiableList(experiments);
	}

	public Map<AExperiment, List<Hypothesis>> getMapping() {
		return Collections.unmodifiableMap(mapping);
	}

	public List<Hypothesis> getHypotheses(AExperiment exp) {
		if (exp == null || !mapping.containsKey(exp))
			throw new IllegalArgumentException("Experiment does not exist!");
		return mapping.get(exp);
	}

	public AExperiment getExperiment(Hypothesis hyp) {
		if (hyp == null)
			return null;

		Optional<Entry<AExperiment, List<Hypothesis>>> res = mapping.entrySet().stream()
				.filter(e -> e.getValue().contains(hyp)).findFirst();
		
		if(res.isPresent())
			return res.get().getKey();
		else
			return null;
	}
}
