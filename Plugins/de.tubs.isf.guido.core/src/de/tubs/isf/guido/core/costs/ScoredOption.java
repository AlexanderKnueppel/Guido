package de.tubs.isf.guido.core.costs;

public class ScoredOption extends Option {

	public double scoreP = 0.0;
	public double scoreVE = 0.0;

	public ScoredOption(String option) {
		super(option);
	}

	public ScoredOption(String option, double scoreProvability, double scoreVerificationEffort) {
		super(option);
		this.scoreP = scoreProvability;
		this.scoreVE = scoreVerificationEffort;
	}

	public double getScoreForProvability() {
		return scoreP;
	}

	public double getScoreForVerificationEffort() {
		return scoreVE;
	}

	public void setScoreForProvability(double score) {
		this.scoreP = score;
	}

	public void setScoreForVerificationEffort(double score) {
		this.scoreVE = score;
	}

}
