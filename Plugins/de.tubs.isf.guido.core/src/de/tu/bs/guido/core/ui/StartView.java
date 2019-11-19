package ui;

import automaticProof.Prover;
import java.io.File;


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
