package hr.fer.zemris.jsdemo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/servlets/poruke"})
public class VratiPorukeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Quote> list = QuotesDB.getRandomSelection();
		
		// Vidi: http://www.ietf.org/rfc/rfc4627.txt
		resp.setContentType("application/json;charset=UTF-8"); 
		
		resp.getWriter().write('[');
		boolean prvi = true;
		for(Quote q : list) {
			if(prvi) {
				prvi = false;
			} else {
				// stavi zarez na kraj prethodnog objekta:
				resp.getWriter().write(',');
			}
			resp.getWriter().write('{');
			resp.getWriter().write("\"text\":\""+q.getText()+"\",");
			resp.getWriter().write("\"author\":\""+q.getAuthor()+"\"");
			resp.getWriter().write('}');
		}
		resp.getWriter().write(']');
		
		resp.getWriter().flush();
	}
	
}
