package game;

public class Piece{
	private boolean isRed;
	private boolean isKing;
	private boolean hasMove;
	private Gameboard gameBoard;
	private int[][] position;
	
	public Piece(boolean _isRed,boolean _isKing, int[][] _position, Gameboard _gameBoard) {
		isRed = _isRed;
		isKing = _isKing;
		position = _position;
		gameBoard = _gameBoard;
	}
	
	public void kingPiece() {
		isKing = true;
	}
	
	public boolean hasValidMove() {
		return true;
	}
	
	public void setPosition(int[][] _position) {
		position = _position;
	}
	
	public int[][] getPosition(){
		return position;
	}
}
