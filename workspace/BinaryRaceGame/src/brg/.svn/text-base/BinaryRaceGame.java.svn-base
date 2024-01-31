package brg;

import java.util.Vector;

/**
 * @author Mike Harris (meh) & Scott Palmer (srp) 
 * 
 * "Binary Race Game" model 
 */
public class BinaryRaceGame {
	
	// game parameters  
	static final int MIN_PLAYERS 		= 2;
	static final int MAX_PLAYERS 		= 4; 
	static final int GOAL				= 25;
	static final int MAX_LEVEL_1		= 16;
	static final int MAX_LEVEL_2		= 8;
	
	// player identifiers
	static final int BLUE_PLAYER 	= 0; 
	static final int RED_PLAYER		= 1; 
	static final int YELLOW_PLAYER 	= 2; 
	static final int GREEN_PLAYER	= 3; 

	// initial player positions
	static final int BLUE_INIT_POS		= 1;
	static final int RED_INIT_POS		= 9;
	static final int YELLOW_INIT_POS	= 13;
	static final int GREEN_INIT_POS		= 5;
	
	// players
	Player[] players = new Player[MAX_PLAYERS];

	BinaryRaceGame() {
		players[BLUE_PLAYER]	= new Player("Blue", BLUE_INIT_POS);
		players[RED_PLAYER]		= new Player("Red", RED_INIT_POS);
		players[YELLOW_PLAYER]	= new Player("Yellow", YELLOW_INIT_POS);
		players[GREEN_PLAYER]	= new Player("Green", GREEN_INIT_POS);
	}
	
	/**
	 * Applies a "move number" to a specific pawn 
	 * 
	 * @param l 	Player number
	 * @param p 	pawn
	 * @param mn 	move number
	 * @param ep	end position
	 * @return		if a valid move
	 */
	boolean move(int l, int p, int mn, int ep) {
		boolean moveRequiresTurn;
		int currentLevel;
		int modifiedPos = 0;

		currentLevel = (players[l].getPawnPos(p) <= MAX_LEVEL_1 ? 1 : 2);

		// normalize pawn positions
		if (currentLevel == 1) {
			switch (l) {
			case BLUE_PLAYER:
				modifiedPos = players[l].getPawnPos(p);
				break;
			case RED_PLAYER:
				modifiedPos = players[l].getPawnPos(p) - 8;
				break;
			case YELLOW_PLAYER:
				modifiedPos = players[l].getPawnPos(p) - 12;
				break;
			case GREEN_PLAYER:
				modifiedPos = players[l].getPawnPos(p) - 4;
				break;
			}
			if (modifiedPos < 0)
				modifiedPos = modifiedPos + MAX_LEVEL_1;
		}
		else {
			switch (l) {
			case BLUE_PLAYER:
				modifiedPos = players[l].getPawnPos(p) - 16 ;
				break;
			case RED_PLAYER:
				modifiedPos = players[l].getPawnPos(p) - 20;
				break;
			case YELLOW_PLAYER:
				modifiedPos = players[l].getPawnPos(p) - 18;
				break;
			case GREEN_PLAYER:
				modifiedPos = players[l].getPawnPos(p) - 22;
				break;
			}
		if (modifiedPos < 0)
			modifiedPos = modifiedPos + MAX_LEVEL_2;
		}
		
		// test if end position requires a turn-in 
		moveRequiresTurn = (ep >= 17 ? true : false); 
		
		// test if player has enough collisions to make turn (if required)
		if (players[l].getCollisions() == 0 && moveRequiresTurn)
			return false;
		
		// if conditions exist for a turn, compute the new position and 
		// decrement player's available collisions
		if (players[l].getCollisions() > 0 && moveRequiresTurn) {
			// ensures player is before turnInPos and has a large enough moveNum
			if (currentLevel == 1 && 
					modifiedPos <= MAX_LEVEL_1 && 
					modifiedPos + mn > MAX_LEVEL_1)
				players[l].decCollisions();
			else if (currentLevel == 1 && 
					modifiedPos <= MAX_LEVEL_2 && 
					modifiedPos + mn > MAX_LEVEL_2)
				players[l].decCollisions();
			else
				return false;
		}
		
		// check for finishing pawn & winner case
		if (ep == GOAL) {
			players[l].finishPawn(p);
			players[l].evalWinner();
		}
		
		// check for new collisions, send collided piece home (if necessary) 
		Vector<Player> collisionOptions; 
		if (! (collisionOptions = evalCollision(l, p, ep)).isEmpty()) {
			// user selects a pawn to collide from collisionOptions
			// ...
			
			// ... 
			// players[l].incCollisions();

		}
			
		// update the moving pawn's position
		players[l].setPawnPos(p, ep);
		
		return true;
	}
	
