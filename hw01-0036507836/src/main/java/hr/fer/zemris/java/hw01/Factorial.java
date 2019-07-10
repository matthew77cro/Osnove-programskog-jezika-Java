package hr.fer.zemris.java.hw01;

import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.Scanner;

/**
 * 
 * This program calculates factorial function for a given number. Program receives a number via console input.
 * Program will not calculate factorial for a number that is less than 3 or grater than 20. Program will quit
 * if value entered in console input is "kraj".
 * 
 * @author Matija Bačić
 * 
 */
public class Factorial {

	private static final int LOWER_BOUNDARY = 3;
	private static final int UPPER_BOUNDARY = 20;
	private static final String TERMINATE_KEYWORD = "kraj";
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			OptionalInt input;
			try {
				input = nextIntegerConsoleInput("Unesite broj > ", "'%s' nije cijeli broj.%n", sc);
			}catch (NoSuchElementException ex) {
				break;
			}
			int number;
			if(input.isPresent()) number = input.getAsInt();
			else continue;
			//method calculateFactorial calculates factorial of number only if it is within boundaries and prints it on standard output
			calculateFactorial(number);
		}
		
		System.out.println("Doviđenja.");
		
		sc.close();
	}

	/**
	 * This method calculates factorial value for a given integer.
	 * @param input integer greater than 0
	 * @return calculated factorial function for a given input
	 * @throws IllegalArgumentException if input is less than 0
	 */
	public static long factorial(int input) {
		if(input<0) throw new IllegalArgumentException();
		if(input==0) return 1;
		
		return input*factorial(input-1);
	}
	
	//This method returns next integer that user entered via console input
	private static OptionalInt nextIntegerConsoleInput(String inputMsg, String errorMsg, Scanner sc) {
		String in = nextConsoleInput(inputMsg, sc);
		
		if(checkForTerminate(in,TERMINATE_KEYWORD)) throw new NoSuchElementException("Terminate key word detected");
		
		int input;
		try {
			input = Integer.parseInt(in);
		} catch (NumberFormatException ex) {
			System.out.printf(errorMsg, in);
			return OptionalInt.empty();
		}
		
		return OptionalInt.of(input);
	}
	
	//Returns next input user entered via console as a string, null if scanner does not have next element
	private static String nextConsoleInput(String inputMsg, Scanner sc) {
		System.out.print(inputMsg);
		if(!sc.hasNext()) return null;
		String input = sc.next();
		return input;
	}
	
	//checks whether string s is terminate keyword
	private static boolean checkForTerminate(String s, String terminateKeyword) {
		if(s.equals(terminateKeyword)) return true;
		return false;
	}
	
	private static void calculateFactorial(int num) {
		//checks for boundaries
		if(!isWithinBoundaries(num, LOWER_BOUNDARY, UPPER_BOUNDARY)) {
			System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", num);
			return;
		}
		
		//print factorial
		System.out.printf("%d! = %d%n", num, factorial(num));
	}
	
	private static boolean isWithinBoundaries(int number, int low, int up) {
		if(number>=low && number<=up) {
			return true;
		}
		return false;
	}
	
}
