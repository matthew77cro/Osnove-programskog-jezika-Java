package hr.fer.zemris.java.pred05.primjer4;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.pred05.zaposlenici.util.Baza;

public class Demo {
	
	public static void main(String[] args) {
		
		Map<Zaposlenik, Double> mapa = Baza.napuni(new HashMap<>(), Zaposlenik::new);
		
		mapa.forEach((k,v) -> System.out.printf("%s => %f%n", k, v));
		
		Zaposlenik zaposlenik = new Zaposlenik("1","Perić","Pero");
		boolean sadrziZaposlenika = mapa.containsKey(zaposlenik);
		System.out.println("SadrziZaposlenika = " + sadrziZaposlenika);
		
	}

}
