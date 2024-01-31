// Match.java
// initial source code from Jeff Friesen's 6/21/2004 article
// Java Tech: An Intelligent Nim Computer Game, Part 2
// http://today.java.net/pub/a/today/2004/06/21/nim2.html
// Modified to use bring model code out to better support MVC ideal
// also to use resource subdirectories, nim package, ant build file
// M. Wainer, Jan 2007
package nim;

import java.awt.Color;
import java.awt.Graphics;

public class Smile {
	final static int SMILEWIDTH = 20;
	final static int SMILEHEIGHT = 20;
	static private int numSelected;

	private boolean dropped, smileSelected;
	private int xOrgin, yOrgin;

	Smile(int xPoint, int yPoint) {
		setOrigin(xPoint, yPoint);
	}

	boolean contains(int xCoor, int yCoor) {
		return (xCoor >= xOrgin && xCoor <= xOrgin + SMILEWIDTH
				&& yCoor >= yOrgin && yCoor <= yOrgin + SMILEHEIGHT);
	}

	void drawSmiles(Graphics panel) {
		// Draw a match's outline.

		panel.setColor(Color.black);
		panel.drawOval(xOrgin, yOrgin, SMILEWIDTH, SMILEHEIGHT);
		
		// Fill a match's interior with white pixels (if selected is false).
		// Otherwise, fill the interior with cyan pixels.

		panel.setColor((!smileSelected) ? Color.yellow : Color.cyan);
		panel.fillOval(xOrgin + 1, yOrgin + 1, SMILEWIDTH - 1, SMILEHEIGHT - 1);

		// Fill the top of the match (outline and interior) with red pixel
		
		panel.setColor(Color.black);
		panel.drawLine(xOrgin + 7, yOrgin + 4, xOrgin + 7, yOrgin + 8);
		panel.drawLine(xOrgin + 13, yOrgin + 4, xOrgin + 13, yOrgin + 8);
		panel.drawArc(xOrgin + 3, yOrgin + 5, SMILEWIDTH - 6, SMILEHEIGHT - 10, 180, 180);

	}

	int getOriginX() {
		return xOrgin;
	}

	int getOriginY() {
		return yOrgin;
	}

	boolean isDropped() {
		return dropped;
	}

	boolean isSmileSelected() {
		return smileSelected;
	}

	void setDropped(boolean dropped) {
		this.dropped = dropped;
	}

	void setOrigin(int xPoint, int yPoint) {
		xOrgin = xPoint;
		yOrgin = yPoint;
	}

	void setSelected(boolean selected) {
		if (!this.smileSelected && selected == true)
			numSelected++;

		if (this.smileSelected && selected == false)
			numSelected--;

		this.smileSelected = selected;
	}

	static int getNumSelected() {
		return numSelected;
	}
}
