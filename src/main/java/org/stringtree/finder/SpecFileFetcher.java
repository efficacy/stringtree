package org.stringtree.finder;

import java.io.File;
import java.io.IOException;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.FallbackRepository;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.util.spec.SpecReader;

public class SpecFileFetcher extends MapFetcher {
    
    public SpecFileFetcher(File file, StringFinder context) {
        String location = file.getAbsolutePath();
        setup(location, context);
    }

    private void setup(String location, StringFinder context) {
        try {
            Fetcher fetcher = context == null 
                ? (Fetcher)this 
                : new FallbackRepository(this,context);
            
            SpecReader.load(new FetcherStringFinder(fetcher), location);
            lock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SpecFileFetcher(String location, StringFinder context) {
        if (location.indexOf(":") < 0)
            location = new File(location).getAbsolutePath();
        setup(location, context);
    }

    public SpecFileFetcher(File location) {
        this(location, null);
    }

    public SpecFileFetcher(String location) {
        this(location, null);
    }
}
