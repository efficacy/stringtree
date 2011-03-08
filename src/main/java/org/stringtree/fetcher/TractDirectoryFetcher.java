package org.stringtree.fetcher;

import java.io.File;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.filter.RepositoryFilenameFilter;
import org.stringtree.finder.FetcherStringFinder;
import org.stringtree.finder.StringFinder;
import org.stringtree.tract.FileTractReader;

public class TractDirectoryFetcher 
    extends DirectoryFetcher implements ContextSensitiveFetcher {

    private StringFinder context;

    public TractDirectoryFetcher(File dir, RepositoryFilenameFilter filter, StringFinder context) {
        super(dir, filter);
        setContext(context);
    }

    public TractDirectoryFetcher(File dir, StringFinder context) {
        super(dir);
        setContext(context);
    }

    public TractDirectoryFetcher(File dir, Fetcher context) {
        this(dir, new FetcherStringFinder(context));
    }

    public TractDirectoryFetcher(String dir, StringFinder context) {
        super(dir);
        setContext(context);
    }

    private void setContext(StringFinder context) {
        this.context = context;
    }

    public void setContext(Fetcher context) {
        setContext(new FetcherStringFinder(context));
    }

    public Object getObject(String key) {
        File file = fileToRead(key); 
        if (file == null || ! file.canRead()) {
            return null;
        }
        
        return FileTractReader.load(file, context);
    }
}
