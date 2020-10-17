package tests.fetcher;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.stringtree.Container;
import org.stringtree.Fetcher;
import org.stringtree.fetcher.BeanFetcher;
import org.stringtree.fetcher.CachedFetcher;
import org.stringtree.fetcher.DelegatedFetcher;
import org.stringtree.fetcher.EmptyFetcher;
import org.stringtree.fetcher.FallbackFetcher;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.ResourceFetcher;
import org.stringtree.fetcher.ResourcePropertiesFetcher;
import org.stringtree.fetcher.StringDirectoryRepository;
import org.stringtree.fetcher.SuffixResourceFilter;
import org.stringtree.fetcher.SystemPropertiesFetcher;
import org.stringtree.fetcher.filter.SuffixFilter;

import tests.TestBean;

public class FetcherTest extends TestCase {

    Map<String, Object> map;
    Fetcher fetcher;

    @Override public void setUp() {
        map = new HashMap<String, Object>();
    }

    public void testEmpty() {
        fetcher = new EmptyFetcher();
        assertEquals("EmptySF 1", null, fetcher.getObject("hello"));
        assertEquals("EmptySF 2", null, fetcher.getObject("ugh"));
        assertEquals("EmptySF 3", null, fetcher.getObject(null));
    }

    public void _testMapContainer(String type, Container sf) {
        assertEquals("MapSF(" + type + ").contains null", false, sf
                .contains(null));

        map.remove("hello");
        assertEquals("MapSF(" + type + ").contains missing", false, sf
                .contains("hello"));

        map.put("hello", "world");
        assertEquals("MapSF(" + type + ").contains present", true, sf
                .contains("hello"));
    }

    public void _testMapFetcher(String type, Fetcher sf) {
        map.remove("hello");
        assertEquals("MapSF(" + type + ").get missing 1", null, sf
                .getObject("hello"));
        assertEquals("MapSF(" + type + ").get missing 2", null, sf
                .getObject("ugh"));
        assertEquals("MapSF(" + type + ").get missing 3", null, sf
                .getObject(null));

        map.put("hello", "world");
        assertEquals("MapSF(" + type + ").get present 1", "world", sf
                .getObject("hello"));
        assertEquals("MapSF(" + type + ").get missing 4", null, sf
                .getObject("ugh"));
        assertEquals("MapSF(" + type + ").get missing 5", null, sf
                .getObject(null));

        map.put("ugh", "bazooka joe");
        assertEquals("MapSF(" + type + ").get present 2", "world", sf
                .getObject("hello"));
        assertEquals("MapSF(" + type + ").get present 3", "bazooka joe", sf
                .getObject("ugh"));
        assertEquals("MapSF(" + type + ").get missing 6", null, sf
                .getObject(null));
    }

    public void testMapStringRepository() {
        fetcher = new MapFetcher(map);
        _testMapFetcher("MapFetcher", fetcher);
        _testMapContainer("MapFetcher", (Container) fetcher);
    }

    public void testDelegatedFetcher() {
        fetcher = new DelegatedFetcher(new MapFetcher(map));
        _testMapFetcher("DelegatedFetcher", fetcher);
    }

    public void testFallbackFetcher() {
        fetcher = new FallbackFetcher(new MapFetcher(map), new BeanFetcher(
                new TestBean()));
        assertEquals("testFallback 1", null, fetcher.getObject("hello"));
        assertEquals("testFallback 2", Integer.valueOf(321), fetcher.getObject("mi"));

        map.put("hello", "world");
        assertEquals("testFallback 3", "world", fetcher.getObject("hello"));
        assertEquals("testFallback 4", Integer.valueOf(321), fetcher.getObject("mi"));

        map.put("mi", "ugh");
        assertEquals("testFallback 5", "world", fetcher.getObject("hello"));
        assertEquals("testFallback 6", "ugh", fetcher.getObject("mi"));
    }

    public void testResourceStringFetcher() {
        fetcher = new ResourceFetcher(this, new SuffixResourceFilter(".txt"));
        assertEquals("RSF contains missing resource", null, fetcher
                .getObject("missing"));
        assertEquals("RSF get on present resource", "hi there", fetcher
                .getObject("present"));
    }

    public void testCachedFetcher() {
        fetcher = new CachedFetcher(new BeanFetcher(new TestBean()));
        assertEquals("beanSF get missing property", null, fetcher.getObject("hello"));
        assertEquals("beanSF get object property", "world", fetcher.getObject("ms"));
        assertEquals("beanSF get primitive property", Integer.valueOf(321), fetcher.getObject("mi"));
    }

    public void testResourcePropertiesFetcher() {
        fetcher = new ResourcePropertiesFetcher(this, "test.prp");
        assertEquals("RPSF getObject on missing resource", null, fetcher
                .getObject("missing"));
        assertEquals("RPSF get on present resource", "hello, world", fetcher
                .getObject("present"));
    }

    public void testDirectoryFetcher() {
        fetcher = new StringDirectoryRepository(new File("testfiles/sfdir"), new SuffixFilter(
                ".txt"), false);
        assertEquals("DSF getObject on missing resource", null, fetcher
                .getObject("missing"));
        assertEquals("DSF get on present resource", "hello, world", fetcher
                .getObject("present"));
    }

    public void testSystemPropertiesFetcher() {
        fetcher = new SystemPropertiesFetcher();
        assertEquals("SPSF getObject on missing resource", null, fetcher
                .getObject("missing"));
        assertEquals("SPSF get on present resource", "Java Virtual Machine Specification", fetcher
                .getObject("java.vm.specification.name"));
    }
}
