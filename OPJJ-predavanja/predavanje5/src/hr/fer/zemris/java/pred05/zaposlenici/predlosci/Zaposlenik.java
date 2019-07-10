package hr.fer.zemris.java.pred05.zaposlenici.predlosci;

public class Zaposlenik {

	private String sifra;
	private String prezime;
	private String ime;
	private double placa;
	
	public Zaposlenik(String sifra, String prezime, String ime) {
		super();
		this.sifra = sifra;
		this.prezime = prezime;
		this.ime = ime;
	}
	
	public String getSifra() {
		return sifra;
	}
	
	public String getPrezime() {
		return prezime;
	}
	
	public String getIme() {
		return ime;
	}

	public double getPlaca() {
		return placa;
	}
	
	public void setPlaca(double placa) {
		this.placa = placa;
	}
	
	@Override
	public String toString() {
		return String.format("Zaposlenik: šifra=%s, prezime=%s, ime=%s, plaća=%f", sifra, prezime, ime, placa);
	}
	
}
