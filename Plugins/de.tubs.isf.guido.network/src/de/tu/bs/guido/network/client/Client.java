package de.tu.bs.guido.network.client;

import java.io.IOException;
import java.net.UnknownHostException;

import de.tu.bs.guido.network.server.Server;
import de.tu.bs.guido.verification.system.ASystemFactory;
import de.tu.bs.guido.verification.systems.key.KeyFactory;

public class Client {

	public static void main(String[] args) throws UnknownHostException,
			ClassNotFoundException, IOException {
		ASystemFactory.setAbst(new KeyFactory());
		String ip = "127.0.0.1";
		if (args.length > 0) {
			ip = args[0];
		}
		de.tu.bs.guido.key.pooling.distributed.Client c = new de.tu.bs.guido.key.pooling.distributed.Client(
				ip, Server.PORT);

		if (c.isRestart()) {
			System.out.println("client is restarting");
			String separator = System.getProperty("file.separator");
			String classpath = System.getProperty("java.class.path");
			String path = System.getProperty("java.home") + separator + "bin"
					+ separator + "java";
			ProcessBuilder processBuilder = new ProcessBuilder(path, "-cp",
					classpath, Client.class.getName(), ip);
			processBuilder.start();

		}
	}
}
