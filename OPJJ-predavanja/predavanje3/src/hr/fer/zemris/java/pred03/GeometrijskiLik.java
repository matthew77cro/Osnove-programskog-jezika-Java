package hr.fer.zemris.java.pred03;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public abstract class GeometrijskiLik {
	
	abstract boolean sadrziTocku(int x, int y);
	
	public void popuniLik(Slika slika) {
		int sirina = slika.getSirina();
		int visina = slika.getVisina();
		
		for(int i=0; i<sirina; i++) {
			for(int j=0; j<visina; j++) {
				if(this.sadrziTocku(i, j)) {
					slika.upaliTocku(i, j);
				}
			}
		}
	}

}
