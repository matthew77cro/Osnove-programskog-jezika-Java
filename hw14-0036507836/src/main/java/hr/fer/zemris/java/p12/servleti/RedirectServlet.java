package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for redirection to the main home page
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="redirect", urlPatterns= {"/index.html"})
public class RedirectServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.sendRedirect(resp.encodeRedirectURL(
				 req.getContextPath()+"/servleti/index.html"
				));
		
	}

}
