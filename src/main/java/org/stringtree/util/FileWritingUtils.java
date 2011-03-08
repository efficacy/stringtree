package org.stringtree.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileWritingUtils {
    
    public static void writeFile(File file, String content) throws IOException {
        Writer out = new FileWriter(file);
        out.write(content);
        out.flush();
        out.close();
    }
    
    public static void writeFile(File file, byte[] content) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        out.write(content);
        out.flush();
        out.close();
    }

    public static void writeFile(String fileName, String content)
            throws IOException {
        writeFile(new File(fileName), content);
    }

    public static void writeFile(String fileName, byte[] content)
            throws IOException {
        writeFile(new File(fileName), content);
    }

    public static void writeFile(File dir, String fileName, String content)
            throws IOException {
        writeFile(new File(dir, fileName), content);
    }

    public static void writeFile(File dir, String fileName, byte[] content)
            throws IOException {
        writeFile(new File(dir, fileName), content);
    }
}
