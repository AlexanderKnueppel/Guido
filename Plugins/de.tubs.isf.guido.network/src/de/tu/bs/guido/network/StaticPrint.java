package de.tu.bs.guido.network;

public class StaticPrint {
	static Printer PrinterServer;
	public static Printer getPrinterServer() {
		return PrinterServer;
	}
	public static void setPrinterServer(Printer printerServer) {
		PrinterServer = printerServer;
	}
	public static Printer getPrinterClient() {
		return PrinterClient;
	}
	public static void setPrinterClient(Printer printerClient) {
		PrinterClient = printerClient;
	}
	static Printer PrinterClient;
}
