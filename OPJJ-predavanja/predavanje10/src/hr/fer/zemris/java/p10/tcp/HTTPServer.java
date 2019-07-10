package hr.fer.zemris.java.p10.tcp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Primjer jednostavnog jednodretvenog HTTP-poslužitelja.
 * Koristit ćemo ga na predavanju za daljnju nadogradnju.
 * 
 * @author marcupic
 */
public class HTTPServer {

	public static void main(String[] args) throws IOException {
		if(args.length!=1) {
			System.out.println("Očekivao sam port");
			return;
		}
		
		int port = Integer.parseInt(args[0]);
		
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(
			new InetSocketAddress((InetAddress)null, port)
		);
		
		while(true) {
			Socket toClient = serverSocket.accept();
			new Thread(() -> {
				try {
					serve(toClient);
				} catch (IOException e) {
					System.out.println(e.getCause().toString() + " " + e.getMessage());
				}
			}).start();
		}
	}

	private static void serve(Socket toClient) throws IOException {
		try {
			InputStream cis = new BufferedInputStream(
				toClient.getInputStream()
			);
			OutputStream cos = new BufferedOutputStream(
				toClient.getOutputStream()
			);
			
			byte[] request = readRequest(cis);
			if(request==null) {
				sendError(cos, 400, "Bad request");
				return;
			}
			String requestStr = new String(
				request, 
				StandardCharsets.US_ASCII
			);
			
			List<String> headers = extractHeaders(requestStr);
			String[] firstLine = headers.isEmpty() ? 
				null : headers.get(0).split(" ");
			if(firstLine==null || firstLine.length != 3) {
				sendError(cos, 400, "Bad request");
				return;
			}

			String method = firstLine[0].toUpperCase();
			if(!method.equals("GET")) {
				sendError(cos, 405, "Method Not Allowed");
				return;
			}
			
			String version = firstLine[2].toUpperCase();
			if(!version.equals("HTTP/1.1")) {
				sendError(cos, 505, "HTTP Version Not Supported");
				return;
			}
			
			String path = firstLine[1];
			
			if(path.equals("/slika")) {
				vratiSliku(cos);
				return;
			}
			
			Path docRoot = Paths.get("./web");
			Path reqFile = docRoot.resolve(path.substring(1)); //ubijamo vodeci slash "/"
			// if not reqFile.normalize().startsWith(docRoot); -> send error 404
			
			if(!Files.isReadable(reqFile)) {
				sendError(cos, 404, "File not found");
				return;
			}
			
			serveFile(cos, reqFile);
			
//			showInfo(
//				cos, path, version, 
//				headers.subList(1, headers.size())
//			);
		} catch(Exception ex) {
			System.out.println("Pogreška: " + ex.getMessage());
		} finally {
			toClient.close();
		}
	}

	private static void vratiSliku(OutputStream cos) throws IOException {
		BufferedImage bim = new BufferedImage(300, 200, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g = bim.createGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 300, 200);
		g.setColor(Color.RED);
		g.fillOval(0, 0, 300, 200);
		g.dispose();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bim, "png", baos);
		
		byte[] podaci = baos.toByteArray();
		
