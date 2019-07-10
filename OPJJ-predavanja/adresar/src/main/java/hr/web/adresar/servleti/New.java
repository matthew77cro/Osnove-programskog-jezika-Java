package hr.web.adresar.servleti;

import hr.web.adresar.Record;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji obavlja akciju /new. Pogledajte slideove za opis.
 * 
 * @author marcupic
 */
@WebServlet("/new")
public class New extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Record r = new Record();
		FormularForm f = new FormularForm();
		f.popuniIzRecorda(r);
		
		req.setAttribute("zapis", f);
		
		req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
	}
}
