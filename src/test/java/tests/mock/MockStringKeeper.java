package tests.mock;

import org.stringtree.finder.StringKeeper;
import org.stringtree.template.StringCollector;

public class MockStringKeeper extends MockKeeper implements StringKeeper {
    
    public MockStringKeeper(StringCollector out) {
        super(out, "");
    }

    public String get(String name) {
        log("get('" + name + "')");
        return (String) resultOfGetObject;
    }
}