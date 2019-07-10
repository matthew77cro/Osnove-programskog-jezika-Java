package hr.fer.zermis.java.pred6;

import java.io.File;

public class Obilazak {
	
	public static void obidi(File file, Obrada obrada) {
		
		if(!file.isDirectory()) {
			throw new IllegalArgumentException();
		}
		
		obidiRekurzivno(file, obrada);
		
	}

	private static void obidiRekurzivno(File file, Obrada obrada) {
		File[] children = file.listFiles();
		if(children==null) return;
		
		obrada.ulazimUDirektorij(file);
		
		for(File f : children) {
			
			if(f.isDirectory()) {
				obidiRekurzivno(f, obrada);
			} else if(f.isFile()) {
				obrada.imamDatoteku(f);
			}
			
			
		}
		
		obrada.izlazimIzDirektorija(file);
	}

}
