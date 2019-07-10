package hr.fer.zemris.jsdemo.rest;

import hr.fer.zemris.jsdemo.servlets.Quote;
import hr.fer.zemris.jsdemo.servlets.QuotesDB;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/quotex")
public class QuoteXML {

	// Sljedeća metoda se poziva samo ako zatraženo metodom GET URL koji je konkatenacija
	// staze na koju je bio mapiran jerseyev servlet i staze koju smo anotirali iznad
	// ovog razreda; u našem slučaju to bi bilo:
	// http://localhost:8080/jsaplikacija/rest/quotex
	@GET
	@Produces("application/xml")
	public String getQuotesList() {
		int n = QuotesDB.getNumberOfQoutes();
		
		return "<quotes-list><number-of-qoutes>"+n+"</number-of-qoutes></quotes-list>";
	}

	// Sljedeća metoda se poziva samo ako zatraženo metodom GET URL koji je konkatenacija
	// staze na koju je bio mapiran jerseyev servlet, staze koju smo anotirali iznad
	// ovog razreda te staze koju smo napisali iznad ove metode; u vitičastim zagradama
	// definiramo da taj dio staze nazivamo parametrom kako piše u zagradama;
	// u našem slučaju, sljedeća bi adresa aktivirala ovu metodu:
	// http://localhost:8080/jsaplikacija/rest/quotex/5
	// Uočite kako u argumentima metode definiramo koji argument treba dobiti koju vrijednost
	// koja je izvađena iz staze; tipovi se također automatski konvertiraju
	@Path("{index}")
	@GET
	@Produces("application/xml")
	public Response getQuote(@PathParam("index") int index) {
		int n = QuotesDB.getNumberOfQoutes();
		if(index < 0 || index >= n) {
			return Response.status(404).build();
		}
		
		Quote q = QuotesDB.getQuote(index);
		if(q==null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		String xml = "<quote><text>"+q.getText()+"</text><author>"+q.getAuthor()+"</author></quote>";
		return Response.status(Status.OK).entity(xml).build();
	}
	
}
