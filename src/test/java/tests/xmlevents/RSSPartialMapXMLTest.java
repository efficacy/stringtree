package tests.xmlevents;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.stringtree.xmlevents.PartialMapXMLEventHandler;
import org.stringtree.xmlevents.StanzaMatcher;
import org.stringtree.xmlevents.XMLEventParser;
import org.stringtree.xmlevents.rss.RssChannel;
import org.stringtree.xmlevents.rss.RssItem;

class ChannelHandler implements StanzaMatcher {
    public Object match(String path, Map<?,?> values, Object context) {
        RssChannel rss = (RssChannel)context;
        rss.put("title", (String)values.get("/rss/channel/title"));
        rss.put("link", (String)values.get("/rss/channel/link"));
        rss.put("description", (String)values.get("/rss/channel/description"));
        rss.put("date", (String)values.get("/rss/channel/pubDate"));
        rss.put("generator", (String)values.get("/rss/channel/generator"));
        rss.put("language", (String)values.get("/rss/channel/language"));
        
        return context;
    }
}

class ItemHandler implements StanzaMatcher {
    public Object match(String path, Map<?,?> values, Object context) {
        RssItem item = new RssItem();
        item.put("title", (String)values.get("/rss/channel/item/title"));
        item.put("link", (String)values.get("/rss/channel/item/link"));
        item.put("comments", (String)values.get("/rss/channel/item/comments"));
        item.put("pubDate", (String)values.get("/rss/channel/item/pubDate"));
        item.put("dc:creator", (String)values.get("/rss/channel/item/dc:creator"));
        item.put("category", (String)values.get("/rss/channel/item/category"));
        item.put("guid", (String)values.get("/rss/channel/item/guid"));
        item.put("description", (String)values.get("/rss/channel/item/description"));
        item.put("content:encoded", (String)values.get("/rss/channel/item/content:encoded"));
        
        ((RssChannel)context).addItem(item);
        return context;
    }
}

public class RSSPartialMapXMLTest extends TestCase {
    
    static final String url = "testfiles/xml/feed.xml";
    
    XMLEventParser parser;
    PartialMapXMLEventHandler handler;
    Map<String, StanzaMatcher> matchers;
    RssChannel feed;
    RssItem item;

    public void setUp() {
        parser = new XMLEventParser(true, true, false, false);
        matchers = new HashMap<String, StanzaMatcher>();
        matchers.put("/rss/channel", new ChannelHandler());
        matchers.put("/rss/channel/item", new ItemHandler());
        handler = new PartialMapXMLEventHandler(matchers, true);
        feed = new RssChannel();
    }
    
    public void testSingle() throws IOException {
        parser.process(new FileReader("testfiles/xml/smallfeed.xml"), handler, feed);
        assertEquals("blog.stringtree.org", feed.get("title"));
        assertEquals("http://blog.stringtree.org", feed.get("link"));
        assertEquals("Stringtree Development News", feed.get("description"));
        assertEquals("Thu, 31 Jan 2008 13:29:36 +0000", feed.get("date"));
        assertEquals("http://wordpress.org/?v=1.5.2", feed.get("generator"));
        assertEquals("en", feed.get("language"));
        
        List<RssItem> items = feed.items();
        assertEquals(1, items.size());
        RssItem item = items.get(0);
        assertEquals("Slight Improvement to Stringtree XML Parser", item.get("title"));
        assertEquals("http://blog.stringtree.org/2008/01/31/slight-improvement-to-stringtree-xml-parser/", item.get("link"));
        assertEquals("http://blog.stringtree.org/2008/01/31/slight-improvement-to-stringtree-xml-parser/#comments", item.get("comments"));
        assertEquals("Thu, 31 Jan 2008 13:29:08 +0000", item.get("pubDate"));
        String dc_creator = item.get("dc:creator");
        assertEquals("Stringtree", dc_creator);
        assertEquals("Projects", item.get("category"));
        assertEquals("http://blog.stringtree.org/2008/01/31/slight-improvement-to-stringtree-xml-parser/", item.get("guid"));
        assertEquals("\tSomeone just pointed out that the light-weight XML parser included in Stringtree did not handle explicit CDATA blocks. The version in SVN now has provisional support for this.\n\tIf you need a simple and fast parser for textual data, then this should be all you need. For XML documents containing opaque binary data in a CDATA [...]", item.get("description"));
        assertEquals("\t<p>Someone just pointed out that the light-weight <a href=\"http://stringtree.svn.sourceforge.net/svnroot/stringtree/trunk/src/delivery/java/org/stringtree/xml/XMLReader.java\">XML parser</a> included in Stringtree did not handle explicit CDATA blocks. The version in SVN now has provisional support for this.</p>\n\t<p>If you need a simple and fast parser for textual data, then this should be all you need. For XML documents containing opaque binary data in a CDATA block, this may not be ideal. Currently CDATA blocks are loaded as String objects, and this can lead to incorrect data for bytes which do not represent valid characters in the current character set.</p>\n\t<p>I am currently planning for the next version of the Stringtree XMLReader to offer the option of extracting a CDATA block as an unprocessed byte array.\n</p>\n", item.get("content:encoded"));
    }
    
    public void testFull() throws IOException {
        parser.process(new FileReader("testfiles/xml/feed.xml"), handler, feed);
        assertEquals("blog.stringtree.org", feed.get("title"));
        assertEquals("http://blog.stringtree.org", feed.get("link"));
        assertEquals("Stringtree Development News", feed.get("description"));
        assertEquals("Thu, 31 Jan 2008 13:29:36 +0000", feed.get("date"));
        assertEquals("http://wordpress.org/?v=1.5.2", feed.get("generator"));
        assertEquals("en", feed.get("language"));
        
        List<RssItem> items = feed.items();
        assertEquals(10, items.size());
        
        assertEquals("Slight Improvement to Stringtree XML Parser", items.get(0).get("title"));
        assertEquals("Stringtree Maven Repository", items.get(1).get("title"));
    }
}
