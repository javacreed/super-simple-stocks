package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class DividendYield extends BigDecimalBasedDomainObject {

  @Immutable
  public static class Common extends DividendYield {

    private static Common compute(final LastDividend dividend, final TickerPrice price) throws NullPointerException {
      Preconditions.checkNotNull(dividend);
      Preconditions.checkNotNull(price);

      /* TODO: check the rounding mechanism to be used */
      final BigDecimal value = dividend.getValue().setScale(6, RoundingMode.HALF_UP).divide(price.getValue(),
          RoundingMode.HALF_UP);
      return new Common(dividend, price, value);
    }

    private final LastDividend dividend;
    private final TickerPrice price;

    private Common(final LastDividend dividend, final TickerPrice price, final BigDecimal value) {
      super(value);
      this.dividend = dividend;
      this.price = price;
    }

    public LastDividend getDividend() {
      return dividend;
    }

    public TickerPrice getPrice() {
      return price;
    }
  }

  @Immutable
  public static class Preferred extends DividendYield {

    private static Preferred compute(final FixedDividend dividend, final ParValue parValue, final TickerPrice price)
        throws NullPointerException {
      Preconditions.checkNotNull(dividend);
      Preconditions.checkNotNull(parValue);
      Preconditions.checkNotNull(price);

      /* TODO: check the rounding mechanism to be used */
      final BigDecimal value = dividend.getValue().multiply(parValue.getValue()).setScale(4, RoundingMode.HALF_UP)
          .divide(price.getValue(), RoundingMode.HALF_UP);
      return new Preferred(dividend, parValue, price, value);
    }

    private final FixedDividend dividend;
    private final ParValue parValue;
    private final TickerPrice price;

    private Preferred(final FixedDividend dividend, final ParValue parValue, final TickerPrice price,
        final BigDecimal value) {
      super(value);
      this.dividend = dividend;
      this.parValue = parValue;
      this.price = price;
    }

    public FixedDividend getDividend() {
      return dividend;
    }

    public ParValue getParValue() {
      return parValue;
    }

    public TickerPrice getPrice() {
      return price;
    }
  }

  public static Common computeCommon(final LastDividend dividend, final TickerPrice price) throws NullPointerException {
    return Common.compute(dividend, price);
  }

  public static Preferred computePreferred(final FixedDividend dividend, final ParValue parValue,
      final TickerPrice price) throws NullPointerException {
    return Preferred.compute(dividend, parValue, price);
  }

  private DividendYield(final BigDecimal value) {
    super(value);
  }
}
