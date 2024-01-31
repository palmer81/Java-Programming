//file: DriveThrough.java
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class NoteProducer {
	public static final int FOUR_BASS = 0, FIVE_BASS = 1, 
							SIX_GUITAR = 2;
	public static int stringVar, fretVar, guitarRef[][], bassRef[][],
						firstTime, currentGuitar;
	public static Random randomNum;
	public static ButtonGroup GROUP_GUITAR;
	final static ButtonGroup noteRadioButtons[] = new ButtonGroup[7];
	final static JFrame FRAME =  new JFrame("Note Checker");
	static JLabel stringNum, fretNum;
	static JPanel notePromptPanel,guitarChoicePanel,
					stringLabelPanel,noteChoicePanel, submitPanel;
	
	
	public static void main(String[] args) {
		randomNum = new Random();
		currentGuitar = SIX_GUITAR;
		initAnswers();
		createGUI();
	}
	
	public static void createGUI() {
		notePromptPanel = setNotePrompt();
		guitarChoicePanel = createGuitarChoice();
		stringLabelPanel = createStringLabel();
		noteChoicePanel = createNoteChoice();
		submitPanel = createSubmit();

		Container notes = FRAME.getContentPane();
		notes.setLayout(new MigLayout("align center, flowy, insets 1"));
		notes.add(notePromptPanel,"align center");
		notes.add(guitarChoicePanel,"align center");
		notes.add(noteChoicePanel,"align center");
		notes.add(stringLabelPanel,"align center");
		notes.add(submitPanel,"align center");

		FRAME.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		FRAME.setLocation(250, 200);
		FRAME.setSize(700, 320);
		FRAME.setResizable(false);
		FRAME.setVisible(true);
	}
	
	public static JPanel setNotePrompt() {
		stringVar = randomNum.nextInt(6);
		fretVar = randomNum.nextInt(20);

		String cord = Integer.toString(stringVar + 1);
		String fret = Integer.toString(fretVar);
		
		JPanel chosenNote = new JPanel(new MigLayout("align center, flowx, insets 0"));
		stringNum = new JLabel("String Number: " + cord + " ");
		chosenNote.add(stringNum);
		fretNum = new JLabel("Fret Number: " + fret);
		chosenNote.add(fretNum);
		JSeparator separator = new JSeparator(1);
		chosenNote.add(separator);
		
		return chosenNote;
	}
	
	public static JPanel createGuitarChoice() {
		boolean fourBass = false, fiveBass = false, sixGuitar = true;
		
		JPanel guitarType = new JPanel(new MigLayout("align center, flowx, insets 0"));
		GROUP_GUITAR = new ButtonGroup();
		JRadioButton guitar;
		guitarType.add(guitar = new JRadioButton("4 String Bass ", fourBass), "align center");
		guitar.setActionCommand("0");
		GROUP_GUITAR.add(guitar);
		guitarType.add(guitar = new JRadioButton("5 String Bass ", fiveBass), "align center");
		guitar.setActionCommand("1");
		GROUP_GUITAR.add(guitar);
		guitarType.add(guitar = new JRadioButton("6 String Guitar", sixGuitar), "align center");
		guitar.setActionCommand("2");
		GROUP_GUITAR.add(guitar);
		
		return guitarType;
	}
	
	public static JPanel createStringLabel() {
		JPanel stringLabel = new JPanel(new MigLayout("align center, flowx, insets 0"));
		JLabel stringE = new JLabel("E String = 6 ");
		stringLabel.add(stringE, "align center");
		JLabel stringA = new JLabel("A String = 5 ");
		stringLabel.add(stringA, "align center");
		JLabel stringD = new JLabel("D String = 4 ");
		stringLabel.add(stringD, "align center");
		JLabel stringG = new JLabel("G String = 3 ");
		stringLabel.add(stringG, "align center");
		JLabel stringB = new JLabel("B String = 2 ");
		stringLabel.add(stringB, "align center");
		JLabel stringe = new JLabel("E String = 1");
		stringLabel.add(stringe, "align center");
		return stringLabel;
	}
	
	public static JPanel createNoteRadio(int ref, int num) {
		String s1 = "", s2 = "", s3 = "";
		if (ref == 0) {
			s1 = "11";
			s2 = "0";
			s3 = "1";
		}
		else {
			s1 = s1+(ref-1); 
			s2 = s2+ref;
			s3 = s3+(ref+1);
		}
		
		char note = 'A';
		for (int i = 0; i < num; i++)
			note++;
		JPanel panel = new JPanel(new MigLayout("align center, flowy, insets 0"));
		noteRadioButtons[num] = new ButtonGroup();
		JRadioButton aButton;
		panel.add(aButton = new JRadioButton(note+"\u266D"), "alignx left, aligny center");
		aButton.setActionCommand(s1);
		aButton.setFocusable(false);
		noteRadioButtons[num].add(aButton);
		panel.add(aButton = new JRadioButton(note+""), "alignx left, aligny center");
		aButton.setActionCommand(s2);
		aButton.setFocusable(false);
		noteRadioButtons[num].add(aButton);
		panel.add(aButton = new JRadioButton(note+"\u266F"), "alignx left, aligny center");
		aButton.setActionCommand(s3);
		aButton.setFocusable(false);
		noteRadioButtons[num].add(aButton);
		panel.add(aButton = new JRadioButton(""), "alignx left, aligny center");
		aButton.setActionCommand("12");
		aButton.setVisible(false);
		aButton.setSize(new Dimension(0,0));
		noteRadioButtons[num].add(aButton);
		return panel;
	}
	
	public static JPanel createNoteChoice() {
		JPanel allNotes = new JPanel(new MigLayout("align center, flowx, insets 0"));
		JPanel aPanel = createNoteRadio(0, 0);
		JPanel bPanel = createNoteRadio(2, 1);
		JPanel cPanel = createNoteRadio(3, 2);
		JPanel dPanel = createNoteRadio(5, 3);
		JPanel ePanel = createNoteRadio(7 ,4);
		JPanel fPanel = createNoteRadio(8, 5);
		JPanel gPanel = createNoteRadio(10, 6);
		
		allNotes.add(aPanel, "align center");
		allNotes.add(bPanel, "align center");
		allNotes.add(cPanel, "align center");
		allNotes.add(dPanel, "align center");
		allNotes.add(ePanel, "align center");
		allNotes.add(fPanel, "align center");
		allNotes.add(gPanel, "align center");
		
		return allNotes;
	}
	
	public static JPanel createSubmit() {
		JPanel submitPanel = new JPanel();
		JButton newNoteButton = new JButton("New Note");
		submitPanel.add(newNoteButton, "align center");
		JButton submitButton = new JButton("Submit");
		submitPanel.add(submitButton, "align center");
		JButton clearButton = new JButton("Clear Pick");
		submitPanel.add(clearButton, "align center");
		JButton exitButton = new JButton("Exit");
		submitPanel.add(exitButton, "align center");
		
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int guitarChoice = 0;
				
				try {
					String guitarReturn = GROUP_GUITAR.getSelection().getActionCommand();
					guitarChoice = Integer.parseInt(guitarReturn);
				}
				catch (Exception e) {
					System.out.println("REALLY BAD!!!");
				}
				checkAnswer();
				refresh(guitarChoice);
			}
		});
		
		newNoteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int guitarChoice = 2;
				try {
					String guitarReturn = GROUP_GUITAR.getSelection().getActionCommand();
					guitarChoice = Integer.parseInt(guitarReturn);
				}
				catch (Exception e) {
					System.out.println("REALLY BAD!!!");
				}
				
				refresh(guitarChoice);
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clear();
			}
		});
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FRAME.dispose();
				System.exit(0);
			}
		});
		
		return submitPanel;
	}
	
	public static void refresh(int guitarChoice) {
		if (guitarChoice == FOUR_BASS) 
			stringVar = randomNum.nextInt(4);
		else if (guitarChoice == FIVE_BASS) 
			stringVar = randomNum.nextInt(5);
		else if (guitarChoice == SIX_GUITAR) 
			stringVar = randomNum.nextInt(6);
		
		fretVar = randomNum.nextInt(20);
		
		String cord = Integer.toString(stringVar + 1);
		String fret = Integer.toString(fretVar);
		
		stringNum.setText("String Number: " + cord + " ");
		fretNum.setText("Fret Number: " + fret + " ");
		
		if (guitarChoice == FOUR_BASS && 
				currentGuitar != FOUR_BASS) {
			int num = stringLabelPanel.getComponentCount();
			for (int i = 0; i < num; i++)
				stringLabelPanel.remove(0);
			stringLabelPanel.repaint();
			stringLabelPanel.add(new JLabel("E String = 4 "), "align center");
			stringLabelPanel.add(new JLabel("A String = 3 "), "align center");
			stringLabelPanel.add(new JLabel("D String = 2 "), "align center");
			stringLabelPanel.add(new JLabel("G String = 1 "), "align center");
			currentGuitar = guitarChoice;
		}
		else if (guitarChoice == FIVE_BASS && 
				currentGuitar != FIVE_BASS) {
			int num = stringLabelPanel.getComponentCount();
			for (int i = 0; i < num; i++) 
				stringLabelPanel.remove(0);
			stringLabelPanel.repaint();
			stringLabelPanel.add(new JLabel("B String = 5 "), "align center");
			stringLabelPanel.add(new JLabel("E String = 4 "), "align center");
			stringLabelPanel.add(new JLabel("A String = 3 "), "align center");
			stringLabelPanel.add(new JLabel("D String = 2 "), "align center");
			stringLabelPanel.add(new JLabel("G String = 1 "), "align center");
			currentGuitar = guitarChoice;
		}
		else if (guitarChoice == SIX_GUITAR &&
				currentGuitar != SIX_GUITAR) {
			int num = stringLabelPanel.getComponentCount();
			for (int i = 0; i < num; i++) 
				stringLabelPanel.remove(0);
			stringLabelPanel.repaint();
			stringLabelPanel.add(new JLabel("E String = 6 "), "align center");
			stringLabelPanel.add(new JLabel("A String = 5 "), "align center");
			stringLabelPanel.add(new JLabel("D String = 4 "), "align center");
			stringLabelPanel.add(new JLabel("G String = 3 "), "align center");
			stringLabelPanel.add(new JLabel("B String = 2 "), "align center");
			stringLabelPanel.add(new JLabel("E String = 1 "), "align center");
			currentGuitar = guitarChoice;
		}
		
		clear();
		notePromptPanel.repaint();
		guitarChoicePanel.repaint();
		stringLabelPanel.repaint();
		noteChoicePanel.repaint();
	}
	
	public static void checkAnswer() {
		int results[] = {12, 12, 12, 12, 12, 12, 12};
		String entries[] = new String[7];
		for (int i = 0; i < 7; i++) {
			try {
				entries[i] = noteRadioButtons[i].getSelection().getActionCommand();
				results[i] = Integer.parseInt(entries[i]);
			}
			catch (Exception e) {
				results[i] = 12;
			}
		}
		
		int selection = 0;
		for (int result: results) 
			if (result != 12)
				selection++;
		if (selection == 0) {
			JOptionPane.showMessageDialog(null,
					"You have to make a selection!");
			return;
		}
		
		selection = 0;
		for (int result: results) { 
			if (currentGuitar == SIX_GUITAR) {
				if (result != 12 && result != guitarRef[stringVar][fretVar]) 
					selection++;
			}
			else
				if (result != 12 && result != bassRef[stringVar][fretVar]) 
					selection++;
		}
			
		if (currentGuitar == SIX_GUITAR) {
			if (selection != 0)
				switch (guitarRef[stringVar][fretVar]) {
				case 0:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is A");
					break;
				case 1:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is A\u266F and B\u266D");
					break;
				case 2:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is B and C\u266D");
					break;
				case 3:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is C and B\u266F");
					break;
				case 4:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is C\u266F and D\u266D");
					break;
				case 5:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is D");
					break;
				case 6:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is D\u266F and E\u266D");
					break;
				case 7:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is E and F\u266D");
					break;
				case 8:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is F and E\u266F");
					break;
				case 9:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is F\u266F and G\u266D");
					break;
				case 10:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is G");
					break;
				case 11:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is A\u266D and G\u266F");
					break;
				}
			else
				switch (guitarRef[stringVar][fretVar]) {
				case 0:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is A");
					break;
				case 1:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is A\u266F and B\u266D");
					break;
				case 2:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is B and C\u266D");
					break;
				case 3:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is C and B\u266F");
					break;
				case 4:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is C\u266F and D\u266D");
					break;
				case 5:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is D");
					break;
				case 6:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is D\u266F and E\u266D");
					break;
				case 7:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is E and F\u266D");
					break;
				case 8:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is F and E\u266F");
					break;
				case 9:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is F\u266F and G\u266D");
					break;
				case 10:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is G");
					break;
				case 11:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is A\u266D and G\u266F");
					break;
				}
		}
		else {
			if (selection != 0)
				switch (bassRef[stringVar][fretVar]) {
				case 0:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is A");
					break;
				case 1:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is A\u266F and B\u266D");
					break;
				case 2:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is B and C\u266D");
					break;
				case 3:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is C and B\u266F");
					break;
				case 4:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is C\u266F and D\u266D");
					break;
				case 5:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is D");
					break;
				case 6:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is D\u266F and E\u266D");
					break;
				case 7:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is E and F\u266D");
					break;
				case 8:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is F and E\u266F");
					break;
				case 9:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is F\u266F and G\u266D");
					break;
				case 10:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is G");
					break;
				case 11:
					JOptionPane.showMessageDialog(null, 
	                    "You are wrong the note is A\u266D and G\u266F");
					break;
				}
			else
				switch (bassRef[stringVar][fretVar]) {
				case 0:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is A");
					break;
				case 1:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is A\u266F and B\u266D");
					break;
				case 2:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is B and C\u266D");
					break;
				case 3:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is C and B\u266F");
					break;
				case 4:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is C\u266F and D\u266D");
					break;
				case 5:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is D");
					break;
				case 6:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is D\u266F and E\u266D");
					break;
				case 7:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is E and F\u266D");
					break;
				case 8:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is F and E\u266F");
					break;
				case 9:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is F\u266F and G\u266D");
					break;
				case 10:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is G");
					break;
				case 11:
					JOptionPane.showMessageDialog(null, 
	                    "You are right the note is A\u266D and G\u266F");
					break;
				}
		}
	}
	
	public static void initAnswers() {
		//base numbers for each string.
		//G flat is 11 as well as A sharp which is the highest number
		int A = 0, B = 2, D = 5, E = 7, G = 10;
		
		//Note reference is a [sting][fret] reference to a note
		//The 'for' loops are to set the value for each note
		guitarRef = new int[6][21];
		bassRef = new int[5][21];
		int intialNote = E;
		for (int i = 0; i < 21; i++) {
			int temp = intialNote + i;
			if (temp > 23)
				temp = temp - 24;
			if (temp > 11)
				temp = temp - 12;
			guitarRef[0][i] = temp;
			guitarRef[5][i] = temp;
			bassRef[3][i] = temp;
		}
		
		intialNote = A;
		for (int i = 0; i < 21; i++) {
			int temp = intialNote + i;
			if (temp > 23)
				temp = temp - 24;
			if (temp > 11)
				temp = temp - 12;
			guitarRef[4][i] = temp;
			bassRef[2][i] = temp;
		}
		
		intialNote = D;
		for (int i = 0; i < 21; i++) {
			int temp = intialNote + i;
			if (temp > 23)
				temp = temp - 24;
			if (temp > 11)
				temp = temp - 12;
			guitarRef[3][i] = temp;
			bassRef[1][i] = temp;
		}
		
		intialNote = G;
		for (int i = 0; i < 21; i++) {
			int temp = intialNote + i;
			if (temp > 23)
				temp = temp - 24;
			if (temp > 11)
				temp = temp - 12;
			guitarRef[2][i] = temp;
			bassRef[0][i] = temp;
		}
		
		intialNote = B;
		for (int i = 0; i < 21; i++) {
			int temp = intialNote + i;
			if (temp > 23)
				temp = temp - 24;
			if (temp > 11)
				temp = temp - 12;
			guitarRef[1][i] = temp;
			bassRef[4][i] = temp;
		}
	}
	
	public static void clear() {
		for (ButtonGroup radioButtons: noteRadioButtons) { 
			Enumeration<AbstractButton> button = radioButtons.getElements();
			button.nextElement().setSelected(false);
			button.nextElement().setSelected(false);
			button.nextElement().setSelected(false);
			button.nextElement().setSelected(true);
		}
		noteChoicePanel.repaint();
	}
}