package de.tu.bs.guido.key.network.file;

import java.io.Serializable;

public abstract class FileMessage implements Serializable{

	private static final long serialVersionUID = 6426220425011692174L;

	private final String filename;

	public FileMessage(String filename) {
		super();
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}
}
