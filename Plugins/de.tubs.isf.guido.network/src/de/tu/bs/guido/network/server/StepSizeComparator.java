package de.tu.bs.guido.network.server;

import java.util.Comparator;

import de.tubs.isf.guido.core.verifier.IJob;

public class StepSizeComparator implements Comparator<IJob>{

	@Override
	public int compare(IJob o1, IJob o2) {
		return Double.compare(o1.getSo().getMaxEffort(), o2.getSo().getMaxEffort());
	}

}
