package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String paramA = context.getParameter("a");
		String paramB = context.getParameter("b");
		paramA = paramA==null? "1" : paramA;
		paramB = paramB==null? "2" : paramB;
		
		int a, b;
		try {
			a = Integer.parseInt(paramA);
		} catch (Exception ex) {
			a = 1;
		}
		try {
			b = Integer.parseInt(paramB);
		} catch (Exception ex) {
			b = 2;
		}
		
		context.setTemporaryParameter("zbroj", Integer.valueOf(a+b).toString());
		context.setTemporaryParameter("varA", Integer.valueOf(a).toString());
		context.setTemporaryParameter("varB", Integer.valueOf(b).toString());
		
		context.setTemporaryParameter("imgName", (a+b)%2==0 ? "/images/img2.gif" : "/images/img1.jpg");
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}
}