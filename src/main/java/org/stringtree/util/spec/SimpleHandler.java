package org.stringtree.util.spec;

import java.util.Iterator;
import java.util.List;

import org.stringtree.finder.StringFinder;

public abstract class SimpleHandler implements Handler {
    
    protected Initialiser list = new Initialiser();

    public void open(StringFinder context) {
        list.open();
    }

    public void close(StringFinder context) {
        list.close(context);
    }

	public Object parse(String name, Object value) {
        Object ret = value;

        if (value instanceof CreatedHere) {
            ret = parseCreated(name, value);
        } else if (value instanceof String) {
            ret = parseString(name, (String) value);
        }

        return ret;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object parseCreated(String name, Object value) {
        List<Object> list = new SpecList<Object>();
        Iterator<Object> it = ((CreatedHere) value).iterator();
        while (it.hasNext()) {
            list.add(parse(name, it.next()));
        }

        return list;
    }

    public Object parseString(String name, String value) {
        return null; // override me!
    }
}
