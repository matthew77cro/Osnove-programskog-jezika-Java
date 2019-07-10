package hr.fer.zemris.java.hw06.crypto;

/**
 * Some basic utilities used for Crypto.java program. This class contains two
 * static methods for converting number in hex format stored in a String to array of bytes
 * and vice versa.
 * @author Matija
 *
 */
public class Util {

	/**
	 * Method converts number in hex format stored in a String to array of bytes. Given
	 * argument must only contain characters 0-9 and a-f lower cased or upper cased.
	 * Method converts pairs of numbers and stores them in an array of bytes.
	 * @param hex number in hex format to be converted
	 * @return array of bytes converted from hex string
	 * @throws IllegalArgumentException if given argument has odd number of characters or it contains illegal character
	 */
	public static byte[] hextobyte(String hex) {
		
		int len = hex.length();
		if(len%2!=0) throw new IllegalArgumentException();
		if(len==0) return new byte[0];
		
		int byteLen = len/2;
		
		char[] strchr = hex.toCharArray();
		byte[] byteArray = new byte[byteLen];
		
		for(int i=0; i<byteLen; i++) {
			byteArray[i] = (byte) (16 * hexToDec(Character.toLowerCase(strchr[i*2])) 
					                  + hexToDec(Character.toLowerCase(strchr[i*2+1])));
		}
		
		return byteArray;
		
	}
	
	private static byte hexToDec(char hex) {
		
		if(hex>='0' && hex<='9') return Byte.parseByte(Character.toString(hex));
		
		byte value;
		switch(hex) {
			case 'a' :
				value = 10;
				break;
			case 'b' :
				value = 11;
				break;
			case 'c' :
				value = 12;
				break;
			case 'd' :
				value = 13;
				break;
			case 'e' :
				value = 14;
				break;
			case 'f' :
				value = 15;
				break;
			default :
				throw new IllegalArgumentException();
		}
		return value;
		
	}
	
	/**
	 * Method converts array of bytes to number in hex format stored in a String.
	 * @param byteArray array of bytes to be converted
	 * @return array of bytes converted from hex string
	 */
	public static String bytetohex(byte[] byteArray) {
		
		int len = byteArray.length;
		if(len==0) return "";
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<len; i++) {
			byte next = byteArray[i];
			
			byte lowerEnd = (byte) (next & 0x0F);
			byte upperEnd = (byte) ((next >> 4) & 0x0F);
			
			sb.append(decToHex(upperEnd));
			sb.append(decToHex(lowerEnd));
		}
		
		return sb.toString();
		
	}
	
	private static char decToHex(byte dec) {
		if(dec<0 || dec>15) throw new IllegalArgumentException();
		
		if(dec>=0 && dec<=9) return Byte.toString(dec).charAt(0);
		
		char value;
		switch(dec) {
			case 10 :
				value = 'a';
				break;
			case 11 :
				value = 'b';
				break;
			case 12 :
				value = 'c';
				break;
			case 13 :
				value = 'd';
				break;
			case 14 :
				value = 'e';
				break;
			case 15 :
				value = 'f';
				break;
			default :
				throw new IllegalArgumentException();
		}
		return value;
		
	}

}
