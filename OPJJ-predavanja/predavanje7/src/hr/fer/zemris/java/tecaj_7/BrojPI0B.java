package hr.fer.zemris.java.tecaj_7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.java.tecaj_7.ThreadPool.JobInfo;

public class BrojPI0B {

	public static void main(String[] args) {
		
		final int numberOfSamples = 100_000_000;

		ThreadPool pool = new ThreadPool(2);

		for(int i = 0; i < 1; i++) {
			double pi = izracunajParalelno(pool, numberOfSamples);
			if(i==0) System.out.println("Pi = " + pi);
		}
		
		
		final int MEASURES = 1;
		long t0 = System.currentTimeMillis();
		for(int attempt = 0; attempt < MEASURES; attempt++) {
			izracunajParalelno(pool, numberOfSamples);
		}		
		long t1 = System.currentTimeMillis();
		System.out.println("Vrijeme operacije: "+ (t1-t0)/(double)MEASURES+" ms");
		
		pool.shutdown();
	}
	
	private static double izracunajParalelno(ThreadPool pool, int numberOfSamples) {
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
			}
		}

		Posao p1 = new Posao(numberOfSamples/2);
		Posao p2 = new Posao(numberOfSamples - numberOfSamples/2);
		List<JobInfo> rezultati = new ArrayList<JobInfo>();
		rezultati.add(pool.addJob(p1));
		rezultati.add(pool.addJob(p2));
		for(JobInfo f : rezultati) {
			while(true) {
				try {
					f.waitUntilNotExecuted();
					break;
				} catch (InterruptedException e) {
				}
			}
		}
		
		return 4*(p1.inside+p2.inside)/(double)numberOfSamples;
	}

	private static double izracunajParalelnoSkalabilno(ThreadPool pool, int numberOfSamples) {
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
			}
		}
		
		List<Posao> poslovi = new ArrayList<>();
		int left = numberOfSamples;
		for(int i = 0, n = pool.numberOfWorkers(); i < n; i++) {
			int howMany = numberOfSamples/n;
			if(i==n-1) howMany = left;
			poslovi.add(new Posao(howMany));
			left -= howMany;
		}

		List<JobInfo> rezultati = new ArrayList<JobInfo>();
		for(Posao p : poslovi) {
			rezultati.add(pool.addJob(p));
		}
		
		for(JobInfo f : rezultati) {
			while(true) {
				try {
					f.waitUntilNotExecuted();
					break;
				} catch (InterruptedException e) {
				}
			}
		}
		
		int sum = poslovi.stream().mapToInt(p->p.inside).sum();
		return 4*sum/(double)numberOfSamples;
	}
	
}