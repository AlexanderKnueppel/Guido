package deprecated.de.tubs.isf.guido.core.ui;

import java.io.File;

import deprecated.de.tubs.isf.guido.core.automaticProof.Prover;


public class StartView {

	public static void main(String[] args) {
        
		File forGeneratingRules = new File("./../results.txt");
		File hypotheses = new File("./../hypotheses.txt");
		File rules = new File("./../rules.txt");
		Prover prover = new Prover(forGeneratingRules, hypotheses, rules, 60000);

            	GuidanceGUI test = new GuidanceGUI(prover);
                test.show();

    }
}
