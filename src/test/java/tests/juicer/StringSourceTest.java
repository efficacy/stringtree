package tests.juicer;

import org.stringtree.juicer.string.EmptyStringSource;
import org.stringtree.juicer.string.FileStringSource;
import org.stringtree.juicer.string.StringStringSource;

public class StringSourceTest extends JuicerStringTestCase {
    
	public void testEmpty() {
		source = new EmptyStringSource();

		assertEquals("Empty, no string", null, source.nextString());
	}

	public void testString() {
		source = new StringStringSource("hello");

		assertEquals("String", "hello", source.nextString());
		assertEquals("String, at end", null, source.nextString());
		assertEquals("String, at end again", null, source.nextString());

		rewind();

		assertEquals("String, after rewind", "hello", source.nextString());
		assertEquals("String, after rewind at end", null, source.nextString());
	}

	public void testWholeFileAbsent() {
		source = new FileStringSource("testfiles/absent.txt");

		assertEquals("Whole File, absent", null, source.nextString());
	}

	public void testWholeFilePresent()
	{
		source = new FileStringSource("testfiles/ss1.txt");

		assertEquals("Whole File, present", "hello", source.nextString());
		assertEquals("Whole File, at end", null, source.nextString());

		rewind();

		assertEquals("Whole File, after rewind", "hello", source.nextString());
		assertEquals("Whole File, after rewind at end", null, source.nextString());
	}
}
