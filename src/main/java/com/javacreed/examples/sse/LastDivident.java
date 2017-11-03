package com.javacreed.examples.sse;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class LastDivident extends BigDecimalBasedDomainObject {

  private static final LastDivident ZERO = new LastDivident(BigDecimal.ZERO);

  /* TODO: should we allow Zero and return the singleton instead? */
  public static LastDivident of(final BigDecimal value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    Preconditions.checkArgument(value.compareTo(BigDecimal.ZERO) > 0);
    return new LastDivident(value);
  }

  public static LastDivident of(final String value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    return LastDivident.of(new BigDecimal(value));
  }

  public static LastDivident zero() {
    return LastDivident.ZERO;
  }

  private LastDivident(final BigDecimal value) {
    super(value);
  }
}
