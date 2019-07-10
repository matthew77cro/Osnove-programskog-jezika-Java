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

@WebServlet("/servleti/editBlogEntry")
public class EditBlogEntry extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String currentUsr = req.getSession().getAttribute("current.user.nick").toString();
		if(currentUsr==null || currentUsr.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		BlogUser user = DAOProvider.getDAO().getUser(currentUsr);
		if(user==null) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		String id = req.getParameter("id").toString();
		String title = req.getParameter("title").toString();
		String text = req.getParameter("text").toString();
		
		Long eid = null;
		try {
			eid = Long.parseLong(id);
		} catch (Exception ex) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eid);
		if(!entry.getCreator().equals(user)) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		EntryForm entryForm = new EntryForm(title, text);
		
		if(!entryForm.validate()) {
			req.setAttribute("oldTitle", title);
			req.setAttribute("oldText", text);
			req.setAttribute("errors", entryForm.getErrors());
			req.getRequestDispatcher("/WEB-INF/pages/EditBlogEntry.jsp").forward(req, resp);
			return;
		}
		
		Date d = new Date();
		entry.setTitle(title);
		entry.setText(text);
		entry.setLastModifiedAt(d);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
		
	}
	
}