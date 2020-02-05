package de.tu.bs.guido.network.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.UnknownHostException;

import de.tu.bs.guido.network.server.Server;
import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;
import de.tubs.isf.guido.verification.systems.cpachecker.CPACheckerFactory;
import de.tubs.isf.guido.core.verifier.Mode;

import de.tubs.isf.guido.verification.systems.key.KeyFactory;

public class Client {
	public static OutputStream opS;

	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		AVerificationSystemFactory.setFactory(new KeyFactory());

		if (opS != null) {
			System.setOut(new PrintStream(opS));
		}
		if (args[0].equals(Mode.Key.toString())) {

			AVerificationSystemFactory.setFactory(new KeyFactory());
		}else if (args[0].equals("CPAChecker")) {
			AVerificationSystemFactory.setFactory(new CPACheckerFactory());
		}
		String ip = "127.0.0.1";
		if (args.length > 1) {
			if (args[1] != null) {
				ip = args[1];
			}
		}
		de.tubs.isf.guido.key.pooling.distributed.Client c = new de.tubs.isf.guido.key.pooling.distributed.Client(ip,
				Server.PORT);

		if (c.isRestart()) {
			System.out.println("client is restarting");
			String separator = System.getProperty("file.separator");
			String classpath = System.getProperty("java.class.path");
			String path = System.getProperty("java.home") + separator + "bin" + separator + "java";
			ProcessBuilder processBuilder = new ProcessBuilder(path, "-cp", classpath, Client.class.getName(), ip);
			processBuilder.start();

		}
	}
}
