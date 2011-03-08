package tests.util;

import org.stringtree.util.LimitedHashMap;

import junit.framework.TestCase;

public class LimitedHashMapTest extends TestCase {
	LimitedHashMap<String, String> map;
	
	public void testSize1() {
		map = new LimitedHashMap<String, String>(1);
		assertTrue(map.isEmpty());
		
		map.put("hello", "there");
		assertEquals("there", map.get("hello"));

		map.put("goodbye", "world");
		assertNull(map.get("hello"));
		assertEquals("world", map.get("goodbye"));
	}
	
	public void testSize3() {
		map = new LimitedHashMap<String, String>(3);
		assertTrue(map.isEmpty());
		
		map.put("hello", "there");
		assertEquals("there", map.get("hello"));

		map.put("goodbye", "world");
		assertEquals("there", map.get("hello"));
		assertEquals("world", map.get("goodbye"));

		map.put("wibble", "wobble");
		assertEquals("there", map.get("hello"));
		assertEquals("world", map.get("goodbye"));
		assertEquals("wobble", map.get("wibble"));

		map.put("pushy", "innit");
		assertNull(map.get("hello"));
		assertEquals("world", map.get("goodbye"));
		assertEquals("wobble", map.get("wibble"));
		assertEquals("innit", map.get("pushy"));
	}
}
