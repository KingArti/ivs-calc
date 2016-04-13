package IVS;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IVSMathTest extends Assert {

	private IVSMath lib;

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
		lib.setValue(new IVSNumber(5));
		lib.add(new IVSNumber(42));
		assertEquals(lib.getValue(), new IVSNumber(5 + 42));
		lib.setValue(new IVSNumber(5));
		lib.add(new IVSNumber(-42));
		assertEquals(lib.getValue(), new IVSNumber(5 - 42));
		lib.setValue(new IVSNumber(5));
		lib.add(new IVSNumber(42.99));
		assertEquals(lib.getValue(), new IVSNumber(5 + 42.99));
		lib.setValue(new IVSNumber(5));
		lib.add(null);
		assertEquals(lib.getValue(), new IVSNumber(5));
	}

	@Test
	public final void testSub() {
		lib.setValue(new IVSNumber(5));
		lib.sub(new IVSNumber(42));
		assertEquals(lib.getValue(), new IVSNumber(5 - 42));
		lib.setValue(new IVSNumber(5));
		lib.sub(new IVSNumber(-42));
		assertEquals(lib.getValue(), new IVSNumber(5 + 42));
		lib.setValue(new IVSNumber(5));
		lib.sub(new IVSNumber(42.99));
		assertEquals(lib.getValue(), new IVSNumber(5 - 42.99));
		lib.setValue(new IVSNumber(5));
		lib.sub(null);
		assertEquals(lib.getValue(), new IVSNumber(5));
	}

	@Test
	public final void testMul() {
		lib.setValue(new IVSNumber(5));
		lib.mul(new IVSNumber(42));
		assertEquals(lib.getValue(), new IVSNumber(5 * 42));
		lib.setValue(new IVSNumber(5));
		lib.mul(new IVSNumber(-42));
		assertEquals(lib.getValue(), new IVSNumber(5 * (-42)));
		lib.setValue(new IVSNumber(5));
		lib.mul(new IVSNumber(42.99));
		assertEquals(lib.getValue(), new IVSNumber(5 * 42.99));
		lib.setValue(new IVSNumber(5));
		lib.mul(null);
		assertEquals(lib.getValue(), new IVSNumber(5));
		lib.mul(new IVSNumber(0));
		assertEquals(lib.getValue(), new IVSNumber(0));
		lib.mul(new IVSNumber(Double.NaN));
		assertEquals(lib.getValue(), new IVSNumber(Double.NaN));
	}

	@Test
	public final void testDiv() {
		lib.setValue(new IVSNumber(5));
		lib.div(new IVSNumber(2));
		assertEquals(lib.getValue(), new IVSNumber(2));
		lib.setValue(new IVSNumber(5));
		lib.div(new IVSNumber(2.0));
		assertEquals(lib.getValue(), new IVSNumber(2.5));
		lib.setValue(new IVSNumber(5));
		try {
			lib.div(new IVSNumber(0));
			fail("Division by zero");
		} catch (IVSDivisionByZeroException e) {
		}
		assertEquals(lib.getValue(), new IVSNumber(5));
		try {
			lib.div(new IVSNumber(0.0));
			fail("Division by zero");
		} catch (IVSDivisionByZeroException e) {
		}
		assertEquals(lib.getValue(), new IVSNumber(5));
	}

	@Test
	public final void testFacIVSNumber() {
		lib.fac(new IVSNumber(5));
		assertEquals(lib.getValue(), new IVSNumber(120));
		lib.fac(new IVSNumber(0));
		assertEquals(lib.getValue(), new IVSNumber(1));
		try {
			lib.fac(new IVSNumber(-1));
			fail("Negative factorial");
		} catch (IVSNegativeValueException e) {
		}
		assertEquals(lib.getValue(), new IVSNumber(1));
	}

	@Test
	public final void testFac() {
		lib.setValue(new IVSNumber(5));
		lib.fac();
		assertEquals(lib.getValue(), new IVSNumber(120));
		lib.setValue(new IVSNumber(0));
		lib.fac();
		assertEquals(lib.getValue(), new IVSNumber(1));
		lib.setValue(new IVSNumber(-1));
		try {
			lib.fac();
			fail("Negative factorial");
		} catch (IVSNegativeValueException e) {
		}
		assertEquals(lib.getValue(), new IVSNumber(-1));
	}

	@Test
	public final void testAbs() {
		lib.setValue(new IVSNumber(5));
		lib.abs();
		assertEquals(lib.getValue(), new IVSNumber(5));
		lib.setValue(new IVSNumber(-5));
		lib.abs();
		assertEquals(lib.getValue(), new IVSNumber(5));
	}

	@Test
	public final void testSqrt() {
		lib.setValue(new IVSNumber(25));
		lib.sqrt();
		assertEquals(lib.getValue(), new IVSNumber(5));
		lib.setValue(new IVSNumber(-25));
		try {
			lib.sqrt();
			fail("Negative square root");
		} catch (IVSNegativeValueException e) {
		}
		assertEquals(lib.getValue(), new IVSNumber(-25));
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
		lib.setExp(new IVSNumber(5));
		assertEquals(lib.getValue(), new IVSNumber(Math.exp(5)));
		lib.setExp(new IVSNumber(-5));
		assertEquals(lib.getValue(), new IVSNumber(Math.exp(-5)));
		lib.setExp(new IVSNumber(0));
		assertEquals(lib.getValue(), new IVSNumber(1));
	}

	@Test
	public final void testSetExp() {
		lib.setValue(new IVSNumber(5));
		lib.setExp();
		assertEquals(lib.getValue(), new IVSNumber(Math.exp(5)));
		lib.setValue(new IVSNumber(-5));
		lib.setExp();
		assertEquals(lib.getValue(), new IVSNumber(Math.exp(-5)));
		lib.setValue(new IVSNumber(0));
		lib.setExp();
		assertEquals(lib.getValue(), new IVSNumber(1));
	}

	@Test
	public final void testCalculateFormula() {
		try {
			lib.calculateFormula("1 + 1", 10);
		} catch (IVSNegativeValueException | IVSDivisionByZeroException | IVSInvalidFormulaException e) {
			fail("Valid formula");
		}
		assertEquals(lib.getValue(), new IVSNumber(2));
		try {
			lib.calculateFormula("1 /(5*0)", 10);
		} catch (IVSNegativeValueException | IVSInvalidFormulaException e) {
			fail("Valid formula");
		} catch (IVSDivisionByZeroException e) {
			try {
				lib.calculateFormula("B+B", 16);
			} catch (IVSNegativeValueException | IVSDivisionByZeroException | IVSInvalidFormulaException ee) {
				fail("Valid formula");
			}
			assertEquals(lib.getValue(), new IVSNumber(22));
			try {
				lib.calculateFormula("B+B+", 16);
			} catch (IVSNegativeValueException | IVSDivisionByZeroException ee) {
				fail("Valid formula");
			} catch (IVSInvalidFormulaException ee) {
				return;
			}
			fail("Invalid formula");
		}
		fail("Invalid formula");
	}

}
