package SimplexMethod;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;

/**
 * @author Scott Palmer<br>
 * <strong>Descripiton:</strong><br>
 * This is the view for the simplex method.
 */
public class SimplexView extends SimplexMethod {
	private JFrame window = new JFrame("Simplex Method");
	private JTextField dimensionInput;
	private Container main;
	private JCheckBox unresVar[];
	private JComboBox bound;
	private ArrayList<JComboBox> inequalities = new ArrayList<JComboBox>();
	public JPanel subFuncPanel, unresVarPanel;
	private ArrayList<JPanel> funcPanel = new ArrayList<JPanel>();
	private ArrayList<JTextField[]> coefficients = 
		new ArrayList<JTextField[]>();
	private int numOfFunc = 0;
	private JScrollPane subFuncScroll;
	private ArrayList<Boolean> setFunctions = new ArrayList<Boolean>();
	private final int outerPanel = 0,innerPanel = 1,comp = 2,compLeft = 3;
	private JEditorPane results = new JEditorPane("text/html",null);
	
	/**
	 * Constructor for creating the main viewing frame
	 * It adds the starting panels.  The size and window attributes is set by
	 * initView() called by the function in SimplexMethod.java
	 */
	public SimplexView() {
		main = window.getContentPane();
		
		JPanel notePanel = new JPanel(new MigLayout(
			migLayoutSetting(outerPanel)));
		JLabel note = new JLabel("Enter the Dimension of");
		notePanel.add(note, migLayoutSetting(comp));
		note = new JLabel("the desired Model");
		notePanel.add(note, migLayoutSetting(comp));
		notePanel.add(new JLabel(" "), migLayoutSetting(comp));
		
		JPanel inputPanel = createRegionPanel();
		JPanel inputButton = createEnterButton();
		JScrollPane scrolled = new JScrollPane(inputPanel);
		
		main.setLayout(new MigLayout(migLayoutSetting(outerPanel)));
		main.add(notePanel, migLayoutSetting(comp));
		main.add(scrolled, migLayoutSetting(comp));
		main.add(inputButton, migLayoutSetting(comp));
		
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((dim.width-200)/2, (dim.height-200)/2);
		window.setSize(200, 200);
		window.setResizable(true);
		window.setVisible(true);
	}
	
	/**
	 * Returns the values for the coefficients in the view.
	 * @param ref
	 * 	The function to be returned.
	 * @return
	 * 	The values of the coefficients.
	 */
	public JTextField[] getCoefficients(int ref) {		
		return coefficients.get(ref);
	}
	
	/**
	 * Returns the checkboxes for the unresticted variables.
	 * @return
	 * 	The checkboxes for the unresticted variables.
	 */
	public JCheckBox[] getUnresVar() {
		return unresVar;
	}
	
	/**
	 * Returns the comboboxes for the inqualities for each function.
	 * @return
	 * 	The comboboxes for the inqualities for each function.
	 */
	public ArrayList<JComboBox> getInequalities() {
		return inequalities;
	}
	
	/**
	 * Returns the choosen value for the boundary in the view.
	 * @return
	 * 	The choosen value for the boundary in the view.
	 */
	public JComboBox getBound() {
		return bound;
	}
	
	/**
	 * Returns the entered dimension.
	 * @return
	 * 	The entered dimension.
	 */
	public JTextField getDimensionInput() {
		return dimensionInput;
	}
	
	
	/**
	 * Sets the view to begin the input of the functions for the SM
	 * @param dim
	 * 	The dimension of the model.
	 */
	public void setInputFuncView(int dim) {
		
		int count = main.getComponentCount();
		for(int i = 0; i < count; i++)
			main.remove(0);
		
		JPanel notePanel = new JPanel(new MigLayout(
			migLayoutSetting(outerPanel)));
		JLabel note = new JLabel("<html><strong>Note: </strong><html>"
				+"Decimals are not accepted as a valid input.");
		notePanel.add(note, migLayoutSetting(comp));
		note = new JLabel("Fractions must be used if not a whole number.");
		notePanel.add(note, migLayoutSetting(comp));
		notePanel.add(new JLabel(" "), migLayoutSetting(comp));
		
		unresVarPanel = new JPanel(new MigLayout(migLayoutSetting(outerPanel)));
		unresVarPanel.add(createUnresPanel(dim), migLayoutSetting(comp));
		unresVarPanel.setBorder(BorderFactory.createTitledBorder(
			"Unrestricted Varibles"));
		JScrollPane unresVarScroll = new JScrollPane(unresVarPanel);
		unresVarScroll.getVerticalScrollBar().setUnitIncrement(20);
		unresVarScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.
			VERTICAL_SCROLLBAR_ALWAYS);
		unresVarScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.
			HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel objFuncPanel = new JPanel(new MigLayout(
			migLayoutSetting(outerPanel)));
		bound = new JComboBox();
		bound.addItem("Minimize");
		bound.addItem("Maximize");
		bound.setEditable(false);
		objFuncPanel.add(bound, migLayoutSetting(compLeft));
		objFuncPanel.add(createFuncPanel(dim, "obj"), migLayoutSetting(comp));
		objFuncPanel.setBorder(BorderFactory.createTitledBorder(
			"Objective Function"));
		
