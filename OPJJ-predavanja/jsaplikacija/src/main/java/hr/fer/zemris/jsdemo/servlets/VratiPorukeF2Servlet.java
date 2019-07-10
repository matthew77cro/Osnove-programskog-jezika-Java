package hr.fer.zemris.jsdemo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet(urlPatterns={"/servlets/porukef2"})
public class VratiPorukeF2Servlet extends HttpServlet {

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
		Quote[] array = new Quote[list.size()];
		list.toArray(array);
		
		resp.setContentType("application/json;charset=UTF-8"); 
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(array);
		
		resp.getWriter().write(jsonText);
		
		resp.getWriter().flush();
	}
	
}
