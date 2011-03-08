package org.stringtree.streams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.tract.MapTract;
import org.stringtree.tract.StreamTractReader;
import org.stringtree.util.StreamUtils;

public class CannedStreamTractFactory implements ObjectFactory {
    private byte[] bytes;
    
    public CannedStreamTractFactory(InputStream in) {
        try {
            bytes = StreamUtils.readStreamBytes(in, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object create(Fetcher context) {
        if (null == bytes) return null;
        Tract ret = null;
        try {
            ret = new MapTract();
            StreamTractReader.load(ret, new ByteArrayInputStream(bytes), context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
