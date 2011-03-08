package org.stringtree.tract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.finder.StringFinder;

public class FileTractWriter extends TractWriter {
    
    public static void store(Tract tract, File file, Fetcher context)
            throws IOException {
        OutputStream out = new FileOutputStream(file);
        TractWriter.store(tract, out, context);
        out.close();
    }

    public static void store(Tract tract, File file, StringFinder context)
            throws IOException {
        store(tract, file, context.getUnderlyingFetcher());
    }

    public static void store(Tract tract, File file) throws IOException {
        store(tract, file, (Fetcher) null);
    }
}
