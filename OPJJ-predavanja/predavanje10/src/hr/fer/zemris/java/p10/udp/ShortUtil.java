package hr.fer.zemris.java.p10.udp;

public class ShortUtil {
	/**
	 * U predani spremnik {@code buf} od pozicije 
	 * {@code offset} zapisuje dva okteta u big-endian 
	 * poretku koja čine 16-bitova predane cjelobrojne
	 * varijable {@code number} koja je tipa {@code short}
	 * @param number broj koji treba rastaviti na oktete
	 * @param buf spremnik u koji treba zapisati rezultat
	 * @param offset pozicija od koje treba zapisati rezultat
	 */
	public static void shortToBytes(short number, 
		byte[] buf, int offset) {
		buf[offset] = (byte)((number>>8) & 0xFF);
		buf[offset+1] = (byte)(number & 0xFF);
	}
	
	/**
	 * Iz predanog spremnika {@code buf} počev od pozicije 
	 * {@code offset} čita dva okteta, tumači ih kao oktete 
	 * cijelog broja tipa {@code short} u
	 * formatu big-endian i rekonstruira taj cijeli broj. 
	 * @param buf spremnik iz kojeg treba uzeti dva okteta
	 * @param offset pozicija na kojoj se nalazi prvi oktet
	 * @return rekonstruirani cijeli broj
	 */
	public static short bytesToShort(byte[] buf, int offset) {
		return (short)(
			(buf[offset] & 0xFF) << 8 | 
			(buf[offset+1] & 0xFF));
	}
}