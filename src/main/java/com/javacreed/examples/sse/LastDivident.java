package com.javacreed.examples.sse;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class LastDivident extends BigDecimalBasedDomainObject {

  public static LastDivident of(final BigDecimal value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    Preconditions.checkArgument(value.compareTo(BigDecimal.ZERO) > 0);
    return new LastDivident(value);
  }

  public static LastDivident of(final String value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    return LastDivident.of(new BigDecimal(value));
  }

  private LastDivident(final BigDecimal value) {
    super(value);
  }
}
