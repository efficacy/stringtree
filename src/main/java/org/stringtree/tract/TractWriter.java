package org.stringtree.tract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;

import org.stringtree.Fetcher;
import org.stringtree.Tract;
import org.stringtree.finder.StringFinder;
import org.stringtree.util.StringUtils;

public class TractWriter {
    
    public static void store(Tract tract, OutputStream out, Fetcher context)
            throws IOException {
        Properties tmp = new Properties();
        Iterator<String> it = tract.list();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (!Tract.CONTENT.equals(key))
                tmp.put(key, tract.getObject(key));
        }
        tmp.store(out, null);

        out.write('\n');
        String content = tract.getContent();
        if (content != null) {
            String indirect = tract.get(Tract.CONTENT_LOCATION);

            if (!StringUtils.isBlank(indirect)) {
                File root = null;
                if (context != null)
                    root = (File) context.getObject(Tract.INDIRECT_ROOT);
                if (root == null)
                    root = new File(".");
                File file = new File(root, indirect);
                if (file.canWrite()) {
                    FileOutputStream fout = new FileOutputStream(file);
                    fout.write(content.getBytes(tract.getCharacterSet()));
                    fout.close();
                }
            } else {
                out.write(content.getBytes(tract.getCharacterSet()));
            }
        }
    }

    public static void store(Tract tract, OutputStream out, StringFinder context)
            throws IOException {
        store(tract, out, context.getUnderlyingFetcher());
    }

    public static String asString(Tract tract) {
        String ret = "";
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            store(tract, buf, (Fetcher) null);
            ret = buf.toString(tract.getCharacterSet());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return ret;
    }
}
