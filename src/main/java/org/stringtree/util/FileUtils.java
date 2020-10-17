package org.stringtree.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class FileUtils {

    public static File ensureDirectory(File dir) {
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.err.println("could not create directory path for [" + dir.getAbsolutePath() + "]");
            }
        }
        return dir;
    }

    public static File ensureDirectory(String name) {
        return ensureDirectory(new File(name));
    }

    public static File ensureDirectory(File root, String name) {
        return ensureDirectory(new File(root, name));
    }

    public static void delete(File file) {
        if (!file.delete()) {
            System.err.println("could not delete file [" + file.getAbsolutePath() + "]");
        }
    }

    /**
     * extract the local path information from a URL in a form suitable for
     * contructing a File
     *
     * @return a string with the '/' replacd by local separators
     */
    public static String getLocalFilePath(String path) {
        String ret = path;

        if (File.separatorChar != '/' && path.indexOf("/") >= 0) {
            StringBuffer buf = new StringBuffer(path.length());
            CharacterIterator it = new StringCharacterIterator(path);
            for (char c = it.first(); c != CharacterIterator.DONE; c = it
                    .next()) {
                if (c == '/') {
                    c = File.separatorChar;
                }
                buf.append(c);
            }

            ret = buf.toString();
        }

        return ret;
    }

    public static void copy(File source, File dest) {
        File parent = dest.getParentFile();
        if (!parent.isDirectory()) {
            FileUtils.ensureDirectory(parent);
        }

        try (
        	FileChannel in = new FileInputStream(source).getChannel();
            FileChannel out = new FileOutputStream(dest).getChannel();
        ){
            copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy(FileChannel in, FileChannel out) throws IOException {
        long size = in.size();
        MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
        out.write(buf);
    }

}
