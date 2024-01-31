package SimplexMethod;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Scott Palmer<br>
 * <strong>Descripiton:</strong><br>
 * This is the model for the simplex method. It holds the information from the
 * view and performs the chosen method for the simplex method.
 */
public class SimplexModel extends SimplexMethod {
	private int dimension, obj, origObj, phase, origPhase, runs = 1;
	private ArrayList<Fraction[]> functions;
	private ArrayList<Fraction[]> origFunctions = new ArrayList<Fraction[]>();
	private ArrayList<Fraction[]> cycleCheckFunctions = 
		new ArrayList<Fraction[]>();
	private ArrayList<String> inequalities;
	private ArrayList<Integer> artVariables = new ArrayList<Integer>();
	private ArrayList<Integer> unresVariables = new ArrayList<Integer>();
	private boolean[] unresVar;
	private boolean firstRun = true;
	private String bound, method;
	
	/**
	 * Constructor for the simplex model. Sets all variables for use.
	 * @param dim
	 */
	public SimplexModel(int dim) {
		functions = new ArrayList<Fraction[]>();
		inequalities = new ArrayList<String>();
		dimension = dim;
		obj = 0;
		phase = 0;
		unresVar = new boolean[dimension];
		bound = "Minimize";
		createFunc();
		createFunc();
	}
	
	/**
	 * Returns the dimension the model is in.
	 * @return
	 * 	The integer value of the dimension of the model.
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * Returns the integer value the current phase.
	 * 0: Second Phase with no artifical variables introduced.
	 * 1: First Phase with artifical variables introduced.
	 * 2: Second Phase with artifical variables removed.
	 * @return
	 * 	The integer value of the current phase.
	 */
	public int getPhase() {
		return phase;
	}
	
	/**
	 * Returns an ArrayList of all the unrestricted variables in the model.
	 * @return
	 *  The ArrayList of all the unrestricted variables in the model.
	 */
	public ArrayList<Integer> getUnresVariables() {
		return unresVariables;
	}
	
	/**
	 * The refence value of the objective function.
	 * @return
	 * 	The integer value of the refence for the objective function.
	 */
	public int getObj() {
		return obj;
	}
	
	/**
	 * Returns the num of functions in the model
	 * @return
	 * 	The integer values of the num of functions
	 */
	public int getNumOfFunc() {
		return functions.size();
	}
	
	/**
	 * Returns an ArrayList of all the inequalities in the model.
	 * @return
	 * 	The ArrayList of all the inequalities in the model.
	 */
	public ArrayList<String> getInequalities() {
		return inequalities;
	}
	
	/**
	 * Returns the values for a function given by the parameter ref
	 * @param ref
	 * 	The funtion values to return.
	 * @return
	 * 	The fractional values of the a function.
	 */
	public Fraction[] getFuncValues(int ref) {
		return functions.get(ref);
	}
	
	/**
	 * Returns the value of b in the given function.
	 * @param func
	 * 	The integer value of the function.
	 * @return
	 * 	The fractional value of b in the given function.
	 */
	public Fraction getB(int func) {
		return functions.get(func)[functions.get(func).length-1];
	}
	
	/**
	 * Returns the boundary of the model.
	 * @return
	 * The string value of the boundary in the model.
	 */
	public String getBound() {
		return bound;
	}
	
	/**
	 * Returns the set method for the simplex method.
	 * @return
	 * 	The string value of the set method.
	 */
	public String getMethod() {
		return method;
	}
	
	/**
	 * Sets the boolean values for unrestricted variables.
	 * @param unres
	 * 	An Array of boolean values for the unrestircted variables 
	 */
	public void setUnresVar(boolean[] unres) {
		for (int i = 0; i < unres.length; i++)
			unresVar[i] = unres[i];
	}
	
