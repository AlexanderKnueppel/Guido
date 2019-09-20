package de.tu.bs.guido.key.network.file.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	private static final int fileNameSize = 7;
	private static final int charsInAlphabet = 26;
	private static final int maxNumber = (int) Math.pow(charsInAlphabet,
			fileNameSize);
	private Map<String, File> zips = new HashMap<>();
	private Map<String, String> fileNames = new HashMap<>();
	private Map<String, String> newFileNames = new HashMap<>();
	private Set<String> fileWhitelist = null;
	private File tempFolder;

	private int nextNumber = new Random().nextInt(maxNumber);

	public static void main(String[] args) throws IOException {
		File f = new File("temp");
		f.mkdir();
		ZipUtil zu = new ZipUtil(f);
		File folder = new File("./../../VerificationData/VerificationData_ReduxProblemSolved");
		String newName = zu.getNewFilename(folder.getPath());
		File zip = zu.zip(newName);
		//zu.unzip(new File("./../Temp/Client/CUCVLJY.zip"));
	}

	/**
	 * Clientseitiges ZipUtil
	 * @param tempFolder
	 */
	public ZipUtil(File tempFolder) {
		this.tempFolder = tempFolder;
		this.fileWhitelist = new HashSet<>();
	}

	/**
	 * Clientseitiges ZipUtil
	 * @param tempFolder
	 */
	public ZipUtil(File tempFolder, Set<String> whitelist) {
		this.tempFolder = tempFolder;
		this.fileWhitelist = whitelist;
	}

	public synchronized String getNewFilename(String filename) {
		if(!fileWhitelist.contains(filename))
			throw new IllegalArgumentException(filename+" is not whitelisted");
		
		String newFilename = fileNames.get(filename);
		if (newFilename == null) {
			newFilename = generateFilename();
			fileNames.put(filename, newFilename);
			newFileNames.put(newFilename, filename);
		}
		return newFilename;
	}

	public synchronized File zip(String filename) throws IOException {
		File zip = zips.get(filename);
		if (zip == null) {
			String newFilename = newFileNames.get(filename);
			if(newFilename == null)
				throw new IllegalArgumentException("Filename has not yet been generated (call getNewFilename(String) first)");
			File folder = new File(newFilename);
			zip = zipFolder(folder, filename);
			zips.put(filename, zip);
		}
		return zip;
	}

	public synchronized File unzip(File f) throws IOException {
		ZipInputStream zis = new ZipInputStream(new FileInputStream(f));
		String folderName = f.getAbsolutePath().replaceAll(".zip", "");
		
		File result = new File(tempFolder, folderName);
			//result.delete();

		byte[] buffer = new byte[1024];
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
			String fileName = entry.getName();
			if (entry.isDirectory()) {
				File newFolder = new File(folderName + "/" + fileName);
				newFolder.mkdirs();
			} else {
				File file = new File(folderName + "/" + fileName);
				File dir = file.getParentFile();
				if (!dir.exists()){
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(file);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
			}
		}
		zis.close();
		return new File(folderName);
	}

	private String generateFilename() {
		char[] filename = new char[fileNameSize];
		int num = nextNumber;
		int pos = filename.length;
		while (num > 0) {
			int sign = num % charsInAlphabet;
			num = num / charsInAlphabet;
			filename[--pos] = (char) ('A' + sign);
		}
		for (int i = 0; i < pos; i++) {
			filename[i] = 'A';
		}
		nextNumber++;
		if (nextNumber > maxNumber || nextNumber < 0)
			nextNumber = 0;
		return new String(filename);
	}

	private File zipFolder(File folder, String name) throws IOException {
		
		File zipFile = new File(tempFolder, name + ".zip");
		File source = folder.getAbsoluteFile();

		Set<String> fileSet = createFileSet(folder, source);
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
		
		byte[] buffer = new byte[1024];
		for (String file : fileSet) {
			ZipEntry ze = new ZipEntry(file);
			zos.putNextEntry(ze);
			FileInputStream fis = new FileInputStream(source + File.separator
					+ 
					file);
			int len;
			while ((len = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}
			fis.close();
		}
		zos.closeEntry();
		zos.close();
		return zipFile;
	}

	private static Set<String> createFileSet(File source, File current) {
		Set<String> resultSet = new HashSet<>();
		File[] subfolderArray = current.listFiles((file) -> file.isDirectory());
		for (File subfolder : subfolderArray) {
			resultSet.addAll(createFileSet(source, subfolder));
		}
		File[] fileArray = current.listFiles((file) -> !file.isDirectory());
		for (File file : fileArray) {
			resultSet.add(file.getAbsoluteFile().toString().replace(source.getAbsolutePath()+"\\", ""));
		}
		return resultSet;
	}
}
