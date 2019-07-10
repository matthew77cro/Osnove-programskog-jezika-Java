package hr.fer.zemris.java.pred05.zaposlenici.util;

public interface IZaposlenikCreator<T> {
	T create(String sifra, String prezime, String ime);
}
