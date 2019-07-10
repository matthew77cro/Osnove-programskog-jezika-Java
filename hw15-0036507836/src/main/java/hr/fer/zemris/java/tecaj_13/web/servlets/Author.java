package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.models.BlogEntry;
import hr.fer.zemris.java.hw15.models.BlogUser;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

@WebServlet("/servleti/author/*")
public class Author extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo().substring(1);
		String[] splitted = pathInfo.split("/");
		
		if(splitted.length==1 && splitted[0].isEmpty()) {
			String nick = req.getSession().getAttribute("current.user.nick").toString();
			if(nick==null) {
				req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
				return;
			}
			showListOfEntries(req, resp, nick);
		} else if (splitted.length==1) {
			String nick = splitted[0];
			showListOfEntries(req, resp, nick);
		} else if (splitted.length==2){
			String nick = splitted[0];
			String action = splitted[1];
			if(!action.equalsIgnoreCase("NEW") && !action.equalsIgnoreCase("EDIT")) {
				showEntry(req, resp, nick, action);
				return;
			}
			if(action.equalsIgnoreCase("NEW")) {
				showNew(req, resp, nick);
			} else {
				showEdit(req, resp, nick);
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/error.html");
		}
		
	}
	
	private void showListOfEntries(HttpServletRequest req, HttpServletResponse resp, String nick) throws ServletException, IOException {
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		if(user==null) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
		}
		List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(user);
		req.setAttribute("entries", entries);
		req.setAttribute("authorNick", nick);
		req.getRequestDispatcher("/WEB-INF/pages/Author.jsp").forward(req, resp);
	}
	
	private void showEntry(HttpServletRequest req, HttpServletResponse resp, String nick, String eid) throws ServletException, IOException {
		Long l;
		try {
			l = Long.parseLong(eid);
		} catch (NumberFormatException ex) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(l);
		
		if(entry==null) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		if(!entry.getCreator().equals(user)) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		String currentUsrEm = (String) req.getSession().getAttribute("current.user.email");
		String oldEmail = currentUsrEm==null ? "" : currentUsrEm;
		req.setAttribute("entry", entry);
		req.setAttribute("oldEmail", oldEmail);
		req.setAttribute("oldMessage", "Enter text here...");
		
		req.getRequestDispatcher("/WEB-INF/pages/BlogEntry.jsp").forward(req, resp);
	}
	
	private void showNew(HttpServletRequest req, HttpServletResponse resp, String nick) throws ServletException, IOException {
		String currentUsr = (String) req.getSession().getAttribute("current.user.nick");
		if(currentUsr==null || !currentUsr.equals(nick)) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		req.setAttribute("oldText", "Enter text here...");
		
		req.getRequestDispatcher("/WEB-INF/pages/NewBlogEntry.jsp").forward(req, resp);
	}
	
	private void showEdit(HttpServletRequest req, HttpServletResponse resp, String nick) throws ServletException, IOException {
		String currentUsr = (String) req.getSession().getAttribute("current.user.nick");
		if(currentUsr==null || !currentUsr.equals(nick)) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		String id = req.getParameter("eid");
		Long eid = null;
		try {
			eid = Long.parseLong(id);
		} catch (Exception ex) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eid);
		if(entry==null || !entry.getCreator().getNick().equals(req.getSession().getAttribute("current.user.nick").toString())) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		req.setAttribute("entry", entry);
		req.setAttribute("oldTitle", entry.getTitle());
		req.setAttribute("oldText", entry.getText());
		
		req.getRequestDispatcher("/WEB-INF/pages/EditBlogEntry.jsp").forward(req, resp);
	}
	
}