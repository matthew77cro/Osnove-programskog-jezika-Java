package hr.fer.zemris.jsdemo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/servlets/porukef"})
public class VratiPorukeFServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String filter = req.getParameter("filter");
		if(filter!=null) {
			filter = filter.trim();
			if(filter.isEmpty()) {
				filter = null;
			}
		}
		
		List<Quote> list = QuotesDB.getFilteredSelection(filter);
		
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
			resp.getWriter().write("\"text\":\""+jsonEscape(q.getText())+"\",");
			resp.getWriter().write("\"author\":\""+jsonEscape(q.getAuthor())+"\"");
			resp.getWriter().write('}');
		}
		resp.getWriter().write(']');
		
		resp.getWriter().flush();
	}
	
	private String jsonEscape(String s) {
		char[] chars = s.toCharArray();
		StringBuilder sb = new StringBuilder(chars.length);
		for(char c : chars) {
			switch(c) {
			case '"': sb.append('\\').append('"'); break;
			case '\r': sb.append('\\').append('r'); break;
			case '\n': sb.append('\\').append('n'); break;
			case '\t': sb.append('\\').append('t'); break;
			default: sb.append(c);
			}
		}
		return sb.toString();
	}
}
