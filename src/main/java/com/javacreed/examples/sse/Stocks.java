package com.javacreed.examples.sse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.concurrent.NotThreadSafe;

import com.google.common.base.Preconditions;

@NotThreadSafe
public class Stocks {

  public static class BaseStocks<T extends Stock> {

    protected final Map<StockSymbol, T> stocks = new HashMap<>();

    public T get(final StockSymbol symbol) throws NullPointerException, IllegalArgumentException {
      Preconditions.checkNotNull(symbol);

      final T stock = stocks.get(symbol);
      if (stock == null) {
        /* TODO: throw a more specific exception */
        throw new IllegalArgumentException("Stock with symbol: " + symbol + " was not found");
      }

      return stock;
    }
  }

  public static class Common extends BaseStocks<Stock.Common> {
    public Stock.Common getOrCreate(final StockSymbol symbol, final Supplier<LastDividend> dividend) {
      Preconditions.checkNotNull(symbol);
      Preconditions.checkNotNull(dividend);
      return stocks.computeIfAbsent(symbol, k -> Stock.common(symbol, dividend.get()));
    }
  }

  public static class Preferred extends BaseStocks<Stock.Preferred> {
    public Stock.Preferred getOrCreate(final StockSymbol symbol, final Supplier<FixedDividend> dividend,
        final Supplier<ParValue> parValue) {
      Preconditions.checkNotNull(symbol);
      Preconditions.checkNotNull(dividend);
      Preconditions.checkNotNull(parValue);
      return stocks.computeIfAbsent(symbol, k -> Stock.preferred(symbol, dividend.get(), parValue.get()));
    }
  }

  public static Stocks create() {
    final Stocks stocks = new Stocks();
    return stocks;
  }

  private final Common common = new Common();

  private final Preferred preferred = new Preferred();

  private Stocks() {}

  public Common common() {
    return common;
  }

  public Preferred preferred() {
    return preferred;
  }
}
