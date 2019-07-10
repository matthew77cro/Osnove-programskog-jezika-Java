package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for setting background color for user's current session
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="color", urlPatterns= {"/setcolor"})
public class ColorServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		String color = req.getParameter("color");
		
		if(color==null) {
			Util.sendError(req, resp, "Color not provided!");
			return;
		}
		
		if(color.equalsIgnoreCase("white")) {
			color = "#ffffff";
		} else if (color.equalsIgnoreCase("red")) {
			color = "#ff0000";
		} else if (color.equalsIgnoreCase("green")) {
			color = "#00ff00";
		} else if (color.equalsIgnoreCase("cyan")) {
			color = "#00ffff";
		} else {
			Util.sendError(req, resp, "Unsupported color : " + color);
			return;
		}
		
		req.getSession().setAttribute("pickedBgCol", color);
		resp.sendRedirect(resp.encodeRedirectURL(
				 req.getContextPath()+"/index.jsp"
				));
		
	}

}
