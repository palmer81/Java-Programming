// NimPanel.java
// initial source code from Jeff Friesen's 6/21/2004 article
// Java Tech: An Intelligent Nim Computer Game, Part 2
// http://today.java.net/pub/a/today/2004/06/21/nim2.html
// Modified to use bring model code out to better support MVC ideal
// also to use resource subdirectories, nim package, ant build file
// M. Wainer, Jan 2007

package nim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class NimPanel extends JPanel  {
	// Total number of Match objects.
	private final static int NMATCHES = 11;

	// Array of references to NMATCHES Match objects.
	private Match [] matches;

	// Height and width of this panel component, extracted from preferred size.
	private int height, width;

	// Preferred size of this panel component, returns from getPreferredSize().
	private Dimension preferredSize = new Dimension (600, 300);

	// Drag origin. Used to create a relative displacement during dragging, so
	// that each selected onscreen match moves the same relative amount.
	private int dragx, dragy;

	// Index of previously selected onscreen match (when mouse button pressed).
	// Useful for deselecting that onscreen match.
	private int prev = -1;

	// Icon of logo image, and the image's width and height.
	private ImageIcon logo;
	private int logow, logoh;

	// Icon of match-pile image, and the image's width and height.
	private ImageIcon pile;
	private int pilew, pileh;

	// Method continueGame() displays a dialog box, which requires a reference
	// to the enclosing frame window.
	private JFrame enclosingFrame;

	// Icon of negative match-pile image.
	private ImageIcon pileNeg;

	// Animation delay (in milliseconds). This delay determines how quickly the
	// match-pile and match-pile negative images animate when either the human
	// player or computer player wins. The greater the value, the slower the
	// animation.
	private final static int ANIM_DELAY = 100;

	// Identify sound effect file.
	private final static File DROP_SOUND = new File ("sounds/drop.wav");

	NimModel model;

	public NimPanel (JFrame enclosingFrame) {
		//	Create game tree with an initial pile of NMATCHES matches. Player A
		// (the human player) goes first.
		model = new NimModel(NMATCHES);
		model.newGame();

		// Save enclosingFrame for later use in continueGame().
		this.enclosingFrame = enclosingFrame;
		buildGUI(); // buildGUI() - one time GUI initiailizations

	}

	private MouseMotionAdapter makeMouseMotionListener() {
		MouseMotionAdapter mma;
		mma = new MouseMotionAdapter () {
			public void mouseDragged (MouseEvent e)
			{
				// Return if no onscreen matches selected. (Cannot drag
				// what hasn't been selected.)
				if (Match.getNumSelected() == 0)
					return;

				// Get mouse pointer coordinates.
				int x = e.getX ();
				int y = e.getY ();

				// For each selected onscreen match, calculate its new
				// origin and determine if it lies completely on the
				// screen. If even one selected onscreen match is not
				// entirely onscreen, don't move any of them.
				for (int i = 0; i < NMATCHES; i++)
					if (matches [i].isSelected ()) {
						Match m = matches [i];
						int ox = m.getOriginX ();
						int oy = m.getOriginY ();

						if (ox+x-dragx < 0 || oy+y-dragy < 0 ||
								ox+x-dragx+Match.MATCHWIDTH >= width ||
								oy+y-dragy+Match.MATCHHEIGHT >= height)
							return;
					}

				// Move each selected onscreen match.
				for (int i = 0; i < NMATCHES; i++)
					if (matches [i].isSelected ()) {
						Match m = matches [i];
						int ox = m.getOriginX ();
						int oy = m.getOriginY ();

						matches [i].setOrigin (ox+x-dragx, oy+y-dragy);
					}

				// Establish new drag origin.
				dragx = x;
				dragy = y;

				// Reorder Matches so that selected Matches appear first.
				reorder ();

				// Redraw this component so that selected onscreen match is
				// highlighted.
				repaint ();
			}
		};
		return mma;
	}

	private MouseAdapter makeMouseListener() {
		MouseAdapter ma;
		ma = new MouseAdapter () {
			public void mousePressed (MouseEvent e)
			{
				// Get mouse pointer coordinates.

				int x = e.getX ();
				int y = e.getY ();

				// Establish new drag origin.

				dragx = x;
				dragy = y;

				// Search array of Match objects for first object whose
				// nondropped onscreen image lies below the mouse pointer
				// when mouse button pressed. (NOTE: The search begins with
				// Matches that may overlap other Matches.)

				for (int i = 0; i < NMATCHES; i++)
					if (matches [i].contains (x, y) &&
							!matches [i].isDropped ())
					{
						// If Match was previously selected, return. There
						// is no point in selecting the Match.
						if (matches [i].isSelected ())
							return;

						// Find out if Shift key pressed.
						int m = e.getModifiers ();
						if ((m & InputEvent.SHIFT_MASK) != 0) {
							// Shift pressed. If three Matches already
							// selected, deselect previous (i.e., most
							// recently selected) Match.
							if (Match.getNumSelected() == 3)
								matches [prev].setSelected (false);
						}
						else {
							// Shift not pressed. If there was a
							// previously selected Match, deselect it.
							if (prev != -1)
								matches [prev].setSelected (false);
						}

						// Select found Match and make it most recently
						// selected Match.
						matches [i].setSelected (true);
						prev = i;

						// Reorder Matches so that selected Matches appear
						// first.
						reorder ();

						// Redraw component so that selected onscreen match 
						// is highlighted.
						repaint ();

						// Terminate loop.
						break;
					}
			}

			public void mouseReleased (MouseEvent e) {
				// Find out if Shift key pressed. If so, don't deselect
				// onscreen matches.
				int m = e.getModifiers ();
				if ((m & InputEvent.SHIFT_MASK) != 0)
					return;

				// As long as at least one onscreen match was selected, the
				// potential exists to deselect.
				if (Match.getNumSelected() != 0) {
					// Find out if at least one selected onscreen match is
					// over drop target. If so, drop all onscreen matches.
					boolean droppable = false;

					for (int i = 0; i < NMATCHES; i++)
						if (matches [i].isSelected () &&
								matches [i].getOriginX () > width-pilew) {
							droppable = true;
							break;
						}

					if (!droppable)
						return;

					// Based on number of selected onscreen matches, move to
					// appropriate game tree node.
					// User has indicated a move - pass on to model

					model.playTurn(Match.getNumSelected());

					// Drop all selected onscreen matches.

					for (int i = 0; i < NMATCHES; i++)
						if (matches [i].isSelected ()) {
							matches [i].setSelected (false);
							matches [i].setDropped (true);
						}

					// Redraw component so that dropped onscreen matches do
					// not appear.
					repaint ();

					// Play drop sound (if available).
					playSound (DROP_SOUND);

					// If a leaf node is reached, the computer player has
					// won the game. Inform user and ask if user wants a new
					// game. If not, exit. Otherwise, reset game and return,
					// for a new game.
					if (model.gameOver()) {
						final ImageIcon oldPile = pile;

						ActionListener al;
						al = new ActionListener () {
							public void actionPerformed (ActionEvent e) {
								repaint ();

								if (pile == oldPile)
									pile = pileNeg;
								else
									pile = oldPile;
							}
						};

						Timer t = new Timer (ANIM_DELAY, al);
						t.start ();

						boolean continuePlay;
						continuePlay = continueGame ("Computer player " +
						"wins. Play again?");

						t.stop ();
						pile = oldPile;
						repaint ();

						if (!continuePlay)
							System.exit (0);

						resetGUIforNewGame ();
						model.newGame();
						return;
					}

					// Computer calculates the best move to make
					int takenMatches = model.takeBestMove();

					// Computer player takes the matches.
					for (int i = 0; i < NMATCHES; i++)
						if (!matches [i].isDropped ()) {
							matches [i].setDropped (true);
							takenMatches--;
							if (takenMatches == 0)
								break;
						}

					// Redraw component so that dropped onscreen matches do
					// not appear.
					repaint ();

					// If a leaf node is reached, the human player has won
					// the game. Inform user and ask if user wants a new
					// game. If not, exit. Otherwise, reset game and return,
					// for a new game.

					if (model.gameOver()) {
						final ImageIcon oldPile = pile;

						ActionListener al;
						al = new ActionListener () {
							public void actionPerformed (ActionEvent e) {
								repaint ();

								if (pile == oldPile)
									pile = pileNeg;
								else
									pile = oldPile;
							}
						};

						Timer t = new Timer (ANIM_DELAY, al);
						t.start ();

						boolean continuePlay;
						continuePlay = continueGame ("Human player wins. " +
								"Play again?");

						t.stop ();
						pile = oldPile;
						repaint ();

						if (!continuePlay)
							System.exit (0);

						resetGUIforNewGame ();
						model.newGame();
						return;
					}
				}
			}

		};
		return ma;
	}

	public void buildGUI() {
		// Load logo image and obtain its width and height. (Should use error
		// checking in case logo.gif is not available or cannot be read.)
		logo = new ImageIcon ("images/logo.gif");
		logow = logo.getImage ().getWidth (this);
		logoh = logo.getImage ().getHeight (this);

		// Load pile image and obtain its width and height. (Should use error
		// checking in case pile.gif is not available or cannot be read.)
		pile = new ImageIcon ("images/pile.gif");
		pilew = pile.getImage ().getWidth (this);
		pileh = pile.getImage ().getHeight (this);

		// Create a negative of this image.
		int [] pixels = new int [pilew*pileh];

		java.awt.image.PixelGrabber pg;
		pg = new java.awt.image.PixelGrabber (pile.getImage (), 0, 0, pilew,
				pileh, pixels, 0, pilew);

		try {
			pg.grabPixels ();
		}
		catch (InterruptedException e) {
		}

		for (int i = 0; i < pixels.length; i++)
			pixels [i] = pixels [i] ^ 0xffffff;

		java.awt.image.MemoryImageSource mis;
		mis = new java.awt.image.MemoryImageSource (pilew, pileh, pixels, 0,
				pilew);
		pileNeg = new ImageIcon (createImage (mis));

		// Obtain the size of this component. The entire component is used as a
		// drawing surface.
		width = preferredSize.width;
		height = preferredSize.height;

		// Create array of Matches. Position onscreen matches in a 4-row by 4-
		// column grid. Grid locates in center area of component -- below the
		// logo image.
		matches = new Match [NMATCHES];
		for (int i = 0; i < NMATCHES; i++)
			matches [i] = new Match ((width-(Match.MATCHWIDTH*4+15))/2+
					i%4*(Match.MATCHWIDTH+5),
					logoh+60
					+i/4*(Match.MATCHHEIGHT+5));

		// Attach a mouse listener to the component. That listener listens for
		// mouse button pressed and mouse button released events.
		MouseAdapter ma = makeMouseListener();
		addMouseListener(ma);

		// Attach a mouse motion listener to the component. That listener
		// listens for mouse dragged events.
		MouseMotionAdapter mma = makeMouseMotionListener();
		addMouseMotionListener (mma);
	}


	private boolean continueGame (String message) {
		if (JOptionPane.showConfirmDialog (enclosingFrame,
				message,
				"GuiNim",
				JOptionPane.YES_NO_OPTION) ==
					JOptionPane.YES_OPTION)
			return true;
		else
			return false;
	}

	public Dimension getPreferredSize () {
		return preferredSize;
	}

	public void paintComponent (Graphics g) {
		super.paintComponent (g);

		// Clear blackground to white.
		g.setColor (Color.white);
		g.fillRect (0, 0, getWidth (), getHeight ());

		// Establish text color and font.
		g.setColor (Color.black);
		g.setFont (new Font ("Arial", Font.BOLD, 14));

		// Get font metrics for centering labels.
		FontMetrics fm = g.getFontMetrics ();

		// Draw centered labels.
		String s = "Computer Player's Pile";
		g.drawString (s, (pilew-fm.stringWidth (s))/2, 30);

		s = "Human Player's Pile";
		g.drawString (s, getWidth ()-pilew+(pilew-fm.stringWidth (s))/2, 30);

		// Draw match-pile images.
		g.drawImage (pile.getImage (), 0, 50, this);
		g.drawImage (pile.getImage (), width-pilew, 50, this);

		// Draw logo image.
		g.drawImage (logo.getImage (), (width-logow)/2, 50, this);

		// Draw all non-dropped matches.
		for (int i = NMATCHES-1; i >= 0; i--)
			if (!matches [i].isDropped ())
				matches [i].draw (g);
	}

	private void playSound (File file) {
		try {
			// Get an AudioInputStream from the specified file (which must be a
			// valid audio file, otherwise an UnsupportedAudioFileException
			// object is thrown).
			AudioInputStream ais = AudioSystem.getAudioInputStream (file);

			// Get the AudioFormat for the sound data in the AudioInputStream.
			AudioFormat af = ais.getFormat ();

			// Create a DataLine.Info object that describes the format for data
			// to be output over a Line.
			DataLine.Info dli = new DataLine.Info (SourceDataLine.class, af);

			// Do any installed Mixers support the Line? If not, we cannot play
			// a sound file.
			if (AudioSystem.isLineSupported (dli)) {
				// Obtain matching Line as a SourceDataLine (a Line to which
				// sound data may be written).
				SourceDataLine sdl = (SourceDataLine) AudioSystem.getLine (dli);

				// Acquire system resources and make the SourceDataLine
				// operational.
				sdl.open (af);

				// Initiate output over the SourceDataLine.
				sdl.start ();

				// Size and create buffer for holding bytes read and written.
				int frameSize = af.getFrameSize ();
				int bufferLenInFrames = sdl.getBufferSize () / 8;
				int bufferLenInBytes = bufferLenInFrames * frameSize;
				byte [] buffer = new byte [bufferLenInBytes];

				// Read data from the AudioInputStream into the buffer and then
				// copy that buffer's contents to the SourceDataLine.
				int numBytesRead;
				while ((numBytesRead = ais.read (buffer)) != -1)
					sdl.write (buffer, 0, numBytesRead);
			}
		}
		catch (LineUnavailableException e) {
		}
		catch (UnsupportedAudioFileException e) {
		}
		catch (IOException e) {
		}
	}

	private void reorder () {
		// Compute indexes of selected Matches.
		int [] indexes = { -1, -1, -1 };
		int j = 0;

		for (int i = 0; i < NMATCHES; i++)
			if (matches [i].isSelected ())
				indexes [j++] = i;

		// Shuffle Matches array so that selected Match objects appear at the
		// beginning.
		Match temp = matches [0];
		matches [0] = matches [indexes [0]];
		matches [indexes [0]] = temp;

		if (indexes [1] != -1) {
			temp = matches [1];
			matches [1] = matches [indexes [1]];
			matches [indexes [1]] = temp;
		}

		if (indexes [2] != -1) {
			temp = matches [2];
			matches [2] = matches [indexes [2]];
			matches [indexes [2]] = temp;
		}

		// Update prev to account for shuffling.
		if (prev == indexes [0])
			prev = 0;
		else
			if (prev == indexes [1])
				prev = 1;
			else
				prev = 2;
	}

	private void resetGUIforNewGame () {
		for (int i = 0; i < NMATCHES; i++)
		{
			if (matches [i].isSelected ())
				matches [i].setSelected (false);

			if (matches [i].isDropped ())
				matches [i].setDropped (false);

			matches [i].setOrigin ((width-(Match.MATCHWIDTH*4+15))/2+
					i%4*(Match.MATCHWIDTH+5),
					logoh+60
					+i/4*(Match.MATCHHEIGHT+5));
		}

		repaint ();
	}
}



