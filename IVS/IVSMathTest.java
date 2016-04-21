package IVS;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IVSMathTest extends Assert {

	private IVSMath lib;

	private static final IVSNumber getByObject(Object object) {
		if (object == null) {
			return null;
		} else {
			if (object instanceof Double) {
				return new IVSNumber((double) object);
			} else {
				return new IVSNumber((int) object);
			}
		}
	}

	private static final boolean advArithmeticsAssert(IVSMath lib, String op, Object operand1, Object operand2, Object result) {
		return advArithmeticsAssert(lib, op, operand1, operand2, result, false, false);
	}

	private static final boolean advArithmeticsAssert(IVSMath lib, String op, Object operand1, Object operand2, Object result, boolean allowZeroDivision, boolean allowNegativeException) {
		final IVSNumber op1 = getByObject(operand1);
		final IVSNumber op2 = getByObject(operand2);
		final IVSNumber res = getByObject(result);
		lib.setValue(op1);
		switch (op) {
			case "+":
				lib.add(op2);
			break;
			case "-":
				lib.sub(op2);
			break;
			case "*":
				lib.mul(op2);
			break;
			case "/":
				try {
					lib.div(op2);
				} catch (IVSDivisionByZeroException e) {
					return allowZeroDivision;
				}
			break;
			case "!":
				try {
					if (op2 == null) {
						lib.fac();
					} else {
						lib.fac(op2);
					}
				} catch (IVSNegativeValueException e) {
					return allowNegativeException;
				}
			break;
			case "|":
				lib.abs();
			break;
			case "'":
				try {
					lib.sqrt();
				} catch (IVSNegativeValueException e) {
					return allowNegativeException;
				}
			break;
			case "E":
				if (op2 == null) {
					lib.setExp();
				} else {
					lib.setExp(op2);
				}
			break;
		}
		IVSNumber libValue = lib.getValue();
		return libValue.equals(res);
	}

	@Before
	public final void reset() {
		lib = new IVSMathImpl();
	}

	@Test
	public final void testReset() {
		assertNotNull(lib.getValue());
		assertEquals(lib.getValue(), new IVSNumber(0));
		lib.setValue(new IVSNumber(5));
		assertEquals(lib.getValue(), new IVSNumber(5));
		lib.reset();
		assertEquals(lib.getValue(), new IVSNumber(0));
	}

	@Test
	public final void testAdd() {
		assertTrue(advArithmeticsAssert(lib, "+", 1, 2, 3));
		assertTrue(advArithmeticsAssert(lib, "+", 0, 1.0, 1));
		assertTrue(advArithmeticsAssert(lib, "+", -5, 2, -3));
		assertTrue(advArithmeticsAssert(lib, "+", 0.7, 2, 2.7));
		assertTrue(advArithmeticsAssert(lib, "+", 0.7, -2.5, -1.8));
		assertTrue(advArithmeticsAssert(lib, "+", 0.7, null, 0.7));
		assertTrue(advArithmeticsAssert(lib, "+", 0.7, Double.NaN, Double.NaN));
	}

	@Test
	public final void testSub() {
		assertTrue(advArithmeticsAssert(lib, "-", 1, 2, -1));
		assertTrue(advArithmeticsAssert(lib, "-", 1, 0, 1));
		assertTrue(advArithmeticsAssert(lib, "-", 0, 1.0, -1));
		assertTrue(advArithmeticsAssert(lib, "-", -5, 2, -7));
		assertTrue(advArithmeticsAssert(lib, "-", 0.7, 2, -1.3));
		assertTrue(advArithmeticsAssert(lib, "-", 0.7, -2.5, 3.2));
		assertTrue(advArithmeticsAssert(lib, "-", 0.7, null, 0.7));
		assertTrue(advArithmeticsAssert(lib, "-", 0.7, Double.NaN, Double.NaN));
	}

	@Test
	public final void testMul() {
		assertTrue(advArithmeticsAssert(lib, "*", 1, 2, 2));
		assertTrue(advArithmeticsAssert(lib, "*", 1, 0, 0));
		assertTrue(advArithmeticsAssert(lib, "*", 0, 1.0, 0));
		assertTrue(advArithmeticsAssert(lib, "*", 2, 1.0, 2));
		assertTrue(advArithmeticsAssert(lib, "*", -5, 2, -10));
		assertTrue(advArithmeticsAssert(lib, "*", 0.7, 2, 1.4));
		assertTrue(advArithmeticsAssert(lib, "*", 0.7, -2.5, -1.75));
		assertTrue(advArithmeticsAssert(lib, "*", 0.7, null, 0.7));
	}

	@Test
	public final void testDiv() {
		assertTrue(advArithmeticsAssert(lib, "/", 1, 2, 0));
		assertTrue(advArithmeticsAssert(lib, "/", 1.0, 2, 0.5));
		assertTrue(advArithmeticsAssert(lib, "/", 1, 0, null, true, false));
		assertTrue(advArithmeticsAssert(lib, "/", -1, 0, null, true, false));
		assertTrue(advArithmeticsAssert(lib, "/", 0, 1.0, 0));
		assertTrue(advArithmeticsAssert(lib, "/", 2, 1.0, 2));
		assertTrue(advArithmeticsAssert(lib, "/", -5, 2.0, -2.5));
		assertTrue(advArithmeticsAssert(lib, "/", 0.7, 2, 0.35));
		assertTrue(advArithmeticsAssert(lib, "/", 0.7, -2.5, -0.7 / 2.5));
		assertTrue(advArithmeticsAssert(lib, "/", 0.7, null, 0.7));
	}

	@Test
	public final void testFacIVSNumber() {
		assertTrue(advArithmeticsAssert(lib, "!", 0, 0, 1));
		assertTrue(advArithmeticsAssert(lib, "!", 1, 1, 1));
		assertTrue(advArithmeticsAssert(lib, "!", 2, 2, 2));
		assertTrue(advArithmeticsAssert(lib, "!", 5, 5, 120));
		assertTrue(advArithmeticsAssert(lib, "!", -5, -5, 1, false, true));
	}

	@Test
	public final void testFac() {
		assertTrue(advArithmeticsAssert(lib, "!", 0, null, 1));
		assertTrue(advArithmeticsAssert(lib, "!", 1, null, 1));
		assertTrue(advArithmeticsAssert(lib, "!", 2, null, 2));
		assertTrue(advArithmeticsAssert(lib, "!", 5, null, 120));
		assertTrue(advArithmeticsAssert(lib, "!", -5, null, 1, false, true));
	}

	@Test
	public final void testAbs() {
		assertTrue(advArithmeticsAssert(lib, "|", 0, null, 0));
		assertTrue(advArithmeticsAssert(lib, "|", 5, null, 5));
		assertTrue(advArithmeticsAssert(lib, "|", -5, null, 5));
		assertTrue(advArithmeticsAssert(lib, "|", Double.NaN, null, Double.NaN));
	}

	@Test
	public final void testSqrt() {
		assertTrue(advArithmeticsAssert(lib, "'", 0, null, 0));
		assertTrue(advArithmeticsAssert(lib, "'", 1, null, 1));
		assertTrue(advArithmeticsAssert(lib, "'", 25, null, 5));
		assertTrue(advArithmeticsAssert(lib, "'", -25, null, null, false, true));
		assertTrue(advArithmeticsAssert(lib, "'", Double.NaN, null, Double.NaN));
	}

	@Test
	public final void testSetValue() {
		lib.setValue(new IVSNumber(42));
		assertEquals(lib.getValue(), new IVSNumber(42));
		lib.setValue(new IVSNumber(42.0));
		assertEquals(lib.getValue(), new IVSNumber(42));
		lib.setValue(null);
		assertEquals(lib.getValue(), new IVSNumber(42));
		lib.setValue(new IVSNumber(Double.NaN));
		assertEquals(lib.getValue(), new IVSNumber(Double.NaN));
	}

	@Test
	public final void testSetPI() {
		lib.setPI();
		assertEquals(lib.getValue(), new IVSNumber(Math.PI));
	}

	@Test
	public final void testSetExpIVSNumber() {
		assertTrue(advArithmeticsAssert(lib, "E", 5, null, Math.exp(5)));
		assertTrue(advArithmeticsAssert(lib, "E", -5, null, Math.exp(-5)));
		assertTrue(advArithmeticsAssert(lib, "E", 0, null, 1));
	}

	@Test
	public final void testSetExp() {
		assertTrue(advArithmeticsAssert(lib, "E", null, 5, Math.exp(5)));
		assertTrue(advArithmeticsAssert(lib, "E", null, -5, Math.exp(-5)));
		assertTrue(advArithmeticsAssert(lib, "E", null, 0, 1));
	}

	private static boolean advFormulaAssert(IVSMath lib, String formula, int base, Object result, boolean allowNegative, boolean allowDivision, boolean allowInvalidFormula) {
		lib.reset();
		final IVSNumber res = getByObject(result);
		try {
			lib.calculateFormula(formula, base);
			IVSNumber libValue = lib.getValue();
			return libValue.equals(res);
		} catch (IVSNegativeValueException e) {
			return allowNegative;
		} catch (IVSDivisionByZeroException e) {
			return allowDivision;
		} catch (IVSInvalidFormulaException e) {
			return allowInvalidFormula;
		}
	}

	@Test
	public final void testCalculateFormula() {
		assertTrue(advFormulaAssert(lib, "1+1", 10, 2, false, false, false));
		assertTrue(advFormulaAssert(lib, "1/(5*0)", 10, null, false, true, false));
		assertTrue(advFormulaAssert(lib, "B+B", 16, 22, false, false, false));
		assertTrue(advFormulaAssert(lib, "B+B+", 16, null, false, false, true));
		assertTrue(advFormulaAssert(lib, "5+0.1", 10, 5.1, false, false, false));
		assertTrue(advFormulaAssert(lib, "A+1", 16, 11, false, false, false));
		assertTrue(advFormulaAssert(lib, "5!", 10, 120, false, false, false));
		assertTrue(advFormulaAssert(lib, "-5!", 10, null, true, false, false));
		assertTrue(advFormulaAssert(lib, "-(5!)", 10, -120, true, false, false));
		assertTrue(advFormulaAssert(lib, "2^8", 10, 256, false, false, false));
		assertTrue(advFormulaAssert(lib, "1010+1", 2, 11, false, false, false));
		assertTrue(advFormulaAssert(lib, "A+B", 10, null, false, false, true));
		assertTrue(advFormulaAssert(lib, "A+B", 17, 21, false, false, true));
	}
}
