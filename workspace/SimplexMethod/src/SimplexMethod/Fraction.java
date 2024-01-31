package SimplexMethod;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Scott Palmer<br>
 * <strong>Descripiton:</strong><br>
 *	Fraction class creates a fraction and can perform basic arithmetic with 
 *	fractions while reducing it to its lowest terms. For negative values the 
 *	numerator hold the sign value.
 */
public class Fraction {
	public int numerator, denominator;
	
	/**
	 * Creates a zero fraction
	 */
	public Fraction () {
		numerator = 0;
		denominator = 1;
	}
	
	/**
	 * Creates a fraction with denominator of 1
	 * @param num
	 * 	An whole number to be converted into a fraction.
	 */
	public Fraction (int num) {
		numerator = num;
		denominator = 1;
		reduceFrac();
	}
	
	/**
	 * Creates a fraction specified numerator and denominator 
	 * respectively with the parameters
	 * @param num
	 * 	An integer that represents the numerator
	 * @param denom
	 * 	An integer that represents the denominator
	 */
	public Fraction (int num, int denom) {
		numerator = num;
		denominator = denom;
		reduceFrac();
	}
	
	/**
	 * Adds two fractions and returns their result
	 * @param f
	 * 	A Fraction that is to be added to another Fraction
	 * @return
	 * 	A new Fraction
	 */
	public Fraction add(Fraction f) {
		Fraction tmp = new Fraction();
		tmp.numerator = this.numerator*f.denominator + 
			f.numerator*this.denominator;
		tmp.denominator = this.denominator * f.denominator;
		tmp.reduceFrac();
		
		return tmp;
	}
	
	/**
	 * Subtracts two fractions and returns their result
	 * @param f
	 * 	A Fraction to be substracted from another Fraction
	 * @return
	 * 	A new Fraction
	 */
	public Fraction substract(Fraction f) {
		Fraction tmp = new Fraction(-f.numerator, f.denominator);
		tmp = this.add(tmp);
		
		return tmp;
	}
	
	/**
	 * Multiplies two fractions and returns their result
	 * @param f
	 * 	A Fraction to be multiplied to another Fraction
	 * @return
	 * 	A new Fraction
	 */
	public Fraction multiply(Fraction f) {
		Fraction tmp = new Fraction();
		tmp.numerator = this.numerator * f.numerator;
		tmp.denominator = this.denominator * f.denominator;
		tmp.reduceFrac();
		
		return tmp;
	}
	
	/**
	 * Divides two fractions and returns their result
	 * @param f
	 * 	A Fraction to divide another Fraction by 
	 * @return
	 * 	A new Fraction
	 */
	public Fraction divide(Fraction f) {
		Fraction tmp = new Fraction();
		tmp.numerator = this.numerator * f.denominator;
		tmp.denominator = this.denominator * f.numerator;
		tmp.reduceFrac();
		
		return tmp;
	}
	
	/**
	 * Reduces the fraction to its lowest terms
	 */
	public void reduceFrac() {
		if (numerator == 0) {
			denominator = 1;
			signCheck();
			return;
		}
		if (Math.abs(numerator) == Math.abs(denominator)) {
			numerator = numerator/denominator;
			denominator = 1;
			signCheck();
			return;
		}
		else if (Math.abs(numerator)%Math.abs(denominator) == 0) {
			numerator = numerator/denominator;
			denominator = 1;
			signCheck();
			return;
		}
		else if (Math.abs(denominator)%Math.abs(numerator) == 0) {
			denominator = denominator/numerator;
			numerator = 1;
			signCheck();
			return;
		}
		
		int[] numCD = getCommonDivisors(Math.abs(numerator));
		int[] denomCD = getCommonDivisors(Math.abs(denominator));
		for (int i = numCD.length-1, j = denomCD.length-1; 0 <= i && 0 <= j;) {
			if (numCD[i] == denomCD[j]) {
				numerator = numerator/numCD[i];
				denominator = denominator/denomCD[j];
				break;
			}
			
			if (numCD[i] <= denomCD[j])
				j--;
			else if (numCD[i] >= denomCD[j])
				i--;
		}
		signCheck();
	}
	
