package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class models a context in which smartscript script needs to be executed.
 * @author Matija
 *
 */
public class RequestContext {

	//private properties
	private OutputStream outputStream;
	private Charset charset;
	
	private String encoding = "UTF-8";
	private int statusCode = 200;
	private String statusText = "OK";
	private String mimeType = "text/html";
	private Long contentLength = null; 

	private Map<String,String> parameters;
	private Map<String,String> temporaryParameters;
	private Map<String,String> persistentParameters;
	private List<RCCookie> outputCookies;
	
	private IDispatcher dispatcher;
	private String sid;

	private boolean headerGenerated = false;
	
	private static String errorMsg = "Header has already been generated!";
	
	/**
	 * Creates and initialises a new request context for smartscript script execution
	 * @param outputStream stream in which to output the result
	 * @param parameters parameters of the context
	 * @param persistentParameters parameters of the context which are bounded by the session
	 * @param outputCookies cookies
	 * @param sid session id
	 * @throws NullPointerException if outputStream is null
	 */
	public RequestContext(
			OutputStream outputStream, // must not be null!
			Map<String,String> parameters, // if null, treat as empty
			Map<String,String> persistentParameters, // if null, treat as empty
			List<RCCookie> outputCookies,
			String sid) { // if null, treat as empty 
		
		this.outputStream = Objects.requireNonNull(outputStream);
		this.parameters = Collections.unmodifiableMap(parameters==null ? new HashMap<>() : parameters);
		this.persistentParameters = persistentParameters==null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies==null ? new ArrayList<>() : outputCookies;
		this.sid = sid;
		
		this.temporaryParameters = new HashMap<>();
		
	}
	
	/**
	 * Creates and initialises a new request context for smartscript script execution
	 * @param outputStream stream in which to output the result
	 * @param parameters parameters of the context
	 * @param persistentParameters parameters of the context which are bounded by the session
	 * @param outputCookies cookies
	 * @param temporaryParameters parameters set by scripts
	 * @param sid session id
	 * @param dispatcher dispatcher
	 * @throws NullPointerException if outputStream is null
	 */
	public RequestContext(
			OutputStream outputStream, // must not be null!
			Map<String,String> parameters, // if null, treat as empty
			Map<String,String> persistentParameters, // if null, treat as empty
			List<RCCookie> outputCookies, // if null, treat as empty
			Map<String,String> temporaryParameters,
			String sid,
			IDispatcher dispatcher) {  
		
		this(outputStream, parameters, persistentParameters, outputCookies, sid);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		
	}
	
	/**
	 * Returns the dispatcher
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	/**
	 * Sets the output encoding.
	 * @param encoding encoding
	 * @throws RuntimeException if header has already been generated (write method has been called)
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated) throw new RuntimeException(errorMsg);
		this.encoding = encoding;
	}
	
	/**
	 * Sets the status code
	 * @param statusCode staus code
	 * @throws RuntimeException if header has already been generated (write method has been called)
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated) throw new RuntimeException(errorMsg);
		this.statusCode = statusCode;
	}
	
	/**
	 * Sets the status text
	 * @param statusText status text
	 * @throws RuntimeException if header has already been generated (write method has been called)
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated) throw new RuntimeException(errorMsg);
		this.statusText = statusText;
	}
	
	/**
	 * Sets the mime type
	 * @param mimeType mime type
	 * @throws RuntimeException if header has already been generated (write method has been called)
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated) throw new RuntimeException(errorMsg);
		this.mimeType = mimeType;
	}
	
	/**
	 * Sets the content length
	 * @param contentLength content length
	 * @throws RuntimeException if header has already been generated (write method has been called)
	 */
	public void setContentLength(Long contentLength) {
		if(headerGenerated) throw new RuntimeException(errorMsg);
		this.contentLength = contentLength;
	}
	
	/**
	 * Adds a cookie to the header
	 * @param rcCookie cookie to be added to the header
	 * @throws RuntimeException if header has already been generated (write method has been called)
	 */
	public void addRCCookie(RCCookie rcCookie) {
		if(headerGenerated) throw new RuntimeException(errorMsg);
		this.outputCookies.add(Objects.requireNonNull(rcCookie));
	}
	
	/**
	 * Method that retrieves value from parameters map (or null if no association exists)
	 * @param name name of the parameter to be retrieved
	 * @return value from parameters map (or null if no association exists)
	 */
	public String getParameter(String name) {
		return this.parameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in parameters map (note, this set is read-only)
	 * @return names of all parameters in parameters map
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(this.parameters.keySet());
	}
	
	/**
	 * Method that retrieves value from persistentParameters map (or null if no association exists)
	 * @param name name of the parameter to be retrieved
	 * @return value from persistentParameters map (or null if no association exists)
	 */
	public String getPersistentParameter(String name) {
		return this.persistentParameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in persistent parameters map (note, this set is read-only)
	 * @return names of all parameters in parameters map
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(this.persistentParameters.keySet());
	}
	
