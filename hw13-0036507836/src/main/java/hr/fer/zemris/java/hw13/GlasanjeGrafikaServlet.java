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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.hw13.Voting.VoteObject;

/**
 * Servlet used for displaying voting results as a pie graph
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="glasanje-grafika", urlPatterns= {"/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet{

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
		
		resp.setContentType("image/png");
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		for(VoteObject v : results) {
			if(v.getVoteCount()==0) continue;
			dataset.setValue(v.getName(), v.getVoteCount());
		}
        
        JFreeChart chart = ChartFactory.createPieChart("Rezultati", dataset, true, false, false);
        
        ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 500, 500);
		
	}

}