		subFuncPanel = new JPanel(new MigLayout(migLayoutSetting(outerPanel)));
		subFuncPanel.add(createFuncPanel(dim, "st"), migLayoutSetting(comp));
		subFuncPanel.setBorder(BorderFactory.createTitledBorder("Subject to"));
		
		subFuncScroll = new JScrollPane(subFuncPanel);
		subFuncScroll.getVerticalScrollBar().setUnitIncrement(20);
		subFuncScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.
			VERTICAL_SCROLLBAR_ALWAYS);
		subFuncScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.
			HORIZONTAL_SCROLLBAR_NEVER);
		if (getSimplexModel().getDimension() <= 3) {
			setView(485,550);
			subFuncScroll.setPreferredSize(new Dimension(480, 150));
		}
		else if (getSimplexModel().getDimension() <= 9) {
			setView(435+63*getSimplexModel().getDimension(),550);
			subFuncScroll.setPreferredSize(new Dimension(420+63*
				getSimplexModel().getDimension(), 150));
		}
		else {
			setView(450+63*9,550);
			subFuncScroll.setPreferredSize(new Dimension(420+63*9, 180));
		}
		
		JButton runSimplexButton = new JButton("Run Simplex Method");
		runSimplexButton.addActionListener(new runButtonLisnter());
		
		main.add(notePanel, migLayoutSetting(comp));
		main.add(unresVarScroll, migLayoutSetting(comp));
		main.add(objFuncPanel, migLayoutSetting(comp));
		main.add(subFuncScroll, migLayoutSetting(comp));
		main.add(subButtonPanel(), migLayoutSetting(comp));
		main.add(runSimplexButton, migLayoutSetting(comp));
	}
	
	/**
	 * sets the frame to size and centers in the screen
	 * @param xSize
	 * 	The width.
	 * @param ySize
	 * 	The hieght.
	 */
	public void setView(int xSize, int ySize) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((dim.width-xSize)/2, (dim.height-ySize)/2);
		window.setSize(xSize, ySize);
	}
	
	/**
	 * Creates the text and input field for the starting view
	 * @return
	 * 	The region panel.
	 */
	public JPanel createRegionPanel() {
		JPanel inputPanel = new JPanel(new MigLayout(
			migLayoutSetting(innerPanel)));
		JLabel dimensionText = new JLabel("Dimension: ");
		inputPanel.add(dimensionText);
		dimensionInput = new JTextField("", 3);
		inputPanel.add(dimensionInput);
		
		return inputPanel;
	}
	
	/**
	 * Creates the Enter button for the beginning view
	 * @return
	 * 	The enter button panel.
	 */
	public JPanel createEnterButton() {
		JPanel inputButton = new JPanel(new MigLayout(
			migLayoutSetting(innerPanel)));
		JButton enter = new JButton("Enter");
		
		inputButton.add(enter);
		enter.addActionListener(new enterButtonLisnter());
		
		return inputButton;
	}
	
	/**
	 * Creates the check box panel for selecting unrestricted variables 
	 * @param dim
	 * 	The current dimension
	 * @return
	 * 	The unrestricted panel.
	 */
	public JPanel createUnresPanel(int dim) {
		JPanel unrestrictedVarPanel = new JPanel(new MigLayout(
			migLayoutSetting(outerPanel)));
		unresVar = new JCheckBox[dim];
		for (int i = 0; i < unresVar.length; i++) {
			String label = "<html><i>x</i><sub>"+(i+1)+"</sub></html>";
			unresVar[i] = new JCheckBox(label);
			unresVar[i].addActionListener(new unresVarListener());
		}
		
		for (int i = 0; i < unresVar.length;) {
			JPanel tmpPanel = new JPanel(new MigLayout(
				migLayoutSetting(innerPanel)));
			for (int j = 0; j <= 17 && i < unresVar.length; j++, i++) 
				tmpPanel.add(unresVar[i], "align center");
			unrestrictedVarPanel.add(tmpPanel, migLayoutSetting(comp));
		}
		
		return unrestrictedVarPanel;
	}

	/**
	 * Creates either the objective function panel or new subject function panel 
	 * @param dim
	 * 	The current dimension.
	 * @param type
	 * 	If new or old function
	 * @return
	 * 	The created function panel.
	 */
	public JPanel createFuncPanel(int dim, String type) {
		JPanel currentFunc = new JPanel(new MigLayout(
			migLayoutSetting(innerPanel)));
		JPanel tempPanel = createFuncInput(dim, "new", numOfFunc, type);
		JScrollPane funcScroll = createFuncSrcoll(tempPanel, type);
		currentFunc.add(funcScroll, migLayoutSetting(comp));
		String setLabel = "<html>Set</html>" + numOfFunc;
		JButton setButton = new JButton(setLabel);
		setButton.addActionListener(new setButtonLisnter());
		currentFunc.add(setButton);
		funcPanel.add(currentFunc);
		numOfFunc++;
		setFunctions.add(false);
		
		return currentFunc;
	}
	
	/**
	 * Creates a functions coefficient input panel
	 * @param dim
	 * 	The current dimension.
	 * @param type
	 * 	If new or old panel.
	 * @param ref
	 * 	Which function.
	 * @return
	 * 	The created input function panel
	 */
	public JPanel createFuncInput(int dim, String type, int ref, 
			String panel) {
		JPanel tmpPanel = new JPanel(new MigLayout(
			migLayoutSetting(innerPanel)));
		JTextField funcInput[] = new JTextField[dim + 1];
		Fraction[] val = getSimplexModel().getFuncValues(ref);
		JLabel funcLabel;
		tmpPanel.setBackground(Color.white);
		
		for (int i = 0, num = 1; i <= dim; i++,num++) {
			funcInput[i] = new JTextField("", 2);
			funcInput[i].setText(val[i].toVal());
			String label = "<html><i>x</i><sub>"+num+"</sub></html>";
			funcLabel = new JLabel(label);
			
			tmpPanel.add(funcInput[i], migLayoutSetting(comp));
			if (num <= dim)
				tmpPanel.add(funcLabel, migLayoutSetting(comp));
			if (num <= dim - 1) {
				funcLabel = new JLabel(" + ");
				tmpPanel.add(funcLabel, migLayoutSetting(comp));
			}
			if (num == dim){
				funcLabel = new JLabel(" = ");
				if (panel.equals("st")) {
					JComboBox inequality = new JComboBox();
					inequality.addItem("<");
					inequality.addItem("<=");
					inequality.addItem("=");
					inequality.addItem(">=");
					inequality.addItem(">");
					inequality.setEditable(true);
					inequality.setSelectedItem("=");
					inequalities.add(inequality);
					inequality.setEditable(false);
					tmpPanel.add(inequalities.get(ref-1),
						migLayoutSetting(comp));
				}
				else
					tmpPanel.add(funcLabel, migLayoutSetting(comp));
			}
		}
		if (type.equals("new")) {
			coefficients.add(funcInput);
		}
		else {
			coefficients.remove(ref);
			coefficients.add(ref, funcInput);
		}
		
		if (ref == 0) {
			funcLabel = new JLabel(" + ");
			tmpPanel.add(funcLabel, migLayoutSetting(comp));
			String label = "<html><i>z</i><sub>0</sub></html>";
			funcLabel = new JLabel(label);
			tmpPanel.add(funcLabel, migLayoutSetting(comp));
		}
		return tmpPanel;
	}
	
	/**
	 * creates a scroll panel for a function
	 * @param funcPanel
	 * 	If objective or subject panel.
	 * @return
	 */
	public JScrollPane createFuncSrcoll(JPanel funcPanel, String type) {
		JScrollPane funcScroll = new JScrollPane(funcPanel);
		funcScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.
			VERTICAL_SCROLLBAR_NEVER);
		funcScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.
			HORIZONTAL_SCROLLBAR_ALWAYS);
		funcScroll.getHorizontalScrollBar().setUnitIncrement(50);
		if (type.equals("obj") && getSimplexModel().getDimension() >= 10) {
			funcScroll.setPreferredSize(new Dimension(340+63*getSimplexModel().
				getDimension(), 50));
		}
		else if (getSimplexModel().getDimension() >= 11 
				|| (getSimplexModel().getDimension() >= 10 && numOfFunc >= 4)) {
			funcScroll.setPreferredSize(new Dimension(340+63*getSimplexModel().
				getDimension(), 50));
		}
		
		return funcScroll;
	}
	
	/**
	 * Creates the button panel for the Subject Function panel
	 * @return
	 * 	The button panel for the second view.
	 */
	public JPanel subButtonPanel() {
		JPanel buttonPanel = new JPanel(new MigLayout(
			migLayoutSetting(innerPanel)));
		JButton addSubButton = new JButton("Add new Subject Function");
		addSubButton.addActionListener(new addFuncButtonLisnter());
		JButton removeSubButton = new JButton("Remove a Subject Function");
		removeSubButton.addActionListener(new removeFuncButtonLisnter());
		
		buttonPanel.add(addSubButton, migLayoutSetting(comp));
		buttonPanel.add(removeSubButton, migLayoutSetting(comp));
		
		return buttonPanel;
	}
	
	/**
	 * sets a function to text mode
	 * @param ref
	 * 	The funtion to set text.
	 */
	public void setFuncToText(int ref) {
		JPanel currentPanel = funcPanel.get(ref);
		String type = new String();
		if (ref == 0)
			type = "obj";
		else
			type = "st";
		int num = currentPanel.getComponentCount();
		for (int i = 0; i < num; i++)
			currentPanel.remove(0);
		
		Fraction[] j = getSimplexModel().getFuncValues(ref);
		JPanel tempPanel = new JPanel(new MigLayout(
			migLayoutSetting(innerPanel)));
		tempPanel.setBackground(Color.white);
		String s = "<html>";
		for (int i = 0; i < j.length; i++) {
			s = s+j[i].toString();
			if (i < j.length - 1) {
				s = s+"<i>x</i><sub>"+(i+1)+"</sub>";
				if (i < j.length - 2)
					s = s+" + ";
				else {
					String inequality = null;
					if (type.equals("st"))
						inequality = (String) inequalities.get(ref-1).
							getSelectedItem();
					if (type.equals("st") && inequality.equals("<"))
						s = s+" &#60; ";
					else if (type.equals("st") && inequality.equals("<="))
						s = s+" &#60;= ";
					else if (type.equals("st") && inequality.equals(">="))
						s = s+" &#62;= ";
					else if (type.equals("st") && inequality.equals("="))
						s = s+" = ";
					else if (type.equals("st") && inequality.equals(">"))
						s = s+" > ";
					else
						s = s+" = ";
				}
			}
			if (ref == 0 && i == j.length -1) 
				s = s+" + <i>z</i><sub>0</sub>";
		}
		s = s+"</html>";
		JLabel func = new JLabel(s);
		tempPanel.add(func);
		
		JScrollPane funcScroll = createFuncSrcoll(tempPanel, type);
		currentPanel.add(funcScroll, migLayoutSetting(comp));
		
		s = "<html>Edit</html>" + ref;
		JButton editButton = new JButton(s);
		editButton.addActionListener(new editButtonLisnter());
		currentPanel.add(editButton);
		
		currentPanel.repaint();
		currentPanel.validate();
		
		funcPanel.remove(ref);
		funcPanel.add(ref, currentPanel);
		setFunctions.set(ref, true);
		
		refreshSubPanel();
	}
	
	/**
	 * Set a function to edit mode
	 * @param ref
	 * 	The function to set to edit mode.
	 */
	public void setFuncToEdit(int ref) {
		JPanel currentPanel = funcPanel.get(ref);
		String type = new String();
		if (ref == 0)
			type = "obj";
		else
			type = "st";
		int num = currentPanel.getComponentCount();
		for (int i = 0; i < num; i++)
			currentPanel.remove(0);
		
		JPanel tempPanel = createFuncInput(getSimplexModel().getDimension(), 
			"old", ref, type);
		
		JScrollPane funcScroll = createFuncSrcoll(tempPanel, type);
		currentPanel.add(funcScroll, migLayoutSetting(comp));
		
		String s = "<html>Set</html>" + ref;
		JButton setButton = new JButton(s);
		setButton.addActionListener(new setButtonLisnter());
		currentPanel.add(setButton);
		
		currentPanel.repaint();
		currentPanel.validate();
	
		funcPanel.remove(ref);
		funcPanel.add(ref, currentPanel);
		setFunctions.set(ref, false);
		
		refreshSubPanel();
	}
	
	/**
	 * Sets all functions to text.
	 */
	public void setAllFunc() {
		for (int i = 0; i < setFunctions.size(); i++)
			if (!setFunctions.get(i))
				setFuncToText(i);
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

	
	/**
	 * Prompts the user to remove a subjective function
	 * @return
	 * 	User entered value.
	 */
	public int removeUserPrompt() {
		if (funcPanel.size()-1 == 1) {
			JOptionPane.showMessageDialog(main, 
				"You need to add a function frist.");
			return -1;
		}
			
		int ref = -1;
		do {
			String num = JOptionPane.showInputDialog(
					"What Function do you want to remove? (1-"+
					(funcPanel.size()-1)+")");
			if (num == null)
				return -1;
			try {
				ref = Integer.parseInt(num);
			}
			catch (Exception e){}
		} while (ref <= 0 || ref >= funcPanel.size());
		
		return ref;
	}
	
	/**
	 * Prompts the user to select a method
	 * @return
	 * 	User entered value.
	 */
	public String selectMethod() {
		String method;
		do {
			method = JOptionPane.showInputDialog(
					"What Method would you like to use?\n(Method of Fastest " +
					"Decent: MFD, Method Left to Right: MLR, Method Right to " +
					"Left: MRL or Most Negitive Coefficient: MNC)");
			if (method == null)
				return null;
			method = method.trim();
			method = method.toLowerCase();
		} while (!method.equals("mfd") && !method.equals("mlr") 
				&& !method.equals("mrl") && !method.equals("mnc"));
		
		return method;
	}

	/**
	 * Remove a subjective from the view
	 * @param ref
	 * 	The function to be removed.
	 */
	public void removeSubFunc(int ref) {
		funcPanel.remove(ref);
		inequalities.remove(ref-1);
		
		for(int num = subFuncPanel.getComponentCount(), i = 0; i < num; i++)
			subFuncPanel.remove(0);
		
		subFuncPanel.repaint();
		subFuncPanel.validate();
		
		for (int i = 1; i < funcPanel.size(); i++) {
			setFuncToEdit(i);
			subFuncPanel.add(funcPanel.get(i), migLayoutSetting(comp));
		}
		
		refreshSubPanel();
		
		numOfFunc--;
		setFunctions.remove(ref);
	}

	/**
	 * Adds a subjective function to the view
	 */
	public void addSubFunc() {
		createFuncPanel(getSimplexModel().getDimension(), "st");
		
		for(int num = subFuncPanel.getComponentCount(), i = 0; i < num; i++)
			subFuncPanel.remove(0);
		
		for (int i = 1; i < funcPanel.size(); i++)
			subFuncPanel.add(funcPanel.get(i), migLayoutSetting(comp));
		
		refreshSubPanel();
	}
	
	/**
	 * refreshes the subject functions
	 */
	public void refreshSubPanel() {
		subFuncPanel.repaint();
		subFuncPanel.validate();
		
		subFuncScroll.repaint();
		subFuncScroll.validate();
	}
	
	/**
	 * Sets the view to the results
	 * @param s
	 * 	The string to add to the result view.
	 */
	public void setViewResults(String s) {
		results.setText(s);
		int count = main.getComponentCount();
		for(int i = 0; i < count; i++)
			main.remove(0);
		main.repaint();
		main.validate();
		setView(720, 650);
		JScrollPane scroll = new JScrollPane(results);
		scroll.setPreferredSize(new Dimension(690, 570));
		main.add(scroll, migLayoutSetting(comp));
		JButton startOver = new JButton("Start Over");
		startOver.addActionListener(new startOverButtonLisnter());
		main.add(startOver, migLayoutSetting(comp));
		main.repaint();
		main.validate();
	}
	
	/**
	 * Resets results.
	 */
	public void resetResults() {
		results = new JEditorPane("text/html",null);
		results.repaint();
		results.validate();
	}
}