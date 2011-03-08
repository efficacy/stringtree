package org.stringtree.util.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.stringtree.util.FileUtils;
import org.stringtree.util.WriterUtils;

public class FileLogger implements Logger {
    
    protected File dest;

    public FileLogger(String filename) {
        dest = new File(filename);
        FileUtils.ensureDirectory(dest);
    }

    public void logPart(String text) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(dest, true);
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } finally {
            if (null != writer) {
                WriterUtils.close(writer);
            }
        }
    }

    public void log(String text) {
        logPart(text + "\n");
    }
}
