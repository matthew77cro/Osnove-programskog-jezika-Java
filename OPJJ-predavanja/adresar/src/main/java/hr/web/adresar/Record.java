package hr.web.adresar;

/**
 * Model zapisa podataka o jednoj osobi.
 * 
 * @author marcupic
 */
public class Record implements Comparable<Record> {
	
	/**
	 * Identifikator zapisa; jedinstven.
	 */
	private Long id;
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
	 * Konstruktor.
	 * 
	 * @param id jedinstveni identifikator
	 * @param prezime prezime osobe
	 * @param ime ime osobe
	 * @param email e-mail adresa osobe
	 */
	public Record(Long id, String prezime, String ime, String email) {
		super();
		this.id = id;
		this.prezime = prezime;
		this.ime = ime;
		this.email = email;
	}

	/**
	 * Konstruktor.
	 */
	public Record() {
	}
	
	/**
	 * Dohvat jedinstvenog identifikatora osobe.
	 * 
	 * @return identifikator
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Postavljanje jedinstvenog identifikatora.
	 * 
	 * @param id identifikator
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Dohvat prezimena osobe.
	 * 
	 * @return prezime
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Postavljanje prezimena osobe.
	 * 
	 * @param prezime prezime
	 */
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	/**
	 * Dohvat imena osobe.
	 * 
	 * @return ime
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Postavljanje imena osobe.
	 * 
	 * @param ime ime
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	/**
	 * Dohvat e-mail adrese osobe.
	 * 
	 * @return e-mail adresa
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Postavljanje e-mail adrese osobe.
	 * 
	 * @param email e-mail adresa
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int compareTo(Record o) {
		if(this.id==null) {
			if(o.id==null) return 0;
			return -1;
		} else if(o.id==null) {
			return 1;
		}
		return this.id.compareTo(o.id);
	}
	
	@Override
	public String toString() {
		return "{" + (id==null ? "?" : id.toString()) +
				" " + prezime + ", " + ime + " (" +
				email + ")}";
	}
}
