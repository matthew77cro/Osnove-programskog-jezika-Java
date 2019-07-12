package hr.fer.zemris.java.zi.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for displaying a pie graph out of fictional information
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="prikaz", urlPatterns= {"/prikaz"})
public class PrikazServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getParameter("file");
		
		Path p = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike/" + fileName));
		System.out.println(p.toString());
		if(!Files.exists(p)) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		List<String> lines = Files.readAllLines(p);
		
		int line = 0, circle = 0, fcircle = 0, ftriangle = 0;
		for(String s : lines) {
			if(s.startsWith("LINE"))  line++;
			else if(s.startsWith("CIRCLE")) circle++;
			else if(s.startsWith("FCIRCLE")) fcircle++;
			else if(s.startsWith("FTRIANGLE")) ftriangle++;
		}
		
		req.setAttribute("name", fileName);
		req.setAttribute("line", Integer.toString(line));
		req.setAttribute("circle", Integer.toString(circle));
		req.setAttribute("fcircle", Integer.toString(fcircle));
		req.setAttribute("ftriangle", Integer.toString(ftriangle));
		
		req.getRequestDispatcher("/WEB-INF/pages/prikaz.jsp").forward(req, resp);
		
	}

}
