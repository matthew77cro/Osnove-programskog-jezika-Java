package hr.fer.zemris.java.hw16.servlet;

import java.awt.Image;
import java.awt.image.BufferedImage;
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
 * Servlet used for getting the thumbnail version of the picture with the name given in the url parameter 'picture'
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="thumbnailViewer", urlPatterns= {"/servleti/thumbnailViewer"})
public class ThumbnailViewerServlet extends HttpServlet{

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
		
		Path thumb = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails/") + param);
		Path picture = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike/") + param);
		
		if(!Files.exists(thumb) && Files.exists(picture)) {
			BufferedImage img = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
			Image thumbImg = ImageIO.read(picture.toFile()).getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			img.createGraphics().drawImage(thumbImg, 0, 0, null);
			Files.createFile(thumb);
			ImageIO.write(img, "jpg", thumb.toFile());
		}
		
		if(Files.exists(thumb)) {
			resp.setContentType("image/jpg");
			ImageIO.write(ImageIO.read(thumb.toFile()), "jpg", resp.getOutputStream());
		} else {
			Util.sendError(req, resp);
			return;
		}		
		
	}

}
