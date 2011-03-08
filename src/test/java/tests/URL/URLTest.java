package tests.URL;

import org.stringtree.util.SmartPathClassLoader;
import org.stringtree.util.URLReadingUtils;

import junit.framework.TestCase;

public class URLTest extends TestCase {
    
    public void testFile() {
        String content = URLReadingUtils.readURL("file:testfiles/url.properties");
        assertEquals("classpath=org.stringtree.URL.ClassLoaderResourceHandler", content);
    }

    public void testHttp() {
        String content = URLReadingUtils.readURL("http://www.stringtree.org/index.html");
        assertTrue(content.indexOf("<head>") >= 0);
    }

    public void testClasspath() {
        String content = URLReadingUtils.readURL("classpath:ugh.txt");
        assertEquals("hello", content);
    }

    public void testClasspathWithFolder() {
        String content = URLReadingUtils.readURL("classpath:folder/ugh2.txt");
        assertEquals("goodbye", content);
    }

    public void testClasspathWithClassLoader() {
        ClassLoader loader = new SmartPathClassLoader("testfiles/smartpath");
        String content = URLReadingUtils.readURL("classpath:ugh3.txt", loader);
        assertEquals("wibble", content);
    }
}
