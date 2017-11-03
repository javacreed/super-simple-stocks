package com.javacreed.examples.sse;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class Price extends BigDecimalBasedDomainObject {

  public static Price of(final BigDecimal value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    Preconditions.checkArgument(value.compareTo(BigDecimal.ZERO) > 0);
    return new Price(value);
  }

  public static Price of(final String value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    return Price.of(new BigDecimal(value));
  }

  private Price(final BigDecimal value) {
    super(value);
  }
}
