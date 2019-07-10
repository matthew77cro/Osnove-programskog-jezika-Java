package hr.fer.zemris.java.p11.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="c", urlPatterns= {"/calc"})
public class RacunaloServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String a = req.getParameter("a");
		String b = req.getParameter("b");
		
		Double varA = 1.0;
		Double varB = 2.0;
		
		try {
			if(a!=null) {
				varA = Double.valueOf(a);
			}
		} catch (Exception ignorable) {
		}
		
		try {
			if(b!=null) {
				varB = Double.valueOf(b);
			}
		} catch (Exception ignorable) {
		}
		
		req.setAttribute("varA", varA);
		req.setAttribute("varB", varB);
		req.setAttribute("zbroj", varA+varB);
		
		req.getRequestDispatcher("/WEB-INF/pages/crtajSumu.jsp").forward(req, resp);
		
	}

}
