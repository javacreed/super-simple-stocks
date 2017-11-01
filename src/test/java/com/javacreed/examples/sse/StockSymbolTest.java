package com.javacreed.examples.sse;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class StockSymbolTest {

  @Test
  public void invalidInputs() {
    final Set<String> inputs = new LinkedHashSet<>();
    inputs.add("f"); /* Symbols should be upper-case */
    inputs.add("T1"); /* Symbols do not contain numbers */
    inputs.add("Tö"); /* Symbols do not contain non-ASCII characters */
    inputs.add("ABCDEFG"); /* Symbols should not be longer than 6 letters */

    for (final String input : inputs) {
      try {
        StockSymbol.of(input);
        Assert.fail("The input '" + input + "' is invalid and should have failed");
      } catch (final IllegalArgumentException e) {
        /* Expected an exception */
      }
    }
  }

  @Test
  public void validInputs() {
    final Set<String> inputs = new LinkedHashSet<>();
    inputs.add("F");
    inputs.add("TEA");
    inputs.add("POP");
    inputs.add("ABCDEF");

    for (final String input : inputs) {
      final StockSymbol stockSymbol = StockSymbol.of(input);
      Assert.assertNotNull(stockSymbol);
      Assert.assertEquals(input, stockSymbol.getSymbol());
    }
  }
}
