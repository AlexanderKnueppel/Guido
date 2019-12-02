package de.tubs.isf.guido.core.statistics.correction;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import de.tubs.isf.guido.core.statistics.EvaluatedHypothesis;
import de.tubs.isf.guido.core.statistics.Hypotheses;

public class HolmCorrection implements IAlphaCorrection {

	@Override
	public double apply(Hypotheses hypotheses, double uncompensatedAlpha) {
		if(!hypotheses.evaluated()) {
			throw new IllegalArgumentException("Only tested hypotheses are allowed!");
		}
		List<EvaluatedHypothesis> tmp = hypotheses.getHypotheses().stream().map(h -> (EvaluatedHypothesis)h).collect(Collectors.toList());
		tmp = sortForPValue(tmp);

		for (int k = 0; k < tmp.size(); k++) {
			if (tmp.get(k).getPValue() > (uncompensatedAlpha / (tmp.size() + 1 - k))) {
				if (k == 0)
					return 0.0;

				return tmp.get(k - 1).getPValue();
			}
		}

		return tmp.get(tmp.size()-1).getPValue();
	}

	public List<EvaluatedHypothesis> sortForPValue(List<EvaluatedHypothesis> tmp) {
		// TODO Auto-generated method stub
		return tmp.stream().sorted(Comparator.comparingDouble(EvaluatedHypothesis::getPValue))
				.collect(Collectors.toList());
	}

}
