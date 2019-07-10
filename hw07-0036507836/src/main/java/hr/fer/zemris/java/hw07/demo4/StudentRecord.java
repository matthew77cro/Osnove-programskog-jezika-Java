package hr.fer.zemris.java.hw07.demo4;

public class StudentRecord {
	
	private String jmbag;
	private String prezime;
	private String ime;
	private double bodoviMi;
	private double bodoviZi;
	private double bodoviLab;
	private int ocjena;
	
	public StudentRecord(String jmbag, String prezime, String ime, double bodoviMi, double bodoviZi, 
			double bodoviLab, int ocjena) {
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.bodoviMi = bodoviMi;
		this.bodoviZi = bodoviZi;
		this.bodoviLab = bodoviLab;
		this.ocjena = ocjena;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getIme() {
		return ime;
	}

	public double getBodoviMi() {
		return bodoviMi;
	}

	public double getBodoviZi() {
		return bodoviZi;
	}

	public double getBodoviLab() {
		return bodoviLab;
	}

	public int getOcjena() {
		return ocjena;
	}
	
	public double ukupnoBodova() {
		return bodoviMi + bodoviZi + bodoviLab;
	}

	@Override
	public String toString() {
		return String.format("%s\t%s\t%s\t%f\t%f\t%f\t%d", jmbag, prezime, ime, bodoviMi, bodoviZi, 
				bodoviLab, ocjena);
	}

}
