package org.stringtree.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ResourceUtils {
    
    public static Reader convertToReader(InputStream in) {
        Reader ret = null;
        if (in != null) {
            ret = new InputStreamReader(in);
        }
        return ret;
    }

    public static InputStream getResourceStream(ClassLoader loader, String filename) {
        return loader.getResourceAsStream(filename);
    }

    public static InputStream getResourceStream(Object self, String filename) {
        return getResourceStream(self.getClass().getClassLoader(), filename);
    }

    public static Reader getResourceReader(ClassLoader loader, String filename) {
        return convertToReader(getResourceStream(loader, filename));
    }

    public static Reader getResourceReader(Object self, String filename) {
        return convertToReader(getResourceStream(self, filename));
    }

    public static String readRawResource(ClassLoader loader, String filename) throws IOException {
        String ret = null;
        InputStream in = getResourceStream(loader, filename);
        if (in != null) {
            ret = StreamUtils.readStream(in);
        }

        return ret;
    }

    public static String readRawResource(Object self, String filename) throws IOException {
        return readRawResource(self.getClass().getClassLoader(), filename);
    }

    public static String readResource(ClassLoader loader, String filename) {
        try {
            return StringUtils.nullToEmpty(readRawResource(loader, filename));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String readResource(Object self, String filename) {
        return readResource(self.getClass().getClassLoader(), filename);
    }

    /**
     * for use during constructors, when 'this' is not available // // makes a
     * guess that a dummy object is in the same classloader as the caller //
     * this assumprion may be somewhat dodgy, use with care ...
     */
    public static String readResource(String filename) {
        return readResource(ResourceUtils.class.getClassLoader(), filename);
    }
}
