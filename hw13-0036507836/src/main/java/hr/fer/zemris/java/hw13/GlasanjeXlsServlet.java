package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw13.Voting.VoteObject;

/**
 * Servlet used for creating an .xsl (MS excel) file out of voting results
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="glasanje-xls", urlPatterns= {"/glasanje-xls"})
public class GlasanjeXlsServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Učitaj rezultate
		String resFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String defFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path resPath = Paths.get(resFile);
		Path defPath = Paths.get(defFile);
		
		Voting voting = Voting.getInstance();
		try {
			voting.load(resPath, defPath);
		} catch(Exception ex) {
			Util.sendError(req, resp, ex.getMessage());
		}
		List<VoteObject> results = voting.getResults();
		
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
		for(VoteObject v : results) {
			rowhead = sheet.createRow(i);
			rowhead.createCell(0).setCellValue(v.getId());
			rowhead.createCell(1).setCellValue(v.getName());
			rowhead.createCell(2).setCellValue(v.getVoteCount());
			rowhead.createCell(3).setCellValue(v.getUrl());
			i++;
		}
		
		hwb.write(resp.getOutputStream());
		hwb.close();
	}

}
