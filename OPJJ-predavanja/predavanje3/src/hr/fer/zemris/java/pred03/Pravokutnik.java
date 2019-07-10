package hr.fer.zemris.java.pred03;

import java.util.Objects;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Pravokutnik extends GeometrijskiLik {

	private int vrhX;
	private int vrhY;
	private int sirina;
	private int visina;
	
	public Pravokutnik(int vrhX, int vrhY, int sirina, int visina) {
		super();
		this.vrhX = vrhX;
		this.vrhY = vrhY;
		this.sirina = sirina;
		this.visina = visina;
	}

	public int getVrhX() {
		return vrhX;
	}

	public int getVrhY() {
		return vrhY;
	}

	public int getSirina() {
		return sirina;
	}

	public int getVisina() {
		return visina;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sirina, visina, vrhX, vrhY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pravokutnik))
			return false;
		Pravokutnik other = (Pravokutnik) obj;
		return sirina == other.sirina && visina == other.visina && vrhX == other.vrhX && vrhY == other.vrhY;
	}

	@Override
	public boolean sadrziTocku(int x, int y) {
		if(x<vrhX) return false;
		if(x>=vrhX+sirina) return false;
		
		if(y<vrhY) return false;
		if(y>=vrhY+visina) return false;
		
		return true;
	}
	
	@Override
	public void popuniLik(Slika slika) {
		for(int i = vrhX, xend = vrhX+sirina; i<xend; i++) {
			for(int j = vrhY, yend = vrhY+visina; j<yend; j++) {
				slika.upaliTocku(i, j);
			}
		}
	}
	
}