package IVS;

/**
 * @file IVSMath.java
 * @author Tom
 * @version 1.3
 * @todo Add the rest of the operations
 * Represents math library interface
 *
 */
public interface IVSMath {

	/**
	 * Changes base of inner value
	 * Note that decimal places are trimmed
	 * @param base New base of inner value
	 * @throws IVSNegativeValueException Thrown when invalid base is given
	 */
	public void changeBase(int base) throws IVSNegativeValueException;
	
	/**
	 * Reset the inner value to default (0)
	 */
	public void reset();

	/**
	 * Calculates power by given value
	 * @param value Value to be used as exponent
	 */
	public void pow(IVSNumber value);

	/**
	 * Adds value to inner value
	 * @param value Value to be added. Note that when null is used, no operation is done.
	 */
	public void add(IVSNumber value);

	/**
	 * Subtracts value from inner value
	 * @param value Value to be subtracted. Note that when null is used, no operation is done.
	 */
	public void sub(IVSNumber value);

	/**
	 * Multiplies the inner value
	 * @param value Value to multiply the inner value by. Note that when null is used, no operation is done.
	 */
	public void mul(IVSNumber value);

	/**
	 * Divides the inner value
	 * Note that if given value and inner value are integers, integer division is applied. When value is zero, exception is thrown
	 * @param value Value to divide the inner value by. Note that when null is used, no operation is done.
	 * @throws IVSDivisionByZeroException Thrown when value is zero
	 */
	public void div(IVSNumber value) throws IVSDivisionByZeroException;

	/**
	 * Calculates factorial from given value
	 * Note that this replaces the inner value with result of this operation
	 * @param value Value to make factorial from. Note that when null is used, no operation is done.
	 * @throws IVSNegativeValueException Thrown when value is not positive integer or zero
	 */
	public void fac(IVSNumber value) throws IVSNegativeValueException;

	/**
	 * Calculates factorial from inner value
	 * @throws IVSNegativeValueException Thrown when inner value is not positive integer or zero
	 */
	public void fac() throws IVSNegativeValueException;

	/**
	 * Calculates absolute value from inner value
	 */
	public void abs();

	/**
	 * Calculates square root from inner value
	 * @throws IVSNegativeValueException Thrown when value is negative
	 */
	public void sqrt() throws IVSNegativeValueException;

	/**
	 * Sets the inner value
	 * @param value Value to replace inner value with. Note that when null is used, no operation is done.
	 */
	public void setValue(IVSNumber value);

	/**
	 * Returns inner value
	 * Note that this never returns null. If no inner value has been set, default value is returned
	 * @return Inner value
	 */
	public IVSNumber getValue();

	/**
	 * Sets the inner value to PI
	 */
	public void setPI();

	/**
	 * Calculates exponential for given value
	 * Note that this replaces the inner value with result of this operation
	 * @param value Value to calculate exponential for. Note that when null is used, no operation is done.
	 */
	public void setExp(IVSNumber value);

	/**
	 * Calculates exponential for inner value
	 */
	public void setExp();

	/**
	 * Calculates the formula and stores the result
	 * @param formula String representation of formula. Note that when null is used, no operation is done.
	 * @param base Base of the formula
	 * @throws IVSInvalidFormulaException Thrown when formula is not correct
	 * @throws IVSNegativeValueException Thrown when negative number was used where only positive integers were allowed
	 * @throws IVSDivisionByZeroException Thrown when division by zero occurs
	 */
	public void calculateFormula(String formula, int base) throws IVSInvalidFormulaException, IVSNegativeValueException, IVSDivisionByZeroException;
}

class IVSInvalidFormulaException extends Exception {

	private static final long serialVersionUID = 5406237400359121268L;

}

class IVSDivisionByZeroException extends RuntimeException {

	private static final long serialVersionUID = 1191828490472571841L;

}

class IVSNegativeValueException extends RuntimeException {

	private static final long serialVersionUID = 1191828490472571841L;

}
