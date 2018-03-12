import static org.junit.jupiter.api.Assertions.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.junit.jupiter.api.Test;

import checkers.*;

class PieceTests {
	@Test
	//Test getImageSource function.
	void testGetImageSource() {
		//Two pieces, one created red one created black.
		Piece testRedPiece = new Piece(true, 0, 0);
		Piece testBlackPiece = new Piece(false, 0, 0);
		Piece testRedKingPiece = new Piece(true, 0, 0);
		Piece testBlackKingPiece = new Piece(false, 0, 0);
		
		testRedKingPiece.king();
		testBlackKingPiece.king();
		
		assertEquals("RedPiece.gif", testRedPiece.getImageSource());
		assertEquals("BlackPiece.gif", testBlackPiece.getImageSource());
		assertEquals("RedKingPiece.gif", testRedKingPiece.getImageSource());
		assertEquals("BlackKingPiece.gif", testBlackKingPiece.getImageSource());
	}
	
	@Test
	//Test if a piece is black.
	void pieceIsBlack() {
		//Two pieces, one created red one created black.
		Piece testRedPiece = new Piece(true, 0, 0);
		Piece testBlackPiece = new Piece(false, 0, 0);
		
		//Test that the red piece is red and the black piece is not red.
		assertEquals(false, testRedPiece.isBlack());
		assertEquals(true, testBlackPiece.isBlack());
	}
	
	@Test
	//Test if a piece is red.
	void pieceIsRed() {
		//Two pieces, one created red one created black.
		Piece testRedPiece = new Piece(true, 0, 0);
		Piece testBlackPiece = new Piece(false, 0, 0);
		
		//Test that the red piece is red and the black piece is not red.
		assertEquals(true, testRedPiece.isRed());
		assertEquals(false, testBlackPiece.isRed());
	}
	
	@Test
	//Test the isKing function for a piece.
	void pieceIsKing() {
		//The initial piece constructor defaults the piece to not kinged.
		Piece testRedPiece = new Piece(true, 0, 0);
		
		//Test that the king is not a piece.
		assertEquals(false, testRedPiece.isKing());
	}
	
	@Test
	//Test if a Piece can become a King.
	void pieceCanBeKing() {
		Piece testRedPiece = new Piece(true, 0, 0);
		Piece testBlackPiece = new Piece(false, 1, 1);
		
		//Test that both pieces are not kings to start.
		assertEquals(false, testRedPiece.isKing());
		assertEquals(false, testBlackPiece.isKing());
		
		//King Both Pieces.
		testRedPiece.king();
		testBlackPiece.king();
		
		//Test that both pieces are now kings.
		assertEquals(true, testRedPiece.isKing());
		assertEquals(true, testBlackPiece.isKing());
	}
	
	@Test
	//Test the getPosition function.
	void getPiecePosition() {
		//Initial x and y positions.
		int initX = 0;
		int initY = 7;
		
		//Create the Piece.
		Piece testPiecePosition = new Piece(true, initX, initY);
		
		//Get Current Piece positions.
		int[] piecePosition = testPiecePosition.getPosition();
		int xPos = piecePosition[0];
		int yPos = piecePosition[1];
		
		//Test that the returned positions are equal to the initial.
		assertEquals(initX, xPos);
		assertEquals(initY, yPos);
	}
	
	@Test
	//Test the setPosition function.
	void setPiecePosition() {
		//Create the Piece.
		Piece testPiecePosition = new Piece(true, 5, 3);
		
		//Set the position to a new coordinate.
		testPiecePosition.setPosition(7, 6);
		
		//Get Current Piece positions.
		int[] piecePosition = testPiecePosition.getPosition();
		int xPos = piecePosition[0];
		int yPos = piecePosition[1];
		
		//Test the returned positions equal the ones passed in setPosition.
		assertEquals(7, xPos);
		assertEquals(6, yPos);
	}

}
