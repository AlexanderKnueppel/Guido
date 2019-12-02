package de.tubs.isf.guido.core.sampling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

import de.ovgu.featureide.fm.core.io.FileSystem;

public class SampleWriter {
	private String basePath = null;

	public SampleWriter(String basePath) {
		this.basePath = basePath;
	}

	public String writeFolder(String name) {
		String folderPath = "";
		try {
			// System.out.println("Creating Directory");
			folderPath = basePath + "/products_" + name.replaceAll(".xml", "");
			FileSystem.mkDir(Paths.get(folderPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return folderPath;
	}

	public void writeConfiguration(String folderPath, Set<String> list, int solutionNumber) {
		String prefix = getPrefix((int) solutionNumber);
		String filePath = folderPath + "/" + prefix + ".config";
		StringBuilder sb = new StringBuilder();

		for (String s : list) {
			sb.append(s);
			sb.append("\n");
		}
		if (sb.length() > 0) {
			sb.replace(sb.length() - 1, sb.length(), "");
		}

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getPrefix(int number) {
		String count = String.valueOf(number);
		String prefix = "00000";
		switch (count.length()) {
		case 1:
			prefix = "0000" + count;
			break;
		case 2:
			prefix = "000" + count;
			break;
		case 3:
			prefix = "00" + count;
			break;
		case 4:
			prefix = "0" + count;
			break;
		default:
			prefix = count;
			break;
		}
		return prefix;
	}
}
