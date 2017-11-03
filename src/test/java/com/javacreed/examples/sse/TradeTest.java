package com.javacreed.examples.sse;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.examples.sse.Trade.Buy;
import com.javacreed.examples.sse.Trade.Sell;

public class TradeTest {

  @Test
  public void compareDifferentPrice() {
    final LocalDateTime time = LocalDateTime.now();
    final Trade a = Trade.buy(TradeParameters.of(time, Quantity.of(1), Price.of("10.50")));
    final Trade b = Trade.buy(TradeParameters.of(time, Quantity.of(1), Price.of("10.49")));

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());

    /* The comparison is based on time only */
    Assert.assertEquals(0, a.compareTo(b));
    Assert.assertEquals(0, b.compareTo(a));
  }

  @Test
  public void compareDifferentQuantity() {
    final LocalDateTime time = LocalDateTime.now();
    final Trade a = Trade.buy(TradeParameters.of(time, Quantity.of(1), Price.of("10.50")));
    final Trade b = Trade.buy(TradeParameters.of(time, Quantity.of(2), Price.of("10.50")));

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());

    /* The comparison is based on time only */
    Assert.assertEquals(0, a.compareTo(b));
    Assert.assertEquals(0, b.compareTo(a));
  }

  @Test
  public void compareDifferentTime() {
    /* Instance 'b' should come before instance 'a' as it was traded one minute before */
    final Trade a = Trade.buy(TradeParameters.of(Quantity.of(1), Price.of("10.50")));
    final Trade b = Trade
        .buy(TradeParameters.of(LocalDateTime.now().minusMinutes(1), Quantity.of(1), Price.of("10.50")));

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());
    Assert.assertEquals(1, a.compareTo(b));
    Assert.assertEquals(-1, b.compareTo(a));
  }

  @Test
  public void compareDifferentType() {
    final TradeParameters request = TradeParameters.of(Quantity.of(1), Price.of("10.50"));
    final Trade a = Trade.buy(request);
    final Trade b = Trade.sell(request);

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());

    /* The comparison is based on time only */
    Assert.assertEquals(0, a.compareTo(b));
    Assert.assertEquals(0, b.compareTo(a));
  }

  @Test
  public void compareSame() {
    final Trade a = Trade.buy(TradeParameters.of(Quantity.of(1), Price.of("10.50")));

    Assert.assertEquals(a, a);
    Assert.assertEquals(a.hashCode(), a.hashCode());
    Assert.assertEquals(0, a.compareTo(a));
  }

  @Test
  public void compareSimilar() {
    final TradeParameters request = TradeParameters.of(Quantity.of(1), Price.of("10.50"));
    final Trade a = Trade.buy(request);
    final Trade b = Trade.buy(request);

    Assert.assertEquals(a, b);
    Assert.assertEquals(b, a);
    Assert.assertEquals(a.hashCode(), b.hashCode());
    Assert.assertEquals(0, a.compareTo(b));
    Assert.assertEquals(0, b.compareTo(a));
  }

  @Test
  public void compareSomethingElse() {
    final Trade a = Trade.buy(TradeParameters.of(Quantity.of(1), Price.of("10.50")));
    final Object b = new Object();

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());

    /* Make sure that the equals is null safe */
    Assert.assertFalse(a.equals(null));
  }

  @Test
  public void properties() {
    final LocalDateTime time = LocalDateTime.now();
    final Quantity quantity = Quantity.of(1);
    final Price price = Price.of("10.50");

    final Buy a = Trade.buy(TradeParameters.of(time, quantity, price));
    Assert.assertSame(time, a.getTime());
    Assert.assertSame(quantity, a.getQuantity());
    Assert.assertSame(price, a.getPrice());
    Assert.assertNotNull(a.toString());

    final Sell b = Trade.sell(TradeParameters.of(time, quantity, price));
    Assert.assertSame(time, b.getTime());
    Assert.assertSame(quantity, b.getQuantity());
    Assert.assertSame(price, b.getPrice());
    Assert.assertNotNull(b.toString());
  }
}
