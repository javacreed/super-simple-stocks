package com.javacreed.examples.sse;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class StockCommonTest {

  private static PeRatio computePeRatioAndAssert(final Stock.Common stock, final TickerPrice price,
      final BigDecimal expected) {
    Assert.assertNotNull(stock);
    Assert.assertNotNull(price);

    final PeRatio peRatio = stock.computePeRatio(price);
    AssertUtils.assertEquals("a PE ratio", expected, peRatio);
    Assert.assertSame(price, peRatio.getPrice());
    Assert.assertEquals(stock.computeDividendYield(price), peRatio.getDividend());

    return peRatio;
  }

  private static DividendYield.Common computeYieldAndAssert(final Stock.Common stock, final TickerPrice price,
      final BigDecimal expected) {
    Assert.assertNotNull(stock);
    Assert.assertNotNull(price);

    final DividendYield.Common yield = stock.computeDividendYield(price);
    AssertUtils.assertEquals("a dividend yeild", expected, yield);
    Assert.assertSame(price, yield.getPrice());
    Assert.assertSame(stock.getDividend(), yield.getDividend());

    return yield;
  }

  @Test
  public void dividendAndManyTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDAMT");
    final LastDividend dividend = LastDividend.of("8");
    final Stock.Common stock = Stock.common(symbol, dividend);
    stock.buy(TradeParameters.of(Quantity.of(10), Price.of("15.45")));
    stock.buy(TradeParameters.of(Quantity.of(5), Price.of("16.21")));
    stock.buy(TradeParameters.of(Quantity.of(35), Price.of("15.19")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(3, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.610726"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.344"), stock.computeStockPrice());

    final TickerPrice price = TickerPrice.of("15.45");
    StockCommonTest.computeYieldAndAssert(stock, price, new BigDecimal("0.517799"));
    StockCommonTest.computePeRatioAndAssert(stock, price, new BigDecimal("29.837833"));
  }

  @Test
  public void dividendAndNotTrade() {
    final StockSymbol symbol = StockSymbol.of("DANT");
    final LastDividend dividend = LastDividend.of("8");
    final Stock.Common stock = Stock.common(symbol, dividend);

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertTrue(stock.isEmpty());
    Assert.assertEquals(0, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    Assert.assertSame(GeometricMean.zero(), stock.computeGeometricMean());
    Assert.assertSame(StockPrice.zero(), stock.computeStockPrice());

    final TickerPrice price = TickerPrice.of("15.45");
    StockCommonTest.computeYieldAndAssert(stock, price, new BigDecimal("0.517799"));
    StockCommonTest.computePeRatioAndAssert(stock, price, new BigDecimal("29.837833"));
  }

  @Test
  public void dividendAndSingleTrade() {
    final StockSymbol symbol = StockSymbol.of("DAST");
    final LastDividend dividend = LastDividend.of("8");
    final Stock.Common stock = Stock.common(symbol, dividend);
    stock.buy(TradeParameters.of(Quantity.of(1), Price.of("15.45")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(1, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.45"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.45"), stock.computeStockPrice());

    final TickerPrice price = TickerPrice.of("15.45");
    StockCommonTest.computeYieldAndAssert(stock, price, new BigDecimal("0.517799"));
    StockCommonTest.computePeRatioAndAssert(stock, price, new BigDecimal("29.837833"));
  }

  @Test
  public void zeroDividendAndManyTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDAMT");
    final LastDividend dividend = LastDividend.zero();
    final Stock.Common stock = Stock.common(symbol, dividend);
    stock.buy(TradeParameters.of(Quantity.of(10), Price.of("15.45")));
    stock.buy(TradeParameters.of(Quantity.of(5), Price.of("16.21")));
    stock.buy(TradeParameters.of(Quantity.of(35), Price.of("15.19")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(3, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.610726"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.344"), stock.computeStockPrice());

    StockCommonTest.computeYieldAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
    StockCommonTest.computePeRatioAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
  }

  @Test
  public void zeroDividendAndNotTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDANT");
    final LastDividend dividend = LastDividend.zero();
    final Stock.Common stock = Stock.common(symbol, dividend);

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertTrue(stock.isEmpty());
    Assert.assertEquals(0, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    Assert.assertSame(GeometricMean.zero(), stock.computeGeometricMean());
    Assert.assertSame(StockPrice.zero(), stock.computeStockPrice());

    StockCommonTest.computeYieldAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
    StockCommonTest.computePeRatioAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
  }

  @Test
  public void zeroDividendAndSingleTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDAST");
    final LastDividend dividend = LastDividend.zero();
    final Stock.Common stock = Stock.common(symbol, dividend);
    stock.buy(TradeParameters.of(Quantity.of(1), Price.of("15.45")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(1, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.45"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.45"), stock.computeStockPrice());

    StockCommonTest.computeYieldAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
    StockCommonTest.computePeRatioAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
  }
}
