package tests.mock;

import org.stringtree.Tract;
import org.stringtree.finder.TractFinder;
import org.stringtree.template.StringCollector;
import org.stringtree.tract.EmptyTract;

public class MockTractFinder extends MockObjectFinder implements TractFinder {
    
    public MockTractFinder(StringCollector out) {
        super(out, EmptyTract.it);
    }

    public Tract get(String name) {
        log("get('" + name + "')");
        return (Tract) resultOfGetObject;
    }
}