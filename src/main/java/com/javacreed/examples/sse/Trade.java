package com.javacreed.examples.sse;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

/**
 * TODO: Should we use the {@link TradeParameters} instead the individual fields?
 */
@Immutable
public class Trade implements Comparable<Trade> {

  public static class Buy extends Trade {

    public static Buy of(final TradeParameters parameters) {
      Preconditions.checkNotNull(parameters);
      return new Buy(parameters.getTime(), parameters.getQuantity(), parameters.getPrice());
    }

    private Buy(final LocalDateTime time, final Quantity quantity, final Price price) {
      super(time, quantity, price);
    }
  }

  public static class Sell extends Trade {

    public static Sell of(final TradeParameters parameters) {
      Preconditions.checkNotNull(parameters);
      return new Sell(parameters.getTime(), parameters.getQuantity(), parameters.getPrice());
    }

    private Sell(final LocalDateTime time, final Quantity quantity, final Price price) {
      super(time, quantity, price);
    }
  }

  public static Buy buy(final TradeParameters parameters) throws NullPointerException {
    return Buy.of(parameters);
  }

  public static Sell sell(final TradeParameters parameters) throws NullPointerException {
    return Sell.of(parameters);
  }

  private final LocalDateTime time;
  private final Quantity quantity;
  private final Price price;

  protected Trade(final LocalDateTime time, final Quantity quantity, final Price price) {
    this.time = time;
    this.quantity = quantity;
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
    result = prime * result + getClass().hashCode();
    result = prime * result + price.hashCode();
    result = prime * result + quantity.hashCode();
    result = prime * result + time.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return " Trade [time=" + time + ", quantity=" + quantity + ", type=" + getClass().getName() + ", price=" + price
        + "]";
  }
}
