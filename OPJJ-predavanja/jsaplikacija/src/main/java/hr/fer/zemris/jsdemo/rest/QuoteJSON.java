package hr.fer.zemris.jsdemo.rest;

import hr.fer.zemris.jsdemo.servlets.Quote;
import hr.fer.zemris.jsdemo.servlets.QuotesDB;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Razred koristi biblioteku org.json za rad s JSON formatom
 * (da vidite kako se s time radi; mogli smo koristiti i gson).
 * 
 * @author marcupic
 */
@Path("/quotej")
public class QuoteJSON {

	// Sljedeća metoda se poziva samo ako zatraženo metodom GET URL koji je konkatenacija
	// staze na koju je bio mapiran jerseyev servlet i staze koju smo anotirali iznad
	// ovog razreda; u našem slučaju to bi bilo:
	// http://localhost:8080/jsaplikacija/rest/quotej
	@GET
	@Produces("application/json")
	public Response getQuotesList() {
		int n = QuotesDB.getNumberOfQoutes();
		
		JSONObject result = new JSONObject();
		result.put("numberOfQuotes", n);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}

	// Sljedeća metoda se poziva samo ako zatraženo metodom GET URL koji je konkatenacija
	// staze na koju je bio mapiran jerseyev servlet, staze koju smo anotirali iznad
	// ovog razreda te staze koju smo napisali iznad ove metode; u vitičastim zagradama
	// definiramo da taj dio staze nazivamo parametrom kako piše u zagradama;
	// u našem slučaju, sljedeća bi adresa aktivirala ovu metodu:
	// http://localhost:8080/jsaplikacija/rest/quotej/5
	// Uočite kako u argumentima metode definiramo koji argument treba dobiti koju vrijednost
	// koja je izvađena iz staze; tipovi se također automatski konvertiraju
	@Path("{index}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuote(@PathParam("index") int index) {
		int n = QuotesDB.getNumberOfQoutes();
		if(index < 0 || index >= n) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		Quote q = QuotesDB.getQuote(index);
		if(q==null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		JSONObject result = new JSONObject();
		result.put("text", q.getText());
		result.put("author", q.getAuthor());
		
		JSONArray topics = new JSONArray();
		for(String t : q.getTopics()) {
			topics.put(t);
		}
		result.put("topics", topics);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}

	@Path("/j/{index}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Quote getQuote2(@PathParam("index") int index) {
		int n = QuotesDB.getNumberOfQoutes();
		if(index < 0 || index >= n) {
			return null;
		}
		
		Quote q = QuotesDB.getQuote(index);
		if(q==null) {
			return null;
		}

		return q;
	}
	
}
