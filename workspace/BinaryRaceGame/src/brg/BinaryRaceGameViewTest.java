package brg;

import static org.junit.Assert.*;
import org.junit.Test; 
import brg.BinaryRaceGame.Player;

public class BinaryRaceGameViewTest {
	static final int MAX_TESTED_PLAYERS 	= 2; 
	
	static final int bluePlayer = 0;
	static final int redPlayer = 1;
	static final int yellowPlayer = 2;
	static final int greenPlayer = 3; 
	
	// test references 
	BinaryRaceGame model;
	Player[] players; 

	void resetModel() { 
		model = new BinaryRaceGameModel(); 
		players = model.players;
	}
	
}