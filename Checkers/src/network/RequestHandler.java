package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import checkers.Board;

public class RequestHandler {
	private Socket clientSocket = null;
	
	public RequestHandler(Socket _clientSocket) {
		clientSocket = _clientSocket;
	}
	
	public void run() throws ClassNotFoundException, InterruptedException {
		try {
	        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
	        ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
			Board board = new Board(true, true);
			board.initializeGui(board);
			Data data = null;
	        while (true) {
	        	if (data != null) {
	        		board.setPieces(data.pieces);
	        	} else {
	        		data = new Data();
	        	}
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
	        	data = (Data) is.readObject();
	        }
	        
			clientSocket.close();
		} catch(IOException ioex) {
			ioex.printStackTrace();
		}
	}
}
