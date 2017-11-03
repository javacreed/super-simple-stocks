package com.javacreed.examples.sse;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

public class TradeParametersTest {

  @Test
  public void compareDifferentPrice() {
    final LocalDateTime time = LocalDateTime.now();
    final TradeParameters a = TradeParameters.of(time, Quantity.of(1), Price.of("10.50"));
    final TradeParameters b = TradeParameters.of(time, Quantity.of(1), Price.of("10.49"));

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
    final TradeParameters a = TradeParameters.of(time, Quantity.of(1), Price.of("10.50"));
    final TradeParameters b = TradeParameters.of(time, Quantity.of(2), Price.of("10.50"));

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
    final TradeParameters a = TradeParameters.of(Quantity.of(1), Price.of("10.50"));
    final TradeParameters b = TradeParameters.of(LocalDateTime.now().minusMinutes(1), Quantity.of(1),
        Price.of("10.50"));

    Assert.assertNotEquals(a, b);
    Assert.assertNotEquals(b, a);
    Assert.assertNotEquals(a.hashCode(), b.hashCode());
    Assert.assertEquals(1, a.compareTo(b));
    Assert.assertEquals(-1, b.compareTo(a));
  }

  @Test
  public void compareSame() {
    final TradeParameters a = TradeParameters.of(Quantity.of(1), Price.of("10.50"));

    Assert.assertSame(a, a);
    Assert.assertEquals(a, a);
    Assert.assertEquals(a.hashCode(), a.hashCode());
    Assert.assertEquals(0, a.compareTo(a));
  }

  @Test
  public void compareSimilar() {
    final LocalDateTime time = LocalDateTime.now();
    final Quantity quantity = Quantity.of(1);
    final Price price = Price.of("10.50");
    final TradeParameters a = TradeParameters.of(time, quantity, price);
    final TradeParameters b = TradeParameters.of(time, quantity, price);

    Assert.assertNotSame(a, b);
    Assert.assertEquals(a, b);
    Assert.assertEquals(b, a);
    Assert.assertEquals(a.hashCode(), b.hashCode());
    Assert.assertEquals(0, a.compareTo(b));
    Assert.assertEquals(0, b.compareTo(a));
  }

  @Test
  public void compareSomethingElse() {
    final TradeParameters a = TradeParameters.of(Quantity.of(1), Price.of("10.50"));
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

    final TradeParameters a = TradeParameters.of(time, quantity, price);
    Assert.assertSame(time, a.getTime());
    Assert.assertSame(quantity, a.getQuantity());
    Assert.assertSame(price, a.getPrice());
    Assert.assertNotNull(a.toString());
  }
}
