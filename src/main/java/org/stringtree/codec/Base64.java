package org.stringtree.codec;

class BlockN {
	int[] values;
	int limit;
	
	public BlockN(int n, int limit) {
		values = new int[n];
		this.limit = limit;
	}
	
	void put(int pos, int value) {
		if (pos < 0 || pos >= values.length) {
			throw new IllegalArgumentException ("Illegal block index");
		}
		if (value < 0 || value > limit) {
			throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
		}
		values[pos] = value;
	}
	
	int get(int pos) {
		if (pos < 0 || pos >= values.length) {
			throw new IllegalArgumentException ("Illegal block index");
		}
		return values[pos];
	}
}

class Block4 extends BlockN {
	public Block4() {
		super(4, Integer.MAX_VALUE);
	}

	public Block3 extract3() {
		return new Block3(
			( values[0]       <<2) | (values[1]>>>4),
			((values[1] & 0x0f)<<4) | (values[2]>>>2),
			((values[2] & 0x03)<<6) |  values[3]
		);
	}
}

class Block3 extends BlockN {
	public Block3() {
		super(3, 127);
	}

	public Block3(int i, int j, int k) {
		this();
		values[0] = i;
		values[1] = j;
		values[2] = k;
	}
}

public class Base64 {

	private static char[] binaryToBase64Characters = 
		"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	private static byte[] base64CharactersToBinary;
	static {
		base64CharactersToBinary = new byte[128];
		for (int i=0; i<base64CharactersToBinary.length; ++i) base64CharactersToBinary[i] = -1;
		for (int i=0; i<64; ++i) base64CharactersToBinary[binaryToBase64Characters[i]] = (byte)i; 
	}

	public static char[] encode (byte[] in, int start, int length) {
		int unpadded = (length*4+2)/3;
		int padded = ((length+2)/3)*4;
		char[] ret = new char[padded];
		
		int end = start + length;
		int op = 0;
		while (start < end) {
			Block3 b = new Block3();
			for (int i = 0; i < 3; ++i) {
				b.put(i, start < end ? in[start++] & 0xff : 0);
			}
			
			ret[op] = convertOrPad(unpadded, op++, b.get(0) >>> 2);
			ret[op] = convertOrPad(unpadded, op++, ((b.get(0) & 0x03) << 4) | (b.get(1) >>> 4));
			ret[op] = convertOrPad(unpadded, op++, ((b.get(1) & 0x0f) << 2) | (b.get(2) >>> 6));
			ret[op] = convertOrPad(unpadded, op++, b.get(2) & 0x3F); 
		}
		
		return ret; 
	}

	private static char convertOrPad(int unpadded, int op, int o2) {
		return op < unpadded ? binaryToBase64Characters[o2] : '=';
	}

	public static char[] encode (byte[] in) {
		return encode(in, 0, in.length); }

	public static String encode(String s) {
		if (null == s) return null;
		return new String(encode(s.getBytes())); }

	public static byte[] decode (String s) {
		if (null == s) return null;
		return decode(s.toCharArray()); }

	public static String decodeString (String s) {
		if (null == s) return null;
		return new String(decode(s)); }

	public static byte[] decode (char[] in) {
		return decode(in, 0, in.length); }

	public static byte[] decode (char[] in, int start, int length) {
		if (length %4 != 0) throw new IllegalArgumentException ("Length of Base64 must be a multiple of 4");
		while (length > 0 && in[start+length-1] == '=') length--;
		
		int nchars = (length*3) / 4;
		byte[] ret = new byte[nchars];
		
		int end = start + length;
		int op = 0;
		while (start < end) {
			Block4 b = new Block4();
			for (int i = 0; i < 4; ++i) {
				int n = start < end ? in[start++] : 'A';
				b.put(i, base64CharactersToBinary[n]);
			}

			Block3 c = b.extract3();
			for (int i = 0; i < 3; ++i) {
				if (op < nchars) ret[op++] = (byte)c.get(i);
			}
		}
		
		return ret; 
	}
}
