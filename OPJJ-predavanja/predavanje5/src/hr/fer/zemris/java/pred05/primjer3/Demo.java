package hr.fer.zemris.java.pred05.primjer3;

import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.java.pred05.zaposlenici.util.Baza;

public class Demo {
	
	public static void main(String[] args) {
		
		Set<Zaposlenik> lista = Baza.napuni(new TreeSet<>((z1,z2) -> z1.getSifra().compareTo(z2.getSifra())), Zaposlenik::new);
		
		lista.forEach(System.out::println);
		
		Zaposlenik zaposlenik = new Zaposlenik("1","Perić","Pero");
		boolean sadrziZaposlenika = lista.contains(zaposlenik);
		System.out.println("SadrziZaposlenika = " + sadrziZaposlenika);
		
	}

}
