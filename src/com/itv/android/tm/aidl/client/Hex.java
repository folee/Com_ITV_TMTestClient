package com.itv.android.tm.aidl.client;

public class Hex {

	/**
	 * ?¨ä?å»ºç????è¿??å­??????ºç?å°??å­???°ç?
	 */
	private static final char[]	DIGITS_LOWER	= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
			'd', 'e', 'f'						};

	/**
	 * ?¨ä?å»ºç????è¿??å­??????ºç?å¤§å?å­???°ç?
	 */
	private static final char[]	DIGITS_UPPER	= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
			'D', 'E', 'F'						};

	/**
	 * å°?????ç»?½¬??¸º???è¿??å­???°ç?
	 * 
	 * @param data
	 *            byte[]
	 * @return ???è¿??char[]
	 */
	public static char[] encodeHex(byte[] data) {
		return encodeHex(data, true);
	}

	/**
	 * å°?????ç»?½¬??¸º???è¿??å­???°ç?
	 * 
	 * @param data
	 *            byte[]
	 * @param toLowerCase
	 *            <code>true</code> ä¼????????å¼?ï¼?<code>false</code> ä¼????¤§???å¼?	 * @return ???è¿??char[]
	 */
	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * å°?????ç»?½¬??¸º???è¿??å­???°ç?
	 * 
	 * @param data
	 *            byte[]
	 * @param toDigits
	 *            ?¨ä??§å?è¾????har[]
	 * @return ???è¿??char[]
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
	 * å°?????ç»?½¬??¸º???è¿??å­??ä¸?	 * 
	 * @param data
	 *            byte[]
	 * @return ???è¿??String
	 */
	public static String encodeHexStr(byte[] data) {
		return encodeHexStr(data, true);
	}

	/**
	 * å°?????ç»?½¬??¸º???è¿??å­??ä¸?	 * 
	 * @param data
	 *            byte[]
	 * @param toLowerCase
	 *            <code>true</code> ä¼????????å¼?ï¼?<code>false</code> ä¼????¤§???å¼?	 * @return ???è¿??String
	 */
	public static String encodeHexStr(byte[] data, boolean toLowerCase) {
		return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * å°?????ç»?½¬??¸º???è¿??å­??ä¸?	 * 
	 * @param data
	 *            byte[]
	 * @param toDigits
	 *            ?¨ä??§å?è¾????har[]
	 * @return ???è¿??String
	 */
	protected static String encodeHexStr(byte[] data, char[] toDigits) {
		return new String(encodeHex(data, toDigits));
	}

	/**
	 * å°??????¶å?ç¬??ç»?½¬??¸ºå­???°ç?
	 * 
	 * @param data
	 *            ???è¿??char[]
	 * @return byte[]
	 * @throws RuntimeException
	 *             å¦??æº??????¶å?ç¬??ç»??ä¸?¸ªå¥?????åº??å°???ºè?è¡??å¼?¸¸
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
	 * å°??????¶å?ç¬?½¬?¢æ?ä¸?¸ª?´æ?
	 * 
	 * @param ch
	 *            ???è¿??char
	 * @param index
	 *            ???è¿??å­???¨å?ç¬??ç»?¸­???ç½?	 * @return ä¸?¸ª?´æ?
	 * @throws RuntimeException
	 *             å½?hä¸??ä¸?¸ª??????????¶å?ç¬??ï¼???ºè?è¡??å¼?¸¸
	 */
	protected static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}

	public static void main(String[] args) {
		String srcStr = "å¾?½¬?¢å?ç¬?¸²";
		String encodeStr = encodeHexStr(srcStr.getBytes());
		String decodeStr = new String(decodeHex(encodeStr.toCharArray()));
		System.out.println("è½?????" + srcStr);
		System.out.println("è½?????" + encodeStr);
		System.out.println("è¿?????" + decodeStr);
	}

}
