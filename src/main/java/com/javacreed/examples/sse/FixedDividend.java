package com.javacreed.examples.sse;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class FixedDividend extends BigDecimalBasedDomainObject {

  public static FixedDividend of(final BigDecimal value) {
    Preconditions.checkNotNull(value);
    return new FixedDividend(value);
  }

  public static FixedDividend of(final String value) throws NullPointerException {
    Preconditions.checkNotNull(value);
    return FixedDividend.of(new BigDecimal(value));
  }

  private FixedDividend(final BigDecimal value) {
    super(value);
  }

}