	/**
	 * Sets the values of a given function.
	 * @param func
	 * 	The fractional values for a given function
	 * @param ref
	 * 	The integer reference for the given function.
	 */
	public void setFunc(Fraction[] func, int ref, String inequality) {
		Fraction[] refFunc = functions.get(ref);
		for (int i = 0; i < refFunc.length; i++)
			refFunc[i] = func[i];
		inequalities.set(ref, inequality);
	}
	
	/**
	 * Sets the boundary for the objective function.
	 * @param input
	 * 	The string to set the boundary as.
	 */
	public void setBound(String input) {
		bound = input;
	}
	
	/**
	 * Sets the method to perform while going through the simplex method.
	 * @param meth
	 * 	The string value to set the method as.
	 */
	public void setMethod(String meth) {
		method = meth;
	}
	
	/**
	 * Change a max objective function to a min
	 */
	public void fixBound() {
		if (bound.equals("Maximize")) {
			for (int i = 0; i < functions.get(0).length; i++)
				functions.get(0)[i] = new Fraction().
					substract(functions.get(0)[i]);
			stateFixBound();
		}
	}
	
	/**
	 * Ensures all subjective functions b's are greater than zero
	 */
	public void fixB() {
		for (int i = obj+1; i < functions.size(); i++)
			if (getB(i).isLess(new Fraction(0))) {
				for (int j = 0; j < functions.get(i).length; j++)
					functions.get(i)[j] = new Fraction(0).
						substract(functions.get(i)[j]);
				addResultText("Negtive b<sub>"+(i-obj+1)+
						"</sub>. Function adjusted.<br>");
			}
	}
	
	/**
	 * Creates an array for a function & sets the inequality to "="
	 */
	public void createFunc() {
		functions.add(new Fraction[dimension+1]);
		for (int i = 0; i < functions.get(functions.size()-1).length; i++)
			functions.get(functions.size()-1)[i] = new Fraction();
		inequalities.add("=");
	}
	
	/**
	 * Introduces slack variables for inequalities
	 */
	public void introSlackVar() {
		for (int i = 0; i < getNumOfFunc(); i ++)
			if (inequalities.get(i).equals(">") || 
					inequalities.get(i).equals(">=")) {
				addDim(i+obj, -1);
				inequalities.set(i, "=");
				stateSlackVar(i);
			}
			else if (inequalities.get(i).equals("<") || 
					inequalities.get(i).equals("<=")) {
				addDim(i+obj, 1);
				inequalities.set(i, "=");
				stateSlackVar(i);
			}
	}
	
	/**
	 * Introduces Slack variables to unrestricted variables
	 * @param var
	 */
	public void introUnresSlack(int var) {
		dimension++;
		for (int i = 0; i < functions.size(); i++) {
			Fraction[] tmpFunc = new Fraction[dimension+1];
			boolean done = false;
			for (int j = 0; j < tmpFunc.length; j++) {
				if (!done)
					if (var == j) {
						tmpFunc[j] = functions.get(i)[j];
						tmpFunc[j+1] = new Fraction().
							substract(functions.get(i)[j]);
						j++;
						done = true;
					}
					else
						tmpFunc[j] = functions.get(i)[j];
				else {
					tmpFunc[j] = functions.get(i)[j-1];
				}
			}
			functions.set(i, tmpFunc);
		}
		unresVar[var] = false;
		unresVariables.add(var);
		stateUnresSlack(var);
	}
	
	/**
	 * Introduces artifical variables & function to the current model
	 */
	public void introArtVar() {
		int[] basis = findBasis().get(0);
		Fraction[] artFunc = new Fraction[getDimension()+1];
		for (int i = 0; i < artFunc.length; i++)
			artFunc[i] = new Fraction();
		// Creating artifical function to be added to model
		for (int i = 0; i < basis.length; i++)
			if (basis[i] == -1)
				for (int j = 0; j < functions.get(i+1).length; j++)
					artFunc[j] = artFunc[j].substract(functions.get(i+1)[j]);
		functions.add(1, artFunc);
		inequalities.add(1, "=");
		obj = 1;
		// Extending the dimension for the artifical variables
		for (int i = 0; i < basis.length; i++)
			if (basis[i] == -1) { 
				addDim(i+obj+1, 1);
				stateArtVar(getDimension());
				artVariables.add(getDimension());
			}
		addResultText("Added Artifical Function as new"+
				" Objective Function.<br>");
		stateFunc(obj, this, false);
		phase = 1;
	}
	
