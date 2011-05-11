package org.stringtree.util;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.stringtree.Fetcher;
import org.stringtree.Listable;
import org.stringtree.util.sort.SortedIteratorIterator;

class DefaultDumpLine implements DumpLine {

    public void line(Object key, Object value, String indent, StringBuffer out) {
        if (null != indent) out.append(indent);
        out.append("'");
        out.append(key);
        out.append("'->'");
        out.append(value);
        out.append("'\n");
    }

    public void line(Object key, Object value, String indent, PrintStream out) {
        if (null != indent) out.print(indent);
        out.print("'");
        out.print(key);
        out.print("'->'");
        out.print(value);
        out.println("'");
    }
}

@SuppressWarnings("rawtypes")
public class Diagnostics {
    
    public static final DumpLine dflDumpLine = new DefaultDumpLine();
    
	public static void dumpMap(Map<String, ?> map, String title, DumpLine line, OutputStream stream) {
	    Set<Object> exclude = new HashSet<Object>();
	    exclude.add(map);
		PrintStream out;

		if (stream != null) {
		    out = StreamUtils.ensurePrint(stream);
		} else {
		    out = System.err;
		}

		out.println("Map: start of [" + title + "] ---------------");
		dumpMap(map, line, exclude, "", out);
		out.println("Map: end of   [" + title + "] ---------------");
	}

    @SuppressWarnings("unchecked")
	private static void dumpMap(Map<String, ?> map, DumpLine line, 
            Set<Object> exclude, String indent, PrintStream out) {
        Set<String> fetchers = new HashSet<String>();

        Iterator<String> iterator = map.keySet().iterator();
        Iterator<String> keys = new SortedIteratorIterator<String>(iterator);
        while (keys.hasNext()) {
        	String key = keys.next();
        	Object value = map.get(key);
            if (value instanceof Fetcher || value instanceof Map) {
                if (null == exclude) exclude = new HashSet<Object>();
                if (!exclude.contains(value)) {
                    fetchers.add(key);
                    exclude.add(value);
                }
            } else {
                line.line(key, value, indent, out);
            }
        }
        Iterator it = fetchers.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object obj = map.get(key);
            String pad = indent + " ";
            out.print(indent);
            out.print(key);
            out.print("(");
            out.print(obj);
            out.println(")->");
            if (obj instanceof Fetcher) {
                dumpFetcher((Fetcher)obj, line, exclude, pad, out);
            } else if (obj instanceof Map) {
                dumpMap((Map<String, Object>)obj, line, exclude, pad, out); 
            } else {
                out.print(pad);
                out.print("unknown (");
                out.print(obj);
                out.println(")");
            }
        }
    }

    public static void dumpMap(Map<String, ?> map, String title, PrintStream out) {
        dumpMap(map, title, dflDumpLine, out);
    }

    public static void dumpMap(Map<String, ?> map, String title) {
        dumpMap(map, title, dflDumpLine, null);
    }


    public static void dumpFetcher(Fetcher fetcher, String title, DumpLine line, OutputStream stream) {
        Set<Object> exclude = new HashSet<Object>();
        exclude.add(fetcher);
        PrintStream out;

        if (stream != null) {
            out = StreamUtils.ensurePrint(stream);
        } else {
            out = System.err;
        }

        out.println("Fetcher: start of [" + title + "] ---------------");
        dumpFetcher(fetcher, line, exclude, "", out);
        out.println("Fetcher: end of   [" + title + "] ---------------");
    }

    @SuppressWarnings("unchecked")
	private static void dumpFetcher(Fetcher fetcher, DumpLine line, Set<Object> exclude, String indent, PrintStream out) {
        Set<String> fetchers = new HashSet<String>();
        
        Listable listable = null;
        if (fetcher instanceof Listable) {
            listable = (Listable)fetcher;
        } else {
            listable = (Listable)fetcher.getObject(Listable.LIST);
        }
        
        if (listable != null) {
            Iterator keys = new SortedIteratorIterator<Object>(listable.list());
            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = fetcher.getObject(key);
                if (value instanceof Fetcher || value instanceof Map) {
                    if (null == exclude) exclude = new HashSet<Object>();
                    if (!exclude.contains(value)) {
                        fetchers.add(key);
                        exclude.add(value);
                    }
                } else {
                    line.line(key, value, indent, out);
                }
            }
            Iterator it = fetchers.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object obj = fetcher.getObject(key);
                String pad = indent + " ";
                out.print(indent);
                out.print(key);
                out.print("(");
                out.print(obj);
                out.println(")->");
                if (obj instanceof Fetcher) {
                    dumpFetcher((Fetcher)obj, line, exclude, pad, out);
                } else if (obj instanceof Map) {
                    dumpMap((Map)obj, line, exclude, pad, out); 
                } else {
                    out.print(pad);
                    out.print("unknown (");
                    out.print(obj);
                    out.println(")");
                }
            }
        } else {
            out.println("unable to list context");
        }
    }

    public static void dumpFetcher(Fetcher fetcher, String title) {
        dumpFetcher(fetcher, title, dflDumpLine, null);
    }

	public static String showList(List list) {
		return showIterator(list.iterator());
	}

	public static String showIterator(Iterator it) {
		StringBuffer ret = new StringBuffer("[");
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj != null) {
				ret.append(obj.toString());
			}

			if (it.hasNext()) {
				ret.append(",");
			}
		}
		ret.append("]");

		return ret.toString();
	}

	public static void whereAmI(PrintStream out) {
		try {
			throw new Exception("You are Here");
		} catch(Exception e) {
			e.printStackTrace(out);
		}
	}

    public static void whereAmI() {
        whereAmI(System.out);
    }

    public String showArray(Object[] array) {
        StringBuffer ret = new StringBuffer("[");
        for (int i = 0; i < array.length; ++i) {
            ret.append(" '");
            ret.append(array[i]);
            ret.append("'");
        }
        ret.append(" ]");
        return ret.toString();
    }

    public static String tap(String title, String value) {
        System.err.println(title + "=" + value);
        return value;
    }

    public static Object tap(String title, Object value) {
        System.err.println(title + "=" + value);
        return value;
    }

    public static int tap(String title, int value) {
        System.err.println(title + "=" + value);
        return value;
    }
}
