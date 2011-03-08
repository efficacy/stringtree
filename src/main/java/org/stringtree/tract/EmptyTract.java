package org.stringtree.tract;

import java.util.Iterator;

import org.stringtree.Tract;
import org.stringtree.finder.EmptyStringKeeper;

public class EmptyTract extends EmptyStringKeeper implements Tract {

    public static final EmptyTract it = new EmptyTract();

    public void setContent(String content) {
        // this method intentionally left blank
    }

    public String getContent() {
        return "";
    }

    public boolean hasContent() {
        return false;
    }

    public boolean contains(String name) {
        return false;
    }

    public String get(String name, String dfl) {
        return dfl;
    }

    public String getCharacterSet() {
        return "UTF-8";
    }

    public Iterator<String> list() {
        return null;
    }

    public String toString() {
        return "{}";
    }

    public boolean equals(Object other) {
        if (!(other instanceof Tract))
            return false;
        Tract tract = (Tract) other;
        return "".equals(tract.getContent()) && !tract.list().hasNext();
    }
    
    @Override
    public int hashCode() {
        return "$Empty Tract$".hashCode();
    }
}
