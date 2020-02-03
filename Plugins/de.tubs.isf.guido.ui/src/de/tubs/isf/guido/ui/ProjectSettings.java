package de.tubs.isf.guido.ui;


import java.util.HashMap;
import java.util.Map;

public class ProjectSettings {
	public static final String DEFAULT_PATH_JOB = "../Jobs/job.xml";
	public static String pathJob;
	public static final String DEFAULT_OUTPUT = "../Results.txt";
	public static String outputPath;
	public static final String DEFAULT_LIBARY_PATH = "../Library.txt";
	public static String libraryPath;
	public static final String DEFAULT_PUNISHMENT_PATH = "../punishment.txt";
	public static String punishmentPath;
	public static final String DEFAULT_RESULT_PATH = "../result.txt";
	protected static String resultPath;

	public static Map<String, String> getSettings() {
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("pathJob", pathJob);
		h.put("outputPath", outputPath);
		h.put("libraryPath", libraryPath);
		h.put("punishmentPath", punishmentPath);
		h.put("resultPath", resultPath);
		return h;
	}

	public static void setSettings(Map<String, String> map) {
		System.out.println(map.get("pathJob"));
		pathJob = map.get("pathJob") == null ? DEFAULT_PATH_JOB : map.get("pathJob");
		outputPath = map.get("outputPath") == null ? DEFAULT_OUTPUT : map.get("outputPath");
		libraryPath = map.get("libaryPath") == null ? DEFAULT_LIBARY_PATH : map.get("libaryPath");
		punishmentPath = map.get("punishmentPath") == null ? DEFAULT_PUNISHMENT_PATH : map.get("punishmentPath");
		resultPath = map.get("resultPath") == null ? DEFAULT_RESULT_PATH : map.get("resultPath");

	}
}
