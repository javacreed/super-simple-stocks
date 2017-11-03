package com.javacreed.examples.sse;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class StockCommonTest {

  private static DividendYield.Common computeAndAssert(final Stock.Common stock, final TickerPrice price,
      final BigDecimal expected) {
    Assert.assertNotNull(stock);
    Assert.assertNotNull(price);

    final DividendYield.Common yield = stock.computeDividendYield(price);
    AssertUtils.assertEquals("a divident yeild", expected, yield);
    Assert.assertSame(price, yield.getPrice());
    Assert.assertSame(stock.getDivident(), yield.getDivident());

    return yield;
  }

  @Test
  public void dividentAndManyTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDAMT");
    final LastDivident divident = LastDivident.of("8");
    final Stock.Common stock = Stock.common(symbol, divident);
    stock.buy(TradeRequest.of(Quantity.of(10), Price.of("15.45")));
    stock.buy(TradeRequest.of(Quantity.of(5), Price.of("16.21")));
    stock.buy(TradeRequest.of(Quantity.of(35), Price.of("15.19")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(3, stock.size());

    Assert.assertSame(divident, stock.getDivident());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.610726"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.344"), stock.computeStockPrice());

    StockCommonTest.computeAndAssert(stock, TickerPrice.of("15.45"), new BigDecimal("0.517799"));
  }

  @Test
  public void dividentAndNotTrade() {
    final StockSymbol symbol = StockSymbol.of("DANT");
    final LastDivident divident = LastDivident.of("8");
    final Stock.Common stock = Stock.common(symbol, divident);

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertTrue(stock.isEmpty());
    Assert.assertEquals(0, stock.size());

    Assert.assertSame(divident, stock.getDivident());
    Assert.assertSame(GeometricMean.zero(), stock.computeGeometricMean());
    Assert.assertSame(StockPrice.zero(), stock.computeStockPrice());

    StockCommonTest.computeAndAssert(stock, TickerPrice.of("15.45"), new BigDecimal("0.517799"));
  }

  @Test
  public void dividentAndSingleTrade() {
    final StockSymbol symbol = StockSymbol.of("DAST");
    final LastDivident divident = LastDivident.of("8");
    final Stock.Common stock = Stock.common(symbol, divident);
    stock.buy(TradeRequest.of(Quantity.of(1), Price.of("15.45")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(1, stock.size());

    Assert.assertSame(divident, stock.getDivident());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.45"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.45"), stock.computeStockPrice());

    StockCommonTest.computeAndAssert(stock, TickerPrice.of("15.45"), new BigDecimal("0.517799"));
  }

  @Test
  public void zeroDividentAndManyTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDAMT");
    final LastDivident divident = LastDivident.zero();
    final Stock.Common stock = Stock.common(symbol, divident);
    stock.buy(TradeRequest.of(Quantity.of(10), Price.of("15.45")));
    stock.buy(TradeRequest.of(Quantity.of(5), Price.of("16.21")));
    stock.buy(TradeRequest.of(Quantity.of(35), Price.of("15.19")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(3, stock.size());

    Assert.assertSame(divident, stock.getDivident());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.610726"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.344"), stock.computeStockPrice());

    StockCommonTest.computeAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
  }

  @Test
  public void zeroDividentAndNotTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDANT");
    final LastDivident divident = LastDivident.zero();
    final Stock.Common stock = Stock.common(symbol, divident);

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertTrue(stock.isEmpty());
    Assert.assertEquals(0, stock.size());

    Assert.assertSame(divident, stock.getDivident());
    Assert.assertSame(GeometricMean.zero(), stock.computeGeometricMean());
    Assert.assertSame(StockPrice.zero(), stock.computeStockPrice());

    StockCommonTest.computeAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
  }

  @Test
  public void zeroDividentAndSingleTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDAST");
    final LastDivident divident = LastDivident.zero();
    final Stock.Common stock = Stock.common(symbol, divident);
    stock.buy(TradeRequest.of(Quantity.of(1), Price.of("15.45")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(1, stock.size());

    Assert.assertSame(divident, stock.getDivident());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.45"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.45"), stock.computeStockPrice());

    StockCommonTest.computeAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
  }
}
