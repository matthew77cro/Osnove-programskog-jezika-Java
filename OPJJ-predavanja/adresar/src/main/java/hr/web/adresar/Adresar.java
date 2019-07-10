package hr.web.adresar;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Razred predstavlja jednostavan adresar koji podatke čita
 * i zapisuje u datoteku na disku. Primjer rada s adresarom
 * dan je u nastavku.
 * 
 * <pre>
 *   String staza = "C:/podatci.txt";
 *   Adresar adr = Adresar.procitajIzDatoteke(staza);
 *   Record novi = new Record(null, "Ivić", "Ivo", "Ivo.Ivic@acme.org");
 *   adr.zapamti(novi);
 *   
 *   Long dobiveniId = novi.getId();
 *   System.out.println("Dobiven je identifikator: " + dobiveniId);
 *   
 *   System.out.println("e-mail: " + adr.dohvati(dobiveniId).getEmail());
 *   
 *   adr.spremiUDatoteku(staza);
 * </pre>
 * 
 * @author marcupic
 */
public class Adresar {
	
	private Map<Long, Record> mapa = new HashMap<>();
	private Long maxID;
	
	/**
	 * Konstruktor.
	 */
	private Adresar() {
	}

	/**
	 * Metoda za dohvat zapisa preko poznatog identifikatora.
	 * 
	 * @param id identifikator
	 * @return zapis ako postoji, <code>null</code> inače
	 */
	public Record dohvati(Long id) {
		return mapa.get(id);
	}

	/**
	 * Metoda vraća listu svih trenutno korištenih identifikatora.
	 * Identifikatori nisu unaprijed poredani po nekom zadanom 
	 * kriteriju.
	 * 
	 * @return lista identifikatora
	 */
	public List<Long> dohvatiSveIdentifikatore() {
		return new ArrayList<>(mapa.keySet());
	}

	/**
	 * Metoda vraća listu svih zapisa koji su trenutno u adresaru.
	 * 
	 * @return lista zapisa
	 */
	public List<Record> dohvatiSveZapise() {
		return new ArrayList<>(mapa.values());
	}
	
	/**
	 * Metoda briše zapis sa predanim identifikatorom.
	 * 
	 * @param id identifikator zapisa
	 */
	public void obrisi(Long id) {
		mapa.remove(id);
	}
	
	/**
	 * Metoda predani zapis dodaje u adresar. Ako zapis nema
	 * jedinstveni identifikator, automatski će ga dobiti.
	 * 
	 * @param r zapis
	 */
	public void zapamti(Record r) {
		if(r.getId()==null) {
			Long noviId = maxID==null ? 1 : maxID+1;
			r.setId(noviId);
		}
		
		mapa.put(r.getId(), r);
		if(maxID == null || r.getId().compareTo(maxID)>0) {
			maxID = r.getId();
		}
	}
	
	/**
	 * Metoda s diska čita podatke i vraća adresar koji je inicijaliziran
	 * tim podatcima. Ako datoteka ne postoji, metoda vraća prazan adresar.
	 * Ako datoteka postoji ali je nije moguće pročitati, generira se 
	 * iznimka.
	 * 
	 * @param fileName staza do datoteke na disku
	 * @return adresar
	 * @throws IOException u slučaju pogreške prilikom čitanja datoteke
	 */
	public static Adresar procitajIzDatoteke(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		if(!Files.exists(path)) {
			return new Adresar();
		}
		
		Adresar adresar = new Adresar();
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		for(String line : lines) {
			line = line.trim();
			if(line.isEmpty()) continue;
			String[] elementi = line.split("\t");
			Record r = new Record(
				Long.valueOf(elementi[0]), elementi[1], elementi[2], elementi[3]
			);
			adresar.zapamti(r);
		}
		return adresar;
	}
	
	/**
	 * Metoda u predanu datoteku zapisuje sve podatke iz adresara.
	 * 
	 * @param fileName staza do datoteke
	 * @throws IOException u slučaju pogreške
	 */
	public void spremiUDatoteku(String fileName) throws IOException {
		List<Record> lista = new ArrayList<>(mapa.values());
		Collections.sort(lista);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
		for(Record r : lista) {
			bw.write(r.getId().toString());
			bw.write('\t');
			bw.write(r.getPrezime());
			bw.write('\t');
			bw.write(r.getIme());
			bw.write('\t');
			bw.write(r.getEmail());
			bw.write("\r\n");
		}
		bw.flush();
		bw.close();
	}
	
}
