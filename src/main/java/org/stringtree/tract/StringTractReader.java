package org.stringtree.tract;

import java.io.IOException;
import java.io.StringReader;

import org.stringtree.Tract;
import org.stringtree.util.spec.SpecReader;

public class StringTractReader {
    
    public static void read(Tract tract, String source) {
        try {
            ReaderTractReader.load(tract, new StringReader(source), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(Tract tract, String[] headers, String body) {
        tract.clear();
        SpecReader.load(tract, headers);
        tract.setContent(body);
    }

    public static void read(Tract tract, String[] headers) {
        read(tract, headers, "");
    }
}
