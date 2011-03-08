package org.stringtree.fetcher;

import java.util.Map;
import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.Repository;
import org.stringtree.Storer;
import org.stringtree.Tract;
import org.stringtree.finder.ObjectFinder;
import org.stringtree.util.MethodCallUtils;

public class StorerHelper {
    
    public static Storer find(Fetcher context, boolean complain) {
        if (context instanceof Storer) {
            return (Storer) context;
        }

        Storer ret = (Storer) context.getObject(Storer.STORE);
        if (ret == null && complain) {
            throw new ContextNotWritableException(context);
        }
        return ret;
    }

    public static Storer find(Fetcher context) {
        return find(context, true);
    }

    public static void put(Fetcher context, String name, Object value) {
        find(context).put(name, value);
    }

    public static void remove(Fetcher context, String name) {
        find(context).remove(name);
    }

    public static void clear(Fetcher context) {
        find(context).clear();
    }

    public static void putAll(Map<?,?> source, Storer dest) {
        for (Map.Entry<?,?> entry : source.entrySet()) {
            dest.put((String)entry.getKey(), entry.getValue());
        }
    }

    protected static void putAllMap(Map<?,?> source, Storer dest) {
        putAll(source, dest);
    }

    protected static void putAllMap(Map<?,?> source, Repository dest) {
        putAll(source, (Storer) dest);
    }

    protected static void putAllMap(Map<?,?> source, Fetcher dest) {
        putAll(source, find(dest));
    }

    public static void putAll(Map<?,?> source, Fetcher dest) {
        putAllMap(source, dest);
    }

    public static void putAll(Tract source, Storer storer) {
        Iterator<String> it = source.list();
        while (it.hasNext()) {
            String key = (String) it.next();
            storer.put(key, source.getObject(key));
        }
        storer.put(Tract.CONTENT, source.getObject(Tract.CONTENT));
    }

    public static void putAll(Tract source, Fetcher dest) {
        Storer storer = find(dest);
        putAll(source, storer);
    }

    public static void putAll(Fetcher source, Storer storer) {
        Iterator<String> it = ListableHelper.list(source);
        while (it.hasNext()) {
            String key = (String) it.next();
            storer.put(key, source.getObject(key));
        }
    }

    public static void putAll(Fetcher source, Repository dest) {
        putAll(source, (Storer) dest);
    }

    public static void putAll(Fetcher source, Fetcher dest) {
        putAll(source, find(dest));
    }

    public static Object ensure(Fetcher context, String name, Object dfl) {
        Object ret = context.getObject(name);
        if (null != ret) return ret;

        put(context, name, dfl);
        return dfl;
    }

    public static Object ensureAndInit(Fetcher context, String name, Object dfl) {
        Object ret = context.getObject(name);
        if (null != ret) return ret;

        put(context, name, dfl);
        MethodCallUtils.call(dfl, "init", context);
        return dfl;
    }

    public static void put(ObjectFinder context, String key, Object value) {
        put(context.getUnderlyingFetcher(), key, value);
    }
}