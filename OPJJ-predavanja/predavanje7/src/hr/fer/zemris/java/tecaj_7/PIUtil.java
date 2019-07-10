package hr.fer.zemris.java.tecaj_7;

import java.util.Random;

public class PIUtil {

	public static int testNumbersInCircle(int numberOfSamples, Random rand) {
		int inside = 0;
		for(int i = 0; i < numberOfSamples; i++) {
			double x = rand.nextDouble()*2-1;
			double y = rand.nextDouble()*2-1;
			if(x*x + y*y <= 1) {
				inside++;
			}
		}
		return inside;
	}

}