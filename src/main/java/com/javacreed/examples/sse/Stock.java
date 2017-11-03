package com.javacreed.examples.sse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.concurrent.NotThreadSafe;

import com.google.common.base.Preconditions;

@NotThreadSafe
public abstract class Stock implements Iterable<Trade> {

  public static class Common extends Stock {

    private static Common of(final StockSymbol symbol, final LastDivident divident) {
      Preconditions.checkNotNull(symbol);
      Preconditions.checkNotNull(divident);
      return new Common(symbol, divident);
    }

    private final LastDivident divident;

    private Common(final StockSymbol symbol, final LastDivident divident) {
      super(symbol);
      this.divident = divident;
    }

    @Override
    public DividendYield.Common computeDividendYield(final TickerPrice tickerPrice) {
      return DividendYield.computeCommon(divident, tickerPrice);
    }

    public LastDivident getDivident() {
      return divident;
    }
  }

  public static class Preferred extends Stock {

    private static Preferred of(final StockSymbol symbol, final FixedDivident divident, final ParValue parValue) {
      Preconditions.checkNotNull(symbol);
      Preconditions.checkNotNull(divident);
      Preconditions.checkNotNull(parValue);
      return new Preferred(symbol, divident, parValue);
    }

    private final FixedDivident divident;
    private final ParValue parValue;

    private Preferred(final StockSymbol symbol, final FixedDivident divident, final ParValue parValue) {
      super(symbol);
      this.divident = divident;
      this.parValue = parValue;
    }

    @Override
    public DividendYield.Preferred computeDividendYield(final TickerPrice tickerPrice) {
      return DividendYield.computePreferred(divident, parValue, tickerPrice);
    }

    public FixedDivident getDivident() {
      return divident;
    }

    public ParValue getParValue() {
      return parValue;
    }
  }

  public static Common common(final StockSymbol symbol, final LastDivident divident) throws NullPointerException {
    return Common.of(symbol, divident);
  }

  public static Preferred preferred(final StockSymbol symbol, final FixedDivident divident, final ParValue parValue)
      throws NullPointerException {
    return Preferred.of(symbol, divident, parValue);
  }

  private final StockSymbol symbol;

  private final List<Trade> trades = new ArrayList<>();

  private final List<Trade> immutableTrades = Collections.unmodifiableList(trades);

  private Stock(final StockSymbol symbol) {
    this.symbol = symbol;
  }

  public Trade buy(final TradeRequest request) throws NullPointerException {
    return trade(Trade.buy(request));
  }

  /* TODO: Should we have the TickerPrice saved as part of the state or should we have it as a parameter (as is)? */
  public abstract DividendYield computeDividendYield(TickerPrice tickerPrice);

  public GeometricMean computeGeometricMean() {
    return GeometricMean.compute(trades.stream().map(Trade::getPrice).collect(Collectors.toList()));
  }

  public StockPrice computeStockPrice() {
    return StockPrice.compute(immutableTrades);
  }

  public StockSymbol getSymbol() {
    return symbol;
  }

  /**
   *
   * @param index
   * @return
   * @throws IndexOutOfBoundsException
   *           if the <code>index</code> is out of range (<code>index < 0 || index >= size()</code>)
   */
  public Trade getTradeAt(final int index) throws IndexOutOfBoundsException {
    return trades.get(index);
  }

  public boolean isEmpty() {
    return trades.isEmpty();
  }

  @Override
  public Iterator<Trade> iterator() {
    return immutableTrades.iterator();
  }

  public Trade sell(final TradeRequest request) throws NullPointerException {
    return trade(Trade.sell(request));
  }

  public int size() {
    return trades.size();
  }

  private Trade trade(final Trade trade) throws NullPointerException {
    trades.add(trade);
    return trade;
  }
}
