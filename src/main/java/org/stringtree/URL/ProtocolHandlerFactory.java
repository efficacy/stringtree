package org.stringtree.URL;

import java.net.URLStreamHandler;

public interface ProtocolHandlerFactory {
    URLStreamHandler make(Object parameter);
}
