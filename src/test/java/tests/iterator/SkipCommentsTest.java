package tests.iterator;

import java.io.Reader;
import java.io.StringReader;

import org.stringtree.util.iterator.SkipBlankAndCommentLineIterator;
import org.stringtree.util.iterator.SkipBlankLineIterator;

import junit.framework.TestCase;

public class SkipCommentsTest extends TestCase {
	public void testSkipBlankLineAndComment() {
		Reader reader = new StringReader("#first\nsecond\n#third\nfourth");
		SkipBlankAndCommentLineIterator it = new SkipBlankAndCommentLineIterator(reader, "#");
		assertEquals("second", it.next());
		assertEquals("fourth", it.next());
	}

	public void testSkipBlankLine() {
		Reader reader = new StringReader("\nsecond\n\nfourth");
		SkipBlankLineIterator it = new SkipBlankLineIterator(reader);
		assertEquals("second", it.next());
		assertEquals("fourth", it.next());
	}
}
