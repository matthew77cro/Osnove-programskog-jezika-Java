package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.models.BlogComment;
import hr.fer.zemris.java.hw15.models.BlogEntry;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.web.forms.NewCommentForm;

@WebServlet("/servleti/newBlogEntryComment")
public class NewBlogEntryComment extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String usersEMail = req.getParameter("email").toString();
		String message = req.getParameter("message").toString();
		
		String entryId = req.getParameter("entryId").toString();
		Long eid = null;
		try {
			eid = Long.parseLong(entryId);
		} catch (Exception ex) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		NewCommentForm ncf = new NewCommentForm(usersEMail, message);
		if(!ncf.validate()) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(eid);
		if(blogEntry==null) {
			resp.sendRedirect(req.getContextPath() + "/error.html");
			return;
		}
		
		Date d = new Date();
		
		BlogComment comment = new BlogComment();
		comment.setBlogEntry(blogEntry);
		comment.setUsersEMail(usersEMail);
		comment.setMessage(message);
		comment.setPostedOn(d);
		
		DAOProvider.getDAO().storeBlogComment(comment);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
		
	}

}