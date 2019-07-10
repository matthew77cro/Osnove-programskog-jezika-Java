package hr.fer.zemris.java.hw13;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 * Listener used for storing current time when the server is started
 * @author Matija
 *
 */
@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startup-time", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//DO NOTHING
	}

}
