package IVS;

/**
 * @file IVSNumber.java
 * @author Tom
 * @version 1.0
 * Represents universal number for math library. Handles all necessary conversions, so no special data types are required
 *
 */
public class IVSNumber {

	/**
	 * The type of the value
	 */
	private TYPE type = TYPE.INT;
	
	/**
	 * Contains integer value, if given
	 */
	private int intValue = 0;
	
	/**
	 * Contains double value, if given
	 */
	private double doubleValue = 0d;
	
	/**
	 * Contains invalid value, if constructor fails
	 */
	private String invalidValue = "";
	
	/**
	 * Contains base of the value, if based integer is given
	 */
	private int base = 10;

	@Override
	/**
	 * Returns string representation of the value
	 * All necessary conversions are done here
	 */
	public String toString() {
		if (type == TYPE.INT) {
			return "" + intValue;
		} else if (type == TYPE.DOUBLE) {
			return "" + doubleValue;
		} else if (type == TYPE.INT_BASE) {
			return Integer.toString(intValue, base);
		}
		return invalidValue;
	}

	/**
	 * 
	 * @return True of value is regular integer, False otherwise
	 */
	protected boolean isInteger() {
		return type == TYPE.INT;
	}

	/**
	 * 
	 * @return True of value is double, False otherwise
	 */
	protected boolean isDouble() {
		return type == TYPE.DOUBLE;
	}

	/**
	 * 
	 * @return True of value is based integer, False otherwise
	 */
	protected boolean isBaseInteger() {
		return type == TYPE.INT_BASE;
	}

	/**
	 * When no integer value is contained, this always returns 0
	 * @return Integer value
	 */
	protected int getIntValue() {
		return intValue;
	}

	/**
	 * When no double value is contained, this always returns 0
	 * @return Double value
	 */
	protected double getDoubleValue() {
		return doubleValue;
	}

	/**
	 * When no base number is contained, this always returns 0
	 * @return Base of the value
	 */
	protected int getBase() {
		return base;
	}

	/**
	 * When constructor fails, and exception is raised, this function returns the invalid value that caused the exception
	 * @return Invalid value given IVSNumber constructor
	 */
	public String getInvalidValue() {
		return invalidValue;
	}

	/**
	 * IVSNumber constructor for regular numbers
	 * Integer and double literals are required
	 * @param Value Only Integer and Double are allowed
	 * @throws IVSNumberException Raised when invalid Value is passed
	 */
	public IVSNumber(Object Value) throws IVSNumberException {
		if (Value instanceof Integer) {
			intValue = (int) Value;
			type = TYPE.INT;
		} else if (Value instanceof Double) {
			doubleValue = (double) Value;
			type = TYPE.DOUBLE;
		} else {
			type = TYPE.INVALID;
			invalidValue = (String) Value;
			throw new IVSNumberException(this);
		}
	}

	/**
	 * IVSNumber constructor for based numbers.
	 * Value is stored as Integer, therefore all decimal places are removed
	 * @param Value String representation of value in given base
	 * @param Base Base format of the value
	 * @throws IVSNumberException Raised when invalid value or base are passed
	 */
	public IVSNumber(String Value, int Base) throws IVSNumberException {
		if (base <= 0 || Value == null) {
			throw new IVSNumberException(this);
		}
		base = Base;
		try {
			intValue = Integer.parseInt(Value, Base);
			type = TYPE.INT_BASE;
		} catch (NumberFormatException e) {
			invalidValue = Value;
			type = TYPE.INVALID;
			throw new IVSNumberException(this);
		}
	}

	/**
	 * Numeric type
	 * @author Tom
	 *
	 */
	private enum TYPE {
		INT, DOUBLE, INT_BASE, INVALID;
	}

	/**
	 * Invalid number passes in IVSNumber constructor raises this exception
	 * @author Tom
	 *
	 */
	public class IVSNumberException extends Exception {
		private static final long serialVersionUID = -4482955270457081733L;
		
		/**
		 * IVSNumber that raised the exception
		 */
		private IVSNumber source;

		/**
		 * 
		 * @return Source of the exception
		 */
		public IVSNumber getSource() {
			return source;
		}

		/**
		 * Constructor of the exception, allowed only in IVSNumber constructor
		 * @param Source Source of the exception
		 */
		private IVSNumberException(IVSNumber Source) {
			this.source = Source;
		}
	}
}
