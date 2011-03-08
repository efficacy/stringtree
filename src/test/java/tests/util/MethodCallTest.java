package tests.util;

import junit.framework.TestCase;

import org.stringtree.Fetcher;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.finder.FetcherStringKeeper;
import org.stringtree.util.MethodCallUtils;
import static tests.util.MethodCallExample.*;

public class MethodCallTest extends TestCase {
    
    MethodCallExample example;
    MapFetcher fetcher;
    FetcherStringKeeper context;

    public void setUp() {
        example = new MethodCallExample();
        fetcher = new MapFetcher();
        context = new FetcherStringKeeper(fetcher);
    }

    public void testRequest() {
        assertEquals(0, example.requests);
        MethodCallUtils.call(example, "request");
        assertEquals(REQUEST, example.requests);
    }

    public void testInit() {
        assertEquals(null, example.context);
        MethodCallUtils.call(example, "init", context);
        assertEquals(context, example.context);
    }

    public void testUnknown() {
        assertEquals(null, example.context);
        MethodCallUtils.call(example, "wibble", context);
        assertEquals(null, example.context);
    }
    
    public void testMethodMatching() {
        assertEquals(0, example.requests);
        MethodCallUtils.call(example, "m1", context);
        assertEquals(M1, example.requests);
    }
    
    public void testMethodMatchingExactly() {
        assertEquals(0, example.requests);
        MethodCallUtils.call(example, "m2", context);
        // MethodCallUtils calls ensureKeeper() which casts FSK to SK
        assertEquals(M2_SK, example.requests);
    }

    public void testMethodMatchingInterface() {
        assertEquals(0, example.requests);
        callWithFetcherTypeInsteadOfFullType(context);
        assertEquals(M3, example.requests);
    }
    
    public void callWithFetcherTypeInsteadOfFullType(Fetcher context){
        MethodCallUtils.call(example, "m3", context);
    }
}
