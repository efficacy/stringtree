package org.stringtree.template;

import java.io.File;

import org.stringtree.fetcher.DirectoryFetcher;
import org.stringtree.fetcher.filter.RepositoryFilenameFilter;
import org.stringtree.tract.FileTractReader;

public class TreeDirectoryFetcher extends DirectoryFetcher {

    public TreeDirectoryFetcher(File dir, RepositoryFilenameFilter filter) {
    	super(dir, filter);
    }

    public TreeDirectoryFetcher(File dir) {
        super(dir);
    }

    public TreeDirectoryFetcher(String dir) {
        super(dir);
    }

	@Override
	public Object getObject(String name) {
		File file = new File(dir, name);
		return file.exists() && file.canRead()
			? FileTractReader.load(file, null)
			: null;
	}

}
