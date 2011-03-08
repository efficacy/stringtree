package tests.spec;

import org.stringtree.finder.StringFinder;

public class DummyWithBoth extends DummyClass {
    
    public Object value = "unset";
    public String key = "dummy.value";

    public DummyWithBoth(String key) {
        this.key = key;
    }

    public boolean equals(Object other) {
        return other instanceof DummyWithBoth
                && ((DummyWithBoth) other).value.equals(value);
    }
    
    @Override
    public int hashCode() {
        return "$DummyWithBoth$".hashCode() + value.hashCode();
    }

    public void init(StringFinder context) {
        ++requests;
        value = context.getObject(key);
    }

    public void init(String value) {
        ++requests;
        this.value = value;
    }

    public void init() {
        ++requests;
        value = "unknown";
    }

    public String toString() {
        return "DummyWithBoth:" + value;
    }
}
