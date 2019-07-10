package hr.fer.zemris.java.p10.tcp;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HTTPDownload {
	
	public static void main(String[] args) throws IOException {
		
		if(args.length!=4) {
			System.out.println("Ocekivao sam host port urlpath localfilepath");
			return;
		}
		
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		String urlpath = args[2];
		String filepath = args[3];
		
		OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(filepath)));
		
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(InetAddress.getByName(hostname), port));
		
		byte[] reqData = (
			"GET " + urlpath + " HTTP/1.1\r\n" +
			"Host: " + hostname + "\r\n" +
			"User-Agent: Simple Java Client\r\n" +
			"Connection: close\r\n" +
			"\r\n"
		).getBytes(StandardCharsets.US_ASCII);
		
		socket.getOutputStream().write(reqData);
		socket.getOutputStream().flush();
		
		byte[] buff = new byte[1024];
		while(true) {
			int r = socket.getInputStream().read(buff);
			if(r<0) break;
			os.write(buff, 0, r);
		}
		
		os.flush();
		
		os.close();
		socket.close();
		
	}

}
