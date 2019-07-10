package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Servlet for registering users vote
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="glasanje-glasaj", urlPatterns= {"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String idParam = req.getParameter("id");
		int id = 0;
		try {
			id = Integer.parseInt(idParam);
		} catch (NumberFormatException ex) {
			Util.sendError(req, resp, "Error : id must be of type integer");
			return;
		}
		
		try {
			DAOProvider.getDao().voteFor(id);
		} catch (Exception ex) {
			Util.sendError(req, resp, "Sorry, an error occured!");
			return;
		}
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?id=" + DAOProvider.getDao().getPollIdFromOptionId(id));
		
	}

}