	/**
	 * Method that stores a value to persistentParameters map
	 * @param name name to be stored
	 * @param value value to be associated with the name
	 */
	public void setPersistentParameter(String name, String value) {
		this.persistentParameters.put(name, value);
	}
	
	/**
	 * Method that removes a value from persistentParameters map:
	 * @param name name of the parameter to be removed
	 */
	public void removePersistentParameter(String name) {
		this.persistentParameters.remove(name);
	}
	
	/**
	 * Method that retrieves value from temporaryParameters map (or null if no association exists)
	 * @param name name of the parameter whose value needs to be retrieved
	 * @return value of the parameter
	 */
	public String getTemporaryParameter(String name) {
		return this.temporaryParameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in temporary parameters map (note, this set is read-only)
	 * @return names of all parameters in temporary parameters map
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(this.temporaryParameters.keySet());
	}
	
	/**
	 * method that retrieves an identifier which is unique for current user session
	 * @return an identifier which is unique for current user session
	 */
	public String getSessionID() {
		return sid;
	}
	
	/**
	 * Method that stores a value to temporaryParameters map
	 * @param name name to be stored
	 * @param value value to be associated with the name
	 */
	public void setTemporaryParameter(String name, String value) {
		this.temporaryParameters.put(name, value);
	}
	
	/**
	 * Method that removes a value from temporaryParameters map
	 * @param name name of the parameter which needs to be removed
	 */
	public void removeTemporaryParameter(String name) {
		this.temporaryParameters.remove(name);
	}
	
	/**
	 * Writes data to the output stream given in the constructor
	 * @param data data to be written
	 * @return this
	 * @throws IOException if an I/O error occurs
	 */
	public RequestContext write(byte[] data) throws IOException {
		writeHeader();
		return write(data, 0, data.length);
	}
	
	/**
	 * Writes data to the output stream given in the constructor
	 * @param data data to be written
	 * @param offset offset from which to read given data
	 * @param len how long the data is from the offset
	 * @return this
	 * @throws IOException if an I/O error occurs
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		writeHeader();
		outputStream.write(data, offset, len);
		outputStream.flush();
		return this;
	}
	
	/**
	 * Writes text to the output stream given in the constructor. Encoding set by setEncoding(encoding)
	 * method is used.
	 * @param text text to be written
	 * @return this
	 * @throws IOException if an I/O error occurs
	 */
	public RequestContext write(String text) throws IOException {
		writeHeader();
		byte[] data = text.getBytes(charset);
		return write(data);
	}
	
	/**
	 * Writes a header to the output stream. If it has already been written, this method does nothing.
	 * @throws IOException if an I/O error occurs
	 */
	private void writeHeader() throws IOException {
		if(headerGenerated) return;
		
		headerGenerated = true;
		charset = Charset.forName(encoding);
		
		StringBuilder sb = new StringBuilder();
		
		String mime = this.mimeType;
		if(mime.startsWith("text/")) {
			mime = mime + "; charset=" + this.encoding;
		}
		
		sb.append("HTTP/1.1 " + this.statusCode + " " + this.statusText + "\r\n");
		sb.append("Content-Type: " + mime + "\r\n");
		if(contentLength!=null) sb.append("Content-Length: " + contentLength + "\r\n");
		for(var cookie : outputCookies) {
			sb.append("Set-Cookie: " + cookie.name + "=\"" + cookie.value + "\"");
			if(cookie.domain!=null) sb.append("; Domain=" + cookie.domain);
			if(cookie.path!=null) sb.append("; Path=" + cookie.path);
			if(cookie.maxAge!=null) sb.append("; Max-Age=" + cookie.maxAge);
			sb.append("\r\n");
		}
		sb.append("Connection: close\r\n");
		sb.append("\r\n");
	
		byte[] data = sb.toString().getBytes(StandardCharsets.ISO_8859_1);
	
		outputStream.write(data);
		outputStream.flush();
	}

	/**
	 * Models an http cookie with all needed information about it
	 * @author Matija
	 *
	 */
	public static class RCCookie {
		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;
		
		/**
		 * Creates and initialises the cookie
		 * @param name name of the cookie
		 * @param value value of the cookie
		 * @param maxAge maximum age of the cookie in seconds
		 * @param domain domain in which cookie is created
		 * @param path path of the cookie
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = Objects.requireNonNull(name);
			this.value = Objects.requireNonNull(value);
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Returns the name of the cookie
		 * @return the name of the cookie
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns the value of the cookie
		 * @return the value of the cookie
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns the domain of the cookie
		 * @return the domain of the cookie
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Returns the path of the cookie
		 * @return the path of the cookie
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Returns the max age of the cookie
		 * @return the max age of the cookie
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		
	}

}
