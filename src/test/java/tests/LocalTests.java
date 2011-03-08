package tests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import tests.URL.FormTest;
import tests.URL.URLTest;
import tests.codec.Base64Test;
import tests.fetcher.FetcherTests;
import tests.http.HTTPTests;
import tests.iterator.SkipCommentsTest;
import tests.jms.JMSTests;
import tests.jndi.JNDITests;
import tests.json.JSONTests;
import tests.juicer.JuicerTests;
import tests.pool.PoolTest;
import tests.spec.SpecTests;
import tests.streams.StreamTests;
import tests.template.PagePluginTest;
import tests.template.TemplateTests;
import tests.tree.HierarchyTests;
import tests.util.UtilTests;
import tests.util.sheet.SheetTests;
import tests.workflow.WorkflowTests;
import tests.xmlevents.AllXMLTests;

public class LocalTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite ret = new TestSuite();

        ret.addTestSuite(PoolTest.class);
        ret.addTestSuite(Base64Test.class);
        ret.addTest(JMSTests.suite());
        ret.addTest(StreamTests.suite());
        ret.addTestSuite(PagePluginTest.class);
        ret.addTest(AllXMLTests.suite());
        ret.addTest(SheetTests.suite());
        ret.addTest(HierarchyTests.suite());
        ret.addTest(HTTPTests.suite());
        ret.addTestSuite(SkipCommentsTest.class);
        ret.addTest(JSONTests.suite());
        ret.addTest(TemplateTests.suite());
        ret.addTest(WorkflowTests.suite());
        ret.addTest(JuicerTests.suite());
        ret.addTest(JNDITests.suite());
        ret.addTestSuite(URLTest.class);
        ret.addTestSuite(FormTest.class);
        ret.addTest(UtilTests.suite());
        ret.addTest(SpecTests.suite());
        ret.addTest(FetcherTests.suite());

        return ret;
    }
}
