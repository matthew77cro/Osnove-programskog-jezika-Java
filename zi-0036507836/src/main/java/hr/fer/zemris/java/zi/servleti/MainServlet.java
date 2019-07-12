package hr.fer.zemris.java.zi.servleti;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
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
@WebServlet(name="main", urlPatterns= {"/main"})
public class MainServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Path p = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike"));
		System.out.println(p.toString());
		if(!Files.exists(p)) {
			Files.createDirectories(p);
		}
		
		List<String> slike = new ArrayList<String>();
		File[] pics = p.toFile().listFiles();
		
		for(File f : pics) {
			slike.add(f.toPath().getFileName().toString());
		}
		
		slike.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		req.setAttribute("slike", slike);
		
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getParameter("fileName").toString();
		String fileContent = req.getParameter("fileContent").toString();
		
		if(!fileName.endsWith(".jvd")) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		for(char c : fileName.toCharArray()) {
			if(!Character.isLetter(c) && !Character.isDigit(c) && c!='.') {
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
		}
		
		Path p = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike"));
		System.out.println(p.toString());
		if(!Files.exists(p)) {
			Files.createDirectories(p);
		}
		
		p = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike/" + fileName));
		System.out.println(p.toString());
		if(Files.exists(p)) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		Files.createFile(p);
		
		Files.writeString(p, fileContent);
		
		resp.sendRedirect(req.getContextPath() + "/main");
		
	}

}
