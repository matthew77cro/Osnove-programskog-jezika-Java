package hr.fer.zemris.java.tecaj_7;

import java.util.Random;

public class BrojPIParalelno0 {

	public static void main(String[] args) {
		
		final int numberOfSamples = 100_000;

		double pi = izracunajParalelno(numberOfSamples);
		System.out.println("Pi = " + pi);

	}

	private static double izracunajParalelno(int numberOfSamples) {
		
		class Posao implements Runnable {
			private int inside;
			private int trebaRaditi;
			
			public Posao(int trebaRaditi) {
				this.trebaRaditi = trebaRaditi;
			}

			public void run() {
				inside = PIUtil.testNumbersInCircle(trebaRaditi, new Random());
			}
		}
		
		Posao[] poslovi = new Posao[] {
			new Posao(numberOfSamples/2),
			new Posao(numberOfSamples-numberOfSamples/2)
		};
		
		Thread[] radnici = new Thread[poslovi.length];
		for(int i=0; i<radnici.length; i++) {
			radnici[i] = new Thread(poslovi[i]);
			radnici[i].start();
		}
		
		for(Thread t : radnici) {
			while(true) {
				try {
					t.join();
				} catch (InterruptedException ex) {
					continue;
				}
				break;
			}
		}
		
		int inside = 0;
		for(Posao p : poslovi) {
			inside += p.inside;
		}
		
		return 4*inside/(double)numberOfSamples;
	}
	
}