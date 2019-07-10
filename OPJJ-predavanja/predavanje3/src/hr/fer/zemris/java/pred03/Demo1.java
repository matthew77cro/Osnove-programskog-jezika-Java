package hr.fer.zemris.java.pred03;

import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Demo1 {

	public static void main(String[] args) {
		
		GeometrijskiLik[] likovi = {
			new Pravokutnik(10, 10, 30, 20),
			new Krug(40, 60, 20),
			new Pravokutnik(70, 10, 25, 70)
		};
		
		Slika slika = new Slika(100, 100);
		
		for(GeometrijskiLik gl : likovi) {
			gl.popuniLik(slika);
		}
		
		slika.nacrtajSliku(System.out);
		
		PrikaznikSlike.prikaziSliku(slika, 4);
		
	}
	
}
