package game;

public class Piece{
	private boolean isRed;
	private boolean isKing;
	private boolean hasMove;
	private Gameboard gameBoard;
	private int xPosition;
	private int yPosition;
	
	public Piece(boolean _isRed,boolean _isKing, int _xPosition, int _yPosition, Gameboard _gameBoard) {
		isRed = _isRed;
		isKing = _isKing;
		xPosition = _xPosition;
		yPosition = _yPosition;
		gameBoard = _gameBoard;
	}
	
	public void kingPiece() {
		isKing = true;
	}
	
	public boolean hasValidMove() {
		Piece[][] board = gameBoard.getBoard();
		if(isKing) {
			if(xPosition == 0) {
				if(board[xPosition + 1][yPosition - 1] == null || board[xPosition + 1][yPosition + 1] == null) {
					return true;
				}
				else {
					return false;
				}
			}
			else if(yPosition == 0) {
				if(board[xPosition + 1][yPosition + 1] != null || board[xPosition - 1][yPosition + 1] != null) {
					return true;
				}
				else {
					return false;
				}
			}
			else if(board[xPosition + 1][yPosition - 1] != null || board[xPosition - 1][yPosition - 1] != null || board[xPosition + 1][yPosition + 1] != null || board[xPosition - 1][yPosition + 1] != null) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if(isRed) {
				if(xPosition == 0) {
					if(board[xPosition + 1][yPosition - 1] != null) {
						return true;
					}
					else {
						return false;
					}
				}
				else if(board[xPosition + 1][yPosition - 1] != null || board[xPosition - 1][yPosition - 1] != null) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				if(xPosition == 0) {
					if(board[xPosition + 1][yPosition + 1] != null) {
						return true;
					}
					else {
						return false;
					}
				}
				else if(board[xPosition + 1][yPosition + 1] != null || board[xPosition - 1][yPosition + 1] != null) {
					return true;
				}
				else {
					return false;
				}
			}
		}
	}
	
	public void setPosition(int _xPosition, int _yPosition) {
		xPosition = _xPosition;
		yPosition = _yPosition;
	}
	
	public String getPosition(){
		String position = "X: " + xPosition + " Y: " + yPosition;
		return position;
	}
}
