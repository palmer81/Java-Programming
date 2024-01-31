package test;

public class RaceGameModel {
	public static final int NO_PLAYER = 0; 
	public static final int PLAYER_A = 1;
	public static final int PLAYER_B = 2;
	
	public static final int PLAYER_A_INIT_POS = 0;
	public static final int PLAYER_B_INIT_POS = 14;

	private int playerAPos = PLAYER_A_INIT_POS;
	private int playerBPos = PLAYER_B_INIT_POS;
	private int turnId = PLAYER_A;
	private int numClicks = 0; 
	private boolean roll = true; 
	private int winner = NO_PLAYER; 
	
	public int getPlayerPos(int playerA) {
		if (playerA == PLAYER_A) 
			return playerAPos;
		else
			return playerBPos;
	}
	
	public int getTurnId() { 
		return turnId; 
	}
	
	public int getNumClicks() { 
		return numClicks; 
	}
	
	public boolean getRoll() { 
		return roll; 
	}
	
	public int getWinner() { 
		return winner; 
	}
}
