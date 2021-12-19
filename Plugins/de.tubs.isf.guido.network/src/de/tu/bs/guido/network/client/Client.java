package de.tu.bs.guido.network.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import de.tu.bs.guido.network.server.Server;
import de.tubs.isf.guido.core.util.CommandArguments;
import de.tubs.isf.guido.core.verifier.AVerificationSystemFactory;
import de.tubs.isf.guido.core.verifier.loader.VerifierDescription;
import de.tubs.isf.guido.core.verifier.loader.VerifierLoader;

public class Client {
	public static OutputStream opS;

	private static VerifierDescription<AVerificationSystemFactory> description;

	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {

		if (opS != null) {
			System.setOut(new PrintStream(opS));
		}

		List<VerifierDescription<AVerificationSystemFactory>> systems = VerifierLoader
				.load(AVerificationSystemFactory.class);

		if (systems.isEmpty()) {
			System.err.println("No verification systems found! Add plug-ins to the classpath!");
			return;
		}

		CommandArguments parser = new CommandArguments(args);

		if (parser.hasOption("--verifier")) {
			String verifier = parser.valueOf("--verifier");
			Optional<VerifierDescription<AVerificationSystemFactory>> system = systems.stream()
					.filter(desc -> desc.getHandle().equals(verifier)).findFirst();

			if (system.isPresent()) {
				description = system.get();
			} else {
				System.err.println("Verifier '" + parser.valueOf("--verifier") + "' not found! Wrong spelling?");
				System.err.println("The following verifiers are available: ");
				for (VerifierDescription<AVerificationSystemFactory> s : systems) {
					System.err.println("  -- " + s.getName() + " (command: " + s.getHandle() + ")");
				}
				return;
			}
		} else {
			System.out.println("No verification systems specified!");
			description = systems.get(0);
			System.out.println("Using " + description.getName() + "...");
		}

		AVerificationSystemFactory.setFactory(description.getVerifier());

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
			String verifier = "--verifier " + description.getHandle();
			ProcessBuilder processBuilder = new ProcessBuilder(path, "-cp", classpath, Client.class.getName(), verifier,
					ip);
			processBuilder.start();

		}
	}
}
