package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class HomeWorker implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bg = context.getPersistentParameter("bgcolor");
		bg = bg==null ? "7F7F7F" : bg;
		context.setTemporaryParameter("background", bg);
		
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}
}