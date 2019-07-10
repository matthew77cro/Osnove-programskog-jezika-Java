package hr.fer.zemris.java.hw16.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a utility class with static utility methods for this web application.
 * @author Matija
 *
 */
public class Util {

	/**
	 * Renders an error page to the user
	 * @param req request
	 * @param resp response
	 * @throws ServletException if any servlet exception occurs
	 * @throws IOException if any servlet exception occurs
	 */
	public static void sendError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		req.getRequestDispatcher("/WEB-INF/error.html").forward(req, resp);
	}

}
