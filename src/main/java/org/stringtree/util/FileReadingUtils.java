package org.stringtree.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class FileReadingUtils {
    
    public static String readRawFile(File file, boolean reportExceptions) {
        String ret = null;
        try {
            FileReader fin = new FileReader(file);
            ret = ReaderUtils.readReader(fin);
            fin.close();
        } catch (IOException ioe) {
            if (reportExceptions) {
                ioe.printStackTrace();
            }
        }

        return ret;
    }

    public static String readRawFile(File file) {
        return readRawFile(file, false);
    }

    public static String readFile(File file) {
        return StringUtils.nullToEmpty(readRawFile(file));
    }

    public static String readFile(File root, String filename) {
        return readFile(new File(root, filename));
    }

    public static String readFile(String root, String filename) {
        return readFile(new File(new File(root), filename));
    }

    public static String readFile(String filename) {
        return readFile(new File(filename));
    }
    
    /** A fixed size buffer, eww, that's like all those old C programs. 
     *  Buffer overrun city, here we come :(
     */
    public static byte[] readBytes(File file, boolean reportExceptions) {
        byte[] ret = null;
        try {
            byte[] buf = new byte[32767];
            InputStream fin = new FileInputStream(file);
            int len = fin.read(buf);
            fin.close();
            
            ret = new byte[len];
            for (int i = 0; i < len; ++i) {
                ret[i] = buf[i];
            }
        } catch (IOException ioe) {
            if (reportExceptions) {
                ioe.printStackTrace();
            }
        }

        return ret;
    }
    
    public static byte[] readBytes(File file) {
        return readBytes(file, true);
    }
    
    public static byte[] readBytes(String filename) {
        return readBytes(new File(filename));
    }
}
