package tests;

import org.stringtree.fetcher.MapFetcher;

public class ToWord extends MapFetcher {
    
    public ToWord() {
        put("1", "one");
        put("2", "two");
        put("3", "three");
        put("4", "four");
        put("5", "five");
        put("6", "six");
        put("7", "seven");
        put("8", "eight");
        put("9", "nine");
    }

    public Object getObject(String name) {
        Object ret = super.getObject(name);
        if (ret == null)
            ret = "";

        return ret;
    }
}
