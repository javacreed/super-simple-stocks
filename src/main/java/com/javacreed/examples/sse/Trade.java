package com.javacreed.examples.sse;

import java.time.LocalDateTime;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class Trade implements Comparable<Trade> {

  public static Trade buy(final LocalDateTime time, final Quantity quantity, final Price price)
      throws NullPointerException {
    Preconditions.checkNotNull(time);
    Preconditions.checkNotNull(quantity);
    Preconditions.checkNotNull(price);
    return new Trade(time, quantity, TradeType.BUY, price);
  }

  public static Trade buy(final Quantity quantity, final Price price) throws NullPointerException {
    return Trade.buy(LocalDateTime.now(), quantity, price);
  }

  public static Trade sell(final LocalDateTime time, final Quantity quantity, final Price price)
      throws NullPointerException {
    Preconditions.checkNotNull(time);
    Preconditions.checkNotNull(quantity);
    Preconditions.checkNotNull(price);
    return new Trade(time, quantity, TradeType.SELL, price);
  }

  public static Trade sell(final Quantity quantity, final Price price) throws NullPointerException {
    return Trade.sell(LocalDateTime.now(), quantity, price);
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
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Trade other = (Trade) obj;
    if (price == null) {
      if (other.price != null) {
        return false;
      }
    } else if (!price.equals(other.price)) {
      return false;
    }
    if (quantity == null) {
      if (other.quantity != null) {
        return false;
      }
    } else if (!quantity.equals(other.quantity)) {
      return false;
    }
    if (time == null) {
      if (other.time != null) {
        return false;
      }
    } else if (!time.equals(other.time)) {
      return false;
    }
    if (type != other.type) {
      return false;
    }
    return true;
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
    result = prime * result + (price == null ? 0 : price.hashCode());
    result = prime * result + (quantity == null ? 0 : quantity.hashCode());
    result = prime * result + (time == null ? 0 : time.hashCode());
    result = prime * result + (type == null ? 0 : type.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return "Trade [time=" + time + ", quantity=" + quantity + ", type=" + type + ", price=" + price + "]";
  }
}
