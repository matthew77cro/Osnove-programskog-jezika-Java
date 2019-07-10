package hr.fer.zemris.java.pred05.primjer6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import hr.fer.zemris.java.pred05.zaposlenici.util.Baza;

public class Demo {
	
	public static void main(String[] args) {
		
		List<Zaposlenik> lista = Baza.napuni(new ArrayList<>(), Zaposlenik::new);
		
		Map<String, Zaposlenik> mapa = lista.stream().collect(Collectors.toMap((z) -> z.getSifra(), (z) -> z));
		int broj1 = (int) lista.stream().filter((z) -> z.getIme().length()>4).count();
		int broj2 = (int) lista.stream().mapToInt((z) -> z.getIme().length()).sum();
		double broj3 = lista.stream().mapToInt((z) -> z.getIme().length()).average().getAsDouble();
		
		mapa.forEach((s,z) -> System.out.println(s + " " + z.toString()));
		System.out.printf("%d %d %f%n", broj1, broj2, broj3);
		
	}

}
