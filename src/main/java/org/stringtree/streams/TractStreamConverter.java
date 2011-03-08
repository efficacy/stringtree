package org.stringtree.streams;

import java.io.IOException;
import java.io.InputStream;

public class TractStreamConverter implements StreamConverter {

    public ObjectFactory convert(InputStream in) throws IOException {
        if (null == in) throw new IOException("TractStreamConverter cannot convert a null stream");
        return new CannedStreamTractFactory(in);
    }

}
