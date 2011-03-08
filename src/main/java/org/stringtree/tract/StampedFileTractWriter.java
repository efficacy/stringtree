package org.stringtree.tract;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.finder.StringFinder;

public class StampedFileTractWriter {
    
    public static void stamp(Tract tract, String name) {
        if (!tract.contains(Tract.NAME)) {
            tract.put(Tract.NAME, name);
        }
        if (!tract.contains(Tract.DATE)) {
            tract.put(Tract.DATE, Long.toString(System.currentTimeMillis()));
        }
    }

    public static void store(String name, Tract tract, OutputStream out,
            Fetcher context) throws IOException {
        stamp(tract, name);
        TractWriter.store(tract, out, context);
    }

    public static void store(Tract tract, File file, Fetcher context)
            throws IOException {
        stamp(tract, file.getName());
        FileTractWriter.store(tract, file, context);
    }

    public static void store(String name, Tract tract, OutputStream out,
            StringFinder context) throws IOException {
        store(name, tract, out, context.getUnderlyingFetcher());
    }

    public static void store(Tract tract, File file, StringFinder context)
            throws IOException {
        store(tract, file, context.getUnderlyingFetcher());
    }
}
