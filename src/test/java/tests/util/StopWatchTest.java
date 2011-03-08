package tests.util;

import java.io.StringWriter;

import org.stringtree.timing.StopWatch;

import junit.framework.TestCase;

public class StopWatchTest extends TestCase {
    private StopWatch timer;

    public StopWatchTest(String name) {
        super(name);
    }

    public void setUp() {
        timer = new StopWatch();
    }

    private void sleep(int ms) {
    	final long start = System.currentTimeMillis();
    	final long end = start + ms; 
    	long now = start;
    	
        try {
        	while (now < end) {
                Thread.sleep(end - now);
                now = System.currentTimeMillis();
        	}
        	
        } catch(InterruptedException e) {
            fail(e.toString());
        }
    }

    private static final long delta = 99;

    private boolean roughly(long desired, long actual) {
        boolean ret = actual < (desired+delta) && actual > (desired-delta);
        if (!ret) {
            System.err.println("StopWatchTest times out of tolerance, desired=" + desired + " actual=" + actual + " tolerance=" + delta);
        }
        return ret;
    }

    public void testLessThanASecond() {
        timer.start();
          sleep(500);
        timer.stop();

        assertTrue("less than 1000 ms", timer.get() < 1000);
        assertEquals("0s", 0, timer.asSeconds());
    }

    public void testOneSecond() {
        timer.start();
          sleep(1000);
        timer.stop();

        assertTrue("more than 1000 ms", timer.get() >= 1000);
        assertEquals("1s", 1, timer.asSeconds());
    }

    public void testTwoSeconds() {
        timer.start();
          sleep(2000);
        timer.stop();

        assertTrue("more than 2000 ms", timer.get() >= 2000);
        assertEquals("2s", 2, timer.asSeconds());
    }

    public void testTotal() {
        timer.start();
          sleep(1000);
        timer.stop();

        assertTrue("total: more than 1000 ms", timer.get() >= 1000);
        assertEquals("total: 1s", 1, timer.asSeconds());
        assertTrue("total roughly 1s", roughly(1000, timer.total()));
        assertEquals("total 1s", 1, timer.totalSeconds());

        timer.restart();
          sleep(2000);
        timer.stop();

        assertTrue("more than 2000 ms", timer.get() >= 2000);
        assertEquals("2s", 2, timer.asSeconds());
        assertTrue("total roughly 3s", roughly(3000, timer.total()));
        assertEquals("total 3s", 3, timer.totalSeconds());
    }

    public void testBuffered() {
        StringWriter writer = new StringWriter();
        timer = new BufferedStopWatch(writer);

        timer.start();
          sleep(1000);
        timer.stop();

        assertTrue("total: more than 1000 ms", timer.get() >= 1000);
        assertEquals("total: 1s", 1, timer.asSeconds());
        assertTrue("total roughly 1s", roughly(1000, timer.total()));
        assertEquals("total 1s", 1, timer.totalSeconds());
        timer.logSeconds("one second");

        timer.restart();
          sleep(2000);
        timer.stop();

        assertTrue("more than 2000 ms", timer.get() >= 2000);
        assertEquals("2s", 2, timer.asSeconds());
        assertTrue("total roughly 3s", roughly(3000, timer.total()));
        assertEquals("total 3s", 3, timer.totalSeconds());
        timer.logSeconds("two more");

        timer.reset();
        assertEquals("logged", "total: 0\none second (1s)\ntwo more (2s)\ntotal: 3\n", writer.toString());
    }
}