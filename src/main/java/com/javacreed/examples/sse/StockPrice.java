package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import com.google.common.base.Preconditions;

public class StockPrice extends BigDecimalBasedDomainObject {

  private static final StockPrice ZERO = new StockPrice(BigDecimal.ZERO);

  public static StockPrice compute(final Iterable<Trade> trades) throws NullPointerException {
    Preconditions.checkNotNull(trades);

    /* TODO: Should we read this from properties or parameter? */
    final LocalDateTime threshold = LocalDateTime.now().minusMinutes(15);

    BigDecimal nominator = BigDecimal.ZERO;
    BigDecimal denominator = BigDecimal.ZERO;

    for (final Trade trade : trades) {
      if (trade.getTime().isAfter(threshold)) {
        final BigDecimal price = trade.getPrice().getValue();
        final BigDecimal quantity = new BigDecimal(trade.getQuantity().getValue());

        nominator = nominator.add(price.multiply(quantity));
        denominator = denominator.add(quantity);
      }
    }

    if (denominator == BigDecimal.ZERO) {
      return StockPrice.ZERO;
    }

    final BigDecimal value = nominator.setScale(6, RoundingMode.HALF_UP).divide(denominator, RoundingMode.HALF_UP);
    return new StockPrice(value);
  }

  public static StockPrice zero() {
    return StockPrice.ZERO;
  }

  private StockPrice(final BigDecimal value) {
    super(value);
  }
}