		cos.write(
				("HTTP/1.1 "+ 200 +" "+ "OK" +"\r\n"+
				"Server: simple java server\r\n"+
				"Content-Type: image/png\r\n"+
				"Content-Length: " + podaci.length + "\r\n"+
				"Connection: close\r\n"+
				"\r\n").getBytes(StandardCharsets.US_ASCII)
		);
		cos.write(podaci);
		cos.flush();
	}

	private static void serveFile(OutputStream cos, Path reqFile) throws IOException {
		long len = Files.size(reqFile);
		String mime = determineMimeType(reqFile.getFileName().toString());
		
		try(InputStream is = new BufferedInputStream(Files.newInputStream(reqFile))) {
			
			cos.write(
					("HTTP/1.1 "+ 200 +" "+ "OK" +"\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: " + mime + "\r\n"+
					"Content-Length: " + len + "\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII)
			);
			
			byte[] buff = new byte[1024];
			while(true) {
				int r = is.read(buff);
				if(r<1) break;
				cos.write(buff, 0, r);
			}
			cos.flush();
			
		} catch (Exception ex) {
			
		}
	}

	private static String determineMimeType(String name) {
		name = name.toLowerCase();
		
		if(name.endsWith(".html")) {
			return "text/html;charset=UTF-8";
		}
		if (name.endsWith(".jpg")) {
			return "image/jpg";
		}
		
		return "application/octet-stream";
	}

	// Jednostavan automat koji čita zaglavlje HTTP zahtjeva...
	private static byte[] readRequest(InputStream is) 
		throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int state = 0;
l:		while(true) {
			int b = is.read();
			if(b==-1) return null;
			if(b!=13) {
				bos.write(b);
			}
			switch(state) {
			case 0: 
				if(b==13) { state=1; } else if(b==10) state=4;
				break;
			case 1: 
				if(b==10) { state=2; } else state=0;
				break;
			case 2: 
				if(b==13) { state=3; } else state=0;
				break;
			case 3: 
				if(b==10) { break l; } else state=0;
				break;
			case 4: 
				if(b==10) { break l; } else state=0;
				break;
			}
		}
		return bos.toByteArray();
	}

	// Zaglavlje predstavljeno kao jedan string splita po enterima
	// pazeći na višeretčane atribute...
	private static List<String> extractHeaders(String requestHeader) {
		List<String> headers = new ArrayList<String>();
		String currentLine = null;
		for(String s : requestHeader.split("\n")) {
			if(s.isEmpty()) break;
			char c = s.charAt(0);
			if(c==9 || c==32) {
				currentLine += s;
			} else {
				if(currentLine != null) {
					headers.add(currentLine);
				}
				currentLine = s;
			}
		}
		if(!currentLine.isEmpty()) {
			headers.add(currentLine);
		}
		return headers;
	}

	// Pomoćna metoda za slanje odgovora bez tijela...
	private static void sendError(OutputStream cos, 
		int statusCode, String statusText) throws IOException {

		cos.write(
			("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
			"Server: simple java server\r\n"+
			"Content-Type: text/plain;charset=UTF-8\r\n"+
			"Content-Length: 0\r\n"+
			"Connection: close\r\n"+
			"\r\n").getBytes(StandardCharsets.US_ASCII)
		);
		cos.flush();

	}

	// Generiranje HTML-stranice koja sadrži informacije o poslanom upitu.
	private static void showInfo(OutputStream cos, 
		String path, String version, List<String> headers) 
		throws IOException {

		StringBuilder sb = new StringBuilder(
			"<html>\r\n" +
			"  <head>\r\n" + 
			"    <title>Ispis zaglavlja</title>\r\n"+
			"  </head>\r\n" + 
			"  <body>\r\n" + 
			"    <h1>Informacije o poslanom upitu</h1>\r\n" + 
			"    <p>Zatražen resurs: " + path + "</p>\r\n" + 
			"    <p>Definirane varijable:</p>\r\n" + 
			"    <table border='1'>\r\n");
		for(String redak : headers) {
			int pos = redak.indexOf(':');
			sb.append("      <tr><td>")
				.append(redak.substring(0, pos).trim())
				.append("</td><td>")
				.append(redak.substring(pos+1).trim())
				.append("</td></tr>\r\n");
		}
		sb.append(
			"    </table>\r\n" + 
			"  </body>\r\n" + 
			"</html>\r\n");
		
		byte[] tijeloOdgovora = sb.toString().getBytes(
			StandardCharsets.UTF_8
		);
		byte[] zaglavljeOdgovora = (version+" 200 OK\r\n" +
			"Server: simple java server\r\n"+
			"Content-Type: text/html;charset=UTF-8\r\n"+
			"Content-Length: "+tijeloOdgovora.length+"\r\n"+
			"Connection: close\r\n"+
			"\r\n").getBytes(StandardCharsets.US_ASCII);
		
		cos.write(zaglavljeOdgovora);
		cos.write(tijeloOdgovora);
		cos.flush();
	}
}