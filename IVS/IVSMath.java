package IVS;

/**
 * @file IVSMath.java
 * @author Tom
 * @version 1.0
 * @todo Add the rest of the operations
 * Represents math library interface
 *
 */
public interface IVSMath {

	/**
	 * Reset the inner value to default (0)
	 */
	public void reset();

	/**
	 * Adds value to inner value
	 * @param value Value to be added
	 */
	public void add(IVSNumber value);
	
	/**
	 * Subtracts value from inner value
	 * @param value Value to be subtracted
	 */
	public void sub(IVSNumber value);
	
	/**
	 * Multiplies the inner value
	 * @param value Value to multiply the inner value by
	 */
	public void mul(IVSNumber value);
	
	/**
	 * Divides the inner value
	 * Note that if given value and inner value are integers, integer division is applied
	 * @param value Value to divide the inner value by
	 */
	public void div(IVSNumber value);
	
	/**
	 * Calculates factorial from given value
	 * Note that this replaces the inner value with result of this operation
	 * @param value Value to make factorial from
	 */
	public void fac(IVSNumber value);
	
	/**
	 * Calculates factorial from inner value
	 */
	public void fac();
	
	/**
	 * Calculates absolute value from inner value
	 */
	public void abs();
	
	/**
	 * Calculates square root from inner value 
	 */
	public void sqrt();
	
	/**
	 * Sets the inner value
	 * @param value Value to replace inner value with
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
	 * @param value Value to calculate exponential for
	 */
	public void setExp(IVSNumber value);
	
	/**
	 * Calculates exponential for inner value
	 */
	public void setExp();
}
