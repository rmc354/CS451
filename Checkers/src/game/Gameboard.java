package game;

import java.util.ArrayList;

public class Gameboard {
	private Piece[][] board;
	private ArrayList<Piece> pieces;
	
	public Gameboard(Piece[][] _board, ArrayList<Piece> _pieces) {
		board = _board;
		pieces = _pieces;
	}
	
	public void setBoard(Piece[][] _board) {
		board = _board;
	}
	
	public Piece[][] getBoard(){
		return board;
	}
	
	public void setPieces(ArrayList<Piece> _pieces) {
		pieces = _pieces;
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
}
