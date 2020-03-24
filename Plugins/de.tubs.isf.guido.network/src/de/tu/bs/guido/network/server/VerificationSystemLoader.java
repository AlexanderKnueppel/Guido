package de.tu.bs.guido.network.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class VerificationSystemLoader {

	File f = new File("..\\VerificationSystems");

	public String[] getVerifiyingSystems(String path) throws FileNotFoundException {

		if (path != null) {
			if (!path.equals("")) {
				f = new File(path);

			}
		}
		if (f.exists()) {
			File[] fs = f.listFiles();
			for (File file : fs) {

			}
		} else {
			throw new FileNotFoundException();
		}

		return null;
	}
}
