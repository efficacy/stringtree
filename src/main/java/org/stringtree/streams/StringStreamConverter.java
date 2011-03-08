package org.stringtree.streams;

import java.io.IOException;
import java.io.InputStream;

import org.stringtree.util.StreamUtils;

public class StringStreamConverter implements StreamConverter {

    private String charset;

    public StringStreamConverter(String charset) {
        this.charset = charset;
    }

    public StringStreamConverter() {
        this(null);
    }

    public ObjectFactory convert(InputStream in) throws IOException {
        if (null==charset) return new ValueObjectFactory(StreamUtils.readStream(in));
        
        byte[] bytes = StreamUtils.readStreamBytes(in, true);
        return new ValueObjectFactory(new String(bytes, charset));
    }

}
