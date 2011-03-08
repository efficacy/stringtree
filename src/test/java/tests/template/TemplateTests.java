package tests.template;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TemplateTests extends TestCase {
    
    public static TestSuite suite() {
        TestSuite ret = new TestSuite();

        ret.addTest(new TestSuite(TreeTemplaterTest.class));
        ret.addTest(new TestSuite(EasyTemplaterTest.class));
        ret.addTest(new TestSuite(NestedTemplateTest.class));
        ret.addTest(new TestSuite(DirectTemplateTest.class));
        ret.addTest(new TestSuite(InlineTemplateTest.class));

        return ret;
    }
}
