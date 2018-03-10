package checkers;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Piece {
	private boolean isRed;
	private boolean isKinged;
	
	Piece(boolean isRed) {
		this.isRed = isRed;
		isKinged = false;
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
}
