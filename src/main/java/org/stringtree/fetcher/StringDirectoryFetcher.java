package org.stringtree.fetcher;

import java.io.File;

import org.stringtree.fetcher.filter.RepositoryFilenameFilter;
import org.stringtree.util.FileReadingUtils;

public class StringDirectoryFetcher extends DirectoryFetcher {
    
    public StringDirectoryFetcher(File dir) {
        super(dir);
    }

    public StringDirectoryFetcher(File dir, RepositoryFilenameFilter filter) {
        super(dir, filter);
    }

    public StringDirectoryFetcher(String dir) {
        super(dir);
    }

    public Object getObject(String name) {
        return FileReadingUtils.readRawFile(fileToRead(name));
    }
}