	/**
	 * Extends the dimension for the current model
	 * @param func
	 * 	The functions that is receiving the non-zero coefficient
	 * @param coeff
	 * 	The non-zero coefficient
	 */
	public void addDim(int func, int coeff) {
		dimension++;
		for (int i = 0; i < functions.size(); i++) {
			Fraction[] tmpFunc = new Fraction[dimension+1];
			for (int j = 0; j < functions.get(i).length; j++) {
				if (j <= functions.get(i).length-2) {
					tmpFunc[j] = functions.get(i)[j];
					if (func == i)
						tmpFunc[j+1] = new Fraction(coeff);
					else
						tmpFunc[j+1] = new Fraction(0);
				}
				else if (j == functions.get(i).length-1)
					tmpFunc[j+1] = functions.get(i)[j];
			}
			functions.set(i, tmpFunc);
		}
	}
	
	/**
	 * Removes a given function & inequality.
	 * @param func
	 * 	The function to remove
	 */
	public void removeFunc(int func) {
		functions.remove(func);
		inequalities.remove(func);
	}
	
	/**
	 * Removes redunant functions
	 */
	public void removeRedun() {
		boolean remove = true;
		for (int i = obj+1; i < functions.size(); i ++) {
			for ( int j = 0; j < functions.get(i).length-1; j++)
				if (!functions.get(i)[j].equals(new Fraction(0)))
					remove = false;
			if (remove) {
				removeFunc(i);
				stateRedundant();
			}
			remove = true;
		}
		if (functions.size() == 1) {
			stateAllRedunant();
			return;
		}
			
	}
	
	/**
	 * removes artifical variables that were added at the begining of phase I
	 */
	public void removeArtifical() {
		dimension = dimension - artVariables.size();
		functions.remove(1);
		obj = 0;
		phase = 2;
		for (int i = 0; i < functions.size(); i++) {
			Fraction[] tmpFunc = new Fraction[getDimension()+1];
			for (int j = 0; j < tmpFunc.length; j++) {
				if (j < tmpFunc.length-1)
					tmpFunc[j] = functions.get(i)[j];
				else
					tmpFunc[j] = getB(i);
			}
			functions.set(i, tmpFunc);
		}
	}
	
	/**
	 * Checks if cycling has occured
	 * @return
	 * 	Returns true if cycling is has occured.
	 */
	public boolean cycleCheck() {
		boolean cycling = true;
		for (int i = 0; i < functions.size(); i++) {
			for (int j = 0; j < functions.get(i).length; j++)
				if (!cycleCheckFunctions.get(i)[j].equals(functions.get(i)[j]))
					cycling = false;
		}
		return cycling;
	}
	
	/**
	 * Checks if the current model is in CF
	 * @return
	 * 	Returns true if the current model is in CF.
	 */
	public boolean isCFform() {
		// Checking bound
		if (!bound.equals("Minimize"))
			return false;
		// Checking for unrestricted variable
		for (boolean i: unresVar)
			if (i)
				return false;
		// Checking inequalities
		for (int i = 0; i < inequalities.size(); i++)
			if (!inequalities.get(i).equals("="))
				return false;
		// Checking b sub i's if they below zero
		for (int i = obj+1; i < functions.size(); i++)
			if (getB(i).isLess(new Fraction(0))) 
				return false;
		// Checking for a complete basis
		ArrayList<int[]> basis = findBasis();
		if (basis.size() != 0 && basis.get(0).length != 0)
			for (int i = 0; i < basis.get(0).length; i++) {
				if (basis.get(0)[i] == -1)
					return false;
			}
		else
			return false;
		return true;
	}
	
