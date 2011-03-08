package org.stringtree.http;

import java.io.IOException;

public class Wget {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("usage: java -cp stringtree-httpclient.jar org.stringtree.http.Wget URL {destfile}");
            return;
        }
        
        String url = args[0];
        String destfile = null;
        if (args.length < 2) {
            destfile = leaf(url);
        } else {
            destfile = args[1];
        }

        HTTPClient client = new HTTPClient();
        client.getFile(url, destfile);
    }

    private static String leaf(String url) {
        int query = url.indexOf('?');
        if (query > -1) {
            url = url.substring(0, query);
        }
        int slash = url.lastIndexOf('/');
        String leaf = url.substring(slash+1);
        if ("".equals(leaf)) {
            System.out.println("Warning, could not determine filename, using 'index.html'");
            leaf = "index.html";
        }
        return leaf;
    }
}
