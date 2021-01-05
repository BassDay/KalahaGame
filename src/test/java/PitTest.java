import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PitTest {

	@Test
	void testPitInit() {
		Pit pit = new Pit(2);
		assertNotNull(pit);
	}
	
	@Test
	void testNegativeAmountPitInit() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new Pit(-1);
	    });

	    String expectedMessage = "Initial amount can't be negative";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testPitCountsCorrectly() {
		Pit pit = new Pit(2);
		assertEquals(pit.getCount(), 2);
	}
	
	@Test
	void testPitPurge() {
		Pit pit = new Pit(2);
		assertEquals(pit.getCount(), 2);
		pit.purge();
		assertEquals(pit.getCount(), 0);
	}
	
	@Test
	void testPitIncrement() {
		Pit pit = new Pit(2);
		assertEquals(pit.getCount(), 2);
		pit.addStone();
		assertEquals(pit.getCount(), 3);
	}
	
	@Test
	void testPitAdding() {
		Pit pit = new Pit(2);
		assertEquals(pit.getCount(), 2);
		pit.addStones(3);
		assertEquals(pit.getCount(), 5);
	}

}
