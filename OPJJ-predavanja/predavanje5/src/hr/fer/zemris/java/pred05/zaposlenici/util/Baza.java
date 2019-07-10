package hr.fer.zemris.java.pred05.zaposlenici.util;

import java.util.Collection;
import java.util.Map;

public class Baza {

	public static <T, R extends Collection<T>> R napuni(R kolekcija, IZaposlenikCreator<T> kreator) {
		kolekcija.add(kreator.create("1", "Perić", "Pero"));
		kolekcija.add(kreator.create("2", "Agić", "Agata"));
		kolekcija.add(kreator.create("3", "Ivić", "Ivana"));
		return kolekcija;
	}

	public static <T, R extends Map<T,Double>> R napuni(R kolekcija, IZaposlenikCreator<T> kreator) {
		kolekcija.put(kreator.create("1", "Perić", "Pero"), 100.0);
		kolekcija.put(kreator.create("2", "Agić", "Agata"), 200.0);
		kolekcija.put(kreator.create("3", "Ivić", "Ivana"), 300.0);
		return kolekcija;
	}

}
