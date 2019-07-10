package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.models.BlogUser;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.web.forms.RegisterForm;

@WebServlet("/servleti/register")
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		renderPage(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		
		RegisterForm form = new RegisterForm(firstName, lastName, email, nick, password);
		boolean valid = form.validate();
		
		if(!valid) {
			req.setAttribute("errors", form.getErrors());
			req.setAttribute("oldFirstName", firstName);
			req.setAttribute("oldLastName", lastName);
			req.setAttribute("oldEmail", email);
			req.setAttribute("oldNick", nick);
			renderPage(req,resp);
			return;
		}
		
		BlogUser user = new BlogUser(null, firstName, lastName, nick, email, form.hashPassword());
		
		DAOProvider.getDAO().storeBlogUser(user);
		
		resp.sendRedirect(req.getContextPath() + "/success.html");
		
	}

	private void renderPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
	}
	
}