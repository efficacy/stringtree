package org.stringtree.fetcher;

import java.io.File;
import java.io.IOException;

import org.stringtree.fetcher.filter.RepositoryFilenameFilter;
import org.stringtree.util.FileReadingUtils;
import org.stringtree.util.FileUtils;
import org.stringtree.util.FileWritingUtils;

public class StringDirectoryRepository extends DirectoryRepository {
    
    public StringDirectoryRepository(File dir, boolean writable) {
        super(dir, writable);
    }

    public StringDirectoryRepository(File dir, RepositoryFilenameFilter filter,
            boolean writable) {
        super(dir, filter, writable);
    }

    public StringDirectoryRepository(String dir, boolean writable) {
        super(dir, writable);
    }

    public StringDirectoryRepository(File dir, RepositoryFilenameFilter filter) {
        this(dir, filter, true);
    }

    public StringDirectoryRepository(String dir) {
        super(dir, true);
    }

    public Object getObject(String name) {
        if (writable && STORE.equals(name))
            return this;
        return FileReadingUtils.readRawFile(fileToRead(name));
    }

    public void put(String key, Object value) {
        if (value != null && writable) {
            File file = filter.fileToWrite(dir, key);
            File parent = file.getParentFile();
            if (parent != null) {
                FileUtils.ensureDirectory(parent);
            }
            try {
                FileWritingUtils.writeFile(file, value.toString());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}