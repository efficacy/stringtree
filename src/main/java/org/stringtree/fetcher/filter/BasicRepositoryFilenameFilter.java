package org.stringtree.fetcher.filter;

import java.io.File;
import java.io.IOException;

public class BasicRepositoryFilenameFilter implements RepositoryFilenameFilter {
    
    public boolean accept(File dir, String name) {
        return true;
    }

    public boolean contains(File dir, String localname) {
        File file = fileToRead(dir, localname);
        return file.canRead();
    }

    public String localName(File dir, File file) {
        String name = leafName(dir, file);
        String ret = internalName(name);
        return ret;
    }

    protected String externalName(String name) {
        return name;
    }

    protected String internalName(String name) {
        return name;
    }

    public File fileToRead(File dir, String localname) {
        return new File(dir, externalName(localname));
    }

    public File fileToWrite(File dir, String localname) {
        return new File(dir, externalName(localname));
    }

    public static String leafName(File dir, File file) {
        String leafname = file.getName();
        try {
            leafname = file.getCanonicalPath();
            String prefix = dir.getCanonicalPath();
            String sep = System.getProperty("file.separator");
            if (!prefix.endsWith(sep)) {
                prefix = prefix + sep;
            }
            if (leafname.startsWith(prefix)) {
                leafname = leafname.substring(prefix.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return leafname;
    }
}
