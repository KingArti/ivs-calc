package IVS;

import java.util.ArrayList;
import java.util.List;

import IVS.IVSNumber.IVSNumberException;

public class IVSMathImpl implements IVSMath {

	private IVSNumber innerValue = defaultValue;

	private static final IVSNumber defaultValue = new IVSNumber(0);
	private static final IVSNumber piValue = new IVSNumber(Math.PI);

	@Override
	public void reset() {
		innerValue = defaultValue;
	}

	@Override
	public void add(IVSNumber value) {
		if (value != null) {
			if (value.isInteger()) {
				if (innerValue.isInteger()) {
					innerValue = new IVSNumber(innerValue.getIntValue() + value.getIntValue());
				} else {
					innerValue = new IVSNumber(innerValue.getDoubleValue() + ((double) value.getIntValue()));
				}
			} else {
				if (innerValue.isInteger()) {
					innerValue = new IVSNumber(((double) innerValue.getIntValue()) + value.getDoubleValue());
				} else {
					innerValue = new IVSNumber(innerValue.getDoubleValue() + value.getDoubleValue());
				}
			}
		}
	}

	@Override
	public void sub(IVSNumber value) {
		if (value != null) {
			if (value.isInteger()) {
				if (innerValue.isInteger()) {
					innerValue = new IVSNumber(innerValue.getIntValue() - value.getIntValue());
				} else {
					innerValue = new IVSNumber(innerValue.getDoubleValue() - ((double) value.getIntValue()));
				}
			} else {
				if (innerValue.isInteger()) {
					innerValue = new IVSNumber(((double) innerValue.getIntValue()) - value.getDoubleValue());
				} else {
					innerValue = new IVSNumber(innerValue.getDoubleValue() - value.getDoubleValue());
				}
			}
		}
	}

	@Override
	public void mul(IVSNumber value) {
		if (value != null) {
			if (value.isInteger()) {
				if (innerValue.isInteger()) {
					innerValue = new IVSNumber(innerValue.getIntValue() * value.getIntValue());
				} else {
					innerValue = new IVSNumber(innerValue.getDoubleValue() * ((double) value.getIntValue()));
				}
			} else {
				if (innerValue.isInteger()) {
					innerValue = new IVSNumber(((double) innerValue.getIntValue()) * value.getDoubleValue());
				} else {
					innerValue = new IVSNumber(innerValue.getDoubleValue() * value.getDoubleValue());
				}
			}
		}
	}

	@Override
	public void div(IVSNumber value) {
		if (value != null) {
			if (value.isInteger()) {
				if (value.getIntValue() == 0) {
					throw new IVSDivisionByZeroException();
				}
				if (innerValue.isInteger()) {
					innerValue = new IVSNumber(innerValue.getIntValue() / value.getIntValue());
				} else {
					innerValue = new IVSNumber(innerValue.getDoubleValue() / ((double) value.getIntValue()));
				}
			} else {
				if (value.getDoubleValue() == 0) {
					throw new IVSDivisionByZeroException();
				}
				if (innerValue.isInteger()) {
					innerValue = new IVSNumber(((double) innerValue.getIntValue()) / value.getDoubleValue());
				} else {
					innerValue = new IVSNumber(innerValue.getDoubleValue() / value.getDoubleValue());
				}
			}
		}
	}

	@Override
	public void fac(IVSNumber value) throws IVSNegativeValueException {
		if (value != null) {
			if (value.isInteger()) {
				int val = value.getIntValue();
				if (val < 0) {
					throw new IVSNegativeValueException();
				}
				int res = 1;
				for (int i = val; i > 0; i--) {
					res *= i;
				}
				innerValue = new IVSNumber(res);
			} else {
				throw new IVSNegativeValueException();
			}
		}
	}

	@Override
	public void fac() throws IVSNegativeValueException {
		fac(innerValue);
	}

	@Override
	public void abs() {
		if (innerValue.isInteger()) {
			int value = innerValue.getIntValue();
			if (value < 0) {
				innerValue = new IVSNumber(-value);
			}
		} else {
			double value = innerValue.getDoubleValue();
			if (value < 0) {
				innerValue = new IVSNumber(-value);
			}
		}
	}

	@Override
	public void sqrt() throws IVSNegativeValueException {
		if (innerValue.isInteger()) {
			int value = innerValue.getIntValue();
			if (value < 0) {
				throw new IVSNegativeValueException();
			} else {
				innerValue = new IVSNumber(Math.sqrt(value));
			}
		} else {
			double value = innerValue.getDoubleValue();
			if (value < 0) {
				throw new IVSNegativeValueException();
			} else {
				innerValue = new IVSNumber(Math.sqrt(value));
			}
		}
	}

	@Override
	public void setValue(IVSNumber value) {
		if (value != null) {
			innerValue = value;
		}
	}

	@Override
	public IVSNumber getValue() {
		return innerValue;
	}

	@Override
	public void setPI() {
		innerValue = piValue;
	}

