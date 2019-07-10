package hr.web.adresar.servleti;

import hr.web.adresar.Record;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Model formulara koji odgovara web-reprezentaciji domenskog objekta {@link Record}.
 * U domenskom modelu, različita svojstva su različitih tipova; primjerice, {@link Record#getId()}
 * je tipa {@link Long}. U formularu, sva su svojstva stringovi jer se tako prenose preko HTTP-a
 * i čuvaju u web-stranici u formularima.</p>
 * 
 * <p>Za svako svojstvo, mapa {@link #greske} omogućava da se pri validaciji (metoda {@link #validiraj()}) upiše 
 * je li došlo do pogreške u podatcima. Formular nudi sljedeće funkcionalnosti.</p>
 * 
 * <ol>
 * <li>Punjenje iz trenutnog zahtjeva metodom {@link #popuniIzHttpRequesta(HttpServletRequest)}. Čita parametre
 *     i upisuje odgovarajuća svojstva u formular.</li>
 * <li>Punjenje iz domenskog objekta metodom {@link #popuniIzRecorda(Record)}. Prima {@link Record} kao argument
 *     i temeljem toga što je u njemu upisano popunjava ovaj formular.</li>
 * <li>Punjenje domenskog objekta temeljem upisanog sadržaja u formularu metodom {@link #popuniURecord(Record)}.
 *     Ideja je da se ovo radi tek ako su podatci u formularu prošli validaciju. Pogledajte pojedine servlete koji
 *     su pripremljeni uz ovaj primjer za demonstraciju kako se to radi.</li>
 * </ol>
 * 
 * @author marcupic
 */
public class FormularForm {

	/**
	 * String verzija identifikatora.
	 */
	private String id;
	/**
	 * Prezime osobe.
	 */
	private String prezime;
	/**
	 * Ime osobe.
	 */
	private String ime;
	/**
	 * E-mail osobe.
	 */
	private String email;

	/**
	 * Mapa s pogreškama. Očekuje se da su ključevi nazivi svojstava a vrijednosti
	 * tekstovi pogrešaka.
	 */
	Map<String, String> greske = new HashMap<>();
	
	/**
	 * Konstruktor.
	 */
	public FormularForm() {
	}

	/**
	 * Dohvaća poruku pogreške za traženo svojstvo.
	 * 
	 * @param ime naziv svojstva za koje se traži poruka pogreške
	 * @return poruku pogreške ili <code>null</code> ako svojstvo nema pridruženu pogrešku
	 */
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}
	
	/**
	 * Provjera ima li barem jedno od svojstava pridruženu pogrešku.
	 * 
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}
	
	/**
	 * Provjerava ima li traženo svojstvo pridruženu pogrešku. 
	 * 
	 * @param ime naziv svojstva za koje se ispituje postojanje pogreške
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}
	
	/**
	 * Na temelju parametara primljenih kroz {@link HttpServletRequest} popunjava
	 * svojstva ovog formulara.
	 * 
	 * @param req objekt s parametrima
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("id"));
		this.ime = pripremi(req.getParameter("ime"));
		this.prezime = pripremi(req.getParameter("prezime"));
		this.email = pripremi(req.getParameter("email"));
	}

	/**
	 * Na temelju predanog {@link Record}-a popunjava ovaj formular.
	 * 
	 * @param r objekt koji čuva originalne podatke
	 */
	public void popuniIzRecorda(Record r) {
		if(r.getId()==null) {
			this.id = "";
		} else {
			this.id = r.getId().toString();
		}
		
		this.ime = r.getIme();
		this.prezime = r.getPrezime();
		this.email = r.getEmail();
	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog
	 * objekta. Metodu ne bi trebalo pozivati ako formular prethodno nije
	 * validiran i ako nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r domenski objekt koji treba napuniti
	 */
	public void popuniURecord(Record r) {
		if(this.id.isEmpty()) {
			r.setId(null);
		} else {
			r.setId(Long.valueOf(this.id));
		}
		
		r.setIme(this.ime);
		r.setPrezime(this.prezime);
		r.setEmail(this.email);
	}
	
	/**
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način potrebno
	 * napuniti. Metoda provjerava semantičku korektnost svih podataka te po potrebi
	 * registrira pogreške u mapu pogrešaka.
	 */
	public void validiraj() {
		greske.clear();
		
		if(!this.id.isEmpty()) {
			try {
				Long.parseLong(this.id);
			} catch(NumberFormatException ex) {
				greske.put("id", "Vrijednost identifikatora nije valjana.");
			}
		}
		
		if(this.ime.isEmpty()) {
			greske.put("ime", "Ime je obavezno!");
		} else if (this.ime.length()>50) {
			greske.put("ime", "Ime ne smije biti dulje od 50 znakova");
		}
		
		if(this.prezime.isEmpty()) {
			greske.put("prezime", "Prezime je obavezno!");
		} else if (this.prezime.length()>50) {
			greske.put("prezime", "Prezime ne smije biti dulje od 50 znakova");
		}

		if(this.email.isEmpty()) {
			greske.put("email", "EMail je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				greske.put("email", "EMail nije ispravnog formata.");
			}
		}
	}
	
	/**
	 * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne stringove, što je
	 * puno pogodnije za uporabu na webu.
	 * 
	 * @param s string
	 * @return primljeni string ako je različit od <code>null</code>, prazan string inače.
	 */
	private String pripremi(String s) {
		if(s==null) return "";
		return s.trim();
	}

	/**
	 * Dohvat id-a.
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter za id. 
	 * @param id vrijednost na koju ga treba postaviti.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Dohvat prezimena.
	 * @return prezime
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Setter za prezime. 
	 * @param prezime vrijednost na koju ga treba postaviti.
	 */
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	/**
	 * Dohvat imena.
	 * @return ime
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Setter za ime. 
	 * @param ime vrijednost na koju ga treba postaviti.
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	/**
	 * Dohvat emaila.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter za email. 
	 * @param email vrijednost na koju ga treba postaviti.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
