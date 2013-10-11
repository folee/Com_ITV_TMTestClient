package com.itv.android.tm.aidl.client;

public class Hex {

	/**
	 * ?��?建�????�??�??????��?�??�???��?
	 */
	private static final char[]	DIGITS_LOWER	= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
			'd', 'e', 'f'						};

	/**
	 * ?��?建�????�??�??????��?大�?�???��?
	 */
	private static final char[]	DIGITS_UPPER	= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
			'D', 'E', 'F'						};

	/**
	 * �?????�?��??��???�??�???��?
	 * 
	 * @param data
	 *            byte[]
	 * @return ???�??char[]
	 */
	public static char[] encodeHex(byte[] data) {
		return encodeHex(data, true);
	}

	/**
	 * �?????�?��??��???�??�???��?
	 * 
	 * @param data
	 *            byte[]
	 * @param toLowerCase
	 *            <code>true</code> �????????�?�?<code>false</code> �????��???�?	 * @return ???�??char[]
	 */
	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * �?????�?��??��???�??�???��?
	 * 
	 * @param data
	 *            byte[]
	 * @param toDigits
	 *            ?��??��?�????har[]
	 * @return ???�??char[]
	 */
	protected static char[] encodeHex(byte[] data, char[] toDigits) {
		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

	/**
	 * �?????�?��??��???�??�??�?	 * 
	 * @param data
	 *            byte[]
	 * @return ???�??String
	 */
	public static String encodeHexStr(byte[] data) {
		return encodeHexStr(data, true);
	}

	/**
	 * �?????�?��??��???�??�??�?	 * 
	 * @param data
	 *            byte[]
	 * @param toLowerCase
	 *            <code>true</code> �????????�?�?<code>false</code> �????��???�?	 * @return ???�??String
	 */
	public static String encodeHexStr(byte[] data, boolean toLowerCase) {
		return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * �?????�?��??��???�??�??�?	 * 
	 * @param data
	 *            byte[]
	 * @param toDigits
	 *            ?��??��?�????har[]
	 * @return ???�??String
	 */
	protected static String encodeHexStr(byte[] data, char[] toDigits) {
		return new String(encodeHex(data, toDigits));
	}

	/**
	 * �??????��?�??�?��??���???��?
	 * 
	 * @param data
	 *            ???�??char[]
	 * @return byte[]
	 * @throws RuntimeException
	 *             �??�??????��?�??�??�?���?????�??�???��?�??�?��
	 */
	public static byte[] decodeHex(char[] data) {

		int len = data.length;

		if ((len & 0x01) != 0) {
			throw new RuntimeException("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	/**
	 * �??????��?�?��?��?�?��?��?
	 * 
	 * @param ch
	 *            ???�??char
	 * @param index
	 *            ???�??�???��?�??�?��???�?	 * @return �?��?��?
	 * @throws RuntimeException
	 *             �?h�??�?��??????????��?�??�???��?�??�?��
	 */
	protected static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}

	public static void main(String[] args) {
		String srcStr = "�?��?��?�?��";
		String encodeStr = encodeHexStr(srcStr.getBytes());
		String decodeStr = new String(decodeHex(encodeStr.toCharArray()));
		System.out.println("�?????" + srcStr);
		System.out.println("�?????" + encodeStr);
		System.out.println("�?????" + decodeStr);
	}

}
