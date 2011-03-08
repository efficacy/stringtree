package org.stringtree.URL;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class ClassLoaderResourceHandler extends URLStreamHandler {
    
    public static final String PROTOCOL = "classpath";
    
    private ClassLoader loader;
    
    public ClassLoaderResourceHandler(ClassLoader loader) {
        if (loader == null) loader = Thread.currentThread ().getContextClassLoader();
        this.loader = loader;
    }
    
    protected URLConnection openConnection(URL url) throws IOException {
        return new ClassLoaderResourceURLConnection(url, loader);
    }
    
    protected String toExternalForm (final URL url) {
        return PROTOCOL.concat(":").concat(url.getFile());
    }
    
    private static final class ClassLoaderResourceURLConnection
        extends URLConnection {
    
        private ClassLoader loader;
    
        public ClassLoaderResourceURLConnection(URL url, ClassLoader loader) {
            super (url);
            this.loader = loader;
        }

        public void connect () {
            // Do nothing, as we will look for the resource in getInputStream().
        }
        
        public InputStream getInputStream() throws IOException {
            String resourceName = url.getFile();
            if (resourceName.startsWith ("x/")) {
                resourceName = resourceName.substring (1);
            }
            
            InputStream result = loader.getResourceAsStream (resourceName);
            
            if (result == null) {
                throw new IOException ("resource [" + resourceName + "] could "
                + "not be found by classloader [" + loader.getClass ().getName ()
                + "]");
            }
            
            return result;
        }
    }
}