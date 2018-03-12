package checkers;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Piece {
	private boolean isRed;
	private boolean isKinged;
	private int xPosition;
	private int yPosition;
	
	public Piece(boolean isRed, int _xPosition, int _yPosition) {
		this.isRed = isRed;
		isKinged = false;
		xPosition = _xPosition;
		yPosition = _yPosition;
	}
	
	public void setPosition(int _xPosition, int _yPosition) {
		xPosition = _xPosition;
		yPosition = _yPosition;
	}
	
	public void king() {
		isKinged = true;
	}
	
	public boolean isBlack() {
		return !isRed;
	}
	
	public boolean isRed() {
		return isRed;
	}
	
	public JButton getButton() {
		String image = "";
		if (isRed) {
			image = "images/RedPiece.gif";
		} else {
			image = "images/BlackPiece.gif";
		}
		ImageIcon myImage = new ImageIcon(image);
    	JButton button = new JButton(myImage);
		return button;
	}
	
	public String getImageSource() {
		if (isRed && !isKinged) {
			return "RedPiece.gif";
		} else if (!isRed && !isKinged) {
			return "BlackPiece.gif";
		} else if (isRed) {
			return "RedKingPiece.gif";
		} else {
			return "BlackKingPiece.gif";
		}
	}
	
	public int[] getLeftMove(int x, int y) {
		int[] pos;

		if (isRed && !isKinged) {
			pos = new int[2];
			pos[0] = x - 1;
			pos[1] = y - 1;
		} else if (!isRed && !isKinged) {
			pos = new int[2];
			pos[0] = x - 1;
			pos[1] = y + 1;
		} else {
			pos = new int[4];
			pos[0] = x - 1;
			pos[1] = y - 1;
			pos[2] = x - 1;
			pos[3] = y + 1;
		}
		return pos;
	}

	public int[] getRightMove(int x, int y) {
		int[] pos;

		if (isRed && !isKinged) {
			pos = new int[2];
			pos[0] = x + 1;
			pos[1] = y - 1;
		} else if (!isRed && !isKinged) {
			pos = new int[2];
			pos[0] = x + 1;
			pos[1] = y + 1;
		} else {
			pos = new int[4];
			pos[0] = x + 1;
			pos[1] = y - 1;
			pos[2] = x + 1;
			pos[3] = y + 1;
		}
		return pos;
	}
	
	public boolean hasValidMove(Piece[][] board) {
		int[] topRight = new int[] {xPosition + 1, yPosition - 1};
		int[] jumpTopRight = new int[] {xPosition + 2,yPosition - 2};
		int[] bottomRight = new int[] {xPosition + 1,yPosition + 1};
		int[] jumpBottomRight = new int[] {xPosition + 2,yPosition + 2};
		int[] topLeft = new int[] {xPosition - 1,yPosition - 1};
		int[] jumpTopLeft = new int[] {xPosition - 2,yPosition - 2};
		int[] bottomLeft = new int[] {xPosition - 1,yPosition + 1};
		int[] jumpBottomLeft = new int[] {xPosition - 2,yPosition + 2};
		
		if((!isRed && isKinged || isRed) && topRight[0] >= 0 && topRight[0] < 8 && topRight[1] >= 0 && topRight[1] < 8) {
			Piece topRightPiece = board[topRight[0]][topRight[1]];
			if(topRightPiece == null) {
				return true;
			}
			else {
				if(topRightPiece.isRed() != isRed && jumpTopRight[0] >= 0 && jumpTopRight[0] < 8 && jumpTopRight[1] >= 0 && jumpTopRight[1] < 8) {
					Piece jumpTopRightPiece = board[jumpTopRight[0]][jumpTopRight[1]];
					if(jumpTopRightPiece == null) {
						return true;
					}
				}
			}
		}
		if((!isRed && isKinged || isRed) && topLeft[0] >= 0 && topLeft[1] >= 0 && topLeft[0] < 8 && topLeft[1] < 8) {
			Piece topLeftPiece = board[topLeft[0]][topLeft[1]];
			if(topLeftPiece == null) {
				return true;
			}
			else {
				if(topLeftPiece.isRed() != isRed && jumpTopLeft[0] >= 0 && jumpTopLeft[1] >= 0 && jumpTopLeft[0] < 8 && jumpTopLeft[1] < 8) {
					Piece jumpTopLeftPiece = board[jumpTopLeft[0]][jumpTopLeft[1]];
					if(jumpTopLeftPiece == null) {
						return true;
					}
				}
			}
		}
		if((isRed && isKinged || !isRed) && bottomRight[0] >= 0 && bottomRight[1] >= 0 && bottomRight[0] < 8 && bottomRight[1] < 8) {
			Piece bottomRightPiece = board[bottomRight[0]][bottomRight[1]];
			if(bottomRightPiece == null) {
				return true;
			}
			else {
				if(bottomRightPiece.isRed() != isRed && jumpBottomRight[0] >= 0 && jumpBottomRight[1] >= 0 && jumpBottomRight[0] < 8 && jumpBottomRight[1] < 8) {
					Piece jumpBottomRightPiece = board[jumpBottomRight[0]][jumpBottomRight[1]];
					if(jumpBottomRightPiece == null) {
						return true;
					}
				}
			}
		}
		if((isRed && isKinged || !isRed) && bottomLeft[0] >= 0 && bottomLeft[1] >= 0 && bottomLeft[0] < 8 && bottomLeft[1] < 8) {
			Piece bottomLeftPiece = board[bottomLeft[0]][bottomLeft[1]];
			if(bottomLeftPiece == null) {
				return true;
			}
			else {
				if(bottomLeftPiece.isRed() != isRed && jumpBottomLeft[0] >= 0 && jumpBottomLeft[1] >= 0 && jumpBottomLeft[0] < 8 && jumpBottomLeft[1] < 8) {
					Piece jumpBottomLeftPiece = board[jumpBottomLeft[0]][jumpBottomLeft[1]];
					if(jumpBottomLeftPiece == null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isKing() {
		// TODO Auto-generated method stub
		return isKinged;
	}

	public int[] getPosition() {
		int[] pos = new int[] {xPosition, yPosition};
		return pos;
	}
}
