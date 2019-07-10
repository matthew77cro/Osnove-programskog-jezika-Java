package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program Crypto allows the user to encrypt/decrypt given file using the AES cryptoalgorithm 
 * and the 128-bit encryption key or calculate and check the SHA-256 file digest.
 * <br><br>
 * Usage:<br>
 * java hr.fer.zemris.java.hw06.crypto.Crypto checksha {FILENAME}<br>
 * java hr.fer.zemris.java.hw06.crypto.Crypto encrypt {FILENAME}<br>
 * java hr.fer.zemris.java.hw06.crypto.Crypto decrypt {FILENAME}<br>
 * @author Matija
 *
 */
public class Crypto {
	
	private static Scanner sc;

	public static void main(String[] args) {
		
		if(args.length<2) {
			System.err.println("Not enough arguments!");
			return;
		}
		
		sc = new Scanner(System.in);
		compute(args);
		sc.close();
		
	}
	
	/**
	 * This method checks the input command and calls the right function to be executed on a given file.
	 * @param input user input
	 */
	private static void compute(String[] input) {
		
		if(input[0].equals("checksha")) {
			if(input.length!=2) {
				System.err.println("Expected only one argument after " + input[0]);
				return;
			}
			
			checksha(input[1]);
		} else if(input[0].equals("encrypt")) {
			if(input.length!=3) {
				System.err.println("Expected only one argument after " + input[0]);
				return;
			}
			
			crypt(input[1], input[2], true);
		} else if(input[0].equals("decrypt")) {
			if(input.length!=3) {
				System.err.println("Expected only one argument after " + input[0]);
				return;
			}
			
			crypt(input[1], input[2], false);
		} else {
			System.err.println("Command" + input[0] + " not supported!");
		}
		
	}
	
	/**
	 * Method for checking the SHA-256 file digest
	 * @param input user input
	 */
	private static void checksha(String input) {
		
		System.out.printf("Please provide expected sha-256 digest for " + input + ":%n> ");
		
		String givenDigest = sc.nextLine();
		
		Path p = Paths.get(input);
		
		byte[] computedDigest = null;
		try {
			BufferedInputStream in = new BufferedInputStream(Files.newInputStream(p, StandardOpenOption.READ));
			byte[] b = in.readAllBytes();
			in.close();
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(b);
			computedDigest = md.digest();
		} catch (Exception e) {
			System.err.println("An error occured!");
			System.err.println(e.getClass().toString() + " " + e.getLocalizedMessage());
			return;
		}
		
		if(givenDigest.equalsIgnoreCase(Util.bytetohex(computedDigest))) {
			System.out.println("Digesting completed. Digest of " + input + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + input + " does not match the expected digest. Digest was: " + Util.bytetohex(computedDigest));
		}
		
	}
	
	/**
	 * Method for encrypting/decrypting given file using the AES cryptoalgorithm and the 128-bit encryption key.
	 * @param input input file destination
	 * @param output output file destination
	 * @param encrypt if true, file will be encrypted, otherwise it will be decrypted
	 */
	private static void crypt(String input, String output, boolean encrypt) {
		
		System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
		String keyText = sc.nextLine();
		
		System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n> ");
		String ivText = sc.nextLine();
		
		Path newFile;
		Cipher cipher;
		try {
			newFile = Files.createFile(Paths.get(output));
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (Exception e) {
			System.err.println("An error occured!");
			System.err.println(e.getClass().toString() + " " + e.getLocalizedMessage());
			return;
		}
		
		try {
			BufferedOutputStream outStream = new BufferedOutputStream(Files.newOutputStream(newFile, StandardOpenOption.WRITE));
			BufferedInputStream inStream = new BufferedInputStream(Files.newInputStream(Paths.get(input), StandardOpenOption.READ));
			byte[] b = new byte[4096];

			while(true) {
				int r = inStream.read(b);
				if(r<1) break;
				outStream.write(cipher.update(b, 0, r));
			}
			
			outStream.write(cipher.doFinal());
			
			inStream.close();
			outStream.close();
		} catch (Exception e) {
			System.err.println("An error occured!");
			System.err.println(e.getClass().toString() + " " + e.getLocalizedMessage());
			return;
		}
		
		if(encrypt) {
			System.out.print("Encryption ");
		} else {
			System.out.print("Decryption ");
		}
		
		System.out.println("completed. Generated file " + output + " based on file " + input + ".");
		
	}

}
