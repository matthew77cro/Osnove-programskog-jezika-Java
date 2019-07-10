package hr.fer.zermis.java.pred6;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ekstenzije2 {
	
	private static class Rezultat {
		public Map<String, Integer> mapa = new HashMap<>();
		public File stazaDoNajveceDatoteke = null;
		private long size = -1;
	}
	
	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Wrong number of arguments!");
			return;
		}
		
		File f = new File(args[0]);
		
		Rezultat rezultat = doTheJob(f);
		
		Comparator<Map.Entry<String, Integer>> poBroju = (p1, p2) -> p2.getValue().compareTo(p1.getValue());
		Comparator<Map.Entry<String, Integer>> poEkstenziji = (p1, p2) -> p1.getKey().compareTo(p2.getKey());
		
		List<Map.Entry<String, Integer>> lista = new ArrayList<>(rezultat.mapa.entrySet());
		lista.sort(poBroju.thenComparing(poEkstenziji));
		rezultat.mapa.forEach((k,v) -> System.out.printf("%s => %d%n",k,v));
		
		System.out.println("Staza do najvece datoteke " + rezultat.stazaDoNajveceDatoteke.getName());
		
	}
	
	public static Rezultat doTheJob(File file) {
		
		if(!file.isDirectory()) {
			throw new IllegalArgumentException();
		}
		
		Rezultat rez = new Rezultat();
		doTheJobRecursively(file, rez);
		return rez;
		
	}

	private static void doTheJobRecursively(File file, Rezultat rezultat) {
		File[] children = file.listFiles();
		if(children==null) return;
		
		for(File f : children) {
			
			if(f.isDirectory()) {
				doTheJobRecursively(f, rezultat);
			} else if(f.isFile()) {
				String extension = findExtension(f.getName());
				//mapa.compute(extension, (k,v) -> v==null ? 1 : v+1);
				rezultat.mapa.merge(extension, 1, Integer::sum);
				long velicina = f.length();
				if(velicina>rezultat.size) {
					rezultat.size = velicina;
					rezultat.stazaDoNajveceDatoteke = f;
				}
			}
			
		}
	}

	private static String findExtension(String name) {
		int index = name.lastIndexOf(".");
		if(index<1) return "";
		return name.substring(index+1).toLowerCase();
	}

}
