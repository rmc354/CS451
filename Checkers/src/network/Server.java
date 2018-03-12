package network;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server{
	

	private static Scanner sc;

	public static void main(String[] args) throws IOException {
		sc = new Scanner(System.in);
		System.out.println("Enter a port(1-9999)");
		int i = sc.nextInt();
	
		while(i>9999)			
		{
			System.out.println("Please Enter Valid Port: ");
			i = sc.nextInt();
		}
		ServerSocket listener = new ServerSocket(i);
        System.out.println("Checkers Server Is Running");
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        try {
        	while (true) {
        		Auth p1 = new Auth(listener.accept());
        		Auth p2 = new Auth(listener.accept());
        		p1.start();
        		p2.start();
//                Checkers ch = new Checkers();
//                Players black = new Players(listener.accept(), 'B');
//                Players red = new Players(listener.accept(), 'R');
//                black.setOpp(red);
//                red.setOpp(black);
//                black.start();
//                red.start();
        	}
        } finally {
        	listener.close();
        }
	}

}
