package hr.fer.zemris.web.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="d1", urlPatterns={"/d1"})
public class DemoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<User> userList = new ArrayList<>();
		userList.add(new User("Žunić", "Žunko"));
		userList.add(new User("Šabić", "Šanko"));
		userList.add(new User("Ćinić", "Ćinko"));

		req.setAttribute("userList", userList);
		
		Map<String, User> map = new HashMap<String, User>();
		map.put("Odlikaš", userList.get(0));
		map.put("Tak-tak", userList.get(1));
		map.put("Ajme-meni", userList.get(2));
		req.setAttribute("primjerci", map);
		
		req.getRequestDispatcher("/WEB-INF/pages/demo.jsp").forward(req, resp);
	}

}