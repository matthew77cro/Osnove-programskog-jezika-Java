package hr.web.adresar;

import java.io.IOException;

/**
 * Demonstracijski komandno-linijski program koji ilustrira način
 * uporabe razreda {@link Adresar} koji emulira funkcionalnost jednostavne
 * baze podataka.
 * 
 * @author marcupic
 */
public class Demo {

	/**
	 * Metoda s kojom kreće izvođenje programa.
	 * @param args argumenti naredbenog retka - ne koriste se
	 * @throws IOException u slučaju greške pri čitanju/pisanju adresara
	 */
	public static void main(String[] args) throws IOException {

		// Hardkodirana staza: prije no što pokrenete kod sebe, prilagodite.
		String staza = "C:/podatci.txt";
		
		Adresar adr = Adresar.procitajIzDatoteke(staza);
		Record novi = new Record(null, "Ivić", "Ivo", "Ivo.Ivic@acme.org");
		adr.zapamti(novi);

		Long dobiveniId = novi.getId();
		System.out.println("Dobiven je identifikator: " + dobiveniId);

		Record r = adr.dohvati(dobiveniId);
		System.out.println("e-mail: " + r.getEmail());

		adr.zapamti(new Record(null, "Anić", "Ante", "Ante.Anic@acme.org"));
		adr.zapamti(new Record(null, "Jasić", "Jasmina", "Jasmina.Jasic@acme.org"));
		adr.zapamti(new Record(null, "Krivić", "Karla", "Karla.Krivic@acme.org"));
		
		adr.spremiUDatoteku(staza);
	}
}
