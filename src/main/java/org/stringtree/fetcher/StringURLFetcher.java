package org.stringtree.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.stringtree.Fetcher;
import org.stringtree.util.URLReadingUtils;

public class StringURLFetcher implements Fetcher {

    private URL root;

    public StringURLFetcher(URL root) {
        this.root = root;
    }

    public Object getObject(String name) {
        Object ret = null;
        URL remote = null;
        try {
            if (name.startsWith("/")) name = name.substring(1);
            remote = new URL(root, name);
            ret = URLReadingUtils.readRawURL(remote);
        } catch (MalformedURLException e) {
            /* don't complain, just return null */
        } catch (IOException e) {
            /* don't complain, just return null */
        }
        return ret;
    }
}
