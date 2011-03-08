package org.stringtree.fetcher;

import java.io.File;
import java.io.IOException;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.fetcher.filter.RepositoryFilenameFilter;
import org.stringtree.finder.FetcherStringFinder;
import org.stringtree.finder.StringFinder;
import org.stringtree.tract.FileTractReader;
import org.stringtree.tract.StampedFileTractWriter;

public class TractDirectoryRepository 
    extends DirectoryRepository implements ContextSensitiveFetcher {

    private StringFinder context;

    public TractDirectoryRepository(File dir, RepositoryFilenameFilter filter,
            boolean writable, StringFinder context) {
        super(dir, filter, writable);
        setContext(context);
    }

    public TractDirectoryRepository(File dir, boolean writable,
            StringFinder context) {
        super(dir, writable);
        setContext(context);
    }

    public TractDirectoryRepository(File dir, StringFinder context) {
        this(dir, true, context);
    }

    public TractDirectoryRepository(File dir, Fetcher context) {
        this(dir, true, new FetcherStringFinder(context));
    }

    public TractDirectoryRepository(String dir, boolean writable,
            StringFinder context) {
        super(dir, writable);
        setContext(context);
    }

    private void setContext(StringFinder context) {
        this.context = context;
    }

    public void setContext(Fetcher context) {
        setContext(new FetcherStringFinder(context));
    }

    public void put(String key, Object value) {
        if (value instanceof Tract) {
            put(key, (Tract) value);
        }
    }

    public void put(String key, Tract value) {
        try {
            StampedFileTractWriter.store(value, fileToWrite(key), context);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Object getObject(String key) {
        if (STORE.equals(key))
            return this;

        File file = fileToRead(key); 
        if (file == null || ! file.canRead()) {
            return null;
        }
        
        return FileTractReader.load(file, context);
    }
}
