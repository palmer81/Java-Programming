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

public class Match
{
	final static int MATCHWIDTH = 5;
	final static int MATCHHEIGHT = 15;
	static private int nSelected;

	private boolean dropped, selected;
	private int ox, oy;

	Match (int ox, int oy) {
		setOrigin (ox, oy);
	}

	boolean contains (int x, int y) {
		return (x >= ox && x <= ox+MATCHWIDTH &&
				y >= oy && y <= oy+MATCHHEIGHT);
	}

	void draw (Graphics g) {
		// Draw a match's outline.

		g.setColor (Color.black);
		g.drawRect (ox, oy, MATCHWIDTH, MATCHHEIGHT);

		// Fill a match's interior with white pixels (if selected is false).
		// Otherwise, fill the interior with cyan pixels.

		g.setColor ((!selected) ? Color.white : Color.cyan);
		g.fillRect (ox+1, oy+1, MATCHWIDTH-1, MATCHHEIGHT-1);

		// Fill the top of the match (outline and interior) with red pixels.

		g.setColor (Color.red);
		g.fillRect (ox, oy, MATCHWIDTH+1, 5);
	}

	int getOriginX () {
		return ox;
	}

	int getOriginY () {
		return oy;
	}

	boolean isDropped () {
		return dropped;
	}

	boolean isSelected () {
		return selected;
	}

	void setDropped (boolean dropped) {
		this.dropped = dropped;
	}

	void setOrigin (int x, int y) {
		ox = x;
		oy = y;
	}

	void setSelected (boolean selected) {
		if (!this.selected && selected == true)
			nSelected++;

		if (this.selected && selected == false)
			nSelected--;

		this.selected = selected;
	}
	
	static int getNumSelected() {
		return nSelected;
	}
}
