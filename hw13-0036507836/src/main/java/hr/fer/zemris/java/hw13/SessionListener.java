package hr.fer.zemris.java.hw13;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Listener used for setting session's default background color when the
 * new session is made
 * @author Matija
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setAttribute("pickedBgCol", "#FFFFFF"); //set white as default bg color
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//DO NOTHING
	}

}
