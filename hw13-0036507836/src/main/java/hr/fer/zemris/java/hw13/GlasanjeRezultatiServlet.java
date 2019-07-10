package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.Voting.VoteObject;

/**
 * Servlet used for loading voting results, preparing them and calling jsp file to display them
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="glasanje-rezultati", urlPatterns= {"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Učitaj rezultate
		String resFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String defFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path resPath = Paths.get(resFile);
		Path defPath = Paths.get(defFile);
		
		Voting voting = Voting.getInstance();
		try {
			voting.load(resPath, defPath);
		} catch(Exception ex) {
			Util.sendError(req, resp, ex.getMessage());
		}
		List<VoteObject> results = voting.getResults();
		
		List<VoteObject> winners = new ArrayList<>();
		if(!results.isEmpty()) {
			int maxVotes = results.get(0).getVoteCount();
			for(VoteObject v : results) {
				if(v.getVoteCount() == maxVotes) {
					winners.add(v);
				} else {
					break;
				}
			}
		}
		
		req.setAttribute("results", results);
		req.setAttribute("winners", winners);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		
	}
	
}
