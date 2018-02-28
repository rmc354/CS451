package checkers;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Piece {
	boolean isRed;
	boolean isKinged;
	
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
}
