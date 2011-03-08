package tests.mock;

import org.stringtree.Repository;
import org.stringtree.template.StringCollector;

public class MockRepository extends MockFetcher implements Repository {
    
    public MockRepository(StringCollector out) {
        super(out);
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