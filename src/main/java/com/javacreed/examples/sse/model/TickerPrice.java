package com.javacreed.examples.sse.model;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

/**
 * TODO: Add currency support
 */
public class TickerPrice extends BigDecimalBasedDomainObject {

  public static TickerPrice of(final BigDecimal value) {
    return new TickerPrice(value);
  }

  public static TickerPrice of(final String value) {
    Preconditions.checkNotNull(value);
    return TickerPrice.of(new BigDecimal(value));
  }

  private TickerPrice(final BigDecimal value) {
    super(value);
  }
}
