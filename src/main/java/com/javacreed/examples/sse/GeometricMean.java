package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.concurrent.Immutable;

@Immutable
public class GeometricMean extends BigDecimalBasedDomainObject {

  private static final GeometricMean ZERO = new GeometricMean(BigDecimal.ZERO);

  public static <T extends BigDecimalBasedDomainObject> GeometricMean compute(final Iterable<T> values)
      throws NullPointerException, IllegalAccessError {

    int count = 0;
    BigDecimal product = BigDecimal.ONE;

    for (final T value : values) {
      product = product.multiply(value.getValue());
      count++;
    }

    if (count == 0) {
      return GeometricMean.zero();
    }

    /*
     * TODO: Had to convert to double to compute the nth root and then convert back to BigDecimal. The rounding
     * superficially hides the accuracy problem. Need to find a way to use BigDecimal throughout instead of converting
     * to double.
     */
    final double mean = Math.pow(product.doubleValue(), 1.0 / count);
    /* TODO: check the rounding mechanism to be used */
    final BigDecimal value = new BigDecimal(mean).setScale(6, RoundingMode.HALF_UP);

    return new GeometricMean(value);
  }

  public static GeometricMean zero() {
    return GeometricMean.ZERO;
  }

  private GeometricMean(final BigDecimal value) {
    super(value);
  }
}
