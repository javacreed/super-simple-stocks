package com.javacreed.examples.sse;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class PeRatio extends BigDecimalBasedDomainObject {

  public static PeRatio compute(final TickerPrice price, final DividendYield dividend) throws NullPointerException {
    Preconditions.checkNotNull(price);
    Preconditions.checkNotNull(dividend);
    final BigDecimal value = price.getValue().setScale(4).divide(dividend.getValue());
    return new PeRatio(price, dividend, value);
  }

  private final TickerPrice price;
  private final DividendYield dividend;

  private PeRatio(final TickerPrice price, final DividendYield dividend, final BigDecimal value) {
    super(value);
    this.price = price;
    this.dividend = dividend;
  }

  public DividendYield getDividend() {
    return dividend;
  }

  public TickerPrice getPrice() {
    return price;
  }
}