	/**
	 * Makes the current model to enter CF
	 */
	public void enterCF() {
		fixBound();
		fixB();
		introSlackVar();
		for (int i = 0; i < unresVar.length; i++)
			if (unresVar[i])
				introUnresSlack(i);
		int[] basis = findBasis().get(0);
		boolean artVar = false;
		for (int i = 0; i < basis.length; i++)
			if (basis[i] == -1)
				artVar = true;
		if (artVar)
			introArtVar();
	}
	
	/**
	 * Finds all the basises for the current model 
	 * @return 
	 * 	All the basises for the current model
	 */
	public ArrayList<int[]> findBasis() {
		// For each zero in the objective function will be a possible basis
		Fraction[] currentObj = functions.get(obj);
		ArrayList<Integer> possibleBasis = new ArrayList<Integer>();
		for (int i = 0; i < getDimension(); i++) {
			if (currentObj[i].equals(new Fraction(0))) {
				possibleBasis.add(i);
			}
		}
		// For each Possible Basis the column of subective functions are added
		// Each column that equals one is apart of the basis
		ArrayList<Fraction> sums = new ArrayList<Fraction>();
		for (int i = 0; i < possibleBasis.size(); i++) {
			Fraction tmpSum = new Fraction();
			for (int j = obj; j < getNumOfFunc(); j++) {
				tmpSum = tmpSum.add(functions.get(j)[possibleBasis.get(i)]);
			}
			sums.add(tmpSum);
		}
		// Every variable that is apart of the basis is add to the tmpBasis
		ArrayList<Integer> tmpBasis = new ArrayList<Integer>();
		for(int i = 0; i < sums.size(); i++) {
			if (sums.get(i).equals(new Fraction(1)))
				tmpBasis.add(possibleBasis.get(i));
		}
		
		// As each basis is found for the given function is placed in ordered
		// basis in respect to order of the subjective functions and then
		// removed from the ArryaList of basis.  If more than one basis is 
		// found for a function the variable that is in the basis placed is in
		// a new basis to be filled later.
		ArrayList<int[]> basises = new ArrayList<int[]>();
		int[] firstBasis = new int[(getNumOfFunc()- obj-1)];
		for (int i = 0; i < firstBasis.length; i++)
			firstBasis[i] = -1;
		ArrayList<int[]> extraBasises = new ArrayList<int[]>();
		for (int func = obj + 1, i = 0; func < getNumOfFunc(); func++) {
			while (i < tmpBasis.size()) {
				if (getFuncValues(func)[tmpBasis.get(i)].
						equals(new Fraction(1))) {
					if (firstBasis[func-obj-1] == -1) {
						firstBasis[func-obj-1] = tmpBasis.get(i);
						tmpBasis.remove(i);
						i = 0;
					}
					else {
						int[] currentBasis = new int[(getNumOfFunc()- obj-1)];
						for (int j = 0; j < currentBasis.length; j++)
							currentBasis[j] = -1;
						currentBasis[func-obj-1] = tmpBasis.get(i);
						tmpBasis.remove(i);
						i = 0;
						extraBasises.add(currentBasis);
					}
				}
				else {
					i++;
				}
			} // end while
			i = 0;
		} // end for
		basises.add(0, firstBasis);
		// Filling basises that were added with only one basis in mind
		ArrayList<int[]> allBasises = new ArrayList<int[]>();
		while (extraBasises.size() != 0) {
			int[] tempBasis = new int[(getNumOfFunc()- obj-1)];
			for (int i = 0; i < extraBasises.get(0).length; i++)
				tempBasis[i] = extraBasises.get(0)[i];
			for (int i = 0; i < basises.size(); i++)
				allBasises.add(basises.get(i));
			while (0 != basises.size()) {
				int[] nextBasis = new int[(getNumOfFunc()- obj-1)];
				for (int i = 0; i < basises.get(0).length; i++)
					if (tempBasis[i] != -1)
						nextBasis[i] = tempBasis[i];
					else
						nextBasis[i] = basises.get(0)[i];
				basises.remove(0);
				allBasises.add(nextBasis);
			}
			for (int i = 0; i < allBasises.size(); i++)
				basises.add(allBasises.get(i));
			allBasises.clear();
			extraBasises.remove(0);
		}
		
		return basises;
	}
	
