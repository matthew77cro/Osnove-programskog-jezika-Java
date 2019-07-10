package hr.web.adresar.servleti;

import hr.web.adresar.Adresar;
import hr.web.adresar.Record;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/listaj","/index.html"})
public class Listaj extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Adresar adresar = Adresar.procitajIzDatoteke(
			req.getServletContext().getRealPath("/WEB-INF/adresar-baza.txt")
		);
		
		List<Record> zapisi = adresar.dohvatiSveZapise();
		
		req.setAttribute("zapisi", zapisi);
		
		req.getRequestDispatcher("/WEB-INF/pages/Listaj.jsp").forward(req, resp);
	}
	
}
