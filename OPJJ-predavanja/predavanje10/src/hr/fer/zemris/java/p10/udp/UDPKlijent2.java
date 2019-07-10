package hr.fer.zemris.java.p10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPKlijent2 {
	
	public static void main(String[] args) throws SocketException, UnknownHostException {
		
		if(args.length!=2) {
			System.out.println("Expected arguments: host port");
			return;
		}
		
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		
		Scanner sc = new Scanner(System.in);
		
		DatagramSocket dSocket = new DatagramSocket();
		
		while(true) {
			
			String msg = sc.nextLine();
			byte[] podaci = msg.getBytes(StandardCharsets.UTF_8);
			
			byte[] podaciSlanje = new byte[podaci.length+2];
			ShortUtil.shortToBytes((short)podaci.length, podaciSlanje, 0);
			
			for(int i=0; i<podaci.length; i++) {
				podaciSlanje[i+2] = podaci[i];
			}
			
			DatagramPacket packet = new DatagramPacket(podaciSlanje, podaciSlanje.length);
			packet.setSocketAddress(new InetSocketAddress(InetAddress.getByName(hostname), port));
			
			System.out.println("Saljem upit...");
			try {
				dSocket.send(packet);
			} catch (IOException ex) {
				System.out.println("Ne mogu poslati upit.");
				break;
			}
			
			System.out.println("Poslano");
			
		}
		
		dSocket.close();
		sc.close();
		
	}

}
