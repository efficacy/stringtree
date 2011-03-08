package org.stringtree.fetcher.filter;

import java.io.File;
import java.io.FilenameFilter;

public interface RepositoryFilenameFilter extends FilenameFilter {
    boolean contains(File root, String localname);
    String localName(File root, File file);
    File fileToRead(File root, String localname);
    File fileToWrite(File root, String localname);
}
