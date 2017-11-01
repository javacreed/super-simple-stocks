package com.javacreed.examples.sse;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public abstract class Stock {

  public static class Common extends Stock {
    private LastDivident lastDivident;

    @Override
    public DividendYield dividendYield() {
      return DividendYield.computeCommon(lastDivident, tickerPrice);
    }
  }

  public static class Preferred extends Stock {
    protected FixedDivident fixedDivident;
    protected ParValue parValue;

    @Override
    public DividendYield dividendYield() {
      return DividendYield.computePreferred(fixedDivident, parValue, tickerPrice);
    }
  }

  protected StockSymbol symbol;
  protected final List<Trade> trades = new ArrayList<>();
  protected TickerPrice tickerPrice;

  private Stock() {}

  public void buy(final Quantity quantity, final Price price) {}

  /* TODO: consider providing the ticker price here */
  public abstract DividendYield dividendYield();

  public GeometricMean geometricMean() {
    throw new UnsupportedOperationException();
  }

  public void sell(final Quantity quantity, final Price price) {}

  public StockPrice stockPrice() {
    throw new UnsupportedOperationException();
  }
}
