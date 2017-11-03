package com.javacreed.examples.sse;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class FixedDividend extends BigDecimalBasedDomainObject {

  private static final BigDecimal HUNDRED = new BigDecimal(100);

  private static final FixedDividend ZERO = new FixedDividend(BigDecimal.ZERO);

  public static FixedDividend of(final BigDecimal value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    /* TODO: we need to confirm the range. For the time being this is between 0 and 100 */
    Preconditions.checkArgument(value.compareTo(BigDecimal.ZERO) > 0);
    Preconditions.checkArgument(FixedDividend.HUNDRED.compareTo(value) >= 0);

    return new FixedDividend(value);
  }

  public static FixedDividend of(final String value) throws NullPointerException, IllegalArgumentException {
    Preconditions.checkNotNull(value);
    return FixedDividend.of(new BigDecimal(value));
  }

  public static FixedDividend zero() {
    return FixedDividend.ZERO;
  }

  private FixedDividend(final BigDecimal value) {
    super(value);
  }
}
