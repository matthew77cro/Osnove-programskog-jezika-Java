package hr.fer.zemris.java.p10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPPosluzitelj {
	
	public static void main(String[] args) throws SocketException {
		
		if(args.length!=1) {
			System.out.println("Ocekivao sam port!");
			return;
		}
		
		int port = Integer.parseInt(args[0]);
		
		@SuppressWarnings("resource")
		DatagramSocket dSocket = new DatagramSocket(null);
		dSocket.bind(new InetSocketAddress((InetAddress)null, port));
		
		while(true) {
			
			byte[] recvBuffer = new byte[40];
			DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
			
			try {
				dSocket.receive(recvPacket);
			} catch (IOException ex) {
				continue;
			}
			
			if(recvPacket.getLength()!=4) {
				System.out.println("Primljen neispravan upit od " + recvPacket.getSocketAddress());
				continue;
			}
			
			// Gubitak u komunikacijskom kanalu
			// if(Math.random() < 0.5) continue;
			
			short broj1 = ShortUtil.bytesToShort(recvPacket.getData(), recvPacket.getOffset());
			short broj2 = ShortUtil.bytesToShort(recvPacket.getData(), recvPacket.getOffset() + 2);
			short result = (short) (broj1 + broj2);
			
			System.out.printf("Upit of %s, x=%d, y=%d, r=%d%n", recvPacket.getSocketAddress(), broj1, broj2, result);
			
			byte[] sendBuffer = new byte[2];
			ShortUtil.shortToBytes(result, sendBuffer, 0);
			DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length);
			sendPacket.setSocketAddress(recvPacket.getSocketAddress());
			
			try {
				dSocket.send(sendPacket);
			} catch (IOException ex) {
				System.out.println("Greska pri slanju");
			}
			
		}
		
	}

}
