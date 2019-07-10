package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.DAO.PollOption;

/**
 * Servlet used for displaying voting results as a pie graph
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="glasanje-grafika", urlPatterns= {"/servleti/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet{

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
		
		resp.setContentType("image/png");
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		for(PollOption v : results) {
			if(v.getVotesCount()==0) continue;
			dataset.setValue(v.getTitle(), v.getVotesCount());
		}
        
        JFreeChart chart = ChartFactory.createPieChart("Rezultati", dataset, true, false, false);
        
        ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 500, 500);
		
	}

}
