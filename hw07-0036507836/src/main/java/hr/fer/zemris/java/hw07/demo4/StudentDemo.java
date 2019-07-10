package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDemo {

	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		List<StudentRecord> records = convert(lines);
		
		long broj = vratiBodovaViseOd25(records);
		long broj5 = vratiBrojOdlikasa(records);
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		
		System.out.printf("Zadatak 1%n=========%n");
		System.out.println(broj);
		System.out.printf("Zadatak 2%n=========%n");
		System.out.println(broj5);
		System.out.printf("Zadatak 3%n=========%n");
		odlikasi.forEach(System.out::println);
		System.out.printf("Zadatak 4%n=========%n");
		odlikasiSortirano.forEach(System.out::println);
		System.out.printf("Zadatak 5%n=========%n");
		nepolozeniJMBAGovi.forEach(System.out::println);
		System.out.printf("Zadatak 6%n=========%n");
		mapaPoOcjenama.forEach((ocjena, lista) -> lista.forEach((record) -> System.out.println(ocjena + " => " + record)));
		System.out.printf("Zadatak 7%n=========%n");
		mapaPoOcjenama2.forEach((ocjena, brojStud) -> System.out.println(ocjena + " => " + brojStud));
		System.out.printf("Zadatak 8%n=========%n");
		prolazNeprolaz.forEach((ocjena, lista) -> lista.forEach((record) -> System.out.println(ocjena + " => " + record)));
		
	}
	
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				.mapToDouble((record) -> record.ukupnoBodova())
				.filter((d) -> d>25)
				.count();
	}
	
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
		.filter((record) -> record.getOcjena()==5)
		.count();
	}
	
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter((record) -> record.getOcjena()==5)
				.collect(Collectors.toList());
	}
	
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
		.filter((record) -> record.getOcjena()==5)
		.sorted((r1, r2) -> Double.compare(r2.ukupnoBodova(), r1.ukupnoBodova()))
		.collect(Collectors.toList());
	}
	
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		 return records.stream()
			.filter((record) -> record.getOcjena()==1)
			.map((record) -> record.getJmbag())
			.sorted()
			.collect(Collectors.toList());
	}
	
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy((record) -> record.getOcjena()));
	}
	
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		 return records.stream()
			.collect(Collectors.toMap((record) -> record.getOcjena(), 
									  (record) -> 1, 
									  (oldValue, newValue) -> oldValue+1));
	}
	
	private static Map<Boolean,  List<StudentRecord>>  razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
		.collect(Collectors.partitioningBy((record) -> record.getOcjena()>=2));
	}


	private static List<StudentRecord> convert(List<String> lines) {
		
		List<StudentRecord> records = new ArrayList<StudentRecord>();
		
		for (String line : lines) {
			String[] splitted = line.split("\t");
			if(splitted.length!=7) continue;
			
			records.add(new StudentRecord(splitted[0], 
										  splitted[1], 
										  splitted[2], 
										  Double.parseDouble(splitted[3]), 
										  Double.parseDouble(splitted[4]), 
										  Double.parseDouble(splitted[5]), 
										  Integer.parseInt(splitted[6])));
		}
		
		return records;
		
	}

}
