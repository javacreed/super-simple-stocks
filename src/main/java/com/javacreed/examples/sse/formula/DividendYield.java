package com.javacreed.examples.sse.formula;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.common.base.Preconditions;
import com.javacreed.examples.sse.model.BigDecimalBasedDomainObject;
import com.javacreed.examples.sse.model.FixedDivident;
import com.javacreed.examples.sse.model.LastDivident;
import com.javacreed.examples.sse.model.ParValue;
import com.javacreed.examples.sse.model.TickerPrice;

public class DividendYield {

  public static class Common extends BigDecimalBasedDomainObject {

    private final LastDivident divident;
    private final TickerPrice price;

    private Common(final LastDivident divident, final TickerPrice price, final BigDecimal value) {
      super(value);
      this.divident = divident;
      this.price = price;
    }

    public LastDivident getDivident() {
      return divident;
    }

    public TickerPrice getPrice() {
      return price;
    }
  }

  public static class Preferred extends BigDecimalBasedDomainObject {
    private final FixedDivident divident;
    private final ParValue parValue;
    private final TickerPrice price;

    private Preferred(final FixedDivident divident, final ParValue parValue, final TickerPrice price,
        final BigDecimal value) {
      super(value);
      this.divident = divident;
      this.parValue = parValue;
      this.price = price;
    }

    public FixedDivident getDivident() {
      return divident;
    }

    public ParValue getParValue() {
      return parValue;
    }

    public TickerPrice getPrice() {
      return price;
    }
  }

  public static Common computeCommon(final LastDivident divident, final TickerPrice price) throws NullPointerException {
    Preconditions.checkNotNull(divident);
    Preconditions.checkNotNull(price);

    /* TODO: check the rounding mechanism to be used */
    final BigDecimal value = divident.getValue().setScale(4, RoundingMode.HALF_UP).divide(price.getValue(),
        RoundingMode.HALF_UP);
    return new Common(divident, price, value);
  }

  public static Preferred computePreferred(final FixedDivident divident, final ParValue parValue,
      final TickerPrice price) throws NullPointerException {
    Preconditions.checkNotNull(divident);
    Preconditions.checkNotNull(parValue);
    Preconditions.checkNotNull(price);

    /* TODO: check the rounding mechanism to be used */
    final BigDecimal value = divident.getValue().multiply(parValue.getValue()).setScale(4, RoundingMode.HALF_UP)
        .divide(price.getValue(), RoundingMode.HALF_UP);
    return new Preferred(divident, parValue, price, value);
  }

  private DividendYield() {}
}