	/**
	 * given an end position, construct a vector of possible collisions
	 *  
	 * @param player	player that is moving pawn
	 * @param pawn		pawn in move
	 * @param endPos	move number
	 */
	Vector<Player> evalCollision(int player, int pawn, int endPos) {
		
		Vector<Player> collisionOptions = new Vector<Player>();
		
		for (int l = 0; l < MAX_PLAYERS; l++) {
			for (int p = 0; p < Player.PAWNS_EACH; p++) {
				
				// skip the moving player's own pawns
				if (l == player)
					break;
				else
					if (endPos == players[l].getPawnPos(p)) {
						if (! collisionOptions.contains(players[l]))
								collisionOptions.add(players[l]);
					}
				
			}
		}
		
		return collisionOptions;
	}
	
	/**
	 * @author meh & srp
	 */
	class Player {
		// player static properties
		static final int PAWNS_EACH	= 4;
		
		// player properties 
		String name; 
		int homePos; 
		int[] pawns;
		int numPawns;
		int collisions;

		/*
		 * @param n		the player's name (i.e., color)
		 * @param h		the player's home position
		 */
		Player(String n, int h) {
			// initialize player properties 
			name = n;
			homePos = h;
			pawns = new int[PAWNS_EACH];
			numPawns = pawns.length;
			collisions = 0;
			
			for (int i = 0; i < numPawns; i++){
				pawns[i] = homePos;
			}			
		}

		// ACCESSORS 
		
		/**
		 * @return 		the player's name (i.e., color)
		 */
		String getName() { 
			return name; 
		}
		
		/**
		 * @return		the player's home position 
		 */
		int getHomePos() {
			return homePos; 
		}
		
		/**
		 * @return		a specific pawn position
		 * @param p		pawn id
		 */
		int getPawnPos(int p){
			return pawns[p];
		}
		
		/**
		 * @return		number of pawns in play
		 */
		int getNumPawns() {
			return numPawns;
		}
		
		/**
		 * @return		number of available collisions
		 */
		int getCollisions() { 
			return collisions; 
		}
		
		// MUTATORS 
		
		/**
		 * pawn location mutator
		 * @param id	referenced pawn
		 * @param pos 	pawn position
		 */
		void setPawnPos(int id, int pos) {
			pawns[id] = pos;
		}

		/**
		 * decrement the player's number of pawns in play
		 */
		void decPawn() {
			numPawns--;
		}
		
		/**
		 * increment the player's available collision count 
		 * (adds one to 'collisions' for every collision)
		 */
		void incCollisions() { 
			collisions++; 
		}
		
		/**
		 * decrement the player's available collision count 
		 * (removes one from 'collisions' for every turn-in)
		 */
		void decCollisions() {
			collisions--;
		}

		// OTHERS 
		
		/**
		 * updates player after pawn reaches goal 
		 * 		(decrement the number of pawns in play, 
		 * 		and set finished pawn's position to 0)
		 */
		void finishPawn(int id) {
			decPawn();
			setPawnPos(id, 0);
		}
		
		/**
		 * evaluates winning condition for player 
		 * @return		if player wins
		 */
		boolean evalWinner() {
			return (getNumPawns() == 0 ? true : false);			
		}
	}
}
