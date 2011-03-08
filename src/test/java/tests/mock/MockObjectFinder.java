package tests.mock;

import java.util.Iterator;

import org.stringtree.Fetcher;
import org.stringtree.finder.ObjectFinder;
import org.stringtree.template.StringCollector;
import org.stringtree.util.iterator.EmptyIterator;

import tests.util.LoggingMock;

@SuppressWarnings("unchecked")
public class MockObjectFinder extends LoggingMock implements ObjectFinder {

    public Object resultOfGetObject = null;
    public Iterator<Object> resultOfList = EmptyIterator.it();
    public boolean resultOfContains = false;
    public Fetcher resultOfGetUnderlyingFetcher = null;

    public MockObjectFinder(StringCollector out) {
        super(out);
    }

    public MockObjectFinder(StringCollector out, Object result) {
        super(out);
        this.resultOfGetObject = result;
    }

    public Object getObject(String name) {
        log("get('" + name + "')");
        return resultOfGetObject;
    }

    public Iterator<Object> list() {
        log("list");
        return resultOfList;
    }

    public boolean contains(String name) {
        log("contains('" + name + "')");
        return resultOfContains;
    }

    public Fetcher getUnderlyingFetcher() {
        log("getUnderlyingFetcher");
        return resultOfGetUnderlyingFetcher;
    }
}