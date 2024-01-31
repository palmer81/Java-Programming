// GuiNim.java
// initial source code from Jeff Friesen's 6/21/2004 article
// Java Tech: An Intelligent Nim Computer Game, Part 2
// http://today.java.net/pub/a/today/2004/06/21/nim2.html
// Modified to use bring model code out to better support MVC ideal
// also to use resource subdirectories, nim package, ant build file
// M. Wainer, Jan 2007, Aug 2009
package nim;
import javax.swing.*;

public class GuiNim extends JFrame {
   public GuiNim (String title) {
      // Display title in frame window's title bar.
	   super (title);
	   setDefaultCloseOperation (EXIT_ON_CLOSE);
	   getContentPane ().add (new NimPanel (this));
	   pack (); // Pack all components to their preferred sizes.
	   setResizable (false);
	   // Display frame window and all contained components.
	   setVisible (true);
   }

   
   public static void main(String[] args) {
		// Schedule App's GUI create & show for event-dispatching thread
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GuiNim ("Gui-based Nim");
			}
		});
	}
}

