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

public class Match {
	final static int MATCHWIDTH = 5;
	final static int MATCHHEIGHT = 15;
	static private int numSelected;

	private boolean dropped, selected;
	private int xOrgin, yOrgin;

	Match(int xPoint, int yPoint) {
		setOrigin(xPoint, yPoint);
	}

	boolean contains(int xCoor, int yCoor) {
		return (xCoor >= xOrgin && xCoor <= xOrgin + MATCHWIDTH
				&& yCoor >= yOrgin && yCoor <= yOrgin + MATCHHEIGHT);
	}

	void draw(Graphics panel) {
		// Draw a match's outline.

		panel.setColor(Color.black);
		panel.drawRect(xOrgin, yOrgin, MATCHWIDTH, MATCHHEIGHT);

		// Fill a match's interior with white pixels (if selected is false).
		// Otherwise, fill the interior with cyan pixels.

		panel.setColor((!selected) ? Color.white : Color.cyan);
		panel.fillRect(xOrgin + 1, yOrgin + 1, MATCHWIDTH - 1, MATCHHEIGHT - 1);

		// Fill the top of the match (outline and interior) with red pixels.

		panel.setColor(Color.red);
		panel.fillRect(xOrgin, yOrgin, MATCHWIDTH + 1, 5);
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

	boolean isSelected() {
		return selected;
	}

	void setDropped(boolean dropped) {
		this.dropped = dropped;
	}

	void setOrigin(int xPoint, int yPoint) {
		xOrgin = xPoint;
		yOrgin = yPoint;
	}

	void setSelected(boolean selected) {
		if (!this.selected && selected == true)
			numSelected++;

		if (this.selected && selected == false)
			numSelected--;

		this.selected = selected;
	}

	static int getNumSelected() {
		return numSelected;
	}
}
