package de.tu.bs.guido.network.file;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import de.tu.bs.guido.network.client.FileClient;
import de.tu.bs.guido.network.file.server.FileServer;

public class FileTest {

	public static void main(String[] args) throws InterruptedException {
		Thread server = new Thread(new ServerRunnable());
		server.start();
		Thread.sleep(1000);
		Thread client = new Thread(new ClientRunnable());
		client.start();
		client.join();
		server.join();
	}

	private static class ClientRunnable implements Runnable {

		@Override
		public void run() {
			try {
				File tempFolder = new File("TestClientTemp");
				tempFolder.mkdir();
				FileClient fc = new FileClient("127.0.0.1", 12345);
				String filename = fc.getFilename("C:/Users/Carsten/Microsoft OneDrive/OneDrive/Dokumente/TU/Masterarbeit/SVN/Sourcecode/OpenJDK6_small");
				fc.getFile(filename, tempFolder);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	private static class ServerRunnable implements Runnable {

		@Override
		public void run() {
			try {
				HashSet<String> fileWhiteList = new HashSet<>();
				fileWhiteList.add("C:/Users/Carsten/Microsoft OneDrive/OneDrive/Dokumente/TU/Masterarbeit/SVN/Sourcecode/OpenJDK6_small");
				File tempFolder = new File("TestServerTemp");
				tempFolder.mkdir();
				new FileServer(12345, tempFolder, fileWhiteList);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
