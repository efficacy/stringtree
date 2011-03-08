package tests.codec;

import org.stringtree.codec.Base64;

import junit.framework.TestCase;

public class Base64Test extends TestCase {
	public void testEncode() {
		assertEquals(null, Base64.encode((String)null));
		assertEquals("", Base64.encode(""));
		assertEquals("YQ==", Base64.encode("a"));
		assertEquals("YWI=", Base64.encode("ab"));
		assertEquals("YWJj", Base64.encode("abc"));
	}
	
	public void testDecode() {
		assertEquals(null, Base64.decodeString((String)null));
		assertEquals("", Base64.decodeString(""));
		assertEquals("a", Base64.decodeString("YQ=="));
		assertEquals("ab", Base64.decodeString("YWI="));
		assertEquals("abc", Base64.decodeString("YWJj"));
	}
}
