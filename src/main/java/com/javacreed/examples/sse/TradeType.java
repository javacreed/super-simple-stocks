package com.javacreed.examples.sse;

public enum TradeType {

  BUY("Buy"), SELL("Sell");

  private final String title;

  private TradeType(final String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return title;
  }
}
