package org.stringtree.URL;

import java.net.URLStreamHandler;

public class ClassLoaderResourceHandlerFactory implements ProtocolHandlerFactory {

    public URLStreamHandler make(Object parameter) {
        return new ClassLoaderResourceHandler((ClassLoader)parameter);
    }

}
