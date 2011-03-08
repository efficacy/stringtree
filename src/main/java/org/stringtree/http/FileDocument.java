package org.stringtree.http;

import java.io.File;

import org.stringtree.util.FileReadingUtils;

public class FileDocument extends Document {

	private File file;

	public FileDocument(File file) {
		super(FileReadingUtils.readBytes(file));
		this.file = file;
	}

	public FileDocument(String filename) {
		this(new File(filename));
	}

	@Override
	public String getName() {
		return file.getName();
	}
}
