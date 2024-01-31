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

public class NimPanel extends JPanel {
	/**
	 * Serial value for version UID
	 */
	private static final long serialVersionUID = -7356249547099794782L;

	// Total number of Match objects.
	private final static int NUM_MATCHES = 15;

	// Array of references to NUM_MATCHES Match objects.
	private Smile[] matches;

	// Height and width of this panel component, extracted from preferred size.
	private int height, width;

	// Preferred size of this panel component, returns from getPreferredSize().
	private Dimension preferredSize = new Dimension(600, 300);

	// Drag origin. Used to create a relative displacement during dragging, so
	// that each selected on-screen match moves the same relative amount.
	private int dragx, dragy;

	// Index of previously selected on-screen match (when mouse button pressed).
	// Useful for deselecting that on-screen match.
	private int prev = -1;

	// Icon of logo image, and the image's width and height.
	private ImageIcon logo;
	private int logoWidth, logoHeight;

	// Icon of match-pile image, and the image's width and height.
	private ImageIcon pile;
	private int pileWidth, pileHeight;

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
	private final static File DROP_SOUND = new File("sounds/drop.wav");

	NimModel nimModel;

	public NimPanel(JFrame enclosingFrame) {
		// Create game tree with an initial pile of NMATCHES matches. Player A
		// (the human player) goes first.
		nimModel = new NimModel(NUM_MATCHES);
		nimModel.newGame();

		// Save enclosingFrame for later use in continueGame().
		this.enclosingFrame = enclosingFrame;
		buildGUI(); // buildGUI() - one time GUI initiailizations

	}

