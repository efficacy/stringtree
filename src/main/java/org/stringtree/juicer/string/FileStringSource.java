package org.stringtree.juicer.string;

import java.io.File;

import org.stringtree.util.FileReadingUtils;

public class FileStringSource extends OneShotStringSource {
	private File file;

	public FileStringSource(String fileName) {
		this.file = new File(fileName);
	}

	public Object get() {
		return FileReadingUtils.readRawFile(file);
	}
}
