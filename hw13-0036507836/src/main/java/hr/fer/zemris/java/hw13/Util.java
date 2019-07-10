package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class for hw13
 * @author Matija
 *
 */
public class Util {

	/**
	 * Sends the user an error as a html page with the given error message and a button
	 * to go back to the home page.
	 * @param req users request
	 * @param resp users response
	 * @param error error message for the user
	 * @throws ServletException if the target resource throws this exception
	 * @throws IOException if the target resource throws this exception
	 * @throws IllegalStateException if the response was already committed
	 */
	public static void sendError(HttpServletRequest req, HttpServletResponse resp, String error) throws ServletException, IOException {
		req.setAttribute("error", error);
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		return;
	}
	
	/**
	 * Formats the interval from start to end as 
	 * ( x days, y hours, z minutes, w secounds and q milliseconds).
	 * @param start start of the interval in milliseconds
	 * @param end end of the interval in milliseconds
	 * @return formatted string as said above
	 */
	public static String intervalFormatter(long start, long end) {
		
		long interval = end - start;
		interval = interval >= 0 ? interval : start - end;
		
		if(interval == 0) {
			return "0 days 0 hours 0 minutes 0 seconds and 0 milliseconds";
		}
		
		int days = (int) (interval/1000/60/60/24);
		interval -= days*24*60*60*1000;
		
		int hours = (int) (interval/1000/60/60);
		interval -= hours*60*60*1000;
		
		int minutes = (int) (interval/1000/60);
		interval -= minutes*60*1000;
		
		int seconds = (int) (interval/1000);
		interval -= seconds*1000;
		
		return days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds and " +
				interval + " milliseconds";
	}

}
