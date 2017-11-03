package com.javacreed.examples.sse;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class QuantityTest {

  @Test
  public void compareDifferent() {
    /* Instance 'b' should come before instance 'a' as the comparison is made on the value */
    final Quantity a = Quantity.of(2);
    final Quantity b = Quantity.of(1);

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());

    Assert.assertEquals(1, a.compareTo(b));
    Assert.assertEquals(-1, b.compareTo(a));
  }

  @Test
  public void compareSame() {
    final Quantity a = Quantity.of(1);

    Assert.assertSame(a, a);
    Assert.assertEquals(a, a);
    Assert.assertEquals(a.hashCode(), a.hashCode());
    Assert.assertEquals(0, a.compareTo(a));
  }

  @Test
  public void compareSimilar() {
    final Quantity a = Quantity.of(1);
    final Quantity b = Quantity.of(1);

    Assert.assertNotSame(a, b);
    Assert.assertEquals(a, b);
    Assert.assertEquals(b, a);
    Assert.assertEquals(a.hashCode(), b.hashCode());
    Assert.assertEquals(0, a.compareTo(b));
    Assert.assertEquals(0, b.compareTo(a));
  }

  @Test
  public void compareSomethingElse() {
    final Quantity a = Quantity.of(1);
    final Object b = new Object();

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());

    /* Make sure that the equals is null safe */
    Assert.assertFalse(a.equals(null));
  }

  @Test
  public void invalidInputs() {
    final Set<Integer> inputs = new LinkedHashSet<>();
    inputs.add(0); /* Quantity cannot be less than 1 */
    inputs.add(-1); /* Quantity cannot be less than 1 */
    inputs.add(1001); /* Quantity cannot be more than 1000 */
    inputs.add(Integer.MIN_VALUE); /* Test extreme value */
    inputs.add(Integer.MAX_VALUE); /* Test extreme value */

    for (final int input : inputs) {
      try {
        Quantity.of(input);
        Assert.fail("The input '" + input + "' is invalid and should have failed");
      } catch (final IllegalArgumentException e) {
        /* Expected an exception */
      }
    }
  }

  @Test
  public void validInputs() {
    final Set<Integer> inputs = new LinkedHashSet<>();
    inputs.add(1); /* Lower limit */
    inputs.add(10);
    inputs.add(100);
    inputs.add(1000); /* Upper limit */

    for (final int input : inputs) {
      final Quantity quantity = Quantity.of(input);
      Assert.assertNotNull(quantity);
      Assert.assertEquals(String.valueOf(input), quantity.toString());
      Assert.assertEquals(input, quantity.getValue());
    }
  }
}