	/**
	 * Runs the simplex method
	 */
	public void runSM() {
		if (firstRun)
			stateRun(runs, method);
		stateModel(this);
		removeRedun();
		if (!isCFform()){
			if (theorem2(0)) {
				printResults();
				return;
			}
			addResultText("<br>Not in CF<br>");
			enterCF();
			if (firstRun) {
				origFunctions.clear();
				cycleCheckFunctions.clear();
				for (int i = 0; i < functions.size(); i++) {
					Fraction[] tmpFunc = new Fraction[functions.get(i).length];
					for (int j = 0; j < functions.get(i).length; j++)
						tmpFunc[j] = functions.get(i)[j];
					origFunctions.add(tmpFunc);
					cycleCheckFunctions.add(tmpFunc);
					origObj = obj;
					origPhase = phase;
				}
				firstRun = false;
			}
			stateModel(this);
			stateBasis(findBasis(), this);
		}
		else if (firstRun) {
			origFunctions.clear();
			cycleCheckFunctions.clear();
			for (int i = 0; i < functions.size(); i++) {
				Fraction[] tmpFunc = new Fraction[functions.get(i).length];
				for (int j = 0; j < functions.get(i).length; j++)
					tmpFunc[j] = functions.get(i)[j];
				origFunctions.add(tmpFunc);
				cycleCheckFunctions.add(tmpFunc);
				origObj = obj;
				origPhase = phase;
			}
			firstRun = false;
		}
		while (!theorem1(1) && !theorem2(1)) {
			removeRedun();
			if (theorem3()) {
				stateModel(this);
				stateBasis(findBasis(), this);
				if (cycleCheck()) {
					addResultText("<br>No feasible solution! &#915;"+
							"<sub>0</sub> = &#8709;<br>DONE<br><br>");
					addResultText("<br>Cycling<br><br>DONE<br><br>");
					printResults();
					return;
				}
				if (!isCFform()){
					addResultText("<br>Not in CF<br>");
					addResultText("<br>No feasible solution! &#915;"+
							"<sub>0</sub> = &#8709;<br>DONE<br><br>");
					printResults();
					return;
				}
			}
			else {
				printResults();
				return;
			}
		}
		if (theorem1(0)) {
			if (phase == 0) {
				printResults();
				return;
			}
			else if (phase == 2) {
				printResults();
				return;
			}
			else {
				removeArtifical();
				if (!isCFform()) {
					stateModel(this);
					addResultText("<br>Not in CF<br>");
					addResultText("<br>No feasible solution! &#915;"+
							"<sub>0</sub> = &#8709;<br>DONE<br><br>");
					printResults();
					return;
				}
				cycleCheckFunctions.clear();
				for (int i = 0; i < functions.size(); i++) {
					Fraction[] tmpFunc = new Fraction[functions.get(i).length];
					for (int j = 0; j < functions.get(i).length; j++)
						tmpFunc[j] = functions.get(i)[j];
					cycleCheckFunctions.add(tmpFunc);
				}
				runSM();
				return;
			}
		}
		if (theorem2(0)) {
			stateModel(this);
			printResults();
			return;
		}
	}
	
	/**
	 * Checks if the current model has passed theorem 1.
	 * @return
	 * 	Returns true if the current model has passed theorem 1.
	 */
	public boolean theorem1(int state) {
		for (int i = 0; i < functions.get(obj).length-1; i++) 
			if (functions.get(obj)[i].isLess(new Fraction(0)))
				return false;
		if (state == 0)
			stateThm1(this);
		
		return true;
	}
	
