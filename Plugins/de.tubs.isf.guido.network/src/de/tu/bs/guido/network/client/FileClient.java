package de.tu.bs.guido.network.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import de.tu.bs.guido.network.file.FileNameMessage;
import de.tu.bs.guido.network.file.FileRequestMessage;
import de.tu.bs.guido.network.file.server.ZipUtil;

public class FileClient {

	private final String ip;
	private final int port;

	public FileClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getFilename(String filename) throws IOException,
			ClassNotFoundException {
		Socket s = new Socket(ip, port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		os.writeObject(new FileNameMessage(filename));
		ObjectInputStream is = new ObjectInputStream(s.getInputStream());
		Object o = is.readObject();
		s.close();
		if (o instanceof RuntimeException) {
			throw (RuntimeException) o;
		} else {
			FileNameMessage response = (FileNameMessage) o;
			return response.getFilename();
		}
	}

	public File getFile(String filename, File tempFolder) throws IOException,
			ClassNotFoundException {
		Socket s = new Socket(ip, port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		os.writeObject(new FileRequestMessage(filename));
		InputStream is = s.getInputStream();
		File zipFile = new File(tempFolder, filename + ".zip");
		Path newPath = zipFile.toPath();
		Files.copy(is, newPath);
		s.close();
		ZipUtil zu = new ZipUtil(tempFolder);
		File result = zu.unzip(zipFile);
		zipFile.delete();
		return result;
	}

}
