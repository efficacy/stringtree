package tests.mock;

import org.stringtree.Tract;
import org.stringtree.finder.TractKeeper;
import org.stringtree.template.StringCollector;
import org.stringtree.tract.EmptyTract;

public class MockTractKeeper extends MockKeeper implements TractKeeper {
    
    public Tract resultOfGet = EmptyTract.it;

    public MockTractKeeper(StringCollector out) {
        super(out);
    }

    public Tract get(String name) {
        log("get('" + name + "')");
        return resultOfGet;
    }
}