package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class TickerPriceTest {

  @Test
  public void invalidInputs() {
    final Set<String> inputs = new LinkedHashSet<>();
    inputs.add("-1"); /* The price cannot be negative */
    inputs.add("somthing"); /* The price needs to be a number */
    inputs.add(""); /* The price cannot be empty */
    inputs.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"); /* Test extreme value */

    for (final String input : inputs) {
      try {
        TickerPrice.of(input);
        Assert.fail("The input '" + input + "' is invalid and should have failed");
      } catch (final IllegalArgumentException e) {
        /* Expected an exception */
      }
    }
  }

  @Test
  public void nullInputs() {
    try {
      TickerPrice.of((String) null);
      Assert.fail("null should not be accepted as input");
    } catch (final NullPointerException e) {
      /* Expected an exception */
    }
    try {
      TickerPrice.of((BigDecimal) null);
      Assert.fail("null should not be accepted as input");
    } catch (final NullPointerException e) {
      /* Expected an exception */
    }
  }

  @Test
  public void validInputs() {
    final Set<String> inputs = new LinkedHashSet<>();
    inputs.add("0"); /* Lower limit */
    inputs.add("10");
    inputs.add("11.55");
    inputs.add("1000000000.000000"); /* A large value (note that there is no upper limit for now) */

    for (final String input : inputs) {
      final TickerPrice price = TickerPrice.of(input);
      Assert.assertNotNull(price);
      Assert.assertEquals(input, price.toString());
      Assert.assertEquals(new BigDecimal(input), price.getValue());
    }
  }
}