	private MouseMotionAdapter makeMouseMotionListener() {
		MouseMotionAdapter mouseMotionAdpater;
		mouseMotionAdpater = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent mouseEvent) {
				// Return if no on-screen matches selected. (Cannot drag
				// what hasn't been selected.)
				if (Smile.getNumSelected() == 0)
					return;

				// Get mouse pointer coordinates.
				int xCoordinate = mouseEvent.getX();
				int yCoordinate = mouseEvent.getY();

				// For each selected on-screen match, calculate its new
				// origin and determine if it lies completely on the
				// screen. If even one selected on-screen match is not
				// entirely on-screen, don't move any of them.
				for (int i = 0; i < NUM_MATCHES; i++)
					if (matches[i].isSmileSelected()) {
						Smile m = matches[i];
						int ox = m.getOriginX();
						int oy = m.getOriginY();

						if (ox + xCoordinate - dragx < 0
								|| oy + yCoordinate - dragy < 0
								|| ox + xCoordinate - dragx + Smile.SMILEWIDTH >= width
								|| oy + yCoordinate - dragy + Smile.SMILEHEIGHT >= height)
							return;
					}

				// Move each selected on-screen match.
				for (int i = 0; i < NUM_MATCHES; i++)
					if (matches[i].isSmileSelected()) {
						Smile m = matches[i];
						int xOrgin = m.getOriginX();
						int yOrgin = m.getOriginY();

						matches[i].setOrigin(xOrgin + xCoordinate - dragx, yOrgin
								+ yCoordinate - dragy);
					}

				// Establish new drag origin.
				dragx = xCoordinate;
				dragy = yCoordinate;

				// Reorder Matches so that selected Matches appear first.
				reorder();

				// Redraw this component so that selected on-screen match is
				// highlighted.
				repaint();
			}
		};
		return mouseMotionAdpater;
	}

	private MouseAdapter makeMouseListener() {
		MouseAdapter motionAdpater;
		motionAdpater = new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				// Get mouse pointer coordinates.

				int xPoint = mouseEvent.getX();
				int yPoint = mouseEvent.getY();

				// Establish new drag origin.

				dragx = xPoint;
				dragy = yPoint;

				// Search array of Match objects for first object whose
				// nondropped on-screen image lies below the mouse pointer
				// when mouse button pressed. (NOTE: The search begins with
				// Matches that may overlap other Matches.)

				for (int i = 0; i < NUM_MATCHES; i++)
					if (matches[i].contains(xPoint, yPoint) && !matches[i].isDropped()) {
						// If Match was previously selected, return. There
						// is no point in selecting the Match.
						if (matches[i].isSmileSelected())
							return;

						// Find out if Shift key pressed.
						int shiftModifer = mouseEvent.getModifiers();
						if ((shiftModifer & InputEvent.SHIFT_MASK) != 0) {
							// Shift pressed. If three Matches already
							// selected, deselect previous (i.e., most
							// recently selected) Match.
							if (Smile.getNumSelected() == 3)
								matches[prev].setSelected(false);
						} else {
							// Shift not pressed. If there was a
							// previously selected Match, deselect it.
							if (prev != -1)
								matches[prev].setSelected(false);
						}

						// Select found Match and make it most recently
						// selected Match.
						matches[i].setSelected(true);
						prev = i;

						// Reorder Matches so that selected Matches appear
						// first.
						reorder();

						// Redraw component so that selected on-screen match
						// is highlighted.
						repaint();

						// Terminate loop.
						break;
					}
			}

			public void mouseReleased(MouseEvent mouseEvent) {
				// Find out if Shift key pressed. If so, don't deselect
				// on-screen matches.
				int shiftModifer = mouseEvent.getModifiers();
				if ((shiftModifer & InputEvent.SHIFT_MASK) != 0)
					return;

				// As long as at least one on-screen match was selected, the
				// potential exists to deselect.
				if (Smile.getNumSelected() != 0) {
					// Find out if at least one selected on-screen match is
					// over drop target. If so, drop all on-screen matches.
					boolean droppable = false;

					for (int i = 0; i < NUM_MATCHES; i++)
						if (matches[i].isSmileSelected()
								&& matches[i].getOriginX() > width - pileWidth) {
							droppable = true;
							break;
						}

					if (!droppable)
						return;

					// Based on number of selected on-screen matches, move to
					// appropriate game tree node.
					// User has indicated a move - pass on to model

					nimModel.playTurn(Smile.getNumSelected());

					// Drop all selected on-screen matches.

					for (int i = 0; i < NUM_MATCHES; i++)
						if (matches[i].isSmileSelected()) {
							matches[i].setSelected(false);
							matches[i].setDropped(true);
						}

					// Redraw component so that dropped on-screen matches do
					// not appear.
					repaint();

					// Play drop sound (if available).
					playSound(DROP_SOUND);

					// If a leaf node is reached, the computer player has
					// won the game. Inform user and ask if user wants a new
					// game. If not, exit. Otherwise, reset game and return,
					// for a new game.
					if (nimModel.gameOver()) {
						final ImageIcon oldPile = pile;

						ActionListener actionListener;
						actionListener = new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								repaint();

								if (pile == oldPile)
									pile = pileNeg;
								else
									pile = oldPile;
							}
						};

						Timer timer = new Timer(ANIM_DELAY, actionListener);
						timer.start();

						boolean continuePlay;
						continuePlay = continueGame("Computer player "
								+ "wins. Play again?");

						timer.stop();
						pile = oldPile;
						repaint();

						if (!continuePlay)
							System.exit(0);

						resetGUIforNewGame();
						nimModel.newGame();
						return;
					}

					// Computer calculates the best move to make
					int takenMatches = nimModel.takeBestMove();

					// Computer player takes the matches.
					for (int i = 0; i < NUM_MATCHES; i++)
						if (!matches[i].isDropped()) {
							matches[i].setDropped(true);
							takenMatches--;
							if (takenMatches == 0)
								break;
						}

					// Redraw component so that dropped on-screen matches do
					// not appear.
					repaint();

					// If a leaf node is reached, the human player has won
					// the game. Inform user and ask if user wants a new
					// game. If not, exit. Otherwise, reset game and return,
					// for a new game.

					if (nimModel.gameOver()) {
						final ImageIcon oldPile = pile;

						ActionListener actionListener;
						actionListener = new ActionListener() {
							public void actionPerformed(ActionEvent event) {
								repaint();

								if (pile == oldPile)
									pile = pileNeg;
								else
									pile = oldPile;
							}
						};

						Timer timer = new Timer(ANIM_DELAY, actionListener);
						timer.start();

						boolean continuePlay;
						continuePlay = continueGame("Human player wins. "
								+ "Play again?");

						timer.stop();
						pile = oldPile;
						repaint();

						if (!continuePlay)
							System.exit(0);

						resetGUIforNewGame();
						nimModel.newGame();
						return;
					}
				}
			}

		};
		return motionAdpater;
	}

	public void buildGUI() {
		// Load logo image and obtain its width and height. (Should use error
		// checking in case logo.gif is not available or cannot be read.)
		logo = new ImageIcon("images/logo.gif");
		logoWidth = logo.getImage().getWidth(this);
		logoHeight = logo.getImage().getHeight(this);

		// Load pile image and obtain its width and height. (Should use error
		// checking in case pile.gif is not available or cannot be read.)
		pile = new ImageIcon("images/pile.gif");
		pileWidth = pile.getImage().getWidth(this);
		pileHeight = pile.getImage().getHeight(this);

		// Create a negative of this image.
		int[] pixels = new int[pileWidth * pileHeight];

		java.awt.image.PixelGrabber pixelGrabber;
		pixelGrabber = new java.awt.image.PixelGrabber(pile.getImage(), 0, 0, pileWidth,
				pileHeight, pixels, 0, pileWidth);

		try {
			pixelGrabber.grabPixels();
		} catch (InterruptedException exception) {
		}

		for (int i = 0; i < pixels.length; i++)
			pixels[i] = pixels[i] ^ 0xffffff;

		java.awt.image.MemoryImageSource imageSource;
		imageSource = new java.awt.image.MemoryImageSource(pileWidth, pileHeight,
				pixels, 0, pileWidth);
		pileNeg = new ImageIcon(createImage(imageSource));

		// Obtain the size of this component. The entire component is used as a
		// drawing surface.
		width = preferredSize.width;
		height = preferredSize.height;

		// Create array of Matches. Position on-screen matches in a 4-row by 4-
		// column grid. Grid locates in center area of component -- below the
		// logo image.
		matches = new Smile[NUM_MATCHES];
		for (int i = 0; i < NUM_MATCHES; i++)
			matches[i] = new Smile((width - (Smile.SMILEWIDTH * 4 + 15)) / 2
					+ i % 4 * (Smile.SMILEWIDTH + 5), logoHeight + 60 + i / 4
					* (Smile.SMILEHEIGHT + 5));

		// Attach a mouse listener to the component. That listener listens for
		// mouse button pressed and mouse button released events.
		MouseAdapter mouseListener = makeMouseListener();
		addMouseListener(mouseListener);

		// Attach a mouse motion listener to the component. That listener
		// listens for mouse dragged events.
		MouseMotionAdapter mouseMotoinListener = makeMouseMotionListener();
		addMouseMotionListener(mouseMotoinListener);
	}

	private boolean continueGame(String message) {
		if (JOptionPane.showConfirmDialog(enclosingFrame, message, "GuiNim",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			return true;
		else
			return false;
	}

	public Dimension getPreferredSize() {
		return preferredSize;
	}

	public void paintComponent(Graphics panel) {
		super.paintComponent(panel);

		// Clear background to white.
		panel.setColor(Color.white);
		panel.fillRect(0, 0, getWidth(), getHeight());

		// Establish text color and font.
		panel.setColor(Color.black);
		panel.setFont(new Font("Arial", Font.BOLD, 14));

		// Get font metrics for centering labels.
		FontMetrics fontMetrics = panel.getFontMetrics();

		// Draw centered labels.
		String labels = "Computer Player's Pile";
		panel.drawString(labels, (pileWidth - fontMetrics.stringWidth(labels)) / 2, 30);

		labels = "Human Player's Pile";
		panel.drawString(labels, getWidth() - pileWidth
				+ (pileWidth - fontMetrics.stringWidth(labels)) / 2, 30);

		// Draw match-pile images.
		panel.drawImage(pile.getImage(), 0, 50, this);
		panel.drawImage(pile.getImage(), width - pileWidth, 50, this);

		// Draw logo image.
		panel.drawImage(logo.getImage(), (width - logoWidth) / 2, 50, this);

		// Draw all non-dropped matches.
		for (int i = NUM_MATCHES - 1; i >= 0; i--)
			if (!matches[i].isDropped())
				matches[i].drawSmiles(panel);
	}

	private void playSound(File file) {
		try {
			// Get an AudioInputStream from the specified file (which must be a
			// valid audio file, otherwise an UnsupportedAudioFileException
			// object is thrown).
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

			// Get the AudioFormat for the sound data in the AudioInputStream.
			AudioFormat audioFormat = audioInputStream.getFormat();

			// Create a DataLine.Info object that describes the format for data
			// to be output over a Line.
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

			// Do any installed Mixers support the Line? If not, we cannot play
			// a sound file.
			if (AudioSystem.isLineSupported(dataLineInfo)) {
				// Obtain matching Line as a SourceDataLine (a Line to which
				// sound data may be written).
				SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

				// Acquire system resources and make the SourceDataLine
				// operational.
				sourceDataLine.open(audioFormat);

				// Initiate output over the SourceDataLine.
				sourceDataLine.start();

				// Size and create buffer for holding bytes read and written.
				int frameSize = audioFormat.getFrameSize();
				int bufferLenInFrames = sourceDataLine.getBufferSize() / 8;
				int bufferLenInBytes = bufferLenInFrames * frameSize;
				byte[] buffer = new byte[bufferLenInBytes];

				// Read data from the AudioInputStream into the buffer and then
				// copy that buffer's contents to the SourceDataLine.
				int numBytesRead;
				while ((numBytesRead = audioInputStream.read(buffer)) != -1)
					sourceDataLine.write(buffer, 0, numBytesRead);
			}
		} catch (LineUnavailableException lineException) {
		} catch (UnsupportedAudioFileException audioException) {
		} catch (IOException ioException) {
		}
	}

	private void reorder() {
		// Compute indexes of selected Matches.
		int[] indexes = { -1, -1, -1 };
		int j = 0;

		for (int i = 0; i < NUM_MATCHES; i++)
			if (matches[i].isSmileSelected())
				indexes[j++] = i;

		// Shuffle Matches array so that selected Match objects appear at the
		// beginning.
		Smile temp = matches[0];
		matches[0] = matches[indexes[0]];
		matches[indexes[0]] = temp;

		if (indexes[1] != -1) {
			temp = matches[1];
			matches[1] = matches[indexes[1]];
			matches[indexes[1]] = temp;
		}

		if (indexes[2] != -1) {
			temp = matches[2];
			matches[2] = matches[indexes[2]];
			matches[indexes[2]] = temp;
		}

		// Update prev to account for shuffling.
		if (prev == indexes[0])
			prev = 0;
		else if (prev == indexes[1])
			prev = 1;
		else
			prev = 2;
	}

	private void resetGUIforNewGame() {
		for (int i = 0; i < NUM_MATCHES; i++) {
			if (matches[i].isSmileSelected())
				matches[i].setSelected(false);

			if (matches[i].isDropped())
				matches[i].setDropped(false);

			matches[i].setOrigin((width - (Smile.SMILEWIDTH * 4 + 15)) / 2 + i
					% 4 * (Smile.SMILEWIDTH + 5), logoHeight + 60 + i / 4
					* (Smile.SMILEHEIGHT + 5));
		}

		repaint();
	}
}
