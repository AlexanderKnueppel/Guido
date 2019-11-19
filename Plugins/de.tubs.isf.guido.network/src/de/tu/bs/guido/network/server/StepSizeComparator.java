package de.tu.bs.guido.network.server;

import java.util.Comparator;

import de.tu.bs.guido.verification.system.Job;

public class StepSizeComparator implements Comparator<Job>{

	@Override
	public int compare(Job o1, Job o2) {
		return Integer.compare(o1.getSo().getMaxSteps(), o2.getSo().getMaxSteps());
	}

}
