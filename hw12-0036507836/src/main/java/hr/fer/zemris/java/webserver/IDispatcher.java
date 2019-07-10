package hr.fer.zemris.java.webserver;

/**
 * Interface used for communication with http server and calling tasks manually
 * @author Matija
 *
 */
public interface IDispatcher {
	
	/**
	 * Request dispatch manually
	 * @param urlPath task to be executed
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
	
}