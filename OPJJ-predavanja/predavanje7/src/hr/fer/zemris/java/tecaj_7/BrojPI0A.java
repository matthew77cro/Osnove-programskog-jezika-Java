package hr.fer.zemris.java.tecaj_7;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class BrojPI0A {

	public static void main(String[] args) {
		
		final int numberOfSamples = 100_000_000;

		ThreadPoolSimple pool = new ThreadPoolSimple(2);

		for(int i = 0; i < 1; i++) {
			double pi = izracunajParalelnoSimple(pool, numberOfSamples);
			if(i==0) System.out.println("Pi = " + pi);
		}
		
		
		final int MEASURES = 1;
		long t0 = System.currentTimeMillis();
		for(int attempt = 0; attempt < MEASURES; attempt++) {
			izracunajParalelnoSimple(pool, numberOfSamples);
		}		
		long t1 = System.currentTimeMillis();
		System.out.println("Vrijeme operacije: "+ (t1-t0)/(double)MEASURES+" ms");
		
		pool.shutdown();
	}

	private static double izracunajParalelnoSimple(ThreadPoolSimple pool, int numberOfSamples) {
		Semaphore sem = new Semaphore(0);
		
		class Posao implements Runnable {
			int inside;
			int samples;
			public Posao(int samples) {
				super();
				this.samples = samples;
			}
			@Override
			public void run() {
				inside = PIUtil.testNumbersInCircle(samples, new Random());
				sem.release();
			}
		}

		Posao p1 = new Posao(numberOfSamples/2);
		Posao p2 = new Posao(numberOfSamples - numberOfSamples/2);
		
		pool.addJob(p1);
		pool.addJob(p2);

		sem.acquireUninterruptibly(2);
		
		return 4*(p1.inside+p2.inside)/(double)numberOfSamples;
	}
	
}
