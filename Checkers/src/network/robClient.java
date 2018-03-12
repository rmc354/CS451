package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import checkers.Board;

public class robClient {

	public static void main(String args[]) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter an IP address or \"localhost\"");
		String ip = sc.nextLine();
		
		System.out.println("Enter a port(1-9999)");
		int port = sc.nextInt();
		
		Socket socket = new Socket(ip, port);
        
		ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        Board board = new Board(false, false);
        board.initializeGui(board);
        while (true) {
        	Data data = (Data) is.readObject();
        	board.setPieces(data.pieces);
        	board.refresh();
        	board.yourTurn();
        	while (board.getYourTurn() == true) {
        		TimeUnit.SECONDS.sleep(1);
        	}
        	if (board.gameOver()) {
        		break;
        	}
        	data.pieces = board.getPieces();
        	os.writeObject(data);
        }
        
        Data data = (Data) is.readObject();

        socket.close();
	}
	
}
