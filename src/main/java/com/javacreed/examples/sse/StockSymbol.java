package com.javacreed.examples.sse;

import java.util.regex.Pattern;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class StockSymbol implements Comparable<StockSymbol> {

  private static final Pattern REGEX = Pattern.compile("^[A-Z]{1,6}$");

  public static StockSymbol of(final String input) throws IllegalArgumentException, NullPointerException {
    Preconditions.checkNotNull(input);
    Preconditions.checkArgument(StockSymbol.REGEX.matcher(input).matches());
    return new StockSymbol(input);
  }

  private final String symbol;

  private StockSymbol(final String symbol) {
    this.symbol = symbol;
  }

  @Override
  public int compareTo(final StockSymbol other) {
    return symbol.compareTo(other.symbol);
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }

    if (object != null && getClass() == object.getClass()) {
      return symbol.equals(((StockSymbol) object).symbol);
    }

    return false;
  }

  public String getSymbol() {
    return symbol;
  }

  @Override
  public int hashCode() {
    return symbol.hashCode();
  }

  @Override
  public String toString() {
    return symbol;
  }
}
