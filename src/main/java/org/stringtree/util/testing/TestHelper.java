package org.stringtree.util.testing;

import junit.framework.Assert;

import org.stringtree.util.FileReadingUtils;

public class TestHelper {
    
	public static void assertFileString(String filename, String contents) {
		String desired = FileReadingUtils.readFile(filename);
		Assert.assertEquals(desired, contents);
	}

    public static void assertBytes(byte[] a, byte[] b) {
    	if (a.length != b.length) Assert.fail("bytes arrays different sizes (expected " + a.length + " was " + b.length + ")");
    	for (int i = 0; i < a.length; ++i) {
    		if (a[i] != b[i]) Assert.fail("different bytes at index " + i);
    	}
    }

    public static void assertFileBytes(String filename, byte[] b) {
    	byte[] a = FileReadingUtils.readBytes(filename);
    	assertBytes(a, b);
    }

    public static void assertContains(String string, Object object) {
        Assert.assertTrue(object != null && object.toString().contains(string));
    }

    public static void assertSameish(String expected, String actual) {
        Assert.assertEquals(expected.replace("\r\n", "\n"), actual.replace("\r\n", "\n"));
    }
}