	/**
	 * Verifies the sign is with the numerator if at all
	 */
	public void signCheck() {
		if (numerator <  0 && denominator < 0) {
			numerator = Math.abs(numerator);
			denominator = Math.abs(denominator);
		}
		else if (numerator >  0 && denominator < 0) {
			numerator = -numerator;
			denominator = Math.abs(denominator);
		}
	}
	
	/**
	 * Gets the common divisors of given number
	 * @param num
	 * 	A integer to find the common divisor from
	 * @return
	 * 	Returns the array of common divisors sorted from
	 *  smallest to largest
	 */
	public int[] getCommonDivisors(int num) {
		ArrayList<Integer> comDiv = new ArrayList<Integer>();
		int[] sortedComDiv;
		int greatestFactor = num;
		for (int i = 1; Math.pow(i, 2) <= greatestFactor; i++)
			if (greatestFactor%i == 0) {
				int j = greatestFactor/i;
				comDiv.add(i);
				comDiv.add(j);			}
		
		sortedComDiv = new int[comDiv.size()];
		for (int i = 0; i < sortedComDiv.length; i++) {
			sortedComDiv[i] = comDiv.get(i);
		}
		Arrays.sort(sortedComDiv);
		
		return sortedComDiv;
	}
	
	/**
	 * Returns true is the value is equal to passed value.
	 * @param f Fraction
	 * 	A Fraction to compare with
	 * @return
	 * 	true if they are equal
	 */
	public boolean equals(Fraction f) {
		if (this.numerator == f.numerator 
				&& this.denominator == f.denominator)
			return true;
		
		return false;
	}
	
	/**
	 * Returns true is the value is less to passed value.
	 * @param f
	 * 	A Fraction to compare with
	 * @return
	 * 	true if the object is smaller
	 */
	public boolean isLess(Fraction f) {
		int tmp = this.numerator * f.denominator;
		int tmp1 = f.numerator * this.denominator;
		if (tmp < tmp1)
			return true;
		
		return false;
	}
	
	/**
	 * Returns true is the value is less or equal to passed value.
	 * @param f
	 * 	A Fraction to compare with
	 * @return
	 * 	true is the ojbect value is less or equal
	 */
	public boolean isLessEqual(Fraction f) {
		return this.isLess(f) || this.equals(f);
	}
	
	/**
	 * Returns true is the value is greater to passed value.
	 * @param f
	 * 	A Fraction to compare with
	 * @return
	 * 	true is the ojbect value is greater
	 */
	public boolean isGreater(Fraction f) {
		int tmp = this.numerator * f.denominator;
		int tmp1 = f.numerator * this.denominator;
		if (tmp > tmp1)
			return true;
		
		return false;
	}
	
	/**
	 * Returns true is the value is greater or equal to passed value.
	 * @param f
	 * 	A Fraction to compare with
	 * @return
	 * 	true is the ojbect value is greater or equal
	 */
	public boolean isGreaterEqual(Fraction f) {
		return this.isGreater(f) || this.equals(f);
	}
	
	/**
	 * The refence for the lowest value in the ArrayList.
	 * @param list
	 * 	An ArrayList of Fractions
	 * @return
	 * 	The refence in the Arraylist that has the smallest value.
	 */
	public static int minRef(ArrayList<Fraction> list) {
		int ref = 0;
		Fraction tmp = new Fraction();
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) 
				tmp = list.get(i);
			else
				if (tmp.isGreater(list.get(i))) {
					tmp = list.get(i);
					ref = i;
				}
		}
		return ref;
	}
	
	/**
	 * Overrides Object toString() to return a string
	 * that properly represents fraction or whole number
	 */
	public String toString() {
		String frac = new String();
		if (denominator == 1)
			frac += numerator;
		else
			frac += "<sup>"+numerator+"</sup>/<sub>"+denominator+ "</sub>";
		
		return frac;
	}
	
	/**
	 * Strictly the terminal value of the string.
	 * @return
	 * 	Just the integer string with a '/'
	 */
	public String toVal() {
		String frac = new String();
		if (denominator == 1)
			frac += numerator;
		else
			frac += numerator+"/"+denominator;
		
		return frac;
	}
}
