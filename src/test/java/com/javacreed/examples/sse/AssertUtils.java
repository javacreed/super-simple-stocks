package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Assert;

public class AssertUtils {

  public static <T extends BigDecimalBasedDomainObject> T assertEquals(final BigDecimal expected, final T actual) {
    return AssertUtils.assertEquals(Optional.empty(), expected, actual);
  }

  public static <T extends BigDecimalBasedDomainObject> T assertEquals(final Optional<String> valueName,
      final BigDecimal expected, final T actual) {
    Assert.assertNotNull(valueName);
    Assert.assertNotNull(expected);
    Assert.assertNotNull(actual);

    if (0 != expected.compareTo(actual.getValue())) {
      Assert.fail("Expected " + valueName.orElse("the") + " value of " + expected + " but found " + actual);
    }

    return actual;
  }

  public static <T extends BigDecimalBasedDomainObject> T assertEquals(final String valueName,
      final BigDecimal expected, final T actual) {
    Assert.assertNotNull(valueName);
    return AssertUtils.assertEquals(Optional.of(valueName), expected, actual);
  }

  private AssertUtils() {}

}
