package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAO.Poll;
import hr.fer.zemris.java.p12.dao.DAO.PollOption;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Servlet used for displaying the user all voting options
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="glasanje", urlPatterns= {"/servleti/glasanje"})
public class GlasanjeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pollIDparam = req.getParameter("pollID");
		if(pollIDparam==null) {
			Util.sendError(req, resp, "pollID expected in parameter list!");
			return;
		}
		
		int pollID = 0;
		try {
			pollID = Integer.parseInt(pollIDparam);
		} catch (NumberFormatException ex) {
			Util.sendError(req, resp, "pollID parameter must be an integer!");
			return;
		}
		
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		if(poll==null) {
			Util.sendError(req, resp, "Error : No such poll!");
			return;
		}
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		
		req.setAttribute("pollTitle", poll.getTitle());
		req.setAttribute("pollMessage", poll.getMessage());
		req.setAttribute("pollOptions", pollOptions);
		
		// Pošalji ih JSP-u...
		req.getRequestDispatcher("/WEB-INF/pages/glasanje.jsp").forward(req, resp);
		
	}

}
