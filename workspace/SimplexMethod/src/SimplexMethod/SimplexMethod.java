package SimplexMethod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JTextField;

/**
 * @author Scott Palmer<br>
 * This is the controller for the view and the model and runs the main function.
 * All the listeners for the view are located here as well as many helper 
 * functions for work between the view and model.
 */
public class SimplexMethod {
	private static SimplexView view;
	private SimplexModel sm;
	private String result = new String();
	
	/**
	 * The main method for the program.
	 * @param args
	 * 	The string values for the arguments in the command line.
	 */
	public static void main(String[] args) {
		view = new SimplexView();
	}
	
	/**
	 * Returns the current model.
	 * @return
	 * 	The current simplex model.
	 */
	public SimplexModel getSimplexModel() {
		return sm;
	}
	
	/**
	 * @author Scott Palmer
	 *	The listener for the enter button in first view.
	 */
	class enterButtonLisnter implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int dim = 0;
			try {
				dim = Integer.parseInt(view.getDimensionInput().getText());
				if (dim <= 1)
					return;
			}
			catch (Exception e){
				return;
			}
			sm = new SimplexModel(dim);
			view.setInputFuncView(dim);
		}
	}
	
	/**
	 * @author Scott Palmer
	 *	The listener for the set button for each function in the second view.
	 *	Sets the values for the given function to text.
	 */
	class setButtonLisnter implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String button = event.getActionCommand();
			String bNum = button.substring(button.lastIndexOf('>')+1);
			int ref = Integer.parseInt(bNum);
			Fraction temp[] = new Fraction[sm.getDimension()+1];
			for (int i = 0; i < temp.length; i++)
				temp[i] = new Fraction();
			JTextField[] j = view.getCoefficients(ref);
			for (int i = 0; i < j.length; i++) {
				 if (j[i].getText().contains("/")) {
					 try {
						 temp[i].numerator = Integer.parseInt(j[i].getText().
								 substring(0, j[i].getText().indexOf('/')));
						 temp[i].denominator = Integer.parseInt(j[i].getText().
								 substring(j[i].getText().indexOf('/')+1, j[i].
										 getText().length()));
						 temp[i].reduceFrac();
					 }
					 catch (Exception e){
							return;
					 }
				 }
				 else if(!j[i].getText().equals("")) {
					try {
						temp[i].numerator = Integer.parseInt(j[i].getText());
						temp[i].denominator = 1;
					}
					catch (Exception e){
						return;
					}
				 }
			}
			if (ref != 0)
				sm.setFunc(temp, ref,(String) view.getInequalities().
					get(ref-1).getSelectedItem());
			else
				sm.setFunc(temp, ref,"=");
			view.setFuncToText(ref);
		}
	}
	
	/**
	 * @author Scott Palmer
	 *	The listener for the edit button for each function in the second view. 
	 *	Set the values for the given function to edit form.
	 */
	class editButtonLisnter implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String button = event.getActionCommand();
			String bNum = button.substring(button.lastIndexOf('>')+1);
			int ref = Integer.parseInt(bNum);
			view.setFuncToEdit(ref);
		}
	}
	
	/**
	 * @author Scott Palmer
	 *	The listener for the add function button the second view. Add a new 
	 *	subjective function.
	 */
	class addFuncButtonLisnter implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			sm.createFunc();
			view.addSubFunc();
		}
	}
	
	/**
	 * @author Scott Palmer
	 *	The listener for the remove function in the second view. Creates a 
	 *	prompt to request which subjective function to remove.
	 */
	class removeFuncButtonLisnter implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int ref = view.removeUserPrompt();
			if (ref == -1)
				return;
			sm.removeFunc(ref);
			view.removeSubFunc(ref);
			
		}
	}
	
	/**
	 * @author scott
	 *	The listener for the unrestircted checkboxes.
	 */
	class unresVarListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			view.getUnresVar();
		}
	}
	
	/**
	 * @author Scott Palmer
	 *	The listener for the run button in the second view. Prompts the user
	 *	for which miethod to use while performing the simplex method.
	 */
	class runButtonLisnter implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			boolean unres[] = new boolean [sm.getDimension()];
			for (int i = 0; i < unres.length; i++)
				if (view.getUnresVar()[i].isSelected())
					unres[i] = true;
				else
					unres[i] = false;
			view.setAllFunc();
			String method = view.selectMethod();
			if (method.equals(null))
				return;
			sm.setMethod(method);
			sm.setUnresVar(unres);
			sm.setBound((String) view.getBound().getSelectedItem()); 
			sm.runSM();
		}
	}
	
	/**
	 * @author Scott Palmer
	 *	The listener the start over button in the final view. Prompts the user 
	 *	for which method to use while performing the simplex from the previous
	 *	when entered CF.
	 */
	class startOverButtonLisnter implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			sm.startOver();
			result = new String();
			view.resetResults();
			String method = view.selectMethod();
			if (method == null)
				return;
			sm.setMethod(method); 
			sm.runSM();
		}
	}
	
	/**
	 * Adds the string value for the number runs ran on the entered model.
	 * @param run
	 * 	The number of runs performed.
	 * @param method
	 * 	The current method in use
	 */
	public void stateRun(int run, String method) {
		for (int i = 0; i < 100; i++)
			addResultText("-");
		addResultText("<br>");
		addResultText("<strong>Starting Run "+run+" using "+method.toUpperCase()
				+"...</strong><br><br>");
	}
	
	/**
	 * Creates a string of the current model contents and adds it to the 
	 * results.
	 * @param smModel
	 * 	The current simplex model.
	 */
	public void stateModel(SimplexModel smModel) {
		String initStatement = new String();
		ArrayList<Integer> unres = smModel.getUnresVariables();
		Collections.sort(unres);
		for (int i = 0; i < smModel.getNumOfFunc(); i++) {
			if (i == 0)
				addResultText("<p>"+smModel.getBound()+":<br>");
			else if (i == smModel.getObj()+1)
				addResultText("<br>Subject to:<br>"); 
			stateFunc(i, smModel, true);
		}
		initStatement += "</p>";
		addResultText(initStatement);
	}
	
	/**
	 * Creates a string for the given function.
	 * @param func
	 * 	The function the needs to be printed.
	 * @param smModel
	 * 	The current simplex model
	 * @param all
	 *	The boolean value if all the functions should be printed
	 */
	public void stateFunc(int func, SimplexModel smModel, boolean all) {
		String statement = new String();
		Fraction[] coeff = smModel.getFuncValues(func);
		ArrayList<Integer> unres = smModel.getUnresVariables();
		ArrayList<String> inequalities = smModel.getInequalities();
		int obj = smModel.getObj();
		for (int i = 0, j = 0, num = 0; i < coeff.length; i++) {
			if (unres.size() != 0 && j < unres.size())
				if (i == unres.get(j))
					num++;
			if (i == 0) {
				if (0 == func) 
					statement += "<ul>";
				else if (obj == func && !all)
					statement += "<ul>";
				else if (obj+1 == func  && all)
					statement += "<ul>";
				else if (obj < func  && !all)
					statement += "<ul>";
				statement += coeff[i].toString();
				if (unres.size() != 0 && j < unres.size()) {
					if (i == unres.get(j))
						statement += "<i>x'</i><sub>" + (i-j+1) + "</sub>";
					else if (j != num) {
						statement += "<i>x\"</i><sub>" + (i-j) + "</sub>";
						j++;
					}
					else
						statement += "<i>x</i><sub>" + (i-j+1) + "</sub>";
				}
				else
					statement += "<i>x</i><sub>" + (i-j+1) + "</sub>";
			}
			else if (i != coeff.length-1) {
				statement += " + " + coeff[i].toString();
				if (unres.size() != 0 && j < unres.size())
					if (i == unres.get(j))
						statement += "x'<sub>" + (i-j+1) + "</sub>";
					else if (j != num) {
						statement += "<i>x\"</i><sub>" + (i-j) + "</sub>";
						j++;
					}
					else
						statement += "<i>x</i><sub>" + (i-j+1) + "</sub>";
				else
					statement += "<i>x</i><sub>" + (i-j+1) + "</sub>";
			}
			else
				if (0 == func && all && smModel.getPhase() == 1)
					statement += " " + inequalities.get(func) + " " + 
						coeff[i].toString() + " + <i>z</i><sub>0</sub><br>";
				else if (0 == func)
					statement += " " + inequalities.get(func) + " " +
						coeff[i].toString() + " + <i>z</i><sub>0</sub></ul>";
				else if (obj == func)
					statement += " " + inequalities.get(func) + " " +
						coeff[i].toString() + " + <i>w</i><sub>0</sub></ul>";
				else if (obj < func && all && smModel.getNumOfFunc()-1 != func)
					statement += " " + inequalities.get(func) + " " +
						coeff[i].toString() + "<br>";
				else
					statement += " " + inequalities.get(func) + " "+ 
						coeff[i].toString() + "</ul><br>";
		}
		addResultText(statement);
	}
	
	/**
	 * Creates a string that states the basis and adds it to the result.
	 * @param basis
	 * 	The current basises.
	 * @param smModel
	 * 	The current simplex model.
	 */
	public void stateBasis(ArrayList<int[]> basis, SimplexModel smModel) {
		String stateBasis = new String();
		stateBasis += "<p>Current Basis:<br>";
		
		for (int i = 0; i < basis.size(); i++) {
			stateBasis += "I<sub>B</sub> = {";
			for (int j = 0; j < basis.get(i).length; j++) {
				if (-1 != basis.get(i)[j])
					stateBasis += (basis.get(i)[j]+1);
				else
					stateBasis += "?";
				if (j != basis.get(i).length-1) 
					stateBasis += ", ";
			}
			stateBasis += "}<br>";
			stateBasis += "<i>x</i><sub>BF</sub> = (";
			Fraction[] solution = new Fraction[smModel.getDimension()];
			for (int j = 0; j < solution.length; j++)
				solution[j] = new Fraction();
			for (int j = 0; j < basis.get(i).length; j++)
				if (-1 != basis.get(i)[j]) 
					solution[basis.get(i)[j]] = smModel.getB(j+smModel.
						getObj()+1);
			for (int j = 0; j < solution.length; j++) {
				stateBasis += solution[j].toString();
				if (j != solution.length-1) 
					stateBasis += ", ";
			}
			stateBasis += ")<br><br>";
		}
		
		Fraction tmp = new Fraction().substract(smModel.getB(0));
		stateBasis += "&#402;(<i>x</i><sub>BF</sub>) = " + tmp.toString() +
			"<br>";
		if (smModel.getObj() == 1) {
			tmp = new Fraction();
			tmp = tmp.substract(smModel.getB(1));
			stateBasis += "<i>w</i>(<i>x</i><sub>BF</sub>) = " + tmp.toString() 
				+ "<br>";
		}
		stateBasis += "</p>";
		addResultText(stateBasis);
	}
	
	/**
	 * Creates a string that states theorem1 and adds it to the results.
	 * @param smModel
	 * 	The current simplex model.
	 */
	public void stateThm1(SimplexModel smModel) {
		String thm1Note = new String();
		if (smModel.getPhase() == 0) {
			thm1Note += "<p>Stop Theorem 1. Since &#8704; <i>c</i><sub>s</sub>"
				+" <u>></u> 0<br>" + "x &#8712; &#915<sub>0</sub>, min &#402;"+
				"(<i>x</i><sub>BF</sub>) = "+ new Fraction(0).substract(
				smModel.getB(0)).toString()+"</p>"+"<br>DONE<br><br>";
		}
		else if (smModel.getPhase() == 1) 
			thm1Note += "<p>Stop Theorem 1. Since &#8704; <i>c</i><sub>s</sub>"
				+" <u>></u> 0<br>" + "x &#8712; &#915<sub>0</sub>, min "+
				"<i>w</i>(<i>x</i><sub>BF</sub>) = "+ new Fraction(0).
				substract(smModel.getB(0)).toString()+"</p>"+ 
				"End Phase I.<br><br>Starting Phase II <br><br>";
		else
			thm1Note += "<p>Stop Theorem 1. Since &#8704; <i>c</i><sub>"+
				"s</sub> <u>></u> 0<br>" + "x &#8712; &#915<sub>0</sub>, "+
				"min &#402;(<i>x</i><sub>BF</sub>) = "+ new Fraction(0).
				substract(smModel.getB(0)).toString()+"</p>" 
				+"<br><br>End Phase II<br>DONE<br><br>";
		
		addResultText(thm1Note);
	}
	
	/**
	 * Creates a string that states theorem2 and adds it to the results.
	 * @param var
	 * 	The column that paassed theorem 2.
	 */
	public void stateThm2(int var) {
		String thm2Note = new String();
		thm2Note += "<p>Stop Theorem 2. Since &#8704; <i>a</i><sub>"+(var+1)+
			"</sub> <u>&#60</u> 0<br>" +"x &#8713; &#915;<sub>0</sub>," +
			" min &#402;(<i>x</i><sub>BF</sub>) = -&#8734;</p><br>DONE<br><br>";
		addResultText(thm2Note);
	}
	
	/**
	 * Creates a string that states theorem3 and adds it to the results
	 * @param method
	 * 	The current method in use.
	 */
	public void stateThm3(String method) {
		String thm3Note = new String();
		thm3Note += "<p>Perfomed Threorem 3 on current model with "+
			method.toUpperCase()+"</p>";
		addResultText(thm3Note);
	}
	/**
	 * Creates a string for the current pivot for the MFD
	 * @param possPivots
	 * 	The current possible pivots in the model.
	 * @param smModel
	 * 	The simplex model
	 * @param pivotRef
	 * The refence for the pivot in the model.
	 */
	public void stateMDF(ArrayList<Integer> possPivots, SimplexModel smModel,
			int[] pivotRef) {
		String pivotNote = new String();
		ArrayList<Fraction> quotientList = new ArrayList<Fraction>();
		ArrayList<Fraction> pivotList = new ArrayList<Fraction>();
		ArrayList<Fraction> productList = new ArrayList<Fraction>();
		for (int s: possPivots) {
			pivotNote += "S = " + (s+1) + " min{";
			for (int i = smModel.getObj()+1; i < smModel.getNumOfFunc(); i++) {
				if (smModel.getFuncValues(i)[s].isGreater(new Fraction(0))) {
					pivotNote += smModel.getB(i).toString()+
						" / " + smModel.getFuncValues(i)[s].toString();
					quotientList.add(smModel.getB(i).divide(smModel.
						getFuncValues(i)[s]));
					if (pivotNote.lastIndexOf('{') != pivotNote.length()-1)
						pivotNote += ", ";
				}
			}
			if (pivotNote.lastIndexOf(',') == pivotNote.length()-2)
				pivotNote = pivotNote.substring(0, pivotNote.length()-2);
			pivotNote += "} = " + quotientList.get(Fraction.minRef(
				quotientList)).toString() + "<br>";
			pivotList.add(quotientList.get(Fraction.minRef(quotientList)));
			quotientList.clear();
		}
		pivotNote += "MFD min{";
		for (int i = 0; i < pivotList.size(); i++) {
			int s = possPivots.get(i);
			pivotNote += pivotList.get(i).toString() + " * " + smModel.
				getFuncValues(smModel.getObj())[s].toString();
			productList.add(pivotList.get(i).multiply(smModel.getFuncValues(
				smModel.getObj())[s]));
			if (pivotNote.lastIndexOf('{') != pivotNote.length()-1)
				pivotNote += ", ";
		}
		if (pivotNote.lastIndexOf(',') == pivotNote.length()-2)
			pivotNote = pivotNote.substring(0, pivotNote.length()-2);
		pivotNote += "} = " + productList.get(Fraction.minRef(productList)).
			toString() + "<br>";
		ArrayList<int[]> basis = smModel.findBasis();
		for (int i = 0; i < basis.size(); i++)
			pivotNote += "<i>x</i><sub>"+(pivotRef[1]+1)+
				"</sub> enter the basis for <i>x</i><sub>"+
				(basis.get(i)[pivotRef[0]-smModel.getObj()-1]+1)+"</sub><br>";
		addResultText(pivotNote);
	}
	
	/**
	 * Creates the notes for the pivot for the MLR
	 *  @param possPivots
	 * 	The current possible pivots in the model.
	 * @param smModel
	 * 	The simplex model
	 * @param pivotRef
	 * The refence for the pivot in the model.
	 */
	public void stateMLR(ArrayList<Integer> possPivots, SimplexModel smModel,
			int[] pivotRef) {
		String pivotNote = new String();
		int ref = possPivots.get(0);
		ArrayList<Fraction> quotientList = new ArrayList<Fraction>();
		pivotNote += "MLR S = " + (ref+1) + " min{";
		for (int i = smModel.getObj()+1; i < smModel.getNumOfFunc(); i++) {
			if (smModel.getFuncValues(i)[ref].isGreater(new Fraction(0))) {
				pivotNote += smModel.getB(i).toString()+ " / " +
					smModel.getFuncValues(i)[ref].toString();
				quotientList.add(smModel.getB(i).divide(smModel.
					getFuncValues(i)[ref]));
				if (pivotNote.lastIndexOf('{') != pivotNote.length()-1)
					pivotNote += ", ";
			}
		}
		if (pivotNote.lastIndexOf(',') == pivotNote.length()-2)
			pivotNote = pivotNote.substring(0, pivotNote.length()-2);
		pivotNote += "} = " + quotientList.get(Fraction.minRef(quotientList)).
			toString() + "<br>";
		ArrayList<int[]> basis = smModel.findBasis();
		for (int i = 0; i < basis.size(); i++)
			pivotNote += "<i>x</i><sub>"+(pivotRef[1]+1)+
				"</sub> enter the basis for <i>x</i><sub>"+
				(basis.get(i)[pivotRef[0]-smModel.getObj()-1]+1)+"</sub><br>";
		
		addResultText(pivotNote);
	}
	
	/**
	 * Creates the notes for the pivot for the MRL
	 *  @param possPivots
	 * 	The current possible pivots in the model.
	 * @param smModel
	 * 	The simplex model
	 * @param pivotRef
	 * The refence for the pivot in the model.
	 */
	public void stateMRL(ArrayList<Integer> possPivots, SimplexModel smModel, 
			int[] pivotRef) {
		String pivotNote = new String();
		int ref = possPivots.get(possPivots.size()-1);
		ArrayList<Fraction> quotientList = new ArrayList<Fraction>();
		pivotNote += "MLR S = " + (ref+1) + " min{";
		for (int i = smModel.getObj()+1; i < smModel.getNumOfFunc(); i++) {
			if (smModel.getFuncValues(i)[ref].isGreater(new Fraction(0))) {
				pivotNote += smModel.getB(i).toString()+ " / " +
					smModel.getFuncValues(i)[ref].toString();
				quotientList.add(smModel.getB(i).divide(smModel.
					getFuncValues(i)[ref]));
				if (pivotNote.lastIndexOf('{') != pivotNote.length()-1)
					pivotNote += ", ";
			}
		}
		if (pivotNote.lastIndexOf(',') == pivotNote.length()-2)
			pivotNote = pivotNote.substring(0, pivotNote.length()-2);
		pivotNote += "} = " + quotientList.get(Fraction.minRef(quotientList)).
			toString() + "<br>";
		ArrayList<int[]> basis = smModel.findBasis();
		for (int i = 0; i < basis.size(); i++)
			pivotNote += "<i>x</i><sub>"+(pivotRef[1]+1)+
				"</sub> enter the basis for <i>x</i><sub>"+
				(basis.get(i)[pivotRef[0]-smModel.getObj()-1]+1)+"</sub><br>";
		addResultText(pivotNote);
	}
	
	/**
	 * Creates the notes for the pivot for the MNC
	 *  @param possPivots
	 * 	The current possible pivots in the model.
	 * @param smModel
	 * 	The simplex model
	 * @param pivotRef
	 * The refence for the pivot in the model.
	 */
	public void stateMNC(ArrayList<Integer> possPivots, SimplexModel smModel, 
			int[] pivotRef) {
		String pivotNote = new String();
		ArrayList<Fraction> coeff = new ArrayList<Fraction>();
		for (int i = 0; i < possPivots.size(); i++) {
			coeff.add(smModel.getFuncValues(
				smModel.getObj())[possPivots.get(i)]);
		}
		int ref = possPivots.get(Fraction.minRef(coeff));
		ArrayList<Fraction> quotientList = new ArrayList<Fraction>();
		pivotNote += "MNC S = " + (ref+1) + " min{";
		for (int i = smModel.getObj()+1; i < smModel.getNumOfFunc(); i++) {
			if (smModel.getFuncValues(i)[ref].isGreater(new Fraction(0))) {
				pivotNote += smModel.getB(i).toString()+ " / " +
					smModel.getFuncValues(i)[ref].toString();
				quotientList.add(smModel.getB(i).divide(smModel.
					getFuncValues(i)[ref]));
				if (pivotNote.lastIndexOf('{') != pivotNote.length()-1)
					pivotNote += ", ";
			}
		}
		if (pivotNote.lastIndexOf(',') == pivotNote.length()-2)
			pivotNote = pivotNote.substring(0, pivotNote.length()-2);
		pivotNote += "} = " + quotientList.get(Fraction.minRef(quotientList)).
			toString() + "<br>";
		ArrayList<int[]> basis = smModel.findBasis();
		for (int i = 0; i < basis.size(); i++)
			pivotNote += "<i>x</i><sub>"+(pivotRef[1]+1)+
				"</sub> enter the basis for <i>x</i><sub>"+
				(basis.get(i)[pivotRef[0]-smModel.getObj()-1]+1)+"</sub><br>";
		addResultText(pivotNote);
	}
	
	/**
	 * Creates a string for the artifical variable added the current model.
	 * @param var
	 * 	The variable added
	 */
	public void stateArtVar(int var) {
		addResultText("Added Artifical Variable <i>x</i><sub>"+var+
			"</sub>.<br>");
	}
	
	/**
	 * Creates a string for changing the boundary to minimize.
	 */
	public void stateFixBound() {
		addResultText("Change Objective function from Maximize to"+
			" Minimize.<br>");
	}
	
	/**
	 * Creates a string for the redundant function removed.
	 */
	public void stateRedundant() {
		addResultText("Removed redundant function since &#8704;<i>a</i>" +
			"<sub>i</sub> = 0");
	}
	
	/**
	 * Creates a string states all the subjective functions are removed.
	 */
	public void stateAllRedunant() {
		addResultText("<br><br>All Subject Functions are redunant." +
			"<br><br>DONE<br><br>");
	}
	
	/**
	 * Creates a string for the slack variables introduced.
	 * @param var
	 * 	The variable that was introduce.
	 */
	public void stateSlackVar(int var) {
		addResultText("Added Slack Variable <i>x</i><sub>"+(var+1)+
			"</sub>.<br>");
	}
	
	/**
	 * Creates a string fo rthe unrestricted variables that was fixed
	 * @param var
	 */
	public void stateUnresSlack(int var) {
		addResultText("Added Slack Variables x'<sub>"+var+
			"</sub> and <i>x\"</i><sub>"+var+"</sub>.<br>");
	}
	
	/**
	 * Add a statement to the result
	 * @param s
	 * 	The string to add.
	 */
	public void addResultText(String s) {
		result += s;
	}
	
	/**
	 * prints the result to the frame
	 */
	public void printResults() {
		view.setViewResults(result);
	}
}
