package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.models.BlogUser;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.web.forms.LoginForm;

@WebServlet("/servleti/main")
public class Main extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		renderPage(req,resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		
		LoginForm loginForm = new LoginForm(nick, password);
		
		if(!loginForm.validate()) {
			if(nick!=null) {
				req.setAttribute("oldNick", nick);
			}
			req.setAttribute("errors", loginForm.getErrors());
			renderPage(req,resp);
			return;
		}

		String hashedPass = loginForm.hashPassword();
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		
		if(user==null || !user.getPasswordHash().equals(hashedPass)) {
			req.setAttribute("oldNick", nick);
			loginForm.getErrors().put("login", "Username and/or password incorrect!");
			req.setAttribute("errors", loginForm.getErrors());
			renderPage(req,resp);
			return;
		}
		
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		req.getSession().setAttribute("current.user.email", user.getEmail());
		
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
		
	}

	private void renderPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getUsers();
		req.setAttribute("authors", users);
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
	}
	
	
	
}