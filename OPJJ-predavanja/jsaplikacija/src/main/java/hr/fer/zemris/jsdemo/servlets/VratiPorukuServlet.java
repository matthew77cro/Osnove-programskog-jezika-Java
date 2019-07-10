package hr.fer.zemris.jsdemo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/servlets/poruka"})
public class VratiPorukuServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Quote q = QuotesDB.getRandomQuote();
		
		resp.setContentType("text/plain;charset=UTF-8"); 
		
		resp.getWriter().write(q.getText());
		resp.getWriter().write('#');
		resp.getWriter().write(q.getAuthor());
		
		resp.getWriter().flush();
	}
	
}
