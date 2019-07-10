package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAO.Poll;
import hr.fer.zemris.java.p12.dao.DAOProvider;

@SuppressWarnings("serial")
@WebServlet(name="index", urlPatterns= {"/servleti/index.html"})
public class IndexServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Poll> polls = DAOProvider.getDao().getPolls();
		
		req.setAttribute("polls", polls);
		
		// Pošalji ih JSP-u...
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
		
	}

}
