package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class BgColorWorker implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) throws Exception {
		StringBuilder output = new StringBuilder();
		output.append("<html> <head> </head> <body> ");
		
		String hexColor = context.getParameter("bgcolor");
		boolean valid = hexColor.length()==6;
		
		if(valid) {
			for(char c : hexColor.toCharArray()) {
				c = Character.toUpperCase(c);
				if(!(c=='0' || c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' 
						|| c=='9' || c=='A' || c=='B' || c=='C' || c=='D' || c=='E' || c=='F')) {
					valid = false;
					break;
				}
			}
		}
		
		if(valid) {
			context.setPersistentParameter("bgcolor", hexColor);
			output.append("Color has been updated!");
		} else {
			output.append("ERROR: Color has NOT been updated!");
		}
		
		output.append(" <br> <a href=\"http://www.localhost.com:5721/index2.html\"> BACK </a> to the home page. </body> </html>");
	
		context.write(output.toString());
	}
}