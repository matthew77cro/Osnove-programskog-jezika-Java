package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for displaying the user all voting options
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="glasanje", urlPatterns= {"/glasanje"})
public class GlasanjeServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Učitaj raspoložive bendove
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));

		Map<Integer, String> voteObjects = new LinkedHashMap<>();
		for(String s : lines) {
			String[] splitted = s.split("\t");
			Integer id = Integer.parseInt(splitted[0]);
			voteObjects.put(id, splitted[1]);
		}
		
		req.setAttribute("voteObjects", voteObjects);
		
		// Pošalji ih JSP-u...
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		
	}
}
