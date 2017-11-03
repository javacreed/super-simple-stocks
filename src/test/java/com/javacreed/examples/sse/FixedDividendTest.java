package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class FixedDividendTest {

  @Test
  public void invalidInputs() {
    final Set<String> inputs = new LinkedHashSet<>();
    inputs.add("");
    inputs.add("Hello");
    inputs.add("-1"); /* The dividend percentage cannot be less than 0 */
    inputs.add("101"); /* The dividend percentage cannot be more than 100 */

    for (final String input : inputs) {
      try {
        FixedDividend.of(input);
        Assert.fail("The input '" + input + "' is invalid and should have failed");
      } catch (final IllegalArgumentException e) {
        /* Expected an exception */
      }
    }
  }

  @Test
  public void nullInputs() {
    try {
      FixedDividend.of((BigDecimal) null);
      Assert.fail("null should not be accepted as input");
    } catch (final NullPointerException e) {
      /* Expected an exception */
    }

    try {
      FixedDividend.of((String) null);
      Assert.fail("null should not be accepted as input");
    } catch (final NullPointerException e) {
      /* Expected an exception */
    }
  }

  @Test
  public void validInputs() {
    final Set<String> inputs = new LinkedHashSet<>();
    inputs.add("0");
    inputs.add("1");
    inputs.add("11.25");
    inputs.add("100");

    for (final String input : inputs) {
      final FixedDividend dividend = FixedDividend.of(input);
      Assert.assertNotNull(dividend);
      Assert.assertEquals(input, dividend.toString());
    }
  }

  @Test
  public void zero() {
    /* The same instance should be returned */
    Assert.assertSame(FixedDividend.zero(), FixedDividend.zero());

    /* Zero is Zero!! */
    Assert.assertTrue(FixedDividend.zero().isZero());

    /* All variants of zero should return the same zero instance irrespective from the scale */
    final String[] zeros = { "0", "0.000000" };
    for (final String zero : zeros) {
      Assert.assertSame(FixedDividend.zero(), FixedDividend.of(zero));
    }
  }
}