	@Override
	public void setExp(IVSNumber value) {
		if (value != null) {
			if (value.isInteger()) {
				int valuee = value.getIntValue();
				innerValue = new IVSNumber(Math.exp(valuee));
			} else {
				double valuee = value.getDoubleValue();
				innerValue = new IVSNumber(Math.exp(valuee));
			}
		}
	}

	@Override
	public void setExp() {
		setExp(innerValue);
	}

	@Override
	public void calculateFormula(String formula, int base) throws IVSNegativeValueException, IVSDivisionByZeroException, IVSInvalidFormulaException {
		IntegerFormulaParser parser = new IntegerFormulaParser(formula, base);
		IVSNumber result;
		if (base <= 0) {
			throw new IVSNegativeValueException();
		}
		try {
			result = parser.getResult();
			if (base == 10) {
				innerValue = result;
			} else {
				try {
					if (result.isInteger()) {
						innerValue = new IVSNumber(Integer.toString(result.getIntValue(), base), base);
					} else {
						innerValue = result;
					}
				} catch (IVSNumberException e) {
					innerValue = result;
				}
			}
		} catch (IVSNegativeValueException | IVSDivisionByZeroException | IVSInvalidFormulaException e) {
			throw e;
		}
	}

	@Override
	public void pow(IVSNumber value) {
		if (value != null) {
			if (value.isInteger()) {
				if (innerValue.isInteger()) {
					innerValue = new IVSNumber(Math.pow(innerValue.getIntValue(), value.getIntValue()));
				} else {
					innerValue = new IVSNumber(Math.pow(innerValue.getDoubleValue(), ((double) value.getIntValue())));
				}
			} else {
				if (innerValue.isInteger()) {
					innerValue = new IVSNumber(Math.pow(((double) innerValue.getIntValue()), value.getDoubleValue()));
				} else {
					innerValue = new IVSNumber(Math.pow(innerValue.getDoubleValue(), value.getDoubleValue()));
				}
			}
		}
	}

	@Override
	public void changeBase(int base) throws IVSNegativeValueException {
		if (base <= 0) {
			throw new IVSNegativeValueException();
		}
		int val = 0;
		if (innerValue.isInteger()) {
			val = innerValue.getIntValue();
		} else {
			val = (int) innerValue.getDoubleValue();
		}
		try {
			innerValue = new IVSNumber(Integer.toString(val, base));
		} catch (IVSNumberException e) {
			throw new IVSNegativeValueException();
		}
	}
}

/**
 * Expression formula parser
 * Note that this only works with Integers with any base OR decadic numbers (base 10)
 * @author Tom
 * @version 1.0
 */
class IntegerFormulaParser {

	/**
	 * Used when no more tokens are available, but required
	 * @author Tom
	 *
	 */
	private class TokenEndException extends Exception {

		private static final long serialVersionUID = 6990153259701972424L;

	}

	/**
	 * Inner calculator
	 */
	private final IVSMath lib = new IVSMathImpl();

	/**
	 * List of tokens
	 */
	private final List<String> tokens;

	/**
	 * Index of current token
	 */
	private int currentToken = 0;

	/**
	 * Total number of tokens
	 */
	private final int tokenCount;

	/**
	 * Base of the numbers used in formula
	 */
	private final int base;

	/**
	 * Stack structure
	 */
	private final List<IVSNumber> stack = new ArrayList<>();

	/**
	 * Push number to stack
	 * @param number Number to be pushed into stack
	 */
	private void push(IVSNumber number) {
		stack.add(number);
	}

	/**
	 * Pops number from stack
	 * @return Integer from top of stack
	 */
	private IVSNumber pop() {
		return stack.remove(stack.size() - 1);
	}

	/**
	 * Gets next token from token list
	 * @return String representation of the token
	 * @throws TokenEndException Thrown when end of list is reached
	 */
	private String getNextToken() throws TokenEndException {
		if (currentToken == tokenCount) {
			throw new TokenEndException();
		} else {
			currentToken++;
			return getToken();
		}
	}

	/**
	 * Doesn't process the token, only returns it
	 * @return Current string representation of the token
	 */
	private String getToken() {
		return tokens.get(currentToken);
	}

	/**
	 * The constructor
	 * @param formula Formula to be calculated 
	 * @param base Base of numbers used in formula
	 */
	public IntegerFormulaParser(String formula, int base) {
		String[] replaces = new String[] { "\\(", "\\)", "\\+", "-", "!", "\\*", "/", "\\^" };
		for (String replace : replaces) {
			formula = formula.replaceAll(replace, " " + replace + " ");
		}
		formula = formula.replaceAll("\\s+", " ");
		String[] tokenArr = formula.split(" ");
		List<String> tokens = new ArrayList<>();
		for (String s : tokenArr) {
			if (!s.isEmpty()) {
				tokens.add(s);
			}
		}
		tokens.add("");
		this.tokens = tokens;
		this.tokenCount = tokens.size();
		this.base = base;
	}

	/**
	 * Performs quick operator check
	 * @param token Token to be checked
	 * @return True if token is operator, false if otherwise
	 */
	private boolean isOperator(String token) {
		if (token == null) {
			return false;
		}
		switch (token) {
			case "+":
			case "-":
			case "!":
			case "*":
			case "/":
			case "^":
				return true;
			default:
				return false;
		}
	}

