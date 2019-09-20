package de.tu.bs.guido.key.network.file.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Set;

import javax.net.ServerSocketFactory;

import de.tu.bs.guido.key.network.file.FileNameMessage;
import de.tu.bs.guido.key.network.file.FileRequestMessage;

public class FileServer {

	private final ServerSocket server;
	private final FileServerSocketRunnable fssr;

	public FileServer(int port, File tempFolder, Set<String> fileWhitelist)
			throws IOException {
		server = ServerSocketFactory.getDefault().createServerSocket(port);

		fssr = new FileServerSocketRunnable(server, tempFolder, fileWhitelist);

		Thread t = new Thread(fssr, "FileServerSocket");
		t.setDaemon(true);
		t.start();
	}

	private static final class FileServerSocketRunnable implements Runnable {
		private final ServerSocket ss;
		private final ZipUtil zu;

		public FileServerSocketRunnable(ServerSocket ss, File tempFolder,
				Set<String> fileWhitelist) {
			this.ss = ss;
			this.zu = new ZipUtil(tempFolder, fileWhitelist);
		}

		@Override
		public void run() {
			try {
				while (true) {
					final Socket clientSocket = ss.accept();
					Thread client = new Thread(
							() -> {
								try {
									ObjectInputStream inFromClient;
									inFromClient = new ObjectInputStream(
											clientSocket.getInputStream());
									Object o = inFromClient.readObject();
									if (o instanceof FileNameMessage) {
										FileNameMessage fnmr = (FileNameMessage) o;
										try {
											FileNameMessage fnma = new FileNameMessage(
													zu.getNewFilename(fnmr
															.getFilename()));
											new ObjectOutputStream(clientSocket
													.getOutputStream())
													.writeObject(fnma);
										} catch (RuntimeException e) {
											new ObjectOutputStream(clientSocket
													.getOutputStream())
													.writeObject(e);
											e.printStackTrace();
										}
									} else if (o instanceof FileRequestMessage) {
										FileRequestMessage frmr = (FileRequestMessage) o;
										Files.copy(zu.zip(frmr.getFilename())
												.toPath(), clientSocket
												.getOutputStream());
									}
									clientSocket.close();
								} catch (Exception e) {
									e.printStackTrace();
								}
							});
					client.setDaemon(true);
					client.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
