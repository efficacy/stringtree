package org.stringtree.juicer.tract;

import java.io.File;

import org.stringtree.tract.MapTract;
import org.stringtree.util.FileReadingUtils;

public class FileTractSource extends OneShotTractSource {
    
	private File file;

	public FileTractSource(String fileName) {
		this.file = new File(fileName);
	}

	public Object get() {
		String ret =  FileReadingUtils.readRawFile(file);
		return ret != null ? new MapTract(ret) : null;
	}
}
