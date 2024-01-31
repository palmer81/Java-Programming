// NimModel.java
// initial source code from Jeff Friesen's 6/21/2004 article
// Java Tech: An Intelligent Nim Computer Game, Part 2
// http://today.java.net/pub/a/today/2004/06/21/nim2.html
// Modified to use bring model code out to better support MVC ideal
// also to use resource subdirectories, nim package, ant build file
// M. Wainer, Jan 2007

package nim;

public class NimModel {
	public static final char PLAYER_A = 'A';
	public static final char PLAYER_B = 'B';
	
	// Game tree root node reference. Modified during game play; reset if user
	// chooses to play again.
	private Node tempRoot;
	private Node gameTreeRoot;
	private int numMatches;

	public NimModel(int nmatches) {
		gameTreeRoot = buildGameTree (nmatches, PLAYER_A);
		numMatches = nmatches;
		newGame();
	}
	
	public boolean gameOver() {
		return (tempRoot.nmatches == 0);
	}

	public int getNumMatches() {
		return numMatches;
	}

	public void playTurn(int matchesToTake) {
		switch (matchesToTake)
		{
		case 1: tempRoot = tempRoot.left;
		break;

		case 2: tempRoot = tempRoot.center;
		break;

		case 3: tempRoot = tempRoot.right;
		}

	}

	public void newGame() {
		tempRoot = gameTreeRoot;
	}

	public Node buildGameTree (int nmatches, char player) {
		Node n = new Node ();
		n.nmatches = nmatches;
		n.player = player;

		if (nmatches >= 1)
			n.left = buildGameTree (nmatches-1, (player == PLAYER_A) ? PLAYER_B : PLAYER_A);
		if (nmatches >= 2)
			n.center = buildGameTree (nmatches-2, (player == PLAYER_A) ? PLAYER_B : PLAYER_A);
		if (nmatches >= 3)
			n.right = buildGameTree (nmatches-3, (player == PLAYER_A) ? PLAYER_B : PLAYER_A);

		return n;
	}

	public int computeMinimax (Node n) {
		int ans;

		if (n.nmatches == 0)
			return (n.player == PLAYER_A) ? 1 : -1;
		else
			if (n.player == PLAYER_A) {
				ans = Math.max (-1, computeMinimax (n.left));
				if (n.center != null) {
					ans = Math.max (ans, computeMinimax (n.center));
					if (n.right != null)
						ans = Math.max (ans, computeMinimax (n.right));
				}
			}
			else {
				ans = Math.min (1, computeMinimax (n.left));
				if (n.center != null) {
					ans = Math.min (ans, computeMinimax (n.center));
					if (n.right != null)
						ans = Math.min (ans, computeMinimax (n.right));
				}
			}

		return ans;
	}
	
	public int takeBestMove() {
		// Use the minimax algorithm to determine if the
		// computer player's optimal move is the child node left
		// of the current root node, the child node below the
		// current root node, or the child node right of the
		// current root node.

		int v1 = computeMinimax (tempRoot.left);
		int v2 = (tempRoot.center != null)
		? computeMinimax (tempRoot.center) : 2;
		int v3 = (tempRoot.right != null) ?
				computeMinimax (tempRoot.right) : 2;

				int takenMatches;
				if (v1 < v2 && v1 < v3) {
					takenMatches = 1;
					tempRoot = tempRoot.left;
				}
				else
					if (v2 < v1 && v2 < v3) {
						takenMatches = 2;
						tempRoot = tempRoot.center;
					}
					else
						if (v3 < v1 && v3 < v2) {
							takenMatches = 3;
							tempRoot = tempRoot.right;
						}
						else {
							takenMatches = (int) (Math.random () * 3) + 1;
							switch (takenMatches)
							{
							case 1: tempRoot = tempRoot.left;
							break;

							case 2: tempRoot = tempRoot.center;
							break;

							case 3: tempRoot = tempRoot.right;
							}
						}
		return takenMatches;
	}
}

