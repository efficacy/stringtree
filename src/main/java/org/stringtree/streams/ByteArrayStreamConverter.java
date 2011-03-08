package org.stringtree.streams;

import java.io.IOException;
import java.io.InputStream;

import org.stringtree.util.StreamUtils;

public class ByteArrayStreamConverter implements StreamConverter {

    public ObjectFactory convert(InputStream in) throws IOException {
        return new ValueObjectFactory(StreamUtils.readStreamBytes(in, true));
    }

}
