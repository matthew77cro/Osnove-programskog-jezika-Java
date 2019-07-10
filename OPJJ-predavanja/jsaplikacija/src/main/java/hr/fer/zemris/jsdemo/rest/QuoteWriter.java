package hr.fer.zemris.jsdemo.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.jsdemo.servlets.Quote;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Ovo je potrebno zbog jersey biblioteke. Ako se kao resurs vraća objekt
 * tipa {@link Quote}, netko ga treba znati konvertirati u application/json;
 * ovaj razred je markiran kao provider koji radi upravo to (po učitavanju jerseyjevog
 * servleta napravit će se sken i pa će konverter biti pronađen i korišten kada zatreba).
 * 
 * @author marcupic
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class QuoteWriter implements MessageBodyWriter<Quote> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type == Quote.class;
	}
	
	@Override
	public long getSize(Quote q, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return toData(q).length;
	}

	@Override
	public void writeTo(Quote q, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		
		
		entityStream.write(toData(q));
	}
	
	private byte[] toData(Quote q) {
		String text;
		if(q==null) {
			text = "null;";
		} else {
			JSONObject result = new JSONObject();
			result.put("text", q.getText());
			result.put("author", q.getAuthor());
			
			JSONArray topics = new JSONArray();
			for(String t : q.getTopics()) {
				topics.put(t);
			}
			result.put("topics", topics);
			
			text = result.toString();
		}
		return text.getBytes(StandardCharsets.UTF_8);
	}
}