	/**
	 * Checks if the current model has passed theorem 2.
	 * @return
	 * 	Returns true if the current model has passed theorem 2.
	 */
	public boolean theorem2(int state) {
		int count = 0;
		for (int i = 0; i < getDimension(); i++) { 
			for (int j = 0; j < getNumOfFunc(); j++) 
				if (functions.get(j)[i].isLessEqual(new Fraction(0)))
					count++;	
			if (functions.get(obj)[i].isLess(new Fraction(0)) &&
					count == getNumOfFunc()) {
				if (state == 0)
					stateThm2(i);
				
				return true;
			}
			count = 0;
		}
		
		return false;
	}
	
	/**
	 * Performs theorem 3 on current simplex model. Basis will change based 
	 * from the current selected method.
	 * @return
	 * 	Returns true if the basis was able to be calculated correctly.
	 */
	public boolean theorem3() {
		ArrayList<Integer> potentialPivots = potentialPivots();
		for (int i: potentialPivots) {
			int count = 0;
			for(int j = obj+1; j < getNumOfFunc(); j++) {
				if (functions.get(j)[i].numerator == 0) {
					count++;
				}
			}
			if (count == getNumOfFunc()-1)
				return false;
		}
		int[] pivotRef = new int[2];
		if (method.equals("mfd"))
			pivotRef = methodFastestDecent(potentialPivots);
		else if (method.equals("mlr"))
			pivotRef = methodLeftToRight(potentialPivots);
		else if (method.equals("mnc"))
			pivotRef = methodNegativeCoeff(potentialPivots);
		else
			pivotRef = methodRightToLeft(potentialPivots);
		// Reduce function that is changing it's basis
		Fraction div = functions.get(pivotRef[0])[pivotRef[1]];
		for (int i = 0; i < functions.get(pivotRef[0]).length; i++) 
			functions.get(pivotRef[0])[i] = functions.get(pivotRef[0])[i].
				divide(div);
		
		for (int i = 0; i < functions.size(); i++) {
			Fraction rowFactor = functions.get(i)[pivotRef[1]];
			for (int j = 0; j < functions.get(i).length; j++)
				if (i != pivotRef[0])
					functions.get(i)[j] = functions.get(i)[j].substract(
							functions.get(pivotRef[0])[j].multiply(rowFactor));
		}
		stateThm3(method);
		
		return true;
	}
	
	/**
	 * From a list of possible pivot variables the lowest possible function is
	 * found to produce the fastest pivot to reach theorem 1 constraints.
	 * @param possPivotVar
	 * 	An ArrayList of possible variable to enter the basis and pivot about.
	 * @return
	 * 	An array of integers where the first reference is the function and the
	 *  second is the variable to pivot on.
	 */
	public int[] methodFastestDecent(ArrayList<Integer> possPivotVar) {
		Collections.sort(possPivotVar);
		int[] potentailPivotFunc = new int[possPivotVar.size()];
		for (int i = 0; i < potentailPivotFunc.length; i++) 
			potentailPivotFunc[i] = findLowestPivot(possPivotVar.get(i));
		
		ArrayList<Fraction> products = new ArrayList<Fraction>();
		for (int i = 0; i < potentailPivotFunc.length; i++)
			products.add(functions.get(obj)[possPivotVar.get(i)].multiply(
					functions.get(potentailPivotFunc[i])[possPivotVar.get(i)]));
		
		int[] pivotRef = new int[2];
		pivotRef[0] = potentailPivotFunc[Fraction.minRef(products)];
		pivotRef[1] = possPivotVar.get(Fraction.minRef(products));
		stateMDF(possPivotVar, this, pivotRef);
		
		return pivotRef;
	}
	
	/**
	 * Performs the simplex method from left to right.
	 * @param possPivotVar
	 * 	An ArrayList of possible variable to enter the basis and pivot about.
	 * @return
	 * 	An array of integers where the first reference is the function and the
	 *  second is the variable to pivot on.
	 */
	public int[] methodLeftToRight(ArrayList<Integer> possPivotVar) {
		Collections.sort(possPivotVar);
		int[] pivotRef = new int[2];
		pivotRef[1] = possPivotVar.get(0);
		pivotRef[0] = findLowestPivot(possPivotVar.get(0));
		stateMLR(possPivotVar, this, pivotRef);
		
		return pivotRef;
	}
	
