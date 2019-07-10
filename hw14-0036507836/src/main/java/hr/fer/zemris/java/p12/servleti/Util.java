package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class for hw14
 * @author Matija
 *
 */
public class Util {

	/**
	 * Sends the user an error as a html page with the given error message and a button
	 * to go back to the home page.
	 * @param req users request
	 * @param resp users response
	 * @param error error message for the user
	 * @throws ServletException if the target resource throws this exception
	 * @throws IOException if the target resource throws this exception
	 * @throws IllegalStateException if the response was already committed
	 */
	public static void sendError(HttpServletRequest req, HttpServletResponse resp, String error) throws ServletException, IOException {
		req.setAttribute("error", error);
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		return;
	}

}
