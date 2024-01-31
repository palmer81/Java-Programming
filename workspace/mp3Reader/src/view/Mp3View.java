/**
 * 
 */
package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * @author scott
 *
 */
public class Mp3View {
	public JFrame mp3window = new JFrame("Mp3 Modifier");
	public JTextField textInput;
	public JLabel textLabel;
	public Container main;
	public final int outerPanel = 0,innerPanel = 1,comp = 2,compLeft = 3;
	public Mp3View() {
		main = mp3window.getContentPane();
		JPanel notePanel = new JPanel(new MigLayout(
			migLayoutSetting(outerPanel)));
		JLabel note = new JLabel("Enter the ID3 version to be tested");
		notePanel.add(note, migLayoutSetting(comp));
		
		main.setLayout(new MigLayout(migLayoutSetting(outerPanel)));
		main.add(notePanel, migLayoutSetting(comp));
		textInput = new JTextField("", 6);
		main.add(textInput, migLayoutSetting(comp));
		
		mp3window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mp3window.setLocation((dim.width-200)/2, (dim.height-200)/2);
		mp3window.setSize(500, 200);
		mp3window.setResizable(true);
		mp3window.setVisible(true);
	}
	
	/**
	 * Aids in adjusting the view 
	 * @param type
	 * 	The of view component.
	 * @return
	 * 	The string parameter.
	 */
	public String migLayoutSetting(int type) {
		String setting;
		
		switch (type) {
		case outerPanel:
			setting = "align center, flowy, wmax 960, wmin 415, insets 0";
			break;
		case innerPanel:
			setting = "align center, flowx, insets 1";
			break;
		case comp:
			setting = "align center, pad 0";
			break;
		case compLeft:
			setting = "align left, pad 0";
			break;	
		default:
			setting = null;
		}
		
		return setting;
	}
}
