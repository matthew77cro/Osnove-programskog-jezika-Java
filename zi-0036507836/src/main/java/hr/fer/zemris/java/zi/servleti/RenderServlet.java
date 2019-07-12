package hr.fer.zemris.java.zi.servleti;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.FilledTriangle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.Line;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModelImpl;

/**
 * Servlet used for displaying a pie graph out of fictional information
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="image", urlPatterns= {"/pic"})
public class RenderServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("image/png");
		
		String fileName = req.getParameter("file");
		
		Path p = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike/" + fileName));
		System.out.println(p.toString());
		if(!Files.exists(p)) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		List<String> lines = Files.readAllLines(p);
			
		DrawingModel model = new DrawingModelImpl();
		
		//ucitavanje file-a
		try {
			for(String s : lines) {
				String[] splitted = s.trim().split("\\s+");
				if(splitted[0].equals("LINE")) {
					Color c = new Color(Integer.parseInt(splitted[5]), 
										Integer.parseInt(splitted[6]),
										Integer.parseInt(splitted[7]));
					model.add(new Line(Integer.parseInt(splitted[1]), 
							Integer.parseInt(splitted[3]), 
							Integer.parseInt(splitted[2]), 
							Integer.parseInt(splitted[4]), c));
				} else if(splitted[0].equals("CIRCLE")) {
					Color c = new Color(Integer.parseInt(splitted[4]), 
										Integer.parseInt(splitted[5]),
										Integer.parseInt(splitted[6]));
					model.add(new Circle(
							Integer.parseInt(splitted[1]),
							Integer.parseInt(splitted[2]),
							Integer.parseInt(splitted[3]),
							c));
				} else if(splitted[0].equals("FCIRCLE")) {
					Color c1 = new Color(Integer.parseInt(splitted[4]), 
							Integer.parseInt(splitted[5]),
							Integer.parseInt(splitted[6]));
					Color c2 = new Color(Integer.parseInt(splitted[7]), 
							Integer.parseInt(splitted[8]),
							Integer.parseInt(splitted[9]));
					model.add(new FilledCircle(
							Integer.parseInt(splitted[1]),
							Integer.parseInt(splitted[2]),
							Integer.parseInt(splitted[3]),
							c1, c2));
				} else if(splitted[0].equals("FTRIANGLE")) {
					Color c1 = new Color(Integer.parseInt(splitted[7]), 
							Integer.parseInt(splitted[8]),
							Integer.parseInt(splitted[9]));
					Color c2 = new Color(Integer.parseInt(splitted[10]), 
							Integer.parseInt(splitted[11]),
							Integer.parseInt(splitted[12]));
					model.add(new FilledTriangle(
							Integer.parseInt(splitted[1]),
							Integer.parseInt(splitted[2]),
							Integer.parseInt(splitted[3]),
							Integer.parseInt(splitted[4]),
							Integer.parseInt(splitted[5]),
							Integer.parseInt(splitted[6]),
							c1, c2));
				} else {
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}
			}
		} catch (Exception ex) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		//Calculating the bounding box
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for(int i=0, stop=model.getSize(); i<stop; i++)
			model.getObject(i).accept(bbcalc);
		Rectangle box = bbcalc.getBoundingBox();
		
		//Drawing the image
		BufferedImage image = new BufferedImage(
		 box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
		);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, box.width, box.height);
		g.translate(-box.x, -box.y);
		GeometricalObjectVisitor painter = new GeometricalObjectPainter(g);
		for(int i=0, stop=model.getSize(); i<stop; i++)
			model.getObject(i).accept(painter);
		g.dispose();
		
		ImageIO.write(image, "png", resp.getOutputStream());
		
	}

}
