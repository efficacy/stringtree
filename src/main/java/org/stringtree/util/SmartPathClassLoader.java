package org.stringtree.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class AcceptJarFiles implements FileFilter {
    
    public boolean accept(File file) {
        return !file.isDirectory() && file.getName().endsWith(".jar");
    }
}

public class SmartPathClassLoader extends URLClassLoader {
    protected static final URL[] prototype = new URL[0];
    protected static final FileFilter filter = new AcceptJarFiles();

    protected static URL here = null;
    static  {
        synchronized(SmartPathClassLoader.class) {
            try {
                here = URLReadingUtils.findURL(".");
            } catch (IOException e) { 
                /* can never happen? */
                e.printStackTrace();
            }
        }
    }

    public SmartPathClassLoader(String path, ClassLoader parent) {
        super(split(path), parent);
    }

    public SmartPathClassLoader(String path) {
        super(split(path));
    }

    public InputStream getResourceAsStream(String name) {
        return super.getResourceAsStream(name);
    }
    
    protected static URL[] split(String path) {
        List<URL> list = new ArrayList<URL>();
        StringTokenizer it = new StringTokenizer(path, ";");
        while (it.hasMoreTokens()) {
            try {
                String name = it.nextToken();
                File f = new File(name);
                if (f.isDirectory() && f.canRead()) {
                    File[] contents = f.listFiles(filter);
                    for (int i = 0; i < contents.length; ++i) {
                        String jar = contents[i].getCanonicalPath();
                        URL url = URLReadingUtils.findURL(jar);
                        list.add(url);
                    }
                    URL url = URLReadingUtils.findURL(name + "/");
                    list.add(url);
                } else {
                    URL url = URLReadingUtils.findURL(name);
                    list.add(url);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list.toArray(prototype);
    }

    public String toString() {
        StringBuffer ret = new StringBuffer("SmartPathClassLoader( ");
        URL[] urls = getURLs();
        for (int i = 0; i < urls.length; ++i) {
            ret.append(urls[i].toString());
            ret.append(" ");
        }
        ret.append(")");
        return ret.toString();
    }
}
