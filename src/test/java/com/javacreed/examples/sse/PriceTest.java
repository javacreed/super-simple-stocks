package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class PriceTest {

  @Test
  public void invalidInputs() {
    final Set<String> inputs = new LinkedHashSet<>();
    inputs.add("");
    inputs.add("Hello");
    inputs.add("0"); /* The price cannot be 0 or less */
    inputs.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"); /* Test extreme value */

    for (final String input : inputs) {
      try {
        Price.of(input);
        Assert.fail("The input '" + input + "' is invalid and should have failed");
      } catch (final IllegalArgumentException e) {
        /* Expected an exception */
      }
    }
  }

  @Test
  public void nullInputs() {
    try {
      Price.of((BigDecimal) null);
      Assert.fail("null should not be accepted as input");
    } catch (final NullPointerException e) {
      /* Expected an exception */
    }

    try {
      Price.of((String) null);
      Assert.fail("null should not be accepted as input");
    } catch (final NullPointerException e) {
      /* Expected an exception */
    }
  }

  @Test
  public void validInputs() {
    final Set<String> inputs = new LinkedHashSet<>();
    inputs.add("0.000001");
    inputs.add("1");
    inputs.add("11.25");
    inputs.add("100");
    inputs.add("1000000000.000000");

    for (final String input : inputs) {
      final Price price = Price.of(input);
      Assert.assertNotNull(price);
      Assert.assertFalse(price.isZero());
      Assert.assertEquals(new BigDecimal(input), price.getValue());
      Assert.assertEquals(input, price.toString());
    }
  }
}
