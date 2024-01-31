package test;

import static org.junit.Assert.*;
import org.junit.Test; 

public class RaceGameTest {
	public static final int NO_PLAYER = 0; 
	public static final int PLAY_A_INIT_POS = 0;
	public static final int PLAY_B_INIT_POS = 14;
	
	@Test
	public void testInitPlayerPos() {
		RaceGameModel modelTest = new RaceGameModel();

		int playAPos = modelTest.getPlayerPos(RaceGameModel.PLAYER_A);
		assertEquals("Player A initial pos", PLAY_A_INIT_POS, playAPos);
		int playBPos = modelTest.getPlayerPos(RaceGameModel.PLAYER_B);
		assertEquals("Player B initial pos", PLAY_B_INIT_POS, playBPos);
	}
	
	public void testInitTurnID() {
		RaceGameModel modelTest = new RaceGameModel();
		int turnId = modelTest.getTurnId();
		assertEquals("Initial turn ID", RaceGameModel.PLAYER_A, turnId);
	}
	
	public void testInitNumClicks() {
		RaceGameModel modelTest = new RaceGameModel();
		int numClicks = modelTest.getNumClicks(); 
		assertEquals("Initial numClicks", 0, numClicks);
	}
	
	public void testInitRoll() {
		RaceGameModel modelTest = new RaceGameModel();
		boolean roll = modelTest.getRoll(); 
		assertEquals("Initial roll", true, roll);
	}
	
	public void testInitWinner() {
		RaceGameModel modelTest = new RaceGameModel();
		int winner = modelTest.getWinner(); 
		assertEquals("Initial winner", NO_PLAYER, winner);
	}
}
