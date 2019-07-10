package hr.fer.zermis.java.pred6;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ekstenzije {
	
	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Wrong number of arguments!");
			return;
		}
		
		File f = new File(args[0]);
		
		Map<String, Integer> ekstenzije = countExtensions(f);
		
		Comparator<Map.Entry<String, Integer>> poBroju = (p1, p2) -> p2.getValue().compareTo(p1.getValue());
		Comparator<Map.Entry<String, Integer>> poEkstenziji = (p1, p2) -> p1.getKey().compareTo(p2.getKey());
		
		List<Map.Entry<String, Integer>> lista = new ArrayList<>(ekstenzije.entrySet());
		lista.sort(poBroju.thenComparing(poEkstenziji));
		ekstenzije.forEach((k,v) -> System.out.printf("%s => %d%n",k,v));
		
	}
	
	public static Map<String, Integer> countExtensions(File file) {
		
		if(!file.isDirectory()) {
			throw new IllegalArgumentException();
		}
		
		Map<String, Integer> mapa = new HashMap<String, Integer>();
		counteExtensionsRecursively(file, mapa);
		return mapa;
		
	}

	private static void counteExtensionsRecursively(File file, Map<String, Integer> mapa) {
		File[] children = file.listFiles();
		if(children==null) return;
		
		for(File f : children) {
			
			if(f.isDirectory()) {
				counteExtensionsRecursively(f, mapa);
			} else if(f.isFile()) {
				String extension = findExtension(f.getName());
				//mapa.compute(extension, (k,v) -> v==null ? 1 : v+1);
				mapa.merge(extension, 1, Integer::sum);
			}
			
		}
	}

	private static String findExtension(String name) {
		int index = name.lastIndexOf(".");
		if(index<1) return "";
		return name.substring(index+1).toLowerCase();
	}

}
