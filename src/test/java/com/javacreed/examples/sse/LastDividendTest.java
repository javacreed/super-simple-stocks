package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class LastDividendTest {

  @Test
  public void invalidInputs() {
    final Set<String> inputs = new LinkedHashSet<>();
    inputs.add("");
    inputs.add("Hello");
    inputs.add("-1"); /* The dividend be less than 0 */

    for (final String input : inputs) {
      try {
        LastDividend.of(input);
        Assert.fail("The input '" + input + "' is invalid and should have failed");
      } catch (final IllegalArgumentException e) {
        /* Expected an exception */
      }
    }
  }

  @Test
  public void nullInputs() {
    try {
      LastDividend.of((BigDecimal) null);
      Assert.fail("null should not be accepted as input");
    } catch (final NullPointerException e) {
      /* Expected an exception */
    }

    try {
      LastDividend.of((String) null);
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
    inputs.add("1000000000.000000");

    for (final String input : inputs) {
      final LastDividend dividend = LastDividend.of(input);
      Assert.assertNotNull(dividend);
      Assert.assertEquals(new BigDecimal(input), dividend.getValue());
      Assert.assertEquals(input, dividend.toString());
    }
  }

  @Test
  public void zero() {
    /* The same instance should be returned */
    Assert.assertSame(LastDividend.zero(), LastDividend.zero());

    /* Zero is Zero!! */
    Assert.assertTrue(LastDividend.zero().isZero());

    /* All variants of zero should return the same zero instance irrespective from the scale */
    final String[] zeros = { "0", "0.000000" };
    for (final String zero : zeros) {
      Assert.assertSame(LastDividend.zero(), LastDividend.of(zero));
    }
  }
}
