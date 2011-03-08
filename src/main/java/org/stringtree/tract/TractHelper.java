package org.stringtree.tract;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.stringtree.Tract;
import org.stringtree.util.Utils;

public class TractHelper {
    
    public static Tract convert(Object obj, boolean clone) {
        Tract ret;

        if (obj instanceof Tract) {
            ret = (Tract) obj;
            if (clone) {
                ret = new MapTract(ret);
            }
        } else if (obj != null) {
            ret = new MapTract(obj.toString());
        } else {
            ret = new MapTract();
        }

        return ret;
    }

    public static Tract convert(Object obj) {
        return TractHelper.convert(obj, false);
    }

    public static boolean equals(Tract a, Tract b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null)
            return false;

        Set<String> keys = new HashSet<String>();
        addAll(keys, a.list());
        addAll(keys, b.list());
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            String key = (String)obj;
            if (!key.startsWith("~")
                    && !Utils.same(a.getObject(key), b.getObject(key)))
                return false;
        }
        return true;

    }

    private static void addAll(Collection<String> coll, Iterator<String> it) {
        while (it.hasNext()) {
            coll.add(it.next());
        }
    }
}