	/**
	 * Performs the simplex method with pivot from right to left.
	 * @param possPivotVar
	 * 	An ArrayList of possible variable to enter the basis and pivot about.
	 * @return
	 * 	An array of integers where the first reference is the function and the 
	 * second is the variable to pivot on.
	 */
	public int[] methodRightToLeft(ArrayList<Integer> possPivotVar) {
		Collections.sort(possPivotVar); 
		int[] pivotRef = new int[2];
		pivotRef[1] = possPivotVar.get(possPivotVar.size()-1);;
		pivotRef[0] = findLowestPivot(possPivotVar.get(possPivotVar.size()-1));
		stateMRL(possPivotVar, this, pivotRef);
		
		return pivotRef;
	}
	
	/**
	 * Performs the simplex method with pivot from most negative coefficient.
	 * @param possPivotVar
	 * 	An ArrayList of possible variable to enter the basis and pivot about.
	 * @return
	 * 	An array of integers where the first reference is the function and the 
	 * second is the variable to pivot on.
	 */
	public int[] methodNegativeCoeff(ArrayList<Integer> possPivotVar) {
		Collections.sort(possPivotVar);
		ArrayList<Fraction> objCoeff = new ArrayList<Fraction>();
		for (int i = 0; i < possPivotVar.size(); i++) {
			objCoeff.add(functions.get(obj)[possPivotVar.get(i)]);
		}
		int[] pivotRef = new int[2];
		pivotRef[1] = possPivotVar.get(Fraction.minRef(objCoeff));
		pivotRef[0] = findLowestPivot(possPivotVar.get(
			Fraction.minRef(objCoeff)));
		stateMNC(possPivotVar, this, pivotRef);
		
		return pivotRef;
	}
	
	/**
	 * Gets the indexes of the coefficients in the objective function that are
	 * below zero.
	 * @return 
	 * 	ArrayList of the index of negative coefficients in the objective 
	 * 	function.
	 */
	public ArrayList<Integer> potentialPivots() {
		ArrayList<Integer> pivotPoints = new ArrayList<Integer>();
		int ref = 0;
		for (Fraction i: functions.get(obj)) {
			if (i.isLess(new Fraction(0)) && ref != dimension)
				pivotPoints.add(ref);
			ref++;
		}
		
		return pivotPoints;
	}
	
	/**
	 * Finds the subjective function with lowest possible pivot in a column
	 * specified by the parameter ref. 
	 * @param ref 
	 * 	The column to check for the lowest pivot.
	 * @return 
	 * 	The subjective function that has the lowest pivot. 
	 */
	public int findLowestPivot(int ref) {
		ArrayList<Fraction> pivots = new ArrayList<Fraction>();
		ArrayList<Integer> func = new ArrayList<Integer>();
		for (int i = obj+1; i < functions.size(); i++)
			if (functions.get(i)[ref].isGreater(new Fraction(0))) {
				pivots.add(getB(i).divide(functions.get(i)[ref]));
				func.add(i);
			}
		
		return func.get(Fraction.minRef(pivots));
	}
	
	/**
	 * Restarts Simplex Method
	 */
	public void startOver() {
		runs++;
		obj = origObj;
		functions.clear();
		inequalities.clear();
		unresVariables.clear();
		for (int i = 0; i < origFunctions.size(); i++) {
			Fraction[] tmpFunc = new Fraction[origFunctions.get(i).length];
			for (int j = 0; j < origFunctions.get(i).length; j++)
				tmpFunc[j] = origFunctions.get(i)[j];
			functions.add(tmpFunc);
			inequalities.add("=");
		}
		firstRun = true;
		phase = origPhase;
		dimension = functions.get(0).length-1;
		unresVar = new boolean[dimension];
		
	}
}