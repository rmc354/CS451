package checkers;

import java.io.IOException;
import java.net.UnknownHostException;

import network.Client;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		//Client client = new Client();
		Board board = new Board(true, true);
		board.initializeGui(board);
		
	}

}
