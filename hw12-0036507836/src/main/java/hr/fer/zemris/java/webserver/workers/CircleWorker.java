package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class CircleWorker implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("image/png");
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		
		g2d.setColor(Color.RED);
		g2d.fillOval(0, 0, 200, 200);
		
		g2d.dispose();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bim, "png", bos);
		byte[] arr = bos.toByteArray();
		context.setContentLength((long)arr.length);
		context.write(arr);
	}
}