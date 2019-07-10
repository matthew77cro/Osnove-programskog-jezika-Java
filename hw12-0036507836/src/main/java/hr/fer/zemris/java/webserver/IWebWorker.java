package hr.fer.zemris.java.webserver;

/**
 * Interface used for communication with web workers (function which generate web output on the go).
 * @author Matija
 *
 */
public interface IWebWorker {
	
	/**
	 * Processes request (generates required output in the given context).
	 * @param context context in which to generate the output
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;
	
}
