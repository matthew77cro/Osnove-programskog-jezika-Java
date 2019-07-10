package hr.fer.zermis.java.pred6;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EkstenzijeBolje {
	
	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Wrong number of arguments!");
			return;
		}
		
		File f = new File(args[0]);
		
		BrojaloEkstenzija rez = new BrojaloEkstenzija();
		Obilazak.obidi(f, rez);
		
		Map<String, Integer> ekstenzije = rez.mapa;
		
		Comparator<Map.Entry<String, Integer>> poBroju = (p1, p2) -> p2.getValue().compareTo(p1.getValue());
		Comparator<Map.Entry<String, Integer>> poEkstenziji = (p1, p2) -> p1.getKey().compareTo(p2.getKey());
		
		List<Map.Entry<String, Integer>> lista = new ArrayList<>(ekstenzije.entrySet());
		lista.sort(poBroju.thenComparing(poEkstenziji));
		ekstenzije.forEach((k,v) -> System.out.printf("%s => %d%n",k,v));
		
	}
	
	private static class BrojaloEkstenzija extends DefaultObrada {
		
		private Map<String, Integer> mapa = new HashMap<String, Integer>();

		@Override
		public void imamDatoteku(File file) {
			String extension = findExtension(file.getName());
			//mapa.compute(extension, (k,v) -> v==null ? 1 : v+1);
			mapa.merge(extension, 1, Integer::sum);
		}
		
	
		private static String findExtension(String name) {
			int index = name.lastIndexOf(".");
			if(index<1) return "";
			return name.substring(index+1).toLowerCase();
		}
	}

}
