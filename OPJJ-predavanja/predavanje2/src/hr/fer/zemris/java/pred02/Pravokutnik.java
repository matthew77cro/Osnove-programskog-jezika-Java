package hr.fer.zemris.java.pred02;

import java.util.Objects;

public class Pravokutnik extends GeometrijskiLik {

	private int vrhX;
	private int vrhY;
	private int sirina;
	private int visina;
	
	public Pravokutnik(String ime) {
		this(ime, 1, 1);
	}
	
	public Pravokutnik(String ime, int vrhX, int vrhY) {
		this(ime, vrhX, vrhY, 20, 10);
	}
	
	public Pravokutnik(String ime, int vrhX, int vrhY, int sirina, int visina) {
		super(ime);
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
	public double getOpseg() {
		return 2*(sirina+visina);
	}
	
	@Override
	public double getPovrsina() {
		return sirina*visina;
	}

	@Override
	public String toString() {
		return super.toString() + " Pravokutnik [vrhX=" + vrhX + ", vrhY=" + vrhY + ", sirina=" + sirina + ", visina=" + visina + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(sirina, visina, vrhX, vrhY);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Pravokutnik))
			return false;
		Pravokutnik other = (Pravokutnik) obj;
		return sirina == other.sirina && visina == other.visina && vrhX == other.vrhX && vrhY == other.vrhY;
	}
	
}