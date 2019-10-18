package de.tu.bs.guido.key.simulator.generators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FeatureIDEReplaceOption {

	public static void main(String[] args) throws IOException {
		replaceAll("Strings_1__1_Strings_1_on", "Strings_1__1_Strings_1_off", new File("D:\\Temp\\Samplings\\Tac\\StringsOff"));
	}

	private static void replaceAll(String a, String b, File subfolder) throws IOException{
		File[] products = subfolder.listFiles();
		for (File file : products) {
			if(file.isFile())
				replaceAllProc(a, b, file);
			else
				replaceAll(a, b, file);
		}
	}
	private static void replaceAllProc(String a, String b, File product) throws IOException{
		boolean contained = false;
		BufferedReader br = new BufferedReader(new FileReader(product));
		String line;
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine()) != null){
			if(line.contains(a)){
			line = line.replaceAll(a, b);
			contained = true;
			}
			sb.append(line).append("\n");
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(product));
		bw.append(sb);
		bw.close();
		if(!contained) System.err.println("File "+product+" did not contain String");
	}
}
