package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.concurrent.Immutable;

@Immutable
public class BigDecimalBasedDomainObject implements Comparable<BigDecimalBasedDomainObject> {

  protected final BigDecimal value;

  /**
   * {@link BigDecimal} takes into consideration the scale in equality and hash code computation. In order to simplify
   * the use of this class, the scale is ignored. In other words "1" is equal to "1.00". The class hash code is computed
   * once and cashed
   */
  private final int hashCode;

  protected BigDecimalBasedDomainObject(final BigDecimal value) {
    this.value = value;
    /* TODO: should we fix the scale or should we leave that to the caller? */
    hashCode = value.setScale(2, RoundingMode.HALF_UP).hashCode();
  }

  @Override
  public int compareTo(final BigDecimalBasedDomainObject other) {
    return value.compareTo(other.value);
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }

    if (object != null && getClass() == object.getClass()) {
      return compareTo((BigDecimalBasedDomainObject) object) == 0;
    }

    return false;
  }

  public BigDecimal getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  /**
   * Returns <code>true</code> if the value is zero, <code>false</code> otherwise
   *
   * @return <code>true</code> if the value is zero, <code>false</code> otherwise
   */
  public boolean isZero() {
    return BigDecimal.ZERO.compareTo(value) == 0;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
