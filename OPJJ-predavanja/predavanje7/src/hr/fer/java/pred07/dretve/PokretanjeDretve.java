package hr.fer.java.pred07.dretve;

public class PokretanjeDretve {
	
	private volatile static int brojac;

	public static void main(String[] args) {

		PosaoDretve posao = new PosaoDretve(500);
		Thread radnik = new Thread(posao, "radnik");
		radnik.start();
		
		while(true) {
			try {
				radnik.join();
			} catch (InterruptedException e) {
				continue;
			}
			break;
		}
		
		System.out.printf("Rezultat je %d.%n", brojac);
		
	}
	
	private static class PosaoDretve implements Runnable {
		
		private int brojUvecavanja;
		
		public PosaoDretve(int brojUvecavanja) {
			this.brojUvecavanja = brojUvecavanja;
		}

		@Override
		public void run() {
			for(int i=0; i<brojUvecavanja; i++) {
				brojac++;
			}
		}
		
	}

}
