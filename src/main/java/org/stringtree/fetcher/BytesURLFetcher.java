package org.stringtree.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.stringtree.Fetcher;
import org.stringtree.util.URLReadingUtils;

public class BytesURLFetcher implements Fetcher {

    private URL root;

    public BytesURLFetcher(URL root) {
        this.root = root;
    }

    public BytesURLFetcher(String root) throws IOException {
        if (!root.endsWith("/")) root += "/";
        this.root = URLReadingUtils.findURL(root, "file");
    }

    public Object getObject(String name) {
        Object ret = null;
        URL remote = null;
        try {
            if (name.startsWith("/")) name = name.substring(1);
            remote = new URL(root, name);
            ret = URLReadingUtils.readRawURLBytes(remote);
        } catch (MalformedURLException e) {
            /* don't complain, just return null */
        } catch (IOException e) {
            /* don't complain, just return null */
        }
        return ret;
    }
    
    public String toString() {
        return "BytesURLFetcher(" + root + ")";
    }
}
