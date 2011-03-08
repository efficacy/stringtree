package org.stringtree.util.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class Sorter2<T extends Comparable> {
    
    public List<T> sort(Object obj) {
        List<T> list = null;
        if (obj instanceof List) {
            list = (List<T>)obj;
        } else if (obj instanceof Collection){
            Collection<T> collection = (Collection<T>)obj;
            list = new ArrayList<T>(collection.size());
            list.addAll(collection);
        } else if (obj instanceof Iterator) {
            Iterator it = (Iterator)obj;
            list = new ArrayList<T>();
            while (it.hasNext()) {
                list.add((T)it.next());
            }
        } else if (obj instanceof Iterable) {
            Iterable<T> it = (Iterable<T>)obj;
            list = new ArrayList<T>();
            for (T t : it) {
                list.add(t);
            }
        } else if (obj instanceof Comparable[]) {
            Object[] array = (Object[])obj;
            list = new ArrayList<T>();
            for (int i = 0; i < array.length; ++i) {
                list.add((T)array[i]);
            }
        }
        
        if (list != null) {
            Collections.sort(list);
        }
        return list;
    }
}
