package com.javacreed.examples.sse;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class StockPreferredTest {

  private static PeRatio computePeRatioAndAssert(final Stock.Preferred stock, final TickerPrice price,
      final BigDecimal expected) {
    Assert.assertNotNull(stock);
    Assert.assertNotNull(price);

    final PeRatio peRatio = stock.computePeRatio(price);
    AssertUtils.assertEquals("a PE ratio", expected, peRatio);
    Assert.assertSame(price, peRatio.getPrice());
    Assert.assertEquals(stock.computeDividendYield(price), peRatio.getDividend());

    return peRatio;
  }

  private static DividendYield.Preferred computeYieldAndAssert(final Stock.Preferred stock, final TickerPrice price,
      final BigDecimal expected) {
    Assert.assertNotNull(stock);
    Assert.assertNotNull(price);

    final DividendYield.Preferred yield = stock.computeDividendYield(price);
    AssertUtils.assertEquals("a dividend yeild", expected, yield);
    Assert.assertSame(price, yield.getPrice());
    Assert.assertSame(stock.getDividend(), yield.getDividend());
    Assert.assertSame(stock.getParValue(), yield.getParValue());

    return yield;
  }

  @Test
  public void dividendAndManyTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDAMT");
    final FixedDividend dividend = FixedDividend.of("8");
    final ParValue parValue = ParValue.of("100");
    final Stock.Preferred stock = Stock.preferred(symbol, dividend, parValue);
    stock.buy(TradeRequest.of(Quantity.of(10), Price.of("15.45")));
    stock.buy(TradeRequest.of(Quantity.of(5), Price.of("16.21")));
    stock.buy(TradeRequest.of(Quantity.of(35), Price.of("15.19")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(3, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.610726"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.344"), stock.computeStockPrice());

    final TickerPrice price = TickerPrice.of("15.45");
    StockPreferredTest.computeYieldAndAssert(stock, price, new BigDecimal("0.517799"));
    StockPreferredTest.computePeRatioAndAssert(stock, price, new BigDecimal("29.837833"));
  }

  @Test
  public void dividendAndNotTrade() {
    final StockSymbol symbol = StockSymbol.of("DANT");
    final FixedDividend dividend = FixedDividend.of("8");
    final ParValue parValue = ParValue.of("100");
    final Stock.Preferred stock = Stock.preferred(symbol, dividend, parValue);

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertTrue(stock.isEmpty());
    Assert.assertEquals(0, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    Assert.assertSame(GeometricMean.zero(), stock.computeGeometricMean());
    Assert.assertSame(StockPrice.zero(), stock.computeStockPrice());

    final TickerPrice price = TickerPrice.of("15.45");
    StockPreferredTest.computeYieldAndAssert(stock, price, new BigDecimal("0.517799"));
    StockPreferredTest.computePeRatioAndAssert(stock, price, new BigDecimal("29.837833"));
  }

  @Test
  public void dividendAndSingleTrade() {
    final StockSymbol symbol = StockSymbol.of("DAST");
    final FixedDividend dividend = FixedDividend.of("8");
    final ParValue parValue = ParValue.of("100");
    final Stock.Preferred stock = Stock.preferred(symbol, dividend, parValue);
    stock.buy(TradeRequest.of(Quantity.of(1), Price.of("15.45")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(1, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.45"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.45"), stock.computeStockPrice());

    final TickerPrice price = TickerPrice.of("15.45");
    StockPreferredTest.computeYieldAndAssert(stock, price, new BigDecimal("0.517799"));
    StockPreferredTest.computePeRatioAndAssert(stock, price, new BigDecimal("29.837833"));
  }

  @Test
  public void zeroDividendAndManyTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDAMT");
    final FixedDividend dividend = FixedDividend.zero();
    final ParValue parValue = ParValue.of("100");
    final Stock.Preferred stock = Stock.preferred(symbol, dividend, parValue);
    stock.buy(TradeRequest.of(Quantity.of(10), Price.of("15.45")));
    stock.buy(TradeRequest.of(Quantity.of(5), Price.of("16.21")));
    stock.buy(TradeRequest.of(Quantity.of(35), Price.of("15.19")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(3, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.610726"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.344"), stock.computeStockPrice());

    StockPreferredTest.computeYieldAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
    StockPreferredTest.computePeRatioAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
  }

  @Test
  public void zeroDividendAndNotTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDANT");
    final FixedDividend dividend = FixedDividend.zero();
    final ParValue parValue = ParValue.of("100");
    final Stock.Preferred stock = Stock.preferred(symbol, dividend, parValue);

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertTrue(stock.isEmpty());
    Assert.assertEquals(0, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    Assert.assertSame(parValue, stock.getParValue());
    Assert.assertSame(GeometricMean.zero(), stock.computeGeometricMean());
    Assert.assertSame(StockPrice.zero(), stock.computeStockPrice());

    StockPreferredTest.computeYieldAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
    StockPreferredTest.computePeRatioAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
  }

  @Test
  public void zeroDividendAndSingleTrade() {
    final StockSymbol symbol = StockSymbol.of("ZDAST");
    final FixedDividend dividend = FixedDividend.zero();
    final ParValue parValue = ParValue.of("100");
    final Stock.Preferred stock = Stock.preferred(symbol, dividend, parValue);
    stock.buy(TradeRequest.of(Quantity.of(1), Price.of("15.45")));

    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(1, stock.size());

    Assert.assertSame(dividend, stock.getDividend());
    AssertUtils.assertEquals("a geometric mean", new BigDecimal("15.45"), stock.computeGeometricMean());
    AssertUtils.assertEquals("a stock", new BigDecimal("15.45"), stock.computeStockPrice());

    StockPreferredTest.computeYieldAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
    StockPreferredTest.computePeRatioAndAssert(stock, TickerPrice.random(), BigDecimal.ZERO);
  }
}
