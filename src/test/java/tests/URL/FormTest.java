package tests.URL;

import org.stringtree.http.Form;

import junit.framework.TestCase;

public class FormTest extends TestCase {
    Form form;
    
    public void setUp() {
        form = new Form();
    }
    
    public void testEmpty() {
        assertEquals("", form.toString());
    }
    
    public void testSingleField() {
        form.put("name", "Frank Carver");
        assertEquals("name=Frank+Carver", form.toString());
    }
    
    public void testTwoFields() {
        form.put("name", "Frank Carver");
        form.put("company", "Efficacy Solutions Limited");
        assertEquals("name=Frank+Carver&company=Efficacy+Solutions+Limited", form.toString());
    }

}
