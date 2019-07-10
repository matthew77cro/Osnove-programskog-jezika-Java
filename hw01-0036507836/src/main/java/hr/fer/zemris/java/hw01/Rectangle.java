package hr.fer.zemris.java.hw01;

import java.util.OptionalDouble;
import java.util.Scanner;

/**
 * 
 * This program calculates area and perimeter of a rectangle. Input is given via console arguments
 * or (if there are 0 arguments in program call) via console input. If the number of program call 
 * arguments is different from 0 and different from 2, program will not run. All inputs must be
 * positive double values or program will not run. Here are some examples:</br></br>
 * 
 * NOT VALID PROGRAM CALLS:</br>
 * Rectangle word 2 3 (number of arguments is different from 0 and different from 2)</br>
 * Rectangle word 2   (one of the arguments is not parseable to Double value - NaN value)</br>
 * Rectangle -2 2     (one of the arguments is negative)</br></br>
 * 
 * VALID PROGRAM CALLS:</br>
 * Rectangle (will give you console input)</br>
 * Rectangle 2.5 3.5 (will calculate area and perimeter of a rectangle with sides 2.5 and 3.5 units)</br>
 * Rectangle 2 3 (will calculate area and perimeter of a rectangle with sides 2 and 3 units)</br>
 * 
 * @author Matija Bačić
 *
 */
public class Rectangle {
	
	public static void main(String[] args) {
		
		if(args.length!=0 && args.length!=2) {
			System.out.println("Broj argumenata mora biti 0 ili 2.");
			return;
		}
		
		double width=0, height=0;
		
		if(args.length==2) {
			try {
				width=Double.parseDouble(args[0]);
				height=Double.parseDouble(args[1]);
			} catch(NumberFormatException ex) {
				System.out.println("Predani argumenti moraju biti brojevi.");
				return;
			}
		}else {
			Scanner sc = new Scanner(System.in);
			
			OptionalDouble w, h;
			do {
				w = nonNegativeNextDoubleConsoleInput("Unesite širinu > ", "'%s' se ne može protumačiti kao broj.%n", "Unijeli ste negativnu vrijednost.", sc);
			} while(w.isEmpty());
			do {
				h = nonNegativeNextDoubleConsoleInput("Unesite visinu > ", "'%s' se ne može protumačiti kao broj.%n", "Unijeli ste negativnu vrijednost.", sc);
			} while(h.isEmpty());
			
			width = w.getAsDouble();
			height = h.getAsDouble();
			sc.close();
		}
		
		double perimeter = perimeter(width, height);
		double area = area(width, height);
		System.out.printf("Pravokutnik širine %s i visine %s ima površinu %s te opseg %s.", Double.toString(width), Double.toString(height), Double.toString(area), Double.toString(perimeter));
	}
	
	//return number entered via console as optionalDouble, empty if number entered is negative or NaN or scanner does not have next value
	private static OptionalDouble nonNegativeNextDoubleConsoleInput(String inputMsg, String NaNErrorMsg, String negativeErrorMsg, Scanner sc) {
		OptionalDouble temp = nextDoubleConsoleInput(inputMsg, NaNErrorMsg, sc);
		if(temp.isEmpty()) return temp;
		if(temp.getAsDouble()<0) {
			System.out.println(negativeErrorMsg);
			return OptionalDouble.empty();
		}
		return temp;
	}
	
	//This method returns next double that user entered via console input as optionalDouble, empty if input entered is NaN or scanner does not have next value
	private static OptionalDouble nextDoubleConsoleInput(String inputMsg, String NaNErrorMsg, Scanner sc) {
		String in = nextConsoleInput(inputMsg, sc);
		if(in==null) return OptionalDouble.empty();
		
		double input;
		try {
			input = Double.parseDouble(in);
		} catch (NumberFormatException ex) {
			System.out.printf(NaNErrorMsg, in);
			return OptionalDouble.empty();
		}
		
		return OptionalDouble.of(input);
	}
	
	//Returns next input user entered via console as a string, null if scanner does not have next element
	private static String nextConsoleInput(String inputMsg, Scanner sc) {
		System.out.print(inputMsg);
		if(!sc.hasNext()) return null;
		String input = sc.next();
		return input;
	}

	/**
	 * Calculates area of a rectangle with given width and height. Both
	 * arguments must be greater than 0 or method will throw IllegalArgumentException.
	 * @param width width of a rectangle
	 * @param height height of a rectangle
	 * @return calculated area of a rectangle with given width and height
	 * @throws IllegalArgumentException if any of the arguments is less than 0
	 */
	public static double area(double width, double height) {
		if(width<0 || height<0) throw new IllegalArgumentException();
		return width*height;
	}

	/**
	 * Calculates perimeter of a rectangle with given width and height. Both
	 * arguments must be greater than 0 or method will throw IllegalArgumentException.
	 * @param width width of a rectangle
	 * @param height height of a rectangle
	 * @return calculated perimeter of a rectangle with given width and height
	 * @throws IllegalArgumentException if any of the arguments is less than 0
	 */
	public static double perimeter(double width, double height) {
		if(width<0 || height<0) throw new IllegalArgumentException();
		return 2*(width+height);
	}
	
}
