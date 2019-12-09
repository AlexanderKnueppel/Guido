package de.tu.bs.guido.network.server;

import java.util.Comparator;

import de.tu.bs.guido.verification.system.IJob;

public class StepSizeComparator implements Comparator<IJob>{

	@Override
	public int compare(IJob o1, IJob o2) {
		return Integer.compare(o1.getSo().getMaxSteps(), o2.getSo().getMaxSteps());
	}

}
