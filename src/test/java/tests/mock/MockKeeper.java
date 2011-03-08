package tests.mock;

import org.stringtree.finder.ObjectKeeper;
import org.stringtree.template.StringCollector;

public class MockKeeper extends MockObjectFinder implements ObjectKeeper {

    public MockKeeper(StringCollector out) {
        super(out);
    }

    public MockKeeper(StringCollector out, Object result) {
        super(out, result);
    }

    public void put(String name, Object value) {
        log("put('" + name + "','" + value + "')");
    }

    public void remove(String name) {
        log("remove('" + name + "')");
    }

    public void clear() {
        log("clear()");
    }

    public void lock() {
        log("lock()");
    }
    
    public boolean isLocked() {
        log("isLocked()");
        return false;
    }
}
