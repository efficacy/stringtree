package tests.jms;

import javax.jms.JMSException;
import javax.jms.QueueBrowser;
import javax.jms.TextMessage;
import javax.naming.NamingException;

public class QueueAutostartTest extends MessagingTestCase {

	public void testTextMessageHoldThenStart() throws JMSException, NamingException {
        jmsc = defineQueues(false);
        TextMessage tm = jmsc.qsession.createTextMessage("hello");
        assertNotNull(tm);
        assertEquals("hello", tm.getText());
        
        QueueBrowser browser = jmsc.qsession.createBrowser(jmsc.qout);
        assertFalse(browser.getEnumeration().hasMoreElements());

        jmsc.qsender.send(tm);
        assertFalse(browser.getEnumeration().hasMoreElements());
        
        jmsc.qconnection.start();

        assertTrue(browser.getEnumeration().hasMoreElements());
        assertEquals(tm, browser.getEnumeration().nextElement());
	}

    public void testTextMessageAutoStart() throws JMSException, NamingException {
        jmsc = defineQueues(true);
        TextMessage tm = jmsc.qsession.createTextMessage("hello");
        assertNotNull(tm);
        assertEquals("hello", tm.getText());

        QueueBrowser browser = jmsc.qsession.createBrowser(jmsc.qout);
        assertFalse(browser.getEnumeration().hasMoreElements());

        jmsc.qsender.send(tm);

        assertTrue(browser.getEnumeration().hasMoreElements());
        assertEquals(tm, browser.getEnumeration().nextElement());
    }
}
