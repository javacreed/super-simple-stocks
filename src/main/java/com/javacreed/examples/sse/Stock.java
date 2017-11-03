package com.javacreed.examples.sse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.concurrent.NotThreadSafe;

import com.google.common.base.Preconditions;
import com.javacreed.examples.sse.Trade.Buy;
import com.javacreed.examples.sse.Trade.Sell;

@NotThreadSafe
public abstract class Stock implements Iterable<Trade> {

  public static class Common extends Stock {

    private static Common of(final StockSymbol symbol, final LastDividend dividend) {
      Preconditions.checkNotNull(symbol);
      Preconditions.checkNotNull(dividend);
      return new Common(symbol, dividend);
    }

    private final LastDividend dividend;

    private Common(final StockSymbol symbol, final LastDividend dividend) {
      super(symbol);
      this.dividend = dividend;
    }

    @Override
    public DividendYield.Common computeDividendYield(final TickerPrice price) {
      return DividendYield.computeCommon(dividend, price);
    }

    public LastDividend getDividend() {
      return dividend;
    }
  }

  public static class Preferred extends Stock {

    private static Preferred of(final StockSymbol symbol, final FixedDividend dividend, final ParValue parValue) {
      Preconditions.checkNotNull(symbol);
      Preconditions.checkNotNull(dividend);
      Preconditions.checkNotNull(parValue);
      return new Preferred(symbol, dividend, parValue);
    }

    private final FixedDividend dividend;
    private final ParValue parValue;

    private Preferred(final StockSymbol symbol, final FixedDividend dividend, final ParValue parValue) {
      super(symbol);
      this.dividend = dividend;
      this.parValue = parValue;
    }

    @Override
    public DividendYield.Preferred computeDividendYield(final TickerPrice price) {
      return DividendYield.computePreferred(dividend, parValue, price);
    }

    public FixedDividend getDividend() {
      return dividend;
    }

    public ParValue getParValue() {
      return parValue;
    }
  }

  public static Common common(final StockSymbol symbol, final LastDividend dividend) throws NullPointerException {
    return Common.of(symbol, dividend);
  }

  public static Preferred preferred(final StockSymbol symbol, final FixedDividend dividend, final ParValue parValue)
      throws NullPointerException {
    return Preferred.of(symbol, dividend, parValue);
  }

  private final StockSymbol symbol;

  private final List<Trade> trades = new ArrayList<>();

  private final List<Trade> immutableTrades = Collections.unmodifiableList(trades);

  private Stock(final StockSymbol symbol) {
    this.symbol = symbol;
  }

  public Buy buy(final TradeParameters parameters) throws NullPointerException {
    return trade(Trade.buy(parameters));
  }

  /* TODO: Should we have the TickerPrice saved as part of the state or should we have it as a parameter (as is)? */
  public abstract DividendYield computeDividendYield(TickerPrice tickerPrice);

  public GeometricMean computeGeometricMean() {
    return GeometricMean.compute(trades.stream().map(Trade::getPrice).collect(Collectors.toList()));
  }

  public PeRatio computePeRatio(final TickerPrice price) {
    /*
     * TODO: Need to verify the denominator to be used in this formula. I was not able to understand this from the
     * document
     */
    return PeRatio.compute(price, computeDividendYield(price));
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

  public Sell sell(final TradeParameters parameters) throws NullPointerException {
    return trade(Trade.sell(parameters));
  }

  public int size() {
    return trades.size();
  }

  private <T extends Trade> T trade(final T trade) throws NullPointerException {
    trades.add(trade);
    return trade;
  }
}
