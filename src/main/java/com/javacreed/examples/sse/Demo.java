package com.javacreed.examples.sse;

import java.math.BigDecimal;

public class Demo {

  public static void main(final String[] args) {
    /* Contains all stock being traded */
    final Stocks stocks = Stocks.create();

    /* Register a common stock if one with that symbol does not already exists */
    final Stock.Common pop = stocks.common().getOrCreate(StockSymbol.of("POP"),
        () -> LastDividend.of(new BigDecimal("50")));

    /* Make some trade */
    pop.buy(TradeParameters.of(Quantity.of(100), Price.of("15.43")));
    pop.buy(TradeParameters.of(Quantity.of(150), Price.of("15.42")));
    pop.sell(TradeParameters.of(Quantity.of(200), Price.of("15.25")));

    /* The current trading price */
    final TickerPrice tradePrice = TickerPrice.of(new BigDecimal("15.40"));

    /* Compute the values */
    final DividendYield dividendYield = pop.computeDividendYield(tradePrice);
    final PeRatio ratio = pop.computePeRatio(tradePrice);
    final GeometricMean geometricMean = pop.computeGeometricMean();
    final StockPrice stockPrice = pop.computeStockPrice();

    /* Print the stock details */
    System.out.println("Trade of: " + pop);
    pop.forEach(t -> System.out.println("  >> " + t));
    System.out.println("Ticker Price: ... " + tradePrice);
    System.out.println("Dividend Yield: . " + dividendYield);
    System.out.println("PE Ratio: ....... " + ratio);
    System.out.println("Geometric Mean .. " + geometricMean);
    System.out.println("Stock Price ..... " + stockPrice);
  }
}
