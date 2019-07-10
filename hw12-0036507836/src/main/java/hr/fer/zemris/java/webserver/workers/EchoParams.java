package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class EchoParams implements IWebWorker {
	@Override
	public void processRequest(RequestContext context)  throws Exception {
		context.setMimeType("text/html");
		
		StringBuilder sb = new StringBuilder();
		sb.append("<h1><strong>PARAMETERS</strong></h1>" + 
				  "<h3>These are the parameters I obtained:</h3>" + 
				  "<table border=\"1\">" + 
				  "<tbody>");
		
		for(String s : context.getParameterNames()) {
			String param = context.getParameter(s);
			sb.append("<tr>" + 
					"<td>" + s + "</td>" + 
					"<td>" + param + "</td>" + 
					"</tr>");
		}
		
		sb.append("</tbody></table>");
		
		try {
			context.write(sb.toString());
		} catch (IOException e) {
			// Log exception to servers log...
			e.printStackTrace();
		}
	}
}