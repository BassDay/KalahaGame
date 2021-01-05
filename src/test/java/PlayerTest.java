import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
	
	private Player p;
	private int pitCount;
	private int stoneCount;
	
	@BeforeEach
    public void setUp() {
		System.out.println("set up");
		pitCount = 3;
		stoneCount = 5;
		p = new Player(pitCount, stoneCount);
    }
	
	@Test
	void playerInitTest() {
		System.out.println("playerInitTest");
		for(int i = 0; i < pitCount; i++) {
			assertEquals(p.getStoneAmount(i), stoneCount);
		}
	
		assertEquals(p.getStoneAmount(pitCount), 0);
	}
	
	@Test 
	void testCheckingEmptinessIsCorrect() {
		System.out.println("testCheckingEmptinessIsCorrect");
		for(int i = 0; i < pitCount; i++) {
			assertFalse(p.isPitEmpty(i));
		}
	
		assertTrue(p.isPitEmpty(pitCount));
	}
	
	@Test
	void testSeedingIsCorrect() {
		System.out.println("testSeedingIsCorrect");
		p.seed(0);
		assertEquals(p.getStoneAmount(0), 1);
		assertEquals(p.getStoneAmount(1), 7);
		assertEquals(p.getStoneAmount(2), 6);
		assertEquals(p.getStoneAmount(3), 1);
	}
	
	@Test
	void testTotalCountIsCorrect() {
		System.out.println("testTotalCountIsCorrect");
		assertEquals(p.getTotalCount(), pitCount * stoneCount);
		for(int i = 0; i < pitCount; i++) {
			p.seed(i);
			assertEquals(p.getTotalCount(), pitCount * stoneCount);
		}
	}
	
	@Test
	void testCheckingActionPossibilityIsCorrect() {
		System.out.println("testCheckingActionPossibilityIsCorrect");
		for(int i = 0; i < pitCount; i++) {
			assertTrue(p.canPerformAction());
			p.loosePitStones(i);
		}
			assertFalse(p.canPerformAction());		
	}
	
	@Test
	void testLosingStonesIsCorrect() {
		System.out.println("testLosingStonesIsCorrect");
		for(int i = 0; i < pitCount; i++) {
			assertEquals(p.loosePitStones(i), stoneCount);
			assertTrue(p.isPitEmpty(i));
		}
	}
	
	@Test
	void testCapturingStonesIsCorrect() {
		System.out.println("testLosingStonesIsCorrect");
		int capturedAmount = 3;
		for(int i = 0; i < pitCount; i++) {
			int initAmount = p.getStoneAmount(i);
			p.capturePitStones(i, capturedAmount);
			assertEquals(p.getStoneAmount(i), initAmount + capturedAmount);
		}
	}
	
	@AfterEach
    public void tearDown() {
		System.out.println("clean up");
        p = null;
    }
}
