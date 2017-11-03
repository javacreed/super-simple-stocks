package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StockPriceTest {

  private static StockPrice computeAndAssert(final Iterable<Trade> trades, final BigDecimal expected) {
    final StockPrice price = StockPrice.compute(trades);
    return AssertUtils.assertEquals("a stock price", expected, price);
  }

  private static LocalDateTime minusMinutes(final long minutes) {
    return LocalDateTime.now().minusMinutes(minutes);
  }

  @Test
  public void test_1() {
    final List<Trade> parameters = new ArrayList<>();
    parameters.add(Trade.buy(TradeParameters.of(StockPriceTest.minusMinutes(14), Quantity.of(1), Price.of("1"))));

    StockPriceTest.computeAndAssert(parameters, BigDecimal.ONE);
  }

  @Test
  public void test_2() {
    final List<Trade> parameters = new ArrayList<>();
    parameters.add(Trade.buy(TradeParameters.of(StockPriceTest.minusMinutes(14), Quantity.of(1), Price.of("10"))));

    StockPriceTest.computeAndAssert(parameters, BigDecimal.TEN);
  }

  /**
   * The values used in this test will throw a non-terminating decimal expansion error if no rounding is used. This test
   * ensures that such error never happens and the proper rounding is applied
   */
  @Test
  public void test_3() {
    final List<Trade> parameters = new ArrayList<>();
    parameters.add(Trade.buy(TradeParameters.of(StockPriceTest.minusMinutes(14), Quantity.of(5), Price.of("11.45"))));
    parameters.add(Trade.sell(TradeParameters.of(StockPriceTest.minusMinutes(14), Quantity.of(4), Price.of("12.50"))));

    StockPriceTest.computeAndAssert(parameters, new BigDecimal("11.916667"));
  }

  @Test
  public void zero() {
    final List<Iterable<Trade>> parameters = new ArrayList<>();
    parameters.add(Collections.emptyList());
    parameters.add(
        Arrays.asList(Trade.buy(TradeParameters.of(StockPriceTest.minusMinutes(15), Quantity.of(1), Price.of("1")))));
    parameters.add(
        Arrays.asList(Trade.buy(TradeParameters.of(StockPriceTest.minusMinutes(20), Quantity.of(1), Price.of("1")))));

    for (final Iterable<Trade> set : parameters) {
      Assert.assertSame(StockPrice.zero(), StockPrice.compute(set));
    }
  }
}
