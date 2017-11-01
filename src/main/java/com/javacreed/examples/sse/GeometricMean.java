package com.javacreed.examples.sse;

import java.math.BigDecimal;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class GeometricMean extends BigDecimalBasedDomainObject {

  public static GeometricMean of(final BigDecimal value) throws NullPointerException, IllegalAccessError {
    Preconditions.checkNotNull(value);
    return new GeometricMean(value);
  }

  private GeometricMean(final BigDecimal value) {
    super(value);
  }
}
