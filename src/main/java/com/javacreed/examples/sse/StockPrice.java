package com.javacreed.examples.sse;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

public class StockPrice extends BigDecimalBasedDomainObject {

  public static StockPrice of(final BigDecimal value) throws NullPointerException, IllegalAccessError {
    Preconditions.checkNotNull(value);
    return new StockPrice(value);
  }

  private StockPrice(final BigDecimal value) {
    super(value);
  }
}
