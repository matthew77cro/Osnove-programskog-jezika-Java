package hr.web.adresar.servleti;

import hr.web.adresar.Adresar;
import hr.web.adresar.Record;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji obavlja akciju editiranja. Pogledajte slideove: tamo
 * odgovara akciji /edit.
 * 
 * @author marcupic
 *
 */
@WebServlet("/edit")
public class Edit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Long id = null;
		try {
			id = Long.valueOf(req.getParameter("id"));
		} catch(Exception ex) {
			req.setAttribute("poruka", "Primljeni su neispravni parametri.");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}

		Adresar adresar = Adresar.procitajIzDatoteke(
			req.getServletContext().getRealPath("/WEB-INF/adresar-baza.txt")
		);

		Record r = adresar.dohvati(id);
		
		if(r==null) {
			req.setAttribute("poruka", "Traženi zapis ne postoji.");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		
		FormularForm f = new FormularForm();
		f.popuniIzRecorda(r);
		
		req.setAttribute("zapis", f);
		
		req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
	}
}
