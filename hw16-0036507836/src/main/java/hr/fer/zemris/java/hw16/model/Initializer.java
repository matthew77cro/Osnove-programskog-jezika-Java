package hr.fer.zemris.java.hw16.model;

import java.nio.file.Paths;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application initialiser used for setting the ImageDB definition file and loading the data from it.
 * @author Matija
 *
 */
@WebListener
public class Initializer implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ImageDB.setDBPath(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt")));
		ImageDB.getImageDB();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//DO NOTHING
	}

}
