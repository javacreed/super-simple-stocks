package com.javacreed.examples.sse;

import javax.annotation.concurrent.Immutable;

import org.joda.money.Money;

@Immutable
public class MoneyBasedDomainObject implements Comparable<MoneyBasedDomainObject> {

  private final Money value;

  protected MoneyBasedDomainObject(final Money value) {
    this.value = value;
  }

  @Override
  public int compareTo(final MoneyBasedDomainObject other) {
    return value.compareTo(other.value);
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }

    if (object != null && getClass() == object.getClass()) {
      return value.equals(((MoneyBasedDomainObject) object).value);
    }

    return false;
  }

  public Money getValue() {
    return value;
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
