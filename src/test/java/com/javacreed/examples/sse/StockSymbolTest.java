package com.javacreed.examples.sse;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class StockSymbolTest {

  @Test
  public void compareDifferent() {
    /* Instance 'b' should come before instance 'a' as the comparison is made alphabetically */
    final StockSymbol a = StockSymbol.of("CDY");
    final StockSymbol b = StockSymbol.of("CDX");

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());

    Assert.assertEquals(1, a.compareTo(b));
    Assert.assertEquals(-1, b.compareTo(a));
  }

  @Test
  public void compareSame() {
    final StockSymbol a = StockSymbol.of("CSM");

    Assert.assertEquals(a, a);
    Assert.assertEquals(a.hashCode(), a.hashCode());
    Assert.assertEquals(0, a.compareTo(a));
  }

  @Test
  public void compareSimilar() {
    final StockSymbol a = StockSymbol.of("CSL");
    final StockSymbol b = StockSymbol.of("CSL");

    Assert.assertEquals(a, b);
    Assert.assertEquals(b, a);
    Assert.assertEquals(a.hashCode(), b.hashCode());
    Assert.assertEquals(0, a.compareTo(b));
    Assert.assertEquals(0, b.compareTo(a));
  }

  @Test
  public void compareSomethingElse() {
    final StockSymbol a = StockSymbol.of("CSE");
    final Object b = new Object();

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());

    /* Make sure that the equals is null safe */
    Assert.assertFalse(a.equals(null));
  }

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
      final StockSymbol symbol = StockSymbol.of(input);
      Assert.assertNotNull(symbol);
      Assert.assertEquals(input, symbol.toString());
      Assert.assertEquals(input, symbol.getSymbol());
    }
  }
}
