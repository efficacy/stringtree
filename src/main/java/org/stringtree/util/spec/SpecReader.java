package org.stringtree.util.spec;

import java.io.IOException;
import java.io.Reader;

import org.stringtree.Fetcher;
import org.stringtree.finder.StringFinder;
import org.stringtree.util.StringUtils;

public class SpecReader {
    
    public static void load(StringFinder context, StringFinder values) {
        if (values != null) {
            SpecProcessor processor = new SpecProcessor(context);
            processor.load(values);
        }
    }

    public static void load(StringFinder context, boolean autoclose, Reader in) {
        if (in != null) {
            SpecProcessor processor = new SpecProcessor(context);
            processor.load(in, autoclose);
        }
    }

    public static void load(StringFinder context, Reader in) {
        load(context, true, in);
    }

    public static void load(StringFinder context, boolean autoclose, String from) throws IOException {
        if (!StringUtils.isBlank(from)) {
            SpecProcessor processor = new SpecProcessor(context);
            processor.load(from, autoclose);
        }
    }

    public static void load(StringFinder context, String from) throws IOException {
        load(context, true, from);
    }

    public static void load(Fetcher context, String[] from) {
        SpecProcessor processor = new SpecProcessor(context);
        processor.load(from);
    }
}
