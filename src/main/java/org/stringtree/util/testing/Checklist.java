package org.stringtree.util.testing;

import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Checklist<T> {
    public static final int SILENT = 0;
    public static final int NORMAL = 1;
    public static final int VERBOSE = 2;
    
    private T[] items;
    private int[] ticks; // note: extra slot at the end to represent unknown items
    private int verbose = NORMAL;

    public Checklist(int verbose, T... items) {
        set(items);
        setVerbose(verbose);
    }

    public Checklist(Collection<T> items, int verbose) {
        set(items);
        setVerbose(verbose);
    }

    public Checklist(Collection<T> items) {
        set(items);
    }
    
    public Checklist(T... items) {
        set(items);
    }

    private void set(T[] array) {
        ticks = new int[array.length + 1];
        items = array;
        reset();
    }

    @SuppressWarnings("unchecked")
    private void set(Collection<T> collection) {
        int n = collection.size();
        ticks = new int[n + 1];
        items = (T[])new Object[n];
        int i = 0;
        for (T t : collection) {
            items[i++] = t;
        }
    }

    public void reset() {
        for (int i = 0; i < ticks.length; ++i) {
            ticks[i] = 0;
        }
    }

    public void setVerbose(int verbose) {
        this.verbose = verbose;
    }

    private boolean match(Object a, Object b) {
        return (a == null) ? b == null : a.equals(b);
    }

    private int find(T obj) {
        int ret = items.length;

        for (int i = 0; i < items.length; ++i) {
            if (match(items[i], obj)) {
                if (verbose > NORMAL) {
                    System.out.println("Checklist object '" + obj
                            + "' matched item " + (i + 1) + " / "
                            + items.length);
                }

                ret = i;
            }
        }

        if (ret == items.length) {
            if (verbose > SILENT) {
                System.out.println("Checklist object   '" + obj
                        + "' failed to match ");
            }
        }

        return ret;
    }

    public void tick(T o) {
        ++ticks[find(o)];
    }

    public boolean isChecked(T o) {
        return ticks[find(o)] > 0;
    }

    public boolean allCheckedAtLeastOnce() {
        boolean ret = true;

        for (int i = 0; i < items.length; ++i) {
            if (ticks[i] == 0) {
                ret = false;
                if (verbose > SILENT) {
                    System.out.println("Checklist Failure: '" + items[i] + "' never checked");
                }
            }
        }

        return ret;
    }

    public boolean anyCheckedMoreThanOnce() {
        boolean ret = false;

        for (int i = 0; i < items.length; ++i) {
            if (ticks[i] > 1) {
                ret = true;
                if (verbose > SILENT) {
                    System.out.println("Checklist Failure: '" + items[i] + 
                        "' checked " + ticks[i] + " times");
                }
            }
        }

        return ret;
    }

    public boolean anyUnknownItemsChecked() {
        boolean ret = ticks[items.length] > 0;
        if (verbose > SILENT) {
            if (ret) {
                System.out.println("Checklist Failure: unknown item(s) checked "
                        + ticks[items.length] + " times");
            }
        }
        return ret;
    }

    public boolean allAndOnlyOnce() {
        return allCheckedAtLeastOnce() && !anyCheckedMoreThanOnce()
                && !anyUnknownItemsChecked();
    }
    
    public boolean check() {
        return allAndOnlyOnce();
    }

    public String toString() {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < items.length; ++i) {
            ret.append("'");
            ret.append(items[i]);
            ret.append("' [");
            ret.append(ticks[i]);
            ret.append("]\n");
        }
        ret.append("'unknown' [");
        ret.append(ticks[items.length]);
        ret.append("]\n");
        return ret.toString();
    }

    public void consider(T... array) {
        reset();
        for (T t : array) {
            tick(t);
        }
    }

    public void consider(Iterator<T> it) {
        reset();
        while (it.hasNext()) {
            tick(it.next());
        }
    }
    
    public void consider(Enumeration<T> it) {
        reset();
        while (it.hasMoreElements()) {
            tick(it.nextElement());
        }
    }
    
    public void consider(Collection<T> c) {
        consider(c.iterator());
    }

    public boolean check(T... array) {
        consider(array);
        return check();
    }

    public boolean check(Iterator<T> it) {
        consider(it);
        return check();
    }

    public boolean check(Enumeration<T> it) {
        consider(it);
        return check();
    }

    public boolean check(Collection<T> c) {
        consider(c);
        return check();
    }


    @SuppressWarnings("unchecked")
	public static boolean compareSubset(int n, Object[] expected, Collection actual) {
    	return compareSubset(n, Arrays.asList(expected), actual);
    }

    @SuppressWarnings("unchecked")
	public static boolean compareSubset(int n, Collection expected, Collection actual) {
    	if (actual.size() != n) return false;
    	
    	Set<Object> had = new HashSet<Object>(n);
    	
    	Iterator it = actual.iterator();
    	while (it.hasNext()) {
    	    Object obj = it.next();
    		if (!expected.contains(obj) || had.contains(obj)) return false;
    		had.add(obj);
    	}
    	
    	return true;
    }
}