package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class GeometricMeanTest {

  private static class TestObject extends BigDecimalBasedDomainObject {
    public TestObject(final BigDecimal value) {
      super(value);
    }

    public TestObject(final String value) {
      this(new BigDecimal(value));
    }
  }

  private static <T extends BigDecimalBasedDomainObject> GeometricMean computeAndAssert(final Iterable<T> values,
      final BigDecimal expected) {
    final GeometricMean mean = GeometricMean.compute(values);
    return AssertUtils.assertEquals("a geometric mean", expected, mean);
  }

  @Test
  public void test_1() {
    final List<TestObject> parameters = new ArrayList<>();
    parameters.add(new TestObject(BigDecimal.ONE));

    GeometricMeanTest.computeAndAssert(parameters, BigDecimal.ONE);
  }

  @Test
  public void test_2() {
    final List<TestObject> parameters = new ArrayList<>();
    parameters.add(new TestObject("3"));
    parameters.add(new TestObject("9"));
    parameters.add(new TestObject("27"));

    GeometricMeanTest.computeAndAssert(parameters, new BigDecimal("9"));
  }

  @Test
  public void test_3() {
    final List<TestObject> parameters = new ArrayList<>();
    parameters.add(new TestObject("11.25"));
    parameters.add(new TestObject("12.45"));
    parameters.add(new TestObject("10.98"));

    GeometricMeanTest.computeAndAssert(parameters, new BigDecimal("11.542718"));
  }

  @Test
  public void zero() {
    Assert.assertSame(GeometricMean.zero(), GeometricMean.compute(Collections.emptyList()));
  }
}
