package com.javacreed.examples.sse;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class LastDividend extends BigDecimalBasedDomainObject {

  private static final LastDividend ZERO = new LastDividend(BigDecimal.ZERO);

  /* TODO: should we allow Zero and return the singleton instead? */
  public static LastDividend of(final BigDecimal value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    Preconditions.checkArgument(value.compareTo(BigDecimal.ZERO) > 0);
    return new LastDividend(value);
  }

  public static LastDividend of(final String value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    return LastDividend.of(new BigDecimal(value));
  }

  public static LastDividend zero() {
    return LastDividend.ZERO;
  }

  private LastDividend(final BigDecimal value) {
    super(value);
  }
}
