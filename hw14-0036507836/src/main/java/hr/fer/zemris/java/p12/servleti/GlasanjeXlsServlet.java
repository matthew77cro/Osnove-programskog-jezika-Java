package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.DAO.PollOption;

/**
 * Servlet used for creating an .xsl (MS excel) file out of voting results
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="glasanje-xls", urlPatterns= {"/servleti/glasanje-xls"})
public class GlasanjeXlsServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Učitaj rezultate
		String idParam = req.getParameter("id");
		int id = 0;
		try {
			id = Integer.parseInt(idParam);
		} catch (NumberFormatException ex) {
			Util.sendError(req, resp, "Error : id must be of type integer");
			return;
		}
		
		List<PollOption> results = DAOProvider.getDao().getPollOptions(id);
		if(results.isEmpty()) {
			Util.sendError(req, resp, "Error : poll empty or no such poll");
			return;
		}
		results.sort((o1, o2) -> Integer.compare(o2.getVotesCount(), o1.getVotesCount()));
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"Rezultati glasanja.xls\"");
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		HSSFSheet sheet = hwb.createSheet("Rezultati");
		
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("ID");
		rowhead.createCell(1).setCellValue("Name");
		rowhead.createCell(2).setCellValue("Vote count");
		rowhead.createCell(3).setCellValue("URL");

		int i = 1;
		for(PollOption v : results) {
			rowhead = sheet.createRow(i);
			rowhead.createCell(0).setCellValue(v.getId());
			rowhead.createCell(1).setCellValue(v.getTitle());
			rowhead.createCell(2).setCellValue(v.getVotesCount());
			rowhead.createCell(3).setCellValue(v.getLink());
			i++;
		}
		
		hwb.write(resp.getOutputStream());
		hwb.close();
	}

}
