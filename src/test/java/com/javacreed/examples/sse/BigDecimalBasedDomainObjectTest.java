package com.javacreed.examples.sse;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class BigDecimalBasedDomainObjectTest {

  private static class TestDomainObject extends BigDecimalBasedDomainObject {

    private static TestDomainObject of(final BigDecimal value) {
      return new TestDomainObject(value);
    }

    private static TestDomainObject of(final String value) {
      return TestDomainObject.of(new BigDecimal(value));
    }

    private TestDomainObject(final BigDecimal value) {
      super(value);
    }
  }

  @Test
  public void compareDifferent() {
    /* Instance 'b' should come before instance 'a' as the comparison is made numerically */
    final TestDomainObject a = TestDomainObject.of("10.00");
    final TestDomainObject b = TestDomainObject.of("5");

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());

    Assert.assertEquals(1, a.compareTo(b));
    Assert.assertEquals(-1, b.compareTo(a));
  }

  @Test
  public void compareDifferentScale() {
    final TestDomainObject a = TestDomainObject.of("10");
    final TestDomainObject b = TestDomainObject.of("10.000000");

    /*
     * BigDecimal takes the scale into consideration thus two similar values with different scale are considered
     * unequal.
     */
    Assert.assertNotEquals(a.getValue(), b.getValue());

    Assert.assertNotSame(a, b);
    Assert.assertEquals(a, b);
    Assert.assertEquals(b, a);
    Assert.assertEquals(a.hashCode(), b.hashCode());
    Assert.assertEquals(0, a.compareTo(b));
    Assert.assertEquals(0, b.compareTo(a));
  }

  @Test
  public void compareSame() {
    final TestDomainObject a = TestDomainObject.of("10.00");

    Assert.assertSame(a, a);
    Assert.assertEquals(a, a);
    Assert.assertEquals(a.hashCode(), a.hashCode());
    Assert.assertEquals(0, a.compareTo(a));
  }

  @Test
  public void compareSimilar() {
    final TestDomainObject a = TestDomainObject.of("10.00");
    final TestDomainObject b = TestDomainObject.of("10.00");

    Assert.assertNotSame(a, b);
    Assert.assertEquals(a, b);
    Assert.assertEquals(b, a);
    Assert.assertEquals(a.hashCode(), b.hashCode());
    Assert.assertEquals(0, a.compareTo(b));
    Assert.assertEquals(0, b.compareTo(a));
  }

  @Test
  public void compareSomethingElse() {
    final TestDomainObject a = TestDomainObject.of("10.00");
    final Object b = new Object();

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());

    /* Make sure that the equals is null safe */
    Assert.assertFalse(a.equals(null));
  }

  @Test
  public void isZero() {
    final String[] zeros = { "0", "0.000000" };
    for (final String zero : zeros) {
      Assert.assertTrue(zero, TestDomainObject.of(zero).isZero());
    }

    final String[] notZeros = { "0.1", "0.000001" };
    for (final String zero : notZeros) {
      Assert.assertFalse(zero, TestDomainObject.of(zero).isZero());
    }
  }

  @Test
  public void nullInputs() {
    try {
      TestDomainObject.of((BigDecimal) null);
      Assert.fail("null should not be accepted as input");
    } catch (final NullPointerException e) {
      /* Expected an exception */
    }
  }

}
