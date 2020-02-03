package de.tubs.isf.guido.ui;


import java.util.HashMap;
import java.util.Map;

public class ProjectSettings {
	public static final String DEFAULT_PATH_JOB = "../Jobs/job.xml";
	public static String pathJob;
	public static final String DEFAULT_OUTPUT = "../Results";
	public static String outputPath;
	public static final String DEFAULT_LIBARY_PATH = "../Library";
	public static String libraryPath;
	public static final String DEFAULT_PUNISHMENT_PATH = "../punishment";
	public static String punishmentPath;

	public static Map<String, String> getSettings() {
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("pathJob", pathJob);
		h.put("outputPath", outputPath);
		h.put("libraryPath", libraryPath);
		h.put("punishmentPath", punishmentPath);
		return h;
	}

	public static void setSettings(Map<String, String> map) {
		System.out.println(map.get("pathJob"));
		pathJob = map.get("pathJob") == null ? DEFAULT_PATH_JOB : map.get("pathJob");
		outputPath = map.get("outputPath") == null ? DEFAULT_OUTPUT : map.get("outputPath");
		libraryPath = map.get("libaryPath") == null ? DEFAULT_LIBARY_PATH : map.get("libaryPath");
		punishmentPath = map.get("punishmentPath") == null ? DEFAULT_PUNISHMENT_PATH : map.get("punishmentPath");

	}
}
