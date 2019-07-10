package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Servlet used for displaying a pie graph out of fictional information
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="reportImage", urlPatterns= {"/reportImage"})
public class ReportImageServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("image/png");
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 28);
		dataset.setValue("Mac", 19);
		dataset.setValue("Windows", 53);
        
        JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, true, false, false);
        
        ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 500, 500);
		
	}

}
