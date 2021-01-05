import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest {

	@Test
	void testGameOverIsCorrect() {
		Board board;
		board = new Board(2, 0);
		assertTrue(board.gameIsOver());
		
		board = new Board(1, 2);
		assertFalse(board.gameIsOver());
		board.performTurn(1, 0);
		assertTrue(board.gameIsOver());
	}
	
	@Test
	void testAnotherTurnGrantedCorrectly() {
		Board board = new Board(1, 1);
		assertEquals(board.performTurn(1, 0), 1);
	}
	
	@Test
	void testTurnNotGrantedCorrectly() {
		Board board = new Board(1, 2);
		assertNotEquals(board.performTurn(1, 0), 1);
	}
	
	@Test
	void testAnotherTurnGrantedIfPitIsEmpty() {
		Board board = new Board(1, 0);
		assertEquals(board.performTurn(1, 0), 1);
	}
}
