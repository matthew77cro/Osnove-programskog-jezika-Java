package hr.fer.zemris.java.pred02;

public class Primjer1 {

	public static void main(String[] args) {
		
		GeometrijskiLik l1 = new GeometrijskiLik("Lik1");
		GeometrijskiLik l2 = new GeometrijskiLik("Lik2");
		
		System.out.printf("Ime prvog lika je %s.%n", l1.getIme()); // GeometrijskiLik::getIme(l1);
		System.out.printf("Ime drugog lika je %s.%n", l2.getIme()); // GeometrijskiLik::getIme(l2);
		
		// GeometrijskiLik::getIme(l1); 
		//           :->:
		//    (l1->tblptr[0])(l1)
		// jer nasljedivanjem ugovor koji preuzimamo je samo u kojem slotu tablice metoda ce biti koja f-ja => overridanjem samo mijenjamo pointere u tablici pokazivaca na metode
		
	}

}
