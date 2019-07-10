package hr.fer.zemris.java.pred05.primjer1;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.pred05.zaposlenici.util.Baza;

public class Demo {
	
	public static void main(String[] args) {
		
		List<Zaposlenik> lista = Baza.napuni(new ArrayList<>(), Zaposlenik::new);
		
		lista.forEach(System.out::println);
		
		Zaposlenik zaposlenik = new Zaposlenik("1","Perić","Pero");
		boolean sadrziZaposlenika = lista.contains(zaposlenik);
		int indeks = lista.indexOf(zaposlenik);
		System.out.println("SadrziZaposlenika = " + sadrziZaposlenika + "\nIndexOf zaposlenik = " + indeks);
		
	}

}
