package tests.tree;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.stringtree.Repository;
import org.stringtree.fetcher.MapFetcher;
import org.stringtree.fetcher.hierarchy.FlatHierarchyHelper;
import org.stringtree.fetcher.hierarchy.HierarchyHelper;
import org.stringtree.fetcher.hierarchy.HierarchyLoader;
import org.stringtree.fetcher.hierarchy.HierarchyStorer;
import org.stringtree.xml.XMLLoader;
import org.stringtree.xml.XMLStorer;

import junit.framework.TestCase;

public class StoreLoadTest extends TestCase {
    Repository repository;
    HierarchyStorer storer;
    HierarchyLoader loader;
    HierarchyHelper helper;
    
    public void setUp() {
        repository = new MapFetcher();
        
        helper = new FlatHierarchyHelper();
        storer = new XMLStorer(helper);
        loader = new XMLLoader(helper);
    }

    public void testStore() throws IOException {
        String root = helper.getRootKey(repository);
        String key = helper.putChild(repository, root, "test", null);
        String key1 = helper.putChild(repository, key, "hello", "ugh");
        String key2 = helper.putChild(repository, key, "hello", "wibble");
        helper.putChild(repository, key1, "thing", "whatever");
        helper.putAttribute(repository, key2, "name", "Frank");

        Writer writer = new StringWriter();
        storer.store(repository, writer);

        assertEquals(
            "<test>" +
            "<hello>ugh<thing>whatever</thing></hello>" + 
            "<hello name=\"Frank\">wibble</hello>" + 
            "</test>", writer.toString());
    }

    public void testSimpleLoad() throws IOException {
        String xml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<test>" +
            "<hello>ugh<thing>whatever</thing></hello>" + 
            "<hello name='Frank'>wibble</hello>" + 
            "</test>";
        loader.load(repository, new StringReader(xml));

        assertEquals("UTF-8", repository.getObject("//?xml[1]/@encoding"));
        assertEquals("", repository.getObject("//test[1]"));
        assertEquals("ugh", repository.getObject("//test[1]/hello[1]"));
        assertEquals("wibble", repository.getObject("//test[1]/hello[2]"));
        assertEquals(2, helper.count(repository, "//test[1]/hello"));
        assertEquals("Frank", repository.getObject("//test[1]/hello[2]/@name"));
    }

    public void testMoreComplexLoad() throws IOException {
        String xml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<retailItem>\n" +
            " <header>\n" +
            "  <id>4028a62310a135780110a14d1d920007</id>\n" +
            "  <spIdentifier>VIDE4D1D920007</spIdentifier>\n" +
            "  <externalIdentifier>VIDE4D1D920007</externalIdentifier>\n" +
            "  <created>2007-02-08T12:21:30</created>\n" +
            "  <lastChanged>2007-02-09T15:38:35</lastChanged>\n" +
            " </header>\n" +
            " <prices>\n" +
            "  <price>\n" +
            "   <grossRetailPrice>3.0</grossRetailPrice>\n" +
            "   <currencyCode>GBP</currencyCode>\n" +
            "   <paymentMethod>*</paymentMethod>\n" +
            "  </price>\n" +
            " </prices>\n" +
            " <paymentMethods>\n" +
            "  <paymentMethod>\n" +
            "   <id>3</id>\n" +
            "    <attributes>\n" +
            "     <attribute>\n" +
            "      <name>productCode</name>\n" +
            "      <value>00A4</value>\n" +
            "     </attribute>     <attribute>\n" +
            "      <name>shortCode</name>\n" +
            "      <value>1234</value>\n" +
            "     </attribute>\n" +
            "   </attributes>\n" +
            "  </paymentMethod>\n" +
            " </paymentMethods>\n" +
            " <appearance>\n" +
            "  <title>Attitude</title>\n" +
            "  <description>Attitude</description>\n" +
            "  <language>en</language>\n" +
            "  <smallIcon>87763A60-AB5D-FF49-839B-0E52DE9C2331.jpg</smallIcon>\n" +
            "  <largeIcon>E875A3E4-C235-9C91-0F89-5E5BC4043277.png</largeIcon>\n" +
            " </appearance>\n" +
            " <products>\n" +
            "  <product>\n" +
            "   <appearance>  \n" +
            "    <title>Attitude</title>\n" +
            "    <description>Attitude</description>\n" +
            "    <language>en</language>\n" +
            "    <smallIcon>87763A60-AB5D-FF49-839B-0E52DE9C2331.jpg</smallIcon>\n" +
            "    <largeIcon>E875A3E4-C235-9C91-0F89-5E5BC4043277.png</largeIcon>\n" +
            "   </appearance> \n" +
            "   <contentDetail> \n" +
            "    <contentType>Video</contentType>\n" +
            "    <contentProvider>CP000001 Media Group</contentProvider>\n" +
            "    <category>recreation</category>\n" +
            "    <languagesAvailable>en</languagesAvailable>\n" +
            "    <deliveryItem>\n" +
            "     <id>4028a623109bdb5c01109bf27fc3001a</id>\n" +
            "     <mechanism>Download</mechanism>\n" +
            "     <isPreviewAvailable>false</isPreviewAvailable>\n" +
            "    </deliveryItem>\n" +
            "   </contentDetail>\n" +
            "  </product>\n" +
            "   \n" +
            " </products>\n" +
            "</retailItem>\n";
        loader.load(repository, new StringReader(xml));
        
        assertEquals("UTF-8", repository.getObject("//?xml[1]/@encoding"));
        assertEquals("4028a62310a135780110a14d1d920007", repository.getObject("//retailItem[1]/header[1]/id[1]"));
        assertEquals("VIDE4D1D920007", repository.getObject("//retailItem[1]/header[1]/externalIdentifier[1]"));
        assertEquals("Video", repository.getObject("//retailItem[1]/products[1]/product[1]/contentDetail[1]/contentType[1]"));
        assertEquals("false", repository.getObject("//retailItem[1]/products[1]/product[1]/contentDetail[1]/deliveryItem[1]/isPreviewAvailable[1]"));
        assertEquals(2, helper.count(repository, "//retailItem[1]/paymentMethods[1]/paymentMethod[1]/attributes[1]/attribute"));
           }
}
