import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import checkers.Board;
import checkers.Piece;

class BoardTests {
	
	@Test
	//Test setPieces().
	void testSetPieces() {
		//Test board.
		Board testBoard = new Board(true, true);
		
		//Test Piece[].
		Piece[] testPiece = new Piece[] { new Piece(true, 5, 5)};
		//Test Piece[][] to be passed.
		Piece[][] pieces = new Piece[][] {testPiece};
		
		//Call the setPiece function.
		testBoard.setPieces(pieces);
		
		//Test the return of getPieces to pieces.
		assertEquals(pieces,testBoard.getPieces());
		
	}
	
	@Test
	//Test getYourTurn().
	void testGetYourTurn() {
		Board yourTurn = new Board(true, true);
		Board notTurn = new Board(true, false);
		
		assertEquals(true, yourTurn.getYourTurn());
		assertEquals(false, notTurn.getYourTurn());
	}
	
	@Test
	//Test getControlsRed().
	void testControlsRed() {
		Board isRed = new Board(true, true);
		Board notRed = new Board(false, true);
		
		assertEquals(true, isRed.getControlsRed());
		assertEquals(false, notRed.getControlsRed());
	}

}
