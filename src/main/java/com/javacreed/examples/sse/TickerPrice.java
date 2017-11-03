package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

/**
 * TODO: Add currency support
 */
@Immutable
public class TickerPrice extends BigDecimalBasedDomainObject {

  public static TickerPrice of(final BigDecimal value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    Preconditions.checkArgument(value.compareTo(BigDecimal.ZERO) > 0); /* TODO: should we have an upper limit? */
    return new TickerPrice(value);
  }

  public static TickerPrice of(final String value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    return TickerPrice.of(new BigDecimal(value));
  }

  public static TickerPrice random() {
    return TickerPrice.of(new BigDecimal(Math.random() * 100).setScale(4, RoundingMode.HALF_UP));
  }

  private TickerPrice(final BigDecimal value) {
    super(value);
  }
}
