package tests.tree;

import org.stringtree.Repository;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.hierarchy.FlatHierarchyHelper;
import org.stringtree.fetcher.hierarchy.HierarchyHelper;
import org.stringtree.util.testing.Checklist;

import junit.framework.TestCase;

public class CreateTest extends TestCase {
    Repository repository;
    HierarchyHelper helper;
    String root;
    
    public void setUp() {
        repository = new MapFetcher();
        helper = new FlatHierarchyHelper();
        root = helper.getRootKey(repository);
        assertEquals(null, root);
        assertEquals(0, helper.countChildren(repository, root, "hello"));
    }

    public void testSingleRoot() {
        String key = helper.createChildKey(repository, root, "hello");
        assertEquals("//hello[1]", key);
        repository.put(key, "ugh");
        
        assertEquals("ugh", repository.getObject(key));
        assertEquals(1, helper.countChildren(repository, root, "hello"));
        assertEquals("ugh", helper.getChild(repository, root, "hello"));
    }

    public void testDoubleRoot() {
        String key = helper.createChildKey(repository, root, "hello");
        assertEquals("//hello[1]", key);
        repository.put(key, "ugh");

        String key2 = helper.createChildKey(repository, root, "hello");
        assertEquals("//hello[2]", key2);
        repository.put(key2, "wibble");
        
        assertEquals("ugh", repository.getObject(key));
        assertEquals("wibble", repository.getObject(key2));

        assertEquals(2, helper.countChildren(repository, root, "hello"));
        assertEquals("ugh", helper.getChild(repository, root, "hello"));

        assertTrue(new Checklist<String>("//hello[1]", "//hello[2]" ).check( 
                helper.getChildKeys(repository, root, "hello")));
    }

    public void testAttribute() {
        String key = helper.createChildKey(repository, root, "hello");
        assertEquals("//hello[1]", key);
        repository.put(key, "ugh");

        String attribute = helper.getAttributeKey(key, "name");
        assertEquals("//hello[1]/@name", attribute);
        repository.put(attribute, "Frank");
        
        assertEquals("Frank", repository.getObject(attribute));
        assertEquals("Frank", helper.getAttribute(repository, key, "name"));
    }
}
