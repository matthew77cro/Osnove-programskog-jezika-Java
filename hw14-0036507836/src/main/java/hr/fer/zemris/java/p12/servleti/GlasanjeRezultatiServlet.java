package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.DAO.PollOption;

/**
 * Servlet used for loading voting results, preparing them and calling jsp file to display them
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="glasanje-rezultati", urlPatterns= {"/servleti/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Učitaj rezultate
		String idParam = req.getParameter("id");
		int id = 0;
		try {
			id = Integer.parseInt(idParam);
		} catch (NumberFormatException ex) {
			Util.sendError(req, resp, "Error : id must be of type integer");
			return;
		}
		
		List<PollOption> results = DAOProvider.getDao().getPollOptions(id);
		if(results.isEmpty()) {
			Util.sendError(req, resp, "Error : poll empty or no such poll");
			return;
		}
		results.sort((o1, o2) -> Integer.compare(o2.getVotesCount(), o1.getVotesCount()));
		
		List<PollOption> winners = new ArrayList<PollOption>();
		int maxVotes = results.get(0).getVotesCount();
		for(var option : results) {
			if(option.getVotesCount()!=maxVotes) break;
			winners.add(option);
		}
		
		req.setAttribute("id", id);
		req.setAttribute("results", results);
		req.setAttribute("winners", winners);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		
	}
	
}
