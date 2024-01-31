package cs435;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * A Java  example for CS435, M. Wainer
 * Sample java code: Match game starter, Aug 28, 2008:
 * Do not regard this code as a perfect example (it isn't)
 */

public class MatchGame implements ActionListener {
	JPanel p;
	JFrame f;
	String[][] matchList = { {"A.I", "436"}, {"Graphics", "485"},
			{"Algorithms", "455" }, {"Database", "430"}, {"Networking", "440"},
			{"HCI", "484"}, {"Software", "435" }};
	JButton[][] buttons;
	int i = 0;
	boolean flipping = true;
	int cardOne;
	int secIndex;


	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread
		//to create application and display its GUI
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MatchGame app = new MatchGame();
				app.makeGUI();
			}
		});
	}

	public void dealCards(JPanel panel) {
		buttons = new JButton[3][]; // array of buttons used to represent cards
		// should really shuffle cards
		for (int i= 0; i< 3*4; i++) { // initialize 3 rows with 4 columns each
			if (i%4 == 0) buttons[i/4] = new JButton[4];
			buttons[i/4][i%4] = new JButton("-Match-"); // show face down
			buttons[i/4][i%4].addActionListener(this);
			panel.add(buttons[i/4][i%4]);
		}
	}

	public void updateMatchList(String a, String b, boolean add) {
		String[][] courseList;
		int oldLen = matchList.length;

		if (add) { // add the new item to the list
			courseList = new String[oldLen+1][];
			courseList[0] = new String[2];
			courseList[0][0] = new String(a); // new first course
			courseList[0][1] = new String(b); // new first course num
			for (int item=1; item<= oldLen; item++) {
				courseList[item][0] = matchList[item-1][0];
				courseList[item][1] = matchList[item-1][1];
			}
			matchList = courseList;
		} else { // delete matching item
			courseList = new String[oldLen-1][];
			courseList[0] = new String[2];
			courseList[0][0] = new String(a); // new first course
			courseList[0][1] = new String(b); // new first course num
			for (int item=0; item<= oldLen; item++) {
				if (a != courseList[item][0]) { // no match so OK to copy over
					courseList[item][0] = matchList[item][0];
					courseList[item][1] = matchList[item][1];
				}
			}
			matchList = courseList;
		}
	}

	/**
	 * Creates the JFrame and its UI components.
	 */
	public  void makeGUI() {	
		JFrame frame = new JFrame("CS435F08 - Java Match Game Starter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = new JPanel(new GridLayout(3,4));
		p.setPreferredSize(new Dimension(500, 300));
		dealCards(p);    
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(p,BorderLayout.CENTER);

		// Display the window.
		frame.pack();
		frame.setSize(500, 500);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		int r,c;


		if (i<2) { //find the card clicked on and flip it over

			for (r=0; r< 3; r++) {
				for (c=0; c< 4; c++) {
					// if the card is not face down (showing "-Match-") don't flip it
					if ((e.getSource()== buttons[r][c]) && buttons[r][c].getText().equals("-Match-")){
						// flip the card face-up to show text from matchList
						// looks up text based upon indexes
						buttons[r][c].setText(matchList[(r*4+c)/2][(r*4+c)%2]);
						i++; // increment number of cards flipped
						if (i==1) cardOne = (r*4+c)/2; // save which pattern was shown first
						else secIndex = (r*4+c)/2; // save the pattern shown second
						return;
					}
				}
			}
		} else { // 2 cards already flipped, put all cards face down

			for (r=0; r< 3; r++) {
				for (c=0; c< 4; c++) {
					if (cardOne == secIndex) { // first and second cards flipped match
						if (!buttons[r][c].getText().equals("-Match-")) // don't change the face down cards
							buttons[r][c].setText("*******"); // once matched, show the removed pattern
					} else if ((!buttons[r][c].getText().equals("*******")) && (!buttons[r][c].getText().equals("-Match-"))) {
						buttons[r][c].setText("-Match-"); // if 2 face up cards didn't match, flip face down again
					}
				}
				i=0; // new turn, no cards flipped face up
			}
		}
	}
}
