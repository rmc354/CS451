package network;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class robHost {
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter a port(1-9999)");
		int port = sc.nextInt();
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
				
		ServerSocket server = new ServerSocket(port);
    	
    	while(true ) {
    		Socket client = server.accept();
    		
    		RequestHandler rh = new RequestHandler(client);
    		rh.run();
    	}
    }
}
