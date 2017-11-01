package com.javacreed.examples.sse.model;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

public class FixedDivident extends BigDecimalBasedDomainObject {

  public static FixedDivident of(final BigDecimal value) {
    Preconditions.checkNotNull(value);
    return new FixedDivident(value);
  }

  public static FixedDivident of(final String value) throws NullPointerException {
    Preconditions.checkNotNull(value);
    return FixedDivident.of(new BigDecimal(value));
  }

  private FixedDivident(final BigDecimal value) {
    super(value);
  }

}
