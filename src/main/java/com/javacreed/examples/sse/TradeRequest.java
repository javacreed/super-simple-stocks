package com.javacreed.examples.sse;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class TradeRequest implements Comparable<TradeRequest> {

  public static TradeRequest of(final LocalDateTime time, final Quantity quantity, final Price price)
      throws NullPointerException {
    Preconditions.checkNotNull(time);
    Preconditions.checkNotNull(quantity);
    Preconditions.checkNotNull(price);
    return new TradeRequest(time, quantity, price);
  }

  public static TradeRequest of(final Quantity quantity, final Price price) throws NullPointerException {
    return TradeRequest.of(LocalDateTime.now(), quantity, price);
  }

  private final LocalDateTime time;
  private final Quantity quantity;
  private final Price price;

  private TradeRequest(final LocalDateTime time, final Quantity quantity, final Price price) {
    this.time = time;
    this.quantity = quantity;
    this.price = price;
  }

  @Override
  public int compareTo(final TradeRequest other) {
    return time.compareTo(other.time);
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }

    if (object != null && getClass() == object.getClass()) {
      final TradeRequest other = (TradeRequest) object;
      return time.equals(other.time) && quantity.equals(other.quantity) && price.equals(other.price);
    }

    return false;
  }

  public Price getPrice() {
    return price;
  }

  public Quantity getQuantity() {
    return quantity;
  }

  public LocalDateTime getTime() {
    return time;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + price.hashCode();
    result = prime * result + quantity.hashCode();
    result = prime * result + time.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "TradeRequest [time=" + time + ", quantity=" + quantity + ", price=" + price + "]";
  }
}