	/**
	 * Performs operation
	 * @param operator Operator
	 * @param operand1 Operand
	 * @param operand2 Operand. Note that when calculating factorial, this value is ignored
	 * @return
	 * @throws IVSInvalidFormulaException  Thrown when invalid operator is given
	 */
	private IVSNumber handleOperation(String operator, IVSNumber operand1, IVSNumber operand2) throws IVSInvalidFormulaException {
		switch (operator) {
			case "+":
				lib.setValue(operand1);
				lib.add(operand2);
				return lib.getValue();
			case "-":
				lib.setValue(operand1);
				lib.sub(operand2);
				return lib.getValue();
			case "!":
				lib.setValue(operand1);
				lib.fac();
				return lib.getValue();
			case "*":
				lib.setValue(operand1);
				lib.mul(operand2);
				return lib.getValue();
			case "/":
				lib.setValue(operand1);
				lib.div(operand2);
				return lib.getValue();
			case "^":
				lib.setValue(operand1);
				lib.pow(operand2);
				return lib.getValue();
			default: // This should never happen
				throw new IVSInvalidFormulaException();
		}
	}

	private boolean isBracket(String op) {
		if (op == null) {
			return false;
		}
		switch (op) {
			case "(":
			case ")":
				return true;
			default:
				return false;
		}
	}

	/**
	 * Performs quick number check
	 * @param token Checked token
	 * @return True if token is valid numebr, False if otherwise
	 */
	private boolean isNumber(String token) {
		if (token == null) {
			return false;
		}
		try {
			Integer.parseInt(token, base);
			return true;
		} catch (Exception e) {
			try {
				Double.parseDouble(token);
				return true;
			} catch (Exception ee) {
			}
			return false;
		}
	}

	/**
	 * Converts token to number
	 * Note that this doesn't perform type check
	 * @param token Token to be converter
	 * @return Integer value of the token number
	 * @throws IVSInvalidFormulaException Thrown when token is not a number
	 */
	private IVSNumber getNumber(String token) throws IVSInvalidFormulaException {
		try {
			return new IVSNumber(Integer.parseInt(token, base));
		} catch (Exception e) {
			if (base != 10) {
				throw new IVSInvalidFormulaException();
			}
			try {
				return new IVSNumber(Double.parseDouble(token));
			} catch (Exception ee) {
			}
		}
		throw new IVSInvalidFormulaException();
	}

	/**
	 * Performs the calculation and returns the result
	 * @return Result of the give formula
	 * @throws IVSNegativeValueException Thrown when negative number was found where possitive was expected
	 * @throws IVSDivisionByZeroException Thrown when division by zero occured
	 * @throws IVSInvalidFormulaException Thrown when formula is not valid
	 */
	public IVSNumber getResult() throws IVSNegativeValueException, IVSDivisionByZeroException, IVSInvalidFormulaException {
		try {
			boolean b = EXPR();
			if (b) {
				return pop();
			}
		} catch (TokenEndException e) {
		}
		throw new IVSInvalidFormulaException();
	}

	/**
	 * Parser function
	 * @return True if structure is valid, False if otherwise
	 * @throws TokenEndException Thrown when end of token list is reached
	 * @throws IVSInvalidFormulaException Thrown when invalid operator is found
	 */
	private boolean EXPR_MORE() throws TokenEndException, IVSInvalidFormulaException {
		String token = getToken();
		if (isOperator(token)) {
			IVSNumber op1 = pop();
			String op = token;
			if (op.equals("!")) {
				IVSNumber result = handleOperation(op, op1, new IVSNumber(0));
				push(result);
				getNextToken();
				return EXPR_MORE();
			} else {
				getNextToken();
				if (EXPR()) {
					IVSNumber op2 = pop();
					IVSNumber result = handleOperation(op, op1, op2);
					push(result);
					return true;
				}
			}
		} else {
			return true;
		}
		return false;
	}

	/**
	 * Parser function
	 * @return True if structure is valid, False if otherwise
	 * @throws TokenEndException Thrown when end of token list is reached
	 * @throws IVSInvalidFormulaException Thrown when invalid operator is found
	 */
	private boolean EXPR() throws TokenEndException, IVSInvalidFormulaException {
		String token = getToken();
		int minus = 1;
		if (token.equals("-")) {
			minus = -1;
			token = getNextToken();
		}
		if (isBracket(token)) {
			getNextToken();
			if (EXPR()) {
				IVSNumber value = pop();
				lib.setValue(value);
				lib.mul(new IVSNumber(minus));
				push(lib.getValue());
				token = getToken();
				if (token.equals(")")) {
					getNextToken();
					return EXPR_MORE();
				}
			}
		} else {
			if (isNumber(token)) {
				lib.setValue(getNumber(token));
				lib.mul(new IVSNumber(minus));
				push(lib.getValue());
				getNextToken();
				return EXPR_MORE();
			}
		}
		return false;
	}

}
