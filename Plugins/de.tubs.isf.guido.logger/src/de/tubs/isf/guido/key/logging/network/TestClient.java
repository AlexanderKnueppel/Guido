package de.tubs.isf.guido.key.logging.network;

import java.io.IOException;
import java.net.UnknownHostException;

import de.tubs.isf.guido.key.logging.Logger;

public class TestClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Logger l = new NetworkClientLogger("127.0.0.1", 19877);

		l.logStatus("Schalalalala", true);
		l.logStatus("Badadada", false);
	}
}
