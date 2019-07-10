package hr.fer.zemris.java.tecaj_7;

import java.util.Random;

public class BrojPISlijedni {

	public static void main(String[] args) {
		
		final int numberOfSamples = 100_000;

		double pi = izracunajSlijedno(numberOfSamples);
		System.out.println("Pi = " + pi);

	}

	private static double izracunajSlijedno(int numberOfSamples) {
		int inside = PIUtil.testNumbersInCircle(numberOfSamples, new Random());
		return 4*inside/(double)numberOfSamples;
	}
	
}