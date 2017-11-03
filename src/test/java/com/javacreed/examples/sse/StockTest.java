package com.javacreed.examples.sse;

import java.time.LocalDateTime;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.examples.sse.Trade.Buy;
import com.javacreed.examples.sse.Trade.Sell;

public class StockTest {

  @Test
  public void common() {
    final StockSymbol symbol = StockSymbol.of("CMN");
    final LastDividend dividend = LastDividend.zero();
    final Stock.Common stock = Stock.common(symbol, dividend);

    /* Verify that the stock is empty */
    Assert.assertNotNull(stock);
    Assert.assertSame(symbol, stock.getSymbol());
    Assert.assertTrue(stock.isEmpty());
    Assert.assertEquals(0, stock.size());

    /* Add one trade */
    final LocalDateTime time = LocalDateTime.now();
    final Quantity quantity = Quantity.of(10);
    final Price price = Price.of("15.45");
    final Buy buy = stock.buy(TradeParameters.of(time, quantity, price));
    Assert.assertNotNull(buy);
    Assert.assertSame(time, buy.getTime());
    Assert.assertSame(quantity, buy.getQuantity());
    Assert.assertSame(price, buy.getPrice());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(1, stock.size());
    Assert.assertSame(buy, stock.getTradeAt(0));

    /* Add another trade */
    final Sell sell = stock.sell(TradeParameters.of(time, quantity, price));
    Assert.assertNotNull(sell);
    Assert.assertSame(time, sell.getTime());
    Assert.assertSame(quantity, sell.getQuantity());
    Assert.assertSame(price, sell.getPrice());
    Assert.assertFalse(stock.isEmpty());
    Assert.assertEquals(2, stock.size());
    Assert.assertSame(sell, stock.getTradeAt(1));

    /* Verify the order of the items as these were added */
    final Iterator<Trade> iterator = stock.iterator();
    Assert.assertTrue(iterator.hasNext());
    Assert.assertSame(buy, iterator.next());
    Assert.assertTrue(iterator.hasNext());
    Assert.assertSame(sell, iterator.next());
    Assert.assertFalse(iterator.hasNext());
  }
}
