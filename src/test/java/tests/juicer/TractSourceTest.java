package tests.juicer;

import junit.framework.TestCase;

import org.stringtree.Tract;
import org.stringtree.juicer.JuicerConvertHelper;
import org.stringtree.juicer.JuicerLockHelper;
import org.stringtree.juicer.Rewindable;
import org.stringtree.juicer.convert.StringSourceTractSource;
import org.stringtree.juicer.string.StringSource;
import org.stringtree.juicer.string.StringStringSource;
import org.stringtree.juicer.tract.EmptyTractSource;
import org.stringtree.juicer.tract.FileTractSource;
import org.stringtree.juicer.tract.SequenceTractSource;
import org.stringtree.juicer.tract.StringTractSource;
import org.stringtree.juicer.tract.TractSource;
import org.stringtree.tract.MapTract;

public class TractSourceTest extends TestCase {
    
	TractSource source;
	Tract t;

	public void testEmpty() {
		source = new EmptyTractSource();

		assertEquals("Empty, no tract", null, source.nextTract());
	}

	public void testString() {
		source = new StringTractSource("hello");

		t = source.nextTract();
		assertEquals("String", "hello", t.getContent());
		assertEquals("String, at end", null, source.nextTract());
		assertEquals("String, at end again", null, source.nextTract());

		((Rewindable)source).rewind();

		t = source.nextTract();
		assertEquals("String", "hello", t.getContent());
		assertEquals("String, after rewind at end", null, source.nextTract());
	}

	public void testSequence() {
		source = new SequenceTractSource(new Tract[] {
			new MapTract() { { setContent("hello"); } },
			new MapTract() { { setContent("hello"); JuicerLockHelper.lock(this); } },
			new MapTract() { { setContent("hello"); } }
		});

		assertEquals("Seq 1", "hello", JuicerConvertHelper.nextString(source));
		assertEquals("Seq 2", "hello", JuicerConvertHelper.nextString(source));
		assertEquals("Seq 3", "hello", JuicerConvertHelper.nextString(source));
		assertEquals("Seq, at end", null, source.nextTract());

		((Rewindable)source).rewind();

		assertEquals("Seq after 1", "hello", JuicerConvertHelper.nextString(source));
		assertEquals("Seq after 2", "hello", JuicerConvertHelper.nextString(source));
		assertEquals("Seq after 3", "hello", JuicerConvertHelper.nextString(source));
		assertEquals("Seq after, at end", null, source.nextTract());
	}

	public void testStringSource() {
		StringSource ss = new StringStringSource("hello");
		source = new StringSourceTractSource(ss);

		t = source.nextTract();
		assertEquals("String", "hello", t.getContent());
		assertEquals("String, at end", null, source.nextTract());
		assertEquals("String, at end again", null, source.nextTract());

		((Rewindable)ss).rewind();

		t = source.nextTract();
		assertEquals("String", "hello", t.getContent());
		assertEquals("String, after rewind at end", null, source.nextTract());
	}

	public void testWholeFileAbsent() {
		source = new FileTractSource("testfiles/absent.txt");

		assertEquals("Whole File, absent", null, source.nextTract());
	}

	public void testWholeFilePresent() {
		source = new FileTractSource("testfiles/ss1.txt");

		t = source.nextTract();
		assertEquals("String", "hello", t.getContent());
		assertEquals("Whole File, at end", null, source.nextTract());

		((Rewindable)source).rewind();

		t = source.nextTract();
		assertEquals("String", "hello", t.getContent());
		assertEquals("Whole File, after rewind at end", null, source.nextTract());
	}
}
