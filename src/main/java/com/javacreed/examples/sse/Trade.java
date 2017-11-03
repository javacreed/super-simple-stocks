package com.javacreed.examples.sse;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

/**
 * TODO: Should we use the {@link TradeRequest} instead the individual fields?
 */
@Immutable
public class Trade implements Comparable<Trade> {

  public static Trade buy(final TradeRequest request) throws NullPointerException {
    Preconditions.checkNotNull(request);
    return new Trade(request.getTime(), request.getQuantity(), TradeType.BUY, request.getPrice());
  }

  public static Trade sell(final TradeRequest request) throws NullPointerException {
    Preconditions.checkNotNull(request);
    return new Trade(request.getTime(), request.getQuantity(), TradeType.SELL, request.getPrice());
  }

  private final LocalDateTime time;
  private final Quantity quantity;
  private final TradeType type;
  private final Price price;

  private Trade(final LocalDateTime time, final Quantity quantity, final TradeType type, final Price price) {
    this.time = time;
    this.quantity = quantity;
    this.type = type;
    this.price = price;
  }

  @Override
  public int compareTo(final Trade other) {
    return time.compareTo(other.time);
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }

    if (object != null && getClass() == object.getClass()) {
      final Trade other = (Trade) object;
      return type.equals(other.type) && time.equals(other.time) && quantity.equals(other.quantity)
          && price.equals(other.price);
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

  public TradeType getType() {
    return type;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + price.hashCode();
    result = prime * result + quantity.hashCode();
    result = prime * result + time.hashCode();
    result = prime * result + type.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Trade [time=" + time + ", quantity=" + quantity + ", type=" + type + ", price=" + price + "]";
  }
}
