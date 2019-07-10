package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.models.BlogEntry;
import hr.fer.zemris.java.hw15.models.BlogUser;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.web.forms.EntryForm;

@WebServlet("/servleti/newBlogEntry")
public class NewBlogEntry extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String currentUsr = (String) req.getSession().getAttribute("current.user.nick");
		if(currentUsr==null || currentUsr.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		BlogUser user = DAOProvider.getDAO().getUser(currentUsr);
		if(user==null) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		String title = req.getParameter("title").toString();
		String text = req.getParameter("text").toString();
		
		EntryForm entryForm = new EntryForm(title, text);
		
		if(!entryForm.validate()) {
			req.setAttribute("oldTitle", title);
			req.setAttribute("oldText", text);
			req.setAttribute("errors", entryForm.getErrors());
			req.getRequestDispatcher("/WEB-INF/pages/NewBlogEntry.jsp").forward(req, resp);
			return;
		}
		
		Date d = new Date();
		BlogEntry be = new BlogEntry();
		be.setTitle(title);
		be.setText(text);
		be.setCreator(user);
		be.setCreatedAt(d);
		be.setLastModifiedAt(d);
		
		DAOProvider.getDAO().storeBlogEntry(be);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
		
	}

}