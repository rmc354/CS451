package game;

import java.util.ArrayList;

public class Gameboard {
	private int[][] board;
	private ArrayList<Piece> pieces;
	
	public Gameboard(int[][] _board, ArrayList<Piece> _pieces) {
		board = _board;
		pieces = _pieces;
	}
	
	public void setBoard(int[][] _board) {
		board = _board;
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public void setPieces(ArrayList<Piece> _pieces) {
		pieces = _pieces;
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
}
