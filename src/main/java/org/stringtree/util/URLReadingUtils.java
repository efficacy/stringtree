package org.stringtree.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;

import org.stringtree.URL.ClassLoaderResourceHandlerFactory;
import org.stringtree.URL.ProtocolHandlerFactory;

public class URLReadingUtils {
    
    private static final Map<String,ProtocolHandlerFactory> handlers;
    static {
        synchronized(URLReadingUtils.class) {
            handlers = new HashMap<String, ProtocolHandlerFactory>();
            handlers.put("classpath", new ClassLoaderResourceHandlerFactory());
        }
    }
    
    public static URL findURL(String from, String dflProtocol, Object parameter) throws IOException {
        try {
            return new URL(from);
        } catch (MalformedURLException e) {
            URL url = findNonStandardURL(from, parameter);
            if (url == null) {
                url = findNonStandardURL(dflProtocol + ":" + from, parameter);
            }
            return url;
        }
    }

    public static URL findNonStandardURL(String from, Object parameter) throws IOException {
        int colon = from.indexOf (':');
        if (colon < 2) {
            return null;
        }

        String protocol = from.substring (0, colon);
        ProtocolHandlerFactory factory = handlers.get(protocol);
        if (factory != null) {
            URLStreamHandler handler = factory.make(parameter); 
            if (handler == null) {
                return null;
            }

            return new URL(null, from, handler);
        }

        return new URL(from);
    }

    public static URL findURL(String from, String dflProtocol) throws IOException {
        return findURL(from, dflProtocol, null);
    }

    public static URL findURL(String from) throws IOException {
        return findURL(from, "file", null);
    }

    public static String readRawURL(String name, Object parameter) throws IOException {
        URL url = findURL(name, "file", parameter);
        return readRawURL(url);
    }

    public static String readRawURL(String name) throws IOException {
        return readRawURL(name, null);
    }
    
    public static byte[] readRawURLBytes(URL url) throws IOException {
        InputStream in = url.openStream();
        return StreamUtils.readStreamBytes(in, true);
    }
    
    public static String readRawURL(URL url) throws IOException {
        InputStream in = url.openStream();
        return StreamUtils.readStream(in, true);
    }

    public static String readURL(String name, Object parameter) {
        String ret = "";
        try {
            ret = StringUtils.nullToEmpty(readRawURL(name, parameter));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String readURL(String name) {
        return readURL(name, null);
    }

    
    public static String readURL(URL url) {
        String ret = "";
        try {
            ret = StringUtils.nullToEmpty(readRawURL(url));
        } catch (IOException e) {
            // missing or invalue URL is not an error here
        }
        return ret;
    }
}
