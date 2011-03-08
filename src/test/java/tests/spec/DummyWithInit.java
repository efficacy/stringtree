package tests.spec;

import org.stringtree.finder.StringFinder;

public class DummyWithInit extends DummyClass {
    
    public Object value = "unset";

    public boolean equals(Object other) {
        return other instanceof DummyWithInit
                && ((DummyWithInit) other).value.equals(value);
    }
    
    @Override
    public int hashCode() {
        return "$DummyWithInit$".hashCode() + value.hashCode();
    }

    public void init(StringFinder context) {
        ++requests;
        value = context.getObject("dummy.value");
    }

    public String toString() {
        return "DummyWithInit:" + value;
    }
}
