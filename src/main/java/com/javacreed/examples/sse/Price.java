package com.javacreed.examples.sse;

import javax.annotation.concurrent.Immutable;

import org.joda.money.Money;

import com.google.common.base.Preconditions;

@Immutable
public class Price {

  public static Price of(final Money value) throws NullPointerException, IllegalAccessException {
    Preconditions.checkNotNull(value);
    Preconditions.checkArgument(value.isPositive());
    return new Price(value);
  }

  private final Money value;

  private Price(final Money value) {
    this.value = value;
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }

    if (object != null && getClass() == object.getClass()) {
      return value.equals(((Price) object).value);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
