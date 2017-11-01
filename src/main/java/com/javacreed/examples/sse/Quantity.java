package com.javacreed.examples.sse;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class Quantity implements Comparable<Quantity> {

  public static Quantity of(final int value) throws IllegalAccessError {
    Preconditions.checkArgument(value > 0);
    Preconditions.checkArgument(value < 1000); /* TODO: is there an upper limit? */

    return new Quantity(value);
  }

  private final int value;

  private Quantity(final int value) {
    this.value = value;
  }

  @Override
  public int compareTo(final Quantity other) {
    return Integer.compare(value, other.value);
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }

    if (object != null && getClass() == object.getClass()) {
      return value == ((Quantity) object).value;
    }
    return false;
  }

  public int getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
