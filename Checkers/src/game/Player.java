package game;

import java.util.ArrayList;

public class Player {
	private boolean isRed;
	private Gameboard gameBoard;
	private ArrayList<Piece> pieces;
	
	public Player(boolean _isRed, Gameboard _gameBoard, ArrayList<Piece> _pieces) {
		isRed = _isRed;
		gameBoard = _gameBoard;
		pieces = _pieces;
	}
	
	public void selectPiece(Piece piece) {
		
	}
	
	public void movePiece(Piece piece, int[][] position) {
		piece.setPosition(position);
	}
	
	public void setPieces(ArrayList<Piece> _pieces) {
		pieces = _pieces;
	}
	
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
	
	public void endTurn() {
		
	}
}
