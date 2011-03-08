package org.stringtree.tract;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.stringtree.Tract;
import org.stringtree.finder.StringFinder;
import org.stringtree.util.FilenameEndsWithDotTractRecognizer;

public class FileTractReader {
    
    public static void load(Tract tract, File file, boolean isTract,
            StringFinder context) throws IOException {
        Reader in = new FileReader(file);
        if (isTract) {
            ReaderTractReader.load(tract, in, context);
        } else {
            ReaderTractReader.loadText(tract, in);
        }
        in.close();

        if (!tract.contains(Tract.NAME)) {
            tract.put(Tract.NAME, file.getName());
        }
        if (!tract.contains(Tract.DATE)) {
            tract.put(Tract.DATE, Long.toString(file.lastModified()));
        }
    }

    public static void load(Tract tract, File file, boolean isTract)
            throws IOException {
        load(tract, file, isTract, null);
    }

    public static void load(Tract tract, File file, TractRecognizer rec,
            StringFinder context) throws IOException {
        load(tract, file, rec.isTract(file), context);
    }

    public static void load(Tract tract, File file, StringFinder context)
            throws IOException {
        load(tract, file, new FilenameEndsWithDotTractRecognizer(), context);
    }

    public static void loadTract(Tract tract, File file, StringFinder context)
            throws IOException {
        load(tract, file, true, context);
    }

    public static void loadTextFile(Tract tract, File file) throws IOException {
        load(tract, file, false, null);
    }

    public static Tract load(File file, TractRecognizer rec, StringFinder context) {
        Tract ret = new MapTract();

        try {
            load(ret, file, rec, context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static Tract load(File file, StringFinder context) {
        return load(file, new FilenameEndsWithDotTractRecognizer(), context);
    }
}
