package org.stringtree.fetcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.stringtree.Fetcher;
import org.stringtree.streams.ObjectFactory;
import org.stringtree.streams.StreamConverter;

public class SuffixDefinition {
    private String suffix;
    private StreamConverter converter;
    
    public SuffixDefinition(String suffix, StreamConverter converter) {
        this.suffix = suffix;
        this.converter = converter;
    }

    public String getSuffix() {
        return suffix;
    }

    public StreamConverter getConverter() {
        return converter;
    }

    public boolean match(String filename) {
        return filename.endsWith("." + suffix);
    }
    
    public Object read(InputStream in, Fetcher context) throws IOException {
        ObjectFactory ret = converter.convert(in);
        return (null==ret) ? null : ret.create(context);
    }
    
    public Object read(File in, Fetcher context) throws IOException {
        return read(new FileInputStream(in), context);
    }
    
    public Object read(URL in, Fetcher context) throws IOException {
        return read(in.openStream(), context);
    }
}
