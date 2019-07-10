package hr.fer.zemris.java.hw16.servlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for getting the picture with the name given in the url parameter 'picture'
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="pictureViewer", urlPatterns= {"/servleti/pictureViewer"})
public class PictureViewerServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Object paramObj = req.getParameter("picture");
		if(paramObj==null) {
			Util.sendError(req, resp);
			return;
		}
		String param = paramObj.toString();
		
		if(param.contains("..")) {
			Util.sendError(req, resp);
			return;
		}
		
		Path picture = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike/") + param);
		
		if(!Files.exists(picture)) {
			Util.sendError(req, resp);
			return;
		}
		
		resp.setContentType("image/jpg");
		ImageIO.write(ImageIO.read(picture.toFile()), "jpg", resp.getOutputStream());
		
	}

}
