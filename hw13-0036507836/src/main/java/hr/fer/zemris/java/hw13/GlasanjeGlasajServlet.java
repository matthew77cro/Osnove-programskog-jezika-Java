package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for registering users vote
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="glasanje-glasaj", urlPatterns= {"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException ex) {
			Util.sendError(req, resp, "Error : id must be of type integer");
			return;
		}
		
		//checking if id is in definition file (if id is valid)
		String definitionFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> definitionLines = Files.readAllLines(Paths.get(definitionFile));
		boolean valid = false;
		for(String s : definitionLines) {
			String[] splitted = s.split("\t");
			if(id.equals(splitted[0])) {
				valid = true;
				break;
			}
		}
		if(!valid) {
			Util.sendError(req, resp, "Error : unknown id");
			return;
		}
		
		// Učitaj raspoložive bendove
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path p = Paths.get(fileName);
		
		if(!Files.exists(p)) {
			Files.createFile(p);
		}
		
		List<String> lines = Files.readAllLines(p);
		
		//finding the line with required id
		int index = -1;
		int oldCount = 0;
		for(int i=0, stop=lines.size(); i<stop; i++) {
			String[] splitted = lines.get(i).split("\t");
			if(splitted[0].equals(id)) {
				index = i;
				oldCount = Integer.parseInt(splitted[1]);
				break;
			}
		}
		
		if(index==-1) {
			lines.add(id + "\t" + "1");
		} else {
			lines.remove(index);
			lines.add(index, id + "\t" + (oldCount+1));
		}
		
		Files.write(p, lines, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
		
	}

}
