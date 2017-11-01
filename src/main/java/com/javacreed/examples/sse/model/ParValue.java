package com.javacreed.examples.sse.model;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

public class ParValue extends BigDecimalBasedDomainObject {

  public static ParValue of(final BigDecimal value) {
    Preconditions.checkNotNull(value);
    return new ParValue(value);
  }

  public static ParValue of(final String value) throws NullPointerException {
    Preconditions.checkNotNull(value);
    return ParValue.of(new BigDecimal(value));
  }

  private ParValue(final BigDecimal value) {
    super(value);
  }
}
