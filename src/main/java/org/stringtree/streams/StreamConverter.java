package org.stringtree.streams;

import java.io.IOException;
import java.io.InputStream;

public interface StreamConverter {
    ObjectFactory convert(InputStream in) throws IOException;
}
