package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet used for calculating first n power of integer numbers between a and b
 * then, creating an xsl file out of calculated information
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="powers", urlPatterns= {"/powers"})
public class PowersServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String varA = req.getParameter("a");
		String varB = req.getParameter("b");
		String varN = req.getParameter("n");
		
		if(varA==null || varB==null || varN==null) {
			Util.sendError(req, resp, "All parameters must be set! (a, b, n)");
			return;
		}
		
		int a, b, n;
		try {
			a = Integer.parseInt(varA);
		} catch (NumberFormatException ex) {
			Util.sendError(req, resp, "Parameter a must be integer!");
			return;
		}
		try {
			b = Integer.parseInt(varB);
		} catch (NumberFormatException ex) {
			Util.sendError(req, resp, "Parameter b must be integer!");
			return;
		}
		try {
			n = Integer.parseInt(varN);
		} catch (NumberFormatException ex) {
			Util.sendError(req, resp, "Parameter n must be integer!");
			return;
		}
		
		if(a<-100 || a>100) Util.sendError(req, resp, "Parameter a must be within [-100,100]!");
		if(b<-100 || b>100) Util.sendError(req, resp, "Parameter b must be within [-100,100]!");
		if(n<1 || n>5) Util.sendError(req, resp, "Parameter n must be within [1, 5]!");
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");
		
		HSSFWorkbook hwb = createExcelFile(a, b, n);
		hwb.write(resp.getOutputStream());
		hwb.close();
		
	}

	/**
	 * Creates an HSSFWorkbook out of first n powers of integers that are
	 * between a and b.
	 * @param a lower boundary
	 * @param b upper boundary
	 * @param n first n powers of ints between a and b will be calculated
	 * @return HSSFWorkbook out of said information
	 */
	private HSSFWorkbook createExcelFile(int a, int b, int n) {
		
		HSSFWorkbook hwb = new HSSFWorkbook();
			
		for(int i=1; i<=n; i++) {
		
			HSSFSheet sheet = hwb.createSheet("Powers x^" + i);

			for(int j=a; j<=b; j++) {
				HSSFRow rowhead = sheet.createRow(j-a);
				rowhead.createCell(0).setCellValue(j);
				rowhead.createCell(1).setCellValue((int)Math.pow(j, i));
			}
		
		}

		return hwb;
		
	}

}